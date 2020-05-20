package com.atguigu.order.controller;


import com.atguigu.commonutils.R;
import com.atguigu.order.service.TPayLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/eduOrder/payLog")
@CrossOrigin
@Slf4j
public class TPayLogController {
    @Autowired
    private TPayLogService tPayLogService;

    //生成二维码

    @GetMapping("/createPayCode/{orderNo}")
    public R createCode(@PathVariable String orderNo) {
        Map map = this.tPayLogService.createErCode(orderNo);
        log.info("生成二维码" + map.toString());
        return R.ok().data(map);
    }

    //检查订单状态

    @GetMapping(value = "/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String, String> map = this.tPayLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错");
        }

        if (map.get("trade_state").equals("SUCCESS")) {
            log.info("检查订单状态" + map.get("trade_state"));
            //更新订单状态
            //更改订单状态
            tPayLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");

        }
        //前端相应过滤器 25000被拦截
        return R.ok().code(25000).message("支付中。。。。。");
    }




}

