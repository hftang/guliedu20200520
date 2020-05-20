package com.atguigu.smsservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.smsservice.service.MsmService;
import com.atguigu.smsservice.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: hftang
 * @Date: 2020/5/5 14:03
 * @Description:
 */
@RestController
@RequestMapping(value = "/edumsm/msm")
@CrossOrigin
@Slf4j
public class SmsController {

    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /***
     * 发送短信验证码
     * @param phone
     * @return
     */
    @GetMapping("sendSms/{phone}")
    public R sendSms(@PathVariable String phone) {

        //先从redis中获取值，
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }


        code = RandomUtil.getFourBitRandom();

        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        log.info("生成的短信验证码是：" + code);


        boolean isSend = this.msmService.sendSms(param, phone);

        if (isSend) {
            //把验证码保存到redis
            this.redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
