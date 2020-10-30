package com.laishiji.miaosha.service.impl;

import com.laishiji.miaosha.dao.ItemDOMapper;
import com.laishiji.miaosha.dao.ItemStockDOMapper;
import com.laishiji.miaosha.dataobject.ItemDO;
import com.laishiji.miaosha.dataobject.ItemStockDO;
import com.laishiji.miaosha.error.BusinessException;
import com.laishiji.miaosha.error.EnumBusinessError;
import com.laishiji.miaosha.service.ItemService;
import com.laishiji.miaosha.service.model.ItemModel;
import com.laishiji.miaosha.validator.ValidationResult;
import com.laishiji.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("itemService")
public class ItemServiceImpl implements ItemService {

    @Resource(name = "itemDOMapper")
    private ItemDOMapper itemDOMapper;

    @Resource(name = "itemStockDOMapper")
    private ItemStockDOMapper itemStockDOMapper;

    @Resource(name = "validator")
    private ValidatorImpl validator;

    /**
     * 创建商品
     * @param itemModel
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //1.校验入参
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors())
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());

        //2.转化itemModel -> dataobject
        ItemDO itemDO = convertDOFromModel(itemModel);

        //3.入库
        itemDOMapper.insertSelective(itemDO);

        itemModel.setId(itemDO.getId());//设置id用于关联item_stock表
        ItemStockDO itemStockDO = convertStockDOFromModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        return getItemById(itemModel.getId());
    }

    /**
     * 获取商品列表
     * @return
     */
    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList = new ArrayList<>(itemDOList.size());

        for(ItemDO item : itemDOList){
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(item.getId());
            ItemModel itemModel = convertModelFromDO(item, itemStockDO);
            itemModelList.add(itemModel);
        }

        return  itemModelList;
    }

    /**
     * 通过商品id获取商品信息
     * @param id
     * @return
     */
    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null) return null;
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());

        return convertModelFromDO(itemDO,itemStockDO);
    }




    private ItemModel convertModelFromDO(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();

        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));

        itemModel.setStock(itemStockDO.getStock());

        return  itemModel;
    }

    private ItemDO convertDOFromModel(ItemModel itemModel){
        if (itemModel == null) return null;

        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());//类型不一致，需要手动赋值

        return itemDO;
    }

    private ItemStockDO convertStockDOFromModel(ItemModel itemModel){
        if (itemModel == null) return null;

        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());

        return  itemStockDO;
    }
}
