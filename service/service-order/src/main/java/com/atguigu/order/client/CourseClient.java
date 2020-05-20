package com.atguigu.order.client;

import com.atguigu.commonutils.entity.CourseInfoForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Auther: hftang
 * @Date: 2020/5/9 16:42
 * @Description:
 */
@Component
@FeignClient(name = "service-edu")
public interface CourseClient {

    //根据课程id获得课程信息
    @GetMapping(value = "/eduservice/edu-course/getCourseById/{courseId}")
    public CourseInfoForm getCourseInfoById(@PathVariable("courseId") String courseId);
}
