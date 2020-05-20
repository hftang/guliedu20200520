package com.atguigu.ucenter.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auther: hftang
 * @Date: 2020/5/6 14:46
 * @Description:
 */
@Configuration
@Component
public class ConstantWxUtils implements InitializingBean {

    @Value("${wx.open.app_id}")
    private String app_id;

    @Value("${wx.open.app_secret}")
    private String app_secret;

    @Value("${wx.open.redirect_url}")
    private String redirect_url;


    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;


    @Override
    public void afterPropertiesSet() throws Exception {

        APP_ID = app_id;
        APP_SECRET = app_secret;
        REDIRECT_URL = redirect_url;

    }
}
