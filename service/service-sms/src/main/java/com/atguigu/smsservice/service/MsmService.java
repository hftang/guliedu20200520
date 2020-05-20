package com.atguigu.smsservice.service;

import java.util.Map;

/**
 * @Auther: hftang
 * @Date: 2020/5/5 14:06
 * @Description:
 */
public interface MsmService {
    boolean sendSms(Map<String,Object>  params, String phone);
}
