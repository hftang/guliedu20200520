package com.atguigu.serviceedu.client;

import com.atguigu.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/5/3 13:52
 * @Description:
 */
@Component
@FeignClient(value = "service-vod", fallback = VodClientHystrix.class)
public interface VodClient {

    /***
     * 根据视频id删除视频
     * @param id
     * @return
     */

    @DeleteMapping(value = "/eduvod/video/deleVideo/{id}")
    public R deleVideoById(@PathVariable("id") String id);


    //批量删除多个视频
    @DeleteMapping(value = "/eduvod/video/batch/deleteCourse")
    public R batchDelete(@RequestParam("list") List<String> list);
}
