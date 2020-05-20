package com.atguigu.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.ReadData;
import com.atguigu.serviceedu.entity.subject.OneSubject;
import com.atguigu.serviceedu.entity.subject.TwoSubject;
import com.atguigu.serviceedu.listener.ExcelListener;
import com.atguigu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-04-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(EduSubjectService eduSubjectService, MultipartFile file) {


        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ReadData.class, new ExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 组装
     *
     * @return
     */

    @Override
    public List<OneSubject> getAllSubject() {


        //获取一级
        QueryWrapper<EduSubject> queryWrapperOne = new QueryWrapper<>();
        queryWrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubject = this.baseMapper.selectList(queryWrapperOne);

        //获取二级
        QueryWrapper<EduSubject> queryWrapperTwo = new QueryWrapper<>();
        queryWrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubject = this.baseMapper.selectList(queryWrapperTwo);

        List<OneSubject> finalOneList = new ArrayList<>();

        for (int i = 0; i < oneSubject.size(); i++) {
            EduSubject eduSubject = oneSubject.get(i);
            OneSubject oneObj = new OneSubject();
            BeanUtils.copyProperties(eduSubject, oneObj);

            ArrayList<TwoSubject> twoSubjectsList = new ArrayList<>();

            for (int m = 0; m < twoSubject.size(); m++) {
                EduSubject two = twoSubject.get(m);

                if (two.getParentId().equals(eduSubject.getId())) {
                    TwoSubject twoObj = new TwoSubject();
                    BeanUtils.copyProperties(two, twoObj);
                    twoSubjectsList.add(twoObj);

                }
            }
            oneObj.setChildren(twoSubjectsList);

            finalOneList.add(oneObj);

        }
        return finalOneList;
    }
}
