package com.atguigu.statistics.controller;


import com.atguigu.commonutils.R;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author hftang
 * @since 2020-05-11
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {
    @Autowired
    private DailyService dailyService;

    //查询某日的注册人数

    @PostMapping(value = "/createRegistCount/{day}")
    public R createRegistCountByDay(@PathVariable String day) {
        this.dailyService.createRegistCountByDay(day);
        return R.ok();
    }

    //获取echart数据
    @GetMapping("getEchartsDatas/{type}/{begin}/{end}")
    public R getEchartsDatas(@PathVariable String type,
                             @PathVariable String begin,
                             @PathVariable String end) {

        Map<String, Object> map = this.dailyService.getEchatsDatas(type, begin, end);

        return R.ok().data("map", map);

    }


}

