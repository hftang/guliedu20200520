package com.atguigu.order.service;

import com.atguigu.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-05-09
 */
public interface TOrderService extends IService<TOrder> {

    String  createOrderNo(String courserId, String memberId);

    boolean isBuyCouser(String memberId, String courseId);
}
