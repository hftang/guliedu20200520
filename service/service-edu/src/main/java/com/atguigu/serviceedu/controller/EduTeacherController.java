package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.query.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-04-22
 */
@Api(description = "讲师管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-teacher")
@Slf4j
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation(value = "查出讲师列表")
    @GetMapping
    public R list() {

        try {
//            int a = 10 / 0;
            log.info("措辞llll");
        } catch (Exception e) {
            throw new GuliException(20001, "出现自定义异常");
        }


        List<EduTeacher> list = this.eduTeacherService.list(null);
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "根据id删除对应的讲师")
    @DeleteMapping(value = "/{id}")
    public R dele(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {
        boolean b = this.eduTeacherService.removeById(id);
        log.info("b::" + b);
        return R.ok();
    }
//
//    @ApiOperation(value = "讲师分页查询")
//    @GetMapping(value = "/{page}/{limit}")
//    public R getList(@ApiParam(name = "page", value = "当前页码", required = false) @PathVariable Long page,
//                     @ApiParam(name = "limit", value = "每页的记录数", required = false) @PathVariable Long limit) {
//        Page<EduTeacher> page1 = new Page<>(page, limit);
//        this.eduTeacherService.page(page1, null);
//        List<EduTeacher> records = page1.getRecords();
//        long total = page1.getTotal();
//        return R.ok().data("total", total).data("rows", records);
//
//    }

    @ApiOperation(value = "分页条件查询")
    @PostMapping(value = "/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
            @RequestBody TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        this.eduTeacherService.pageQuery(pageParam, teacherQuery);

        List<EduTeacher> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }


    //新增讲师
    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = this.eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //查询讲师
    @ApiOperation("根据id查询讲师")
    @GetMapping("{id}")
    public R findById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable("id") String id) {

        EduTeacher eduTeacher = this.eduTeacherService.getById(id);

        return R.ok().data("item", eduTeacher);
    }

    //根据id修改讲师
    @ApiOperation("根据id修改讲师内容")
    @PutMapping("{id}")
    public R updateById(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable("id") String id,
                        @ApiParam(name = "讲师对象", value = "讲师对象", required = true) @RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        this.eduTeacherService.updateById(eduTeacher);
        return R.ok();
    }

    @PostMapping(value = "/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean update = this.eduTeacherService.saveOrUpdate(eduTeacher);
        if (update) {
            return R.ok().message("修改成功");
        } else {
            return R.ok().message("修改失败");
        }
    }


}

