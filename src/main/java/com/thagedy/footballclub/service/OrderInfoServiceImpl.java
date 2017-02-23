package com.thagedy.footballclub.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.common.pojo.PageResult;
import com.thagedy.footballclub.dao.OrderInfoMapper;
import com.thagedy.footballclub.pojo.OrderInfo;
import com.thagedy.footballclub.pojo.OrderInfoExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thagedy on 2017/2/19.
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Override
    public ClubResult listOrderInfo(int page, int count, String key) {
        PageHelper.startPage(page,count);
        OrderInfoExample payRecordExample = new OrderInfoExample();
        payRecordExample.createCriteria().andParentNameEqualTo(key);
        List<OrderInfo> payRecords = orderInfoMapper.selectByExample(payRecordExample);
        PageInfo<OrderInfo> pageInfo = new PageInfo(payRecords);
        PageResult pageResult = new PageResult();
        pageResult.setData(payRecords);
        pageResult.setTotal(pageInfo.getTotal());
        return ClubResult.ok(pageResult);
    }

    @Override
    public ClubResult saveOrderInfo(OrderInfo payRecord) {
        orderInfoMapper.insert(payRecord);
        return ClubResult.ok();
    }
}
