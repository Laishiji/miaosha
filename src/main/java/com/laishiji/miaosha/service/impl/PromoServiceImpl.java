package com.laishiji.miaosha.service.impl;

import com.laishiji.miaosha.dao.PromoDOMapper;
import com.laishiji.miaosha.dataobject.PromoDO;
import com.laishiji.miaosha.service.PromoService;
import com.laishiji.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service("promoService")
public class PromoServiceImpl implements PromoService {

    @Resource(name = "promoDOMapper")
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        PromoModel promoModel = convertFromDO(promoDO);

        //判断当前时间秒杀活动是否已开始或已结束
        if(promoModel.getStartDate().isAfterNow())//活动开始时间在现在之后
            promoModel.setStatus(1);
        else if(promoModel.getEndDate().isBeforeNow())//活动结束时间在现在之前
            promoModel.setStatus(3);
        else promoModel.setStatus(2);

        return promoModel;
    }

    private PromoModel convertFromDO(PromoDO promoDO) {
        if(promoDO == null) return null;
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoModel;
    }
}
