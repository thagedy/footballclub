package com.thagedy.footballclub.service;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.pojo.AdminUser;

/**
 * Created by thagedy on 2017/2/19.
 */
public interface UserInfoService {
    ClubResult login(AdminUser userInfo);
}
