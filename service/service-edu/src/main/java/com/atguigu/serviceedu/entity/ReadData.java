package com.atguigu.serviceedu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 19:39
 * @Description:
 */
@Data
public class ReadData {

    //设置列对应的属性
    @ExcelProperty(index = 0)
    private String oneSubjectName;
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
