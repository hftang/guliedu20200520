package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: hftang
 * @Date: 2020/5/2 12:06
 * @Description:
 */
public interface VodService {

    //上传视频 返回视频id

    String updateVodFile(MultipartFile file);
}
