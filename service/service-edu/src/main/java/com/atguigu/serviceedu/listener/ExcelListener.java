package com.atguigu.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.ReadData;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 19:45
 * @Description:
 */
public class ExcelListener extends AnalysisEventListener<ReadData> {

    private EduSubjectService eduSubjectService;

    public ExcelListener() {
    }

    public ExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(ReadData readData, AnalysisContext analysisContext) {

        if (readData == null) {
            throw new GuliException(20001, "excel中没有数据");
        }
        EduSubject eduSubject = exitsOne(eduSubjectService, readData);
        if (eduSubject == null) {
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(readData.getOneSubjectName());
            this.eduSubjectService.save(eduSubject);
        }
        EduSubject exitsTwo = exitsTwo(eduSubjectService, readData, eduSubject.getId());
        if (exitsTwo == null) {
            exitsTwo = new EduSubject();
            exitsTwo.setTitle(readData.getTwoSubjectName());
            exitsTwo.setParentId(eduSubject.getId());
            this.eduSubjectService.save(exitsTwo);
        }

    }

    //判断二级是否存在
    private EduSubject exitsTwo(EduSubjectService eduSubjectService, ReadData readData, String id) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", readData.getTwoSubjectName());
        queryWrapper.eq("parent_id", id);
        EduSubject one = this.eduSubjectService.getOne(queryWrapper);
        return one;

    }

    //判断第一层是否重复
    private EduSubject exitsOne(EduSubjectService eduSubjectService, ReadData readData) {
        String sname = readData.getOneSubjectName();
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        wrapper.eq("title", sname);
        EduSubject one = this.eduSubjectService.getOne(wrapper);
        return one;
    }


    //判断第二次是否有重复

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
