package com.atguigu.controller;

import com.atguigu.commonutils.R;
import com.atguigu.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 10:47
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/eduoss/fileoss")
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping(value = "/uploadfile")
    public R uploadFile(MultipartFile file){
        String url = this.ossService.uploadFile(file);
        return R.ok().data("url",url);
    }
}
