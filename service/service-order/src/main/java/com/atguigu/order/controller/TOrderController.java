package com.atguigu.order.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/eduOrder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService tOrderService;

    //生成订单

    @PostMapping(value = "createOrderNo/{courserId}")
    public R createOrderNo(@PathVariable String courserId, HttpServletRequest request) {

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        String  orderId = this.tOrderService.createOrderNo(courserId, memberId);

        return R.ok().data("orderId", orderId);
    }

    // 根据订单id查询订单详情

    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> queryWapper = new QueryWrapper<>();
        queryWapper.eq("order_no", orderId);
        TOrder order = tOrderService.getOne(queryWapper);
        return R.ok().data("item", order);
    }

    //查询是已购买过
    @GetMapping(value = "isBuyCourse/{memberId}/{CourseId}")
    public boolean isBuyCourse(@PathVariable String memberId, @PathVariable String CourseId) {
        boolean isBuy = tOrderService.isBuyCouser(memberId, CourseId);
        return isBuy;
    }

}

