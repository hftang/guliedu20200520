package com.atguigu.order.service.impl;

import com.atguigu.commonutils.OrderNoUtil;
import com.atguigu.commonutils.entity.CourseInfoForm;
import com.atguigu.commonutils.entity.MemberOrder;
import com.atguigu.order.client.CourseClient;
import com.atguigu.order.client.UcenterClient;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.mapper.TOrderMapper;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-05-09
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private TOrderService tOrderService;

    @Override
    public String createOrderNo(String courserId, String memberId) {


        //1 得到课程信息
        CourseInfoForm courseDto = courseClient.getCourseInfoById(courserId);

        //2 得到用户信息
        MemberOrder ucenterMember = ucenterClient.getUserInfoOrder(memberId);


        TOrder order = new TOrder();

        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courserId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        String orderNo = order.getOrderNo();
        return orderNo;
    }

    @Override
    public boolean isBuyCouser(String memberId, String courseId) {

        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);

        Integer integer = this.baseMapper.selectCount(queryWrapper);

        if (integer == 1) {
            return true;
        } else {
            return false;
        }
    }


}
