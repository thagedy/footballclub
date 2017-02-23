package com.thagedy.footballclub;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 *
 *
 *                      _ooOoo_
 *                     o8888888o
 *                     88" . "88
 *                     (| -_- |)
 *                     O\  =  /O
 *                  ____/`---'\____
 *                .'  \\|     |//  `.
 *               /  \\|||  :  |||//  \
 *              /  _||||| -:- |||||-  \
 *              |   | \\\  -  /// |   |
 *              | \_|  ''\---/''  |   |
 *              \  .-\__  `-`  ___/-. /
 *            ___`. .'  /--.--\  `. . __
 *         ."" '<  `.___\_<|>_/___.'  >'"".
 *        | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *        \  \ `-.   \_ __\ /__ _/   .-` /  /
 *  ======`-.____`-.___\_____/___.-`____.-'======
 *                      `=---='
 *   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *            佛祖保佑       永无BUG
 *
 * 工程启动类
 * Created by Kaijia Wei on 2016/9/6.
 */
@SpringBootApplication
@ComponentScan
@EnableConfigurationProperties
public class FootballClubApplication {
    private static Logger logger = LoggerFactory.getLogger(FootballClubApplication.class);

    /**
     * Main Start
     */
    public static void main(String[] args) {
        SpringApplication.run(FootballClubApplication.class, args);
        logger.info("-----------footballclub启动完毕----------");
    }
}

