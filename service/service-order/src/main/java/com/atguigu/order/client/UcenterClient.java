package com.atguigu.order.client;

import com.atguigu.commonutils.entity.MemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Auther: hftang
 * @Date: 2020/5/9 16:42
 * @Description:
 */
@Component
@FeignClient( "service-ucenter")
public interface UcenterClient {

    //根据用户id获取用户信息
    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public MemberOrder getUserInfoOrder(@PathVariable("id") String id);

}
