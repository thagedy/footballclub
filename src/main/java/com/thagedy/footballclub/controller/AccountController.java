package com.thagedy.footballclub.controller;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.pojo.AdminUser;
import com.thagedy.footballclub.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Kaijia Wei on 2017/2/21.
 */
@RestController
public class AccountController {

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/aj/login")
    @ResponseBody
    public  ClubResult login(@RequestBody AdminUser adminUser, HttpServletRequest request){
        ClubResult login = userInfoService.login(adminUser);
        return  login;
    }


}
