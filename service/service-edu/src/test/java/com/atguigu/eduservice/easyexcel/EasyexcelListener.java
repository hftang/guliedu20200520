package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 17:28
 * @Description:
 */
@Slf4j
public class EasyexcelListener extends AnalysisEventListener<demo> {
    @Override
    public void invoke(demo demo, AnalysisContext analysisContext) {
        log.info("读取到的" + demo);
    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

        log.info("头文件：" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
