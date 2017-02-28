package com.thagedy.footballclub.controller;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.pojo.OrderInfo;
import com.thagedy.footballclub.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Kaijia Wei on 2017/2/21.
 */
@RestController
@RequestMapping("/aj/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;


    @PostMapping(value = "/save")
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

    @GetMapping(value = "/list")
    public ClubResult listPayRecord(int page,int count,String key){
        ClubResult clubResult = orderInfoService.listOrderInfo(1, 1000, key);
        return clubResult;
    }
}
