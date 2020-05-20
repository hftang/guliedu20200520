package com.atguigu.servicebase.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: hftang
 * @Date: 2020/4/22 17:10
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException  {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    private String msg;

}
