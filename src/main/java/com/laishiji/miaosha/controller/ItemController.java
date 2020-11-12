package com.laishiji.miaosha.controller;

import com.laishiji.miaosha.controller.viewobject.ItemVO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.response.CommonReturnType;
import com.laishiji.miaosha.service.ItemService;
import com.laishiji.miaosha.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", origins = {"*"})//跨域请求
public class ItemController extends GlobalExceptionHandler{

    @Resource(name="itemService")
    private ItemService itemService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 创建商品接口
     * @param title
     * @param description
     * @param price
     * @param stock
     * @param imgUrl
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "description")String description,
                                       @RequestParam(name = "price") BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl) throws BusinessException {

        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);
        ItemVO itemVO = convertVOFromModel(itemModelForReturn);

        return CommonReturnType.create(itemVO);
    }

    private ItemVO convertVOFromModel(ItemModel itemModel) {
        if(itemModel == null) return null;

        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);

        //如果有正在进行的秒杀活动则设置相关活动属性
        if(itemModel.getPromoModel() != null){
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }

    /**
     * 获取商品详情页，使用Redis缓存
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){

        //根据商品的id到redis获取ItemModel,记住ItemModel及其成员变量一定要实现Serializable接口
        ItemModel itemModel = (ItemModel) redisTemplate.opsForValue().get("item_"+id);

        //若redis内不存在对应的ItemModel，则访问下游service
        if(itemModel == null) {
            itemModel = itemService.getItemById(id);
            //设置itemModel到Redis内
            redisTemplate.opsForValue().set("item_"+id,itemModel);
            //设置缓存失效时间
            redisTemplate.expire("item_"+id, 10, TimeUnit.MINUTES);
        }
        ItemVO itemVO = convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

//    /**
//     * 获取商品详情页
//     * @return
//     */
//    @RequestMapping("/get")
//    @ResponseBody
//    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
//        ItemModel itemModel = itemService.getItemById(id);
//        ItemVO itemVO = convertVOFromModel(itemModel);
//        return CommonReturnType.create(itemVO);
//    }

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public CommonReturnType listItem(){

        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList = new ArrayList<>(itemModelList.size());

        for(ItemModel itemModel : itemModelList){
            ItemVO itemVO = convertVOFromModel(itemModel);
            itemVOList.add(itemVO);
        }

        return CommonReturnType.create(itemVOList);
    }
}
