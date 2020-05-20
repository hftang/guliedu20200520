package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/5/4 16:50
 * @Description:
 */

@RequestMapping(value = "/eduservice/indexfront")
@RestController
@CrossOrigin
public class FrontIndexController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;


    //查询8个热门课程
    @GetMapping("index")
    public R index() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<EduCourse>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> hotCourse = eduCourseService.list(wrapper);

        //查询4个热门老师
        QueryWrapper<EduTeacher> wrapper02 = new QueryWrapper<EduTeacher>();
        wrapper02.orderByDesc("id");
        wrapper02.last("limit 4");
        List<EduTeacher> hotTeacher = eduTeacherService.list(wrapper02);


        return R.ok().data("hotCourse", hotCourse).data("hotTeacher", hotTeacher);
    }





}
