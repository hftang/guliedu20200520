package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoFormVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
public interface EduCourseService extends IService<EduCourse> {

    String  saveCourseInfo(CourseInfoFormVo courseInfo);

    CourseInfoFormVo getCourseInfoByCourseId(String courseId);

    void updateCourseInfos(CourseInfoFormVo courseInfo);

    CoursePublishVo getCourserInfos(String courseId);

    void delCourseById(String courseId);

    Map getAllSubjectAndAllCourse(long page, long limit, CourseQueryVo courseQueryVo);

    CourseWebVo getBaseCouserInfo(String courseId);
}
