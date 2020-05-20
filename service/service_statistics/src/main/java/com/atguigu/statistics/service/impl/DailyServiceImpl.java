package com.atguigu.statistics.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.statistics.client.MemberApi;
import com.atguigu.statistics.entity.Daily;
import com.atguigu.statistics.mapper.DailyMapper;
import com.atguigu.statistics.service.DailyService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author hftang
 * @since 2020-05-11
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {
    @Autowired
    private MemberApi memberApi;

    @Override
    public void createRegistCountByDay(String day) {

        //先删除同一天的
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        this.baseMapper.delete(wrapper);

        //先查询出有多少注册数
        R r = memberApi.queryRegistCountByDay(day);
        Integer count = (Integer) r.getData().get("num");

        Daily daily = new Daily();

        daily.setDateCalculated(day);

        daily.setRegisterNum(count);
        daily.setCourseNum(RandomUtils.nextInt(100, 300));
        daily.setLoginNum(RandomUtils.nextInt(100, 300));
        daily.setVideoViewNum(RandomUtils.nextInt(100, 300));

        //再写入到数据库中

        this.baseMapper.insert(daily);
    }

    /***
     *
     * @param type
     * @param begin
     * @param end
     * @return
     */

    @Override
    public Map<String, Object> getEchatsDatas(String type, String begin, String end) {


        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);

        List<Daily> dailyList = this.baseMapper.selectList(wrapper);

        ArrayList<String> dateDatas = new ArrayList<>();//日期
        ArrayList<Integer> countDatas = new ArrayList<>();


        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            dateDatas.add(daily.getDateCalculated());


            if ("register_num".equals(type)) {
                countDatas.add(daily.getRegisterNum());
            } else if ("login_num".equals(type)) {
                countDatas.add(daily.getLoginNum());
            } else if ("video_view_num".equals(type)) {
                countDatas.add(daily.getVideoViewNum());
            } else if ("course_num".equals(type)) {
                countDatas.add(daily.getCourseNum());
            }
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("dateList", dateDatas);
        hashMap.put("countList", countDatas);

        return hashMap;
    }
}
