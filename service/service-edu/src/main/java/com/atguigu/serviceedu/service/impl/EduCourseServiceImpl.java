package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduCourseDescription;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.vo.CourseInfoFormVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.entity.vo.CourseQueryVo;
import com.atguigu.serviceedu.entity.vo.CourseWebVo;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;


    @Override
    public String saveCourseInfo(CourseInfoFormVo courseInfo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int insert = this.baseMapper.insert(eduCourse);

        if (insert <= 0) {
            throw new GuliException(20001, "couserInfo插入失败");
        }

        String description = courseInfo.getDescription();
        String id = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(description);

        eduCourseDescriptionService.save(eduCourseDescription);

        return id;
    }

    /**
     * @param courseId
     * @return
     */

    @Override
    public CourseInfoFormVo getCourseInfoByCourseId(String courseId) {

        EduCourse eduCourse = this.baseMapper.selectById(courseId);

        CourseInfoFormVo courseInfoFormVo = new CourseInfoFormVo();
        BeanUtils.copyProperties(eduCourse, courseInfoFormVo);

        //查详情

        EduCourseDescription courseDescription = this.eduCourseDescriptionService.getById(courseId);

        courseInfoFormVo.setDescription(courseDescription.getDescription());

        return courseInfoFormVo;
    }

    //修改
    @Override
    public void updateCourseInfos(CourseInfoFormVo courseInfo) {

        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);

        int update = this.baseMapper.update(eduCourse, null);

        if (update <= 0) {
            throw new GuliException(20001, "修改课程基本信息错误");
        }
        //修改详情

        String description = courseInfo.getDescription();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfo.getId());
        eduCourseDescription.setDescription(description);

        this.eduCourseDescriptionService.update(eduCourseDescription, null);


    }

    /***
     * 获取 最终确认课程详情
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getCourserInfos(String courseId) {

        CoursePublishVo coursePublishVo = this.baseMapper.getPublishInfos(courseId);

        return coursePublishVo;
    }

    //删除课程
    @Override
    public void delCourseById(String courseId) {
        //1 删除小节
        this.eduVideoService.removeVideoByCourseId(courseId);

        //2 删除 章节
        QueryWrapper<EduChapter> chapterWapper = new QueryWrapper<>();
        chapterWapper.eq("course_id", courseId);

        this.eduChapterService.remove(chapterWapper);

        //3 删除 详情
        this.eduCourseDescriptionService.removeById(courseId);

        //4 删除 course
        int i = this.baseMapper.deleteById(courseId);

        if (i <= 0) {
            throw new GuliException(20001, "删除课程失败");
        }


    }

    /**
     * 查询所有分类 和 分页按照条件查询课程
     *
     * @param page
     * @param limit
     * @param courseQueryVo
     * @return
     */

    @Override
    public Map getAllSubjectAndAllCourse(long page, long limit, CourseQueryVo courseQueryVo) {


        Page<EduCourse> pageParam = new Page<>(page, limit);


        //有条件的查询
        QueryWrapper<EduCourse> courseMapper = new QueryWrapper<>();

        String subjectParentId = courseQueryVo.getSubjectParentId();
        if (StringUtils.isNotEmpty(subjectParentId)) {
            courseMapper.eq("subject_parent_id", subjectParentId);
        }
        String subjectId = courseQueryVo.getSubjectId();
        if (StringUtils.isNotEmpty(subjectId)) {
            courseMapper.eq("subject_id", subjectId);
        }

        String buyCountSort = courseQueryVo.getBuyCountSort();
        if (StringUtils.isNotEmpty(buyCountSort)) {
            courseMapper.orderByDesc("buy_count");
        }
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        if (StringUtils.isNotEmpty(gmtCreateSort)) {
            courseMapper.orderByDesc("gmt_create");
        }
        String priceSort = courseQueryVo.getPriceSort();
        if (StringUtils.isNotEmpty(priceSort)) {
            courseMapper.orderByDesc("price");
        }

        this.baseMapper.selectPage(pageParam, courseMapper);


        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//下一页
        boolean hasPrevious = pageParam.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;

    }

    /***
     * 获取课程详情
     * @param courseId
     * @return
     */

    @Override
    public CourseWebVo getBaseCouserInfo(String courseId) {

        CourseWebVo courseWebVo = this.baseMapper.queryBaseCouserInfo(courseId);

        return courseWebVo;
    }
}
