package com.atguigu.serviceedu.service.impl;

import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.entity.chapter.VideoVo;
import com.atguigu.serviceedu.entity.vo.CourseInfoFormVo;
import com.atguigu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterAndVideoByCourseId(String courseId) {

        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = this.baseMapper.selectList(chapterQueryWrapper);

        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = this.eduVideoService.list(videoQueryWrapper);


        ArrayList<ChapterVo> arrayListFinal = new ArrayList<>();

        for (int i = 0; i < eduChapterList.size(); i++) {

            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);


            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (int m = 0; m < videoList.size(); m++) {
                EduVideo eduVideo = videoList.get(m);
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);


            arrayListFinal.add(chapterVo);
        }
        return arrayListFinal;
    }

    /**
     * 删除章节
     *
     * @param chapterId
     * @return 这里是如果章节下有小节 就不删除
     */

    @Override
    public boolean delChapterByChapterId(String chapterId) {

        QueryWrapper<EduVideo> wapper = new QueryWrapper<>();
        wapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wapper);
        if (count > 0) {
            //拒绝删除
            throw new GuliException(20001, "此章节下有小章节，不可以删除");
        } else {
            int i = this.baseMapper.deleteById(chapterId);
            return i > 0;
        }

    }
}
