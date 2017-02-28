package com.thagedy.footballclub.service;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.pojo.OrderInfo;

/**
 * Created by thagedy on 2017/2/19.
 */
public interface OrderInfoService {

    ClubResult listOrderInfo(int page,int count,String key);

    ClubResult saveOrderInfo(OrderInfo rderInfo);

    OrderInfo getOrderInfoByOrderNo(String orderNo);

    ClubResult saveOrUpdateOrderInfo(OrderInfo orderInfo);

    ClubResult listByOpenId(int start,int count,String openid);
}
