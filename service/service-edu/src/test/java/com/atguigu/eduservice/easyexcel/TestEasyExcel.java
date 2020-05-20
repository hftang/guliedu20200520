package com.atguigu.eduservice.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: hftang
 * @Date: 2020/4/27 16:56
 * @Description:
 */

public class TestEasyExcel {


    //测试easyexcel的写操作
    String filePath = "E:\\hftang.xlsx";

    @Test
    public void test01() {
        EasyExcel.write(filePath, demo.class).sheet("学生账单").doWrite(getDatas());
    }

    //读
    @Test
    public void test02(){
        EasyExcel.read(filePath,demo.class,new EasyexcelListener()).sheet().doRead();
    }



    public List<demo> getDatas() {
        ArrayList<demo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            demo demo = new demo();
            demo.setName("hfang" + i);
            demo.setNo_id("1111222000" + i);
            list.add(demo);
        }
        return list;
    }


}
