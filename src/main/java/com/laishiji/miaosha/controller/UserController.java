package com.laishiji.miaosha.controller;

import com.alibaba.druid.util.StringUtils;
import com.laishiji.miaosha.controller.viewobject.UserVO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.response.CommonReturnType;
import com.laishiji.miaosha.service.UserService;
import com.laishiji.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", origins = {"*"})//跨域请求
public class UserController extends GlobalExceptionHandler {

    @Resource(name="userService")
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录验证接口
     * @param telphone
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password,
                                  HttpServletRequest request) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //1.参数校验
        if(StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //2.校验登录是否合法
        UserModel userModel = userService.validateLogin(telphone,encodeByMD5(password));

        //3.修改为使用token的方式，将token和用户模型一起存入redis中
        //生成登录凭证token
        String uuidToken = UUID.randomUUID().toString().replace("-","");
        //建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(uuidToken, userModel);
        //设置超时时间
        redisTemplate.expire(uuidToken, 1, TimeUnit.HOURS);

        return CommonReturnType.create(uuidToken);


//        //3.将登录凭证和用户模型加入到用户登录成功的session内
//        request.getSession().setAttribute("IS_LOGIN",true);
//        request.getSession().setAttribute("LOGIN_USER", userModel);
//        return CommonReturnType.create(uuidToken);
    }

    /**
     * 用户注册接口
     * @param telphone
     * @param otpCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @param request
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")String gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password,
                                     HttpServletRequest request) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //1.验证手机号与对应的otpCode是否符合
        String inSessionOtpCode = (String) request.getSession().getAttribute(telphone);
        if(!StringUtils.equals(otpCode, inSessionOtpCode)){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }
        //2.符合则进入注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setTelphone(telphone);
        userModel.setAge(age);
        userModel.setGender(new Byte(String.valueOf(gender=="男" ? 1 : 2)));
        userModel.setRegisterMode("by phone");
        userModel.setEncryptPassword(encodeByMD5(password));
        userService.register(userModel);

        return CommonReturnType.create(null);
    }

    /**
     * 加密密码
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String encodeByMD5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String encryptPassword = base64Encoder.encode(md5.digest(password.getBytes("utf-8")));
        return encryptPassword;
    }

    /**
     * 用户获取otp短信的接口
     * @param telphone
     * @param request
     * @return
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone, HttpServletRequest request){
        //1.随机生成6位otp验证码
        Random random = new Random();
        Integer randomInt = 100000 + random.nextInt(899999);
        String otpCode = randomInt.toString();
        //2.将otp验证码同对应的手机号关联, 企业级应用中常将{telphone: otpCode}存入Redis服务器中
        //此处暂时使用HttpSession的方式绑定otpCode和telphone
        request.getSession().setAttribute(telphone, otpCode);
        //3.将otp验证码通过短信通道发送给用户，本项目省略
        System.out.println("手机号： "+telphone+" 验证码： "+otpCode);
        return CommonReturnType.create(null);

    }

    /**
     * 获取用户信息的接口
     * @param id
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam("id") Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //用户不存在则抛出用户不存在异常
        if(userModel == null) {
            //int i = 1/0; //测试未知错误
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST_ERROR);
        }

        //将核心领域模型用户对象转化为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);
        //归一化处理，返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

}
