package com.laishiji.miaosha.service;

import com.laishiji.miaosha.service.model.PromoModel;

public interface PromoService {
    /**
     * 根据商品id获取即将或正在进行的秒杀活动信息，并展示在商品页面
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);
}
