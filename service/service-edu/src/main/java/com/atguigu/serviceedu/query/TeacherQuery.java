package com.atguigu.serviceedu.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: hftang
 * @Date: 2020/4/22 12:08
 * @Description:
 */
@ApiModel(value = "Teacher查询对象", description = "讲师查询对象封装")
@Data
public class TeacherQuery implements Serializable {

    @ApiModelProperty(value = "教师名称")
    private String name;
    @ApiModelProperty(value = "教师级别 头衔 1高级讲师 2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "开始查询时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
    @ApiModelProperty(value = "结束查询时间", example = "2020-04-21 12:12:22")
    private String end;


}
