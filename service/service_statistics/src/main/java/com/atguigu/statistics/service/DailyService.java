package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author hftang
 * @since 2020-05-11
 */
public interface DailyService extends IService<Daily> {

    void createRegistCountByDay(String day);

    Map<String, Object> getEchatsDatas(String type, String begin, String end);
}
