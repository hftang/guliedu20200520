package com.atguigu.serviceedu.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/5/3 19:19
 * @Description:  hystrix 的兜底方法
 */
@Component
public class VodClientHystrix implements VodClient {
    @Override
    public R deleVideoById(String id) {
        return R.error().message("删除单个视频失败");
    }

    @Override
    public R batchDelete(List<String> list) {
        return R.error().message("删除多个视频失败");
    }
}
