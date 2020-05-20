package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.client.OrderClient;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Auther: hftang
 * @Date: 2020/5/8 16:23
 * @Description: 前端课程
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/couserfront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    //获取所有分类 和 所有课程
    @PostMapping("/getallsubjectandcourse/{page}/{limit}")
    public R getAllSubjectAndAllCourse(@PathVariable long page,
                                       @PathVariable long limit,
                                       @RequestBody CourseQueryVo courseQueryVo) {
        Map map = this.eduCourseService.getAllSubjectAndAllCourse(page, limit, courseQueryVo);
        return R.ok().data(map);
    }

    //获取 课程详情 和 chapter列表
    @GetMapping("/getBaseCourseInfoAndChapterList/{courseId}")
    public R getCourseInfoAndChapterList(@PathVariable String courseId, HttpServletRequest request) {

        //先获取chapterlist
        List<ChapterVo> chapterVos = this.chapterService.getChapterAndVideoByCourseId(courseId);
        //获取基础
        CourseWebVo courseWebVo = eduCourseService.getBaseCouserInfo(courseId);

        String memberId = JwtUtils.getMemberIdByJwtToken(request);

        //查询下此课程是否已购买
        boolean buyCourse = orderClient.isBuyCourse(memberId, courseId);



        return R.ok().data("chapterList", chapterVos).data("courseWebVo", courseWebVo).data("isBuy",buyCourse);
    }


}
