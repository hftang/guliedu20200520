package com.atguigu.serviceedu.entity.subject;

import lombok.Data;

/**
 * @Auther: hftang
 * @Date: 2020/4/28 13:47
 * @Description: 二级
 */
@Data
public class TwoSubject {

    private String id;
    private String title;
    private String parentId;

}
