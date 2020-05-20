package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: hftang
 * @Date: 2020/5/7 11:34
 * @Description:
 */
@RestController
@RequestMapping(value = "/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //分页查询讲师列表
    @PostMapping("teacherlist/{page}/{limit}")
    public R queryPageTeacher(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> pageInfo = new Page<>(page, limit);
        Map map = eduTeacherService.pageQueryTeachers(pageInfo);

        return R.ok().data(map);
    }

    //根据teacherId 获取讲师详情 和 course
    @GetMapping("teacherinfoandcourse/{teacherid}")
    public R getTeacherInfoAndCoursesByTeacherId(@PathVariable String teacherid) {

        Map map = this.eduTeacherService.getTeacherInfoAndCoursesByTeacherId(teacherid);

        return R.ok().data(map);

    }


}
