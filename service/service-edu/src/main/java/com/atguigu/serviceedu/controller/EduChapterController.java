package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.chapter.ChapterVo;
import com.atguigu.serviceedu.service.EduChapterService;
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
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    //根据课程id 找到章节
    @GetMapping(value = "/getChapterAndVideo/{courseId}")
    public R getChapterAndVideoByCourseId(@PathVariable String courseId) {

        List<ChapterVo> chapterVos = this.eduChapterService.getChapterAndVideoByCourseId(courseId);

        return R.ok().data("chapterList", chapterVos);
    }

    //添加章节
    @PostMapping(value = "addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean save = this.eduChapterService.save(eduChapter);
        return R.ok().message("添加chapter成功");
    }


    //修改章节

    //1 先查询章节id
    @GetMapping(value = "getChapter/{chapterId}")
    public R getChapterByCourseId(@PathVariable String chapterId) {
        EduChapter eduChapter = this.eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    //2 再修改
    @PostMapping(value = "updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        boolean save = this.eduChapterService.updateById(eduChapter);
        return R.ok().message("修改chapter成功");
    }

    //删除章节chapter
    @GetMapping(value = "deleChapter/{chapterId}")
    public R delChapter(@PathVariable String chapterId) {

        boolean flag = this.eduChapterService.delChapterByChapterId(chapterId);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }


}

