package com.lu.tech.eduservice.controller;


import com.lu.tech.commonutils.R;
import com.lu.tech.eduservice.entity.subject.OneSubject;
import com.lu.tech.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-11
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping(value = "/addSubject")
    public R addSubject(MultipartFile file) {
        //1 获取上传的excel文件 MultipartFile
        //返回错误提示信息
        subjectService.saveSubjectData(file,subjectService);
        //判断返回集合是否为空
        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation(value = "课程分类的列表方法")
    @GetMapping(value = "/getAllSubject")
    public R getAllSubject(){
        //list集合泛型是一级分类，其中也包含了二级
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}