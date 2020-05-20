package com.atguigu.order.service;

import com.atguigu.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-05-09
 */
public interface TPayLogService extends IService<TPayLog> {

    Map createErCode(String orderNo);

    Map queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);

}
