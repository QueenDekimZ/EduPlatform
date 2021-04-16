package com.lu.tech.eduservice.controller;


import com.lu.tech.commonutils.R;
import com.lu.tech.eduservice.entity.EduCourse;
import com.lu.tech.eduservice.entity.vo.CourseInfoVo;
import com.lu.tech.eduservice.entity.vo.CoursePublishVo;
import com.lu.tech.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
@Api(description = "课程信息管理模块")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @ApiOperation(value = "课程列表 基本实现")
    @GetMapping
    public R getCourseList() {
//        List<EduCourse> list = courseService.findAll();
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    /**
     * 1、添加课程的基本信息方法
     * @param courseInfoForm
     * @return
     */
    @ApiOperation(value = "添加课程的基本信息方法")
    @PostMapping(value = "/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoForm){
        //调用service方法进行数据的保存
        //返回添加之后课程的ID，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoForm);
        return R.ok().data("courseId",id);
    }

    /**
     * 2、根据课程id查询课程基本信息
     */
    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping(value = "/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        //返回的是courseInfoVo，数据回显
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     * 3、修改课程信息
     */
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }


    /**
     * 4、根据课程id查询课程确认信息
     */
    @ApiOperation(value = "根据课程id查询课程确认信息")
    @GetMapping(value = "/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @ApiOperation(value = "修改课程状态")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

//    /**
//     * 5、测试根据ID查询课程信息
//     */
//    @ApiOperation(value = "测试根据ID查询课程信息")
//    @GetMapping(value = "/queryById/{id}")
//    public R queryById(@PathVariable String id){
//        EduCourse eduCourse = courseService.queryById(id);
//        return R.ok().data("eduCourse",eduCourse);
//    }
}