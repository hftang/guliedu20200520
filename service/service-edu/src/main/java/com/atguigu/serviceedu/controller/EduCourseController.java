package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.entity.CourseInfoForm;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoFormVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("saveCourseInfo")
    public R saveCourseInfo(@RequestBody CourseInfoFormVo courseInfo) {

        String courseId = this.eduCourseService.saveCourseInfo(courseInfo);

        return R.ok().message("保存课程基本信息成功").data("courseId", courseId);
    }

    //根据课程id 查找课程详情
    @GetMapping(value = "getCourseInfo/{courseId}")
    public R getCourseInfoByCourseId(@PathVariable String courseId) {
        CourseInfoFormVo courseInfoFormVo = this.eduCourseService.getCourseInfoByCourseId(courseId);
        return R.ok().data("courseInfo", courseInfoFormVo);
    }

    //修改课程详情
    @PostMapping(value = "update/courserInfo")
    public R updateCourseInfos(@RequestBody CourseInfoFormVo courseInfo) {
        this.eduCourseService.updateCourseInfos(courseInfo);
        return R.ok();
    }

    //最后确认课程信息
    @GetMapping(value = "getCourseInfos/{courseId}")
    public R getPushCourseInfo(@PathVariable String courseId) {

        CoursePublishVo coursePublishVo = this.eduCourseService.getCourserInfos(courseId);

        return R.ok().data("coursePublish", coursePublishVo);

    }

    //发布课程

    @GetMapping(value = "publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        this.eduCourseService.updateById(eduCourse);

        return R.ok().message("课程发布成功");

    }
    //获取所有课程

    @GetMapping(value = "courselist")
    public R getCourseList() {
        List<EduCourse> list = this.eduCourseService.list(null);

        return R.ok().data("list", list);

    }

    //根据课程id 删除课程
    @DeleteMapping("deleteCourseByCourseId/{courseId}")
    public R deleCourseByCourseId(@PathVariable String courseId) {
        this.eduCourseService.delCourseById(courseId);
        return R.ok().message("删除成功");
    }

    //根据课程id获得课程信息
    @GetMapping(value = "getCourseById/{courseId}")
    public CourseInfoForm getCourseInfoById(@PathVariable("courseId") String courseId) {

        CourseInfoFormVo courseInfoByCourseId = this.eduCourseService.getCourseInfoByCourseId(courseId);

        CourseInfoForm courseInfoForm = new CourseInfoForm();

        BeanUtils.copyProperties(courseInfoByCourseId, courseInfoForm);

        return courseInfoForm;
    }


}

