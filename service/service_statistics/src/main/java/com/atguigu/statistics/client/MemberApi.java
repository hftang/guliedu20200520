package com.atguigu.statistics.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: hftang
 * @Date: 2020/5/11 10:31
 * @Description:
 */
@FeignClient("service-ucenter")
@Component
public interface MemberApi {

    //根据某天获取注册人数
    @PostMapping("/educenter/member/getRegisterCountByDay/{day}")
    public R queryRegistCountByDay(@PathVariable("day") String day);
}
