package com.atguigu.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: hftang
 * @Date: 2020/5/10 19:42
 * @Description:
 */
@Component
@FeignClient("service-order")
public interface OrderClient {

    //查询是已购买过
    @GetMapping(value = "/eduOrder/order/isBuyCourse/{memberId}/{CourseId}")
    public boolean isBuyCourse(@PathVariable("memberId") String memberId, @PathVariable("CourseId") String CourseId);
}
