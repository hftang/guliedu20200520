package com.atguigu.serviceedu.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.servicebase.config.GuliException;
import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.mapper.EduVideoMapper;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-04-28
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {

        //删除小节  根据课程id 查询所有视频的id  删除多个视频
        QueryWrapper<EduVideo> wrapVideos = new QueryWrapper<>();
        wrapVideos.eq("course_id", courseId);
        wrapVideos.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapVideos);
        ArrayList<String> arrayList = new ArrayList<>();
        videoList.forEach(item -> {
            String videoSourceId = item.getVideoSourceId();
            if (StringUtils.isNotEmpty(videoSourceId)) {
                arrayList.add(videoSourceId);
            }

        });

        if (!arrayList.isEmpty()) {
            R batchDelete = this.vodClient.batchDelete(arrayList);
            if (batchDelete.getCode().equals("20001")) {
                throw new GuliException(20001, "删除批量视频有问题");
            }
        }
        //删除小节

        QueryWrapper<EduVideo> wapper = new QueryWrapper<>();
        wapper.eq("course_id", courseId);
        baseMapper.delete(wapper);


    }
}
