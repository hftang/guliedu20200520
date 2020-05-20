package com.atguigu.serviceedu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/4/28 13:45
 * @Description: 一级
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    private String parentId;

    List children = new ArrayList<TwoSubject>();


}
