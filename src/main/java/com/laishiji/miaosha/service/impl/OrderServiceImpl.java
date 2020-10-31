package com.laishiji.miaosha.service.impl;

import com.laishiji.miaosha.dao.OrderDOMapper;
import com.laishiji.miaosha.dao.SequenceDOMapper;
import com.laishiji.miaosha.dataobject.OrderDO;
import com.laishiji.miaosha.dataobject.SequenceDO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.service.ItemService;
import com.laishiji.miaosha.service.OrderService;
import com.laishiji.miaosha.service.UserService;
import com.laishiji.miaosha.service.model.ItemModel;
import com.laishiji.miaosha.service.model.OrderModel;
import com.laishiji.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource(name = "orderDOMapper")
    private OrderDOMapper orderDOMapper;

    @Resource(name = "itemService")
    private ItemService itemService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "sequenceDOMapper")
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {

        //1.校验下单状态：用户是否合法？商品是否存在？购买数量是否正确？
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");

        if(amount <= 0 || amount >= 99)
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,"数量信息不正确");


        //2.落单成功则减库存
        boolean result = itemService.decreaseStock(itemId,amount);
        if(!result)
            throw new BusinessException(EnumBusinessError.STOCK_NOT_ENOUGH);


        //3.订单入库
        //3.1生成订单号
        String orderId = generateOrderId();
        //3.2创建orderModel并赋值
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        OrderDO orderDO = convertFromOrderModel(orderModel);

        //3.3订单入库
        orderDOMapper.insertSelective(orderDO);
        //3.4更新销量
        itemService.increaseSales(itemId,amount);

        //4.返回前端

        return orderModel;
    }

    /**
     * 生成订单号
     * 生成规则：一共16位，前8位为年月日，中间6位为自增序列，最后2位为分库分表位
     * @return 订单号
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)//如果createOrder执行失败，createOrder事务回滚，
    //则该方法不在createOrder的事务中，并且generateOrderId的事务提交
    String generateOrderId() {
        StringBuilder stringBuilder = new StringBuilder();
        //前8位
        LocalDateTime now = LocalDateTime.now();
        stringBuilder.append(now.format(DateTimeFormatter.ISO_DATE).replace("-",""));

        //中间6位,自增序列
        //获取当前sequence
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        int sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);

        String sequenceString = String.valueOf(sequence);
        for (int i = 0; i < 6 - sequenceString.length(); i++) {
            stringBuilder.append(0);//不足6位用0填充
        }
        stringBuilder.append(sequenceString);

        //最后2位,暂时不考虑
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null) return null;
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
