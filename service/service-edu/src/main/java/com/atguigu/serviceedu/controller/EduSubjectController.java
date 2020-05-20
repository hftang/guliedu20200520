package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.serviceedu.entity.ReadData;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加一个excel 然后存入数据库

    @PostMapping(value = "/upload/excel")
    public R uploadExcelFile(MultipartFile file) {
        this.eduSubjectService.addSubject(eduSubjectService, file);
        return R.ok().message("添加excel成功");
    }

    @GetMapping(value = "getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = this.eduSubjectService.getAllSubject();
        return R.ok().data("list",list);
    }

}

