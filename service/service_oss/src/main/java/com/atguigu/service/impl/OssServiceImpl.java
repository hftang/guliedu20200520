package com.atguigu.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.atguigu.config.OssConfig;
import com.atguigu.service.OssService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 10:48
 * @Description:
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    //获取阿里云存储相关常量
    String endPoint = OssConfig.END_POINT;
    String accessKeyId = OssConfig.ACCESS_KEY_ID;
    String accessKeySecret = OssConfig.ACCESS_KEY_SECRET;
    String bucketName = OssConfig.BUCKET_NAME;


    @Override
    public String uploadFile(MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();

            OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
            InputStream inputStream = file.getInputStream();
            //添加一个uuid 防止名称重复
            filename = UUID.randomUUID().toString().replace("-", "") + filename;

            //添加一个日期 防止 文件夹重复
            String datepath = new DateTime().toString("yyyy/MM/dd");

            filename = datepath + "/" + filename;

            log.info("---->" + filename);

            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();

            // https://qincheng10.oss-cn-beijing.aliyuncs.com/20191119155030gonggonghao.jpg

            String url = "https://" + bucketName + "." + endPoint + "/" + filename;
            return url;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
