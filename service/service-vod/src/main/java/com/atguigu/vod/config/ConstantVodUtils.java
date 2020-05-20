package com.atguigu.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: hftang
 * @Date: 2020/5/2 12:13
 * @Description:
 */
@Component
public class ConstantVodUtils implements InitializingBean {

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;



    @Override
    public void afterPropertiesSet() throws Exception {

        this.ACCESS_KEY_ID=keyid;
        this.ACCESS_KEY_SECRET=keysecret;
    }
}
