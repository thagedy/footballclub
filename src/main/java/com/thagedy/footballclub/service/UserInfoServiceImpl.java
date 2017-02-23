package com.thagedy.footballclub.service;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.dao.AdminUserMapper;
import com.thagedy.footballclub.pojo.AdminUser;
import com.thagedy.footballclub.pojo.AdminUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thagedy on 2017/2/19.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private AdminUserMapper userInfoMapper;
    @Override
    public ClubResult login(AdminUser userInfo) {
        AdminUserExample userInfoExample = new AdminUserExample();
        userInfoExample.createCriteria().andUseremailEqualTo(userInfo.getUseremail());
        List<AdminUser> userInfos = userInfoMapper.selectByExample(userInfoExample);
        if (userInfos != null && userInfos.size()>0){
            AdminUser userInfo1 = userInfos.get(0);
            if (userInfo1.getPassword().equals(userInfo.getPassword())){
                return ClubResult.ok();
            }
        }
        return ClubResult.build(10002,"用户名或密码不对",null);
    }
}
