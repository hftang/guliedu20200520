package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.vod.config.ConstantVodUtils;
import com.atguigu.vod.config.InitVodCilent;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/5/2 12:05
 * @Description:
 */
@Api(value = "上传视频")
@CrossOrigin
@RestController
@RequestMapping(value = "/eduvod/video")

public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频
    @PostMapping(value = "/uploadAlyiVideo")
    public R uploadFile(MultipartFile file) {
        String vid = this.vodService.updateVodFile(file);
        return R.ok().data("videoId", vid);
    }

    //删除视频 阿里端

    @DeleteMapping(value = "/deleVideo/{id}")
    public R deleVideoById(@PathVariable("id") String id) {
        try {
            DefaultAcsClient acsClient = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            deleteVideoRequest.setVideoIds(id);
            acsClient.getAcsResponse(deleteVideoRequest);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();

            throw new GuliException(20001, "删除阿里云视频失败");
        }
    }

    //批量删除多个视频

    @DeleteMapping(value = "/batch/deleteCourse")
    public R batchDelete(@RequestParam("list") List<String> list) {
        try {
            DefaultAcsClient acsClient = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            String vodIds = StringUtils.join(list.toArray(), ",");

            deleteVideoRequest.setVideoIds(vodIds);
            acsClient.getAcsResponse(deleteVideoRequest);
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();

            throw new GuliException(20001, "删除阿里云视频失败");
        }
    }

    //根据视频id vid 获取到播放凭证

    @GetMapping(value = "/getplayAuthByVid/{vid}")
    public R getPlayVideoAuth(@PathVariable String vid) {
        try {
            DefaultAcsClient defaultAcsClient = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest authRequest = new GetVideoPlayAuthRequest();
            authRequest.setVideoId(vid);
            GetVideoPlayAuthResponse response = defaultAcsClient.getAcsResponse(authRequest);

            String playAuth = response.getPlayAuth();

            return R.ok().data("playAuth",playAuth);


        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("获取播放凭证失败");

        }


    }

}
