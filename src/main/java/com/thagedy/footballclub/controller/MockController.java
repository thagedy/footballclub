package com.thagedy.footballclub.controller;

import com.thagedy.footballclub.common.pojo.ClubResult;
import com.thagedy.footballclub.common.util.WxConfig;
import com.thagedy.footballclub.service.OrderInfoService;
import com.thagedy.footballclub.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static com.thagedy.footballclub.common.util.WxConfig.baseUrl;

/**
 * Created by thagedy on 2017/2/18.
 */
@Controller
public class MockController {



    @Autowired
    private OrderInfoService payRecordService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/mock")
    public ClubResult mock(){
        return  ClubResult.ok("HELLO WORLD!!!");
    }






}
