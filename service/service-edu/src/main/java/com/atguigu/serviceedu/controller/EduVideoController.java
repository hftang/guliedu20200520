package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */

@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {

        this.eduVideoService.save(eduVideo);

        return R.ok().message("添加小节成功");
    }

    //删除小节
    //todo 后面还有视频
    @DeleteMapping("deleVideo/{id}")
    public R deleVideo(@PathVariable String id) {

        //根据小节id查出视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            R r = this.vodClient.deleVideoById(videoSourceId);
            if (r.getCode() == 20001) {
                throw new GuliException(20001, "视频删除失败，熔断器");
            }
        }
        eduVideoService.removeById(id);
        return R.ok().message("删除小节成功");
    }

}

