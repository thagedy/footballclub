package com.thagedy.footballclub.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.common.pojo.PageResult;
import com.thagedy.footballclub.dao.OrderInfoMapper;
import com.thagedy.footballclub.pojo.OrderInfo;
import com.thagedy.footballclub.pojo.OrderInfoExample;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thagedy on 2017/2/19.
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;



    @Override
    public ClubResult saveOrderInfo(OrderInfo payRecord) {
        orderInfoMapper.insert(payRecord);
        return ClubResult.ok();
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(orderInfoExample);
        if (orderInfos!=null&&orderInfos.size()>0){
            return orderInfos.get(0);
        }
        return null;
    }

    @Override
    public ClubResult saveOrUpdateOrderInfo(OrderInfo orderInfo) {
        if (orderInfo==null|| StringUtils.isBlank(orderInfo.getOrderNo())){
            return ClubResult.error("订单为空或订单号为空");
        }
        OrderInfo orderInfoByOrderNo = getOrderInfoByOrderNo(orderInfo.getOrderNo());
        if (orderInfo==null){
            orderInfoMapper.insert(orderInfo);
        }else{
            OrderInfoExample orderInfoExample = new OrderInfoExample();
            orderInfoExample.createCriteria().andOrderNoEqualTo(orderInfo.getOrderNo());
            orderInfoMapper.updateByExampleSelective(orderInfo,orderInfoExample);
        }
        return null;
    }

    @Override
    public ClubResult listOrderInfo(int page, int count, String key) {
        List<OrderInfo> payRecords = null;
        PageHelper.startPage(page,count);
        if (StringUtils.isBlank(key)){
            orderInfoMapper.selectByKey(key);
        }else {
            OrderInfoExample payRecordExample = new OrderInfoExample();
            payRecordExample.createCriteria();
            payRecords = orderInfoMapper.selectByExample(payRecordExample);
        }
        PageInfo<OrderInfo> pageInfo = new PageInfo(payRecords);
        PageResult pageResult = new PageResult();
        pageResult.setData(payRecords);
        pageResult.setTotal(pageInfo.getTotal());
        return ClubResult.ok(pageResult);
    }


    @Override
    public ClubResult listByOpenId(int start,int count,String openid) {
        PageHelper.startPage(start,count);
        OrderInfoExample payRecordExample = new OrderInfoExample();
        payRecordExample.createCriteria().andWxAppidEqualTo(openid);
        List<OrderInfo> orderInfos = orderInfoMapper.selectByExample(payRecordExample);
        if (orderInfos==null){
            orderInfos = new ArrayList<>();
        }
        PageInfo<OrderInfo> pageInfo = new PageInfo(orderInfos);
        PageResult pageResult = new PageResult();
        pageResult.setData(orderInfos);
        pageResult.setTotal(pageInfo.getTotal());
        return ClubResult.ok(pageResult);
    }
}
