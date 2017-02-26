package com.thagedy.footballclub.controller;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.pojo.OrderInfo;
import com.thagedy.footballclub.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Kaijia Wei on 2017/2/21.
 */
@Controller
@RequestMapping("/aj/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    @RequestMapping(value = "/save" , method = RequestMethod.POST)
    public ClubResult insertPayRecord(){
        OrderInfo payRecord = new OrderInfo();
        payRecord.setPayFee(new BigDecimal(10000));
        payRecord.setCourseType((byte) 1);
        payRecord.setParentName("张三");
        payRecord.setStudenName("李四");
        payRecord.setCtime(new Date());
        ClubResult clubResult = orderInfoService.saveOrderInfo(payRecord);
        return clubResult;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ClubResult listPayRecord(){
        ClubResult clubResult = orderInfoService.listOrderInfo(1, 10, null);
        return clubResult;
    }
}
