package com.atguigu.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 10:48
 * @Description:
 */
public interface OssService {

    String uploadFile(MultipartFile file);
}
