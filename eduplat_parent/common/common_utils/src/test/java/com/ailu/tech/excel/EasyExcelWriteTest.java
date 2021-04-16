package com.ailu.tech.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class EasyExcelWriteTest {
    public static void main(String[] args) {
        // 写法1
        String fileName = "F:\\Coding_Tools\\java\\eduplat_parent\\common\\common_utils\\src\\test\\java\\com\\ailu\\tech\\excel\\student.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, ExcelData.class).sheet("学生列表").doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<ExcelData> data() {
        List<ExcelData> list = new ArrayList<ExcelData>();
        for (int i = 0; i < 10; i++) {
            ExcelData data = new ExcelData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }
}