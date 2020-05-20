package com.atguigu.statistics.scheduled;

import com.atguigu.commonutils.DateUtil;
import com.atguigu.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Auther: hftang
 * @Date: 2020/5/11 15:06
 * @Description: 定时任务
 */
@Component
public class StaScheduled {
    @Autowired
    private DailyService dailyService;

    //每天的1点
    @Scheduled(cron = "0 0 1 * * ?")
    public void taskStaMethod() {
        Date date = DateUtil.addDays(new Date(), -1);
        String formatDate = DateUtil.formatDate(date);
        dailyService.createRegistCountByDay(formatDate);
    }
}
