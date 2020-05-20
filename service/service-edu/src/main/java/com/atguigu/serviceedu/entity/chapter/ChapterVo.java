package com.atguigu.serviceedu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/4/29 12:02
 * @Description:
 */
@Data
public class ChapterVo {
    private String id;
    private String title;

    List<VideoVo> children = new ArrayList<>();
}
