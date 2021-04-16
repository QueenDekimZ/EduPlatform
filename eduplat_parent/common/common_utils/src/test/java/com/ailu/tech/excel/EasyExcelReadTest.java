package com.ailu.tech.excel;

import com.alibaba.excel.EasyExcel;
import com.ailu.tech.excel.ExcelListener;

public class EasyExcelReadTest {
    public static void main(String[] args) {
        // 写法1
        String fileName = "F:\\Coding_Tools\\java\\eduplat_parent\\common\\common_utils\\src\\test\\java\\com\\ailu\\tech\\excel\\student.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, ExcelData.class, new ExcelListener()).sheet("学生列表").doRead();
    }
}
