package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 16:55
 * @Description:
 */
@Data
public class demo {

    //index 是对应excel 的字段
    @ExcelProperty(value = "学成id", index = 0)
    private String no_id;
    @ExcelProperty(value = "学生名字", index = 1)
    private String name;
}
