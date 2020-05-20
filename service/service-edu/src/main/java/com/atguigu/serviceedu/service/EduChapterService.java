package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.entity.vo.CourseInfoFormVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> getChapterAndVideoByCourseId(String courseId);

    //删除章节
    boolean delChapterByChapterId(String chapterId);
}
