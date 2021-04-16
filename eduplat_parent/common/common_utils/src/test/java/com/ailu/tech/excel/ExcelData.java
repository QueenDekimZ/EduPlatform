package com.ailu.tech.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

//设置表头和添加的数据字段
@Data
@ToString
public class ExcelData {
    //设置表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}