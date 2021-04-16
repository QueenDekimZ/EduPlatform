package com.lu.tech.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lu.tech.eduservice.entity.EduTeacher;
import com.lu.tech.eduservice.entity.vo.TeacherQuery;
import com.lu.tech.eduservice.service.EduTeacherService;
import com.lu.tech.servicebase.exceptionhandler.LuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.lu.tech.commonutils.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 教师 前端控制器
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-03-31
 */

//@Api(description = "教师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //访问地址：http://localhost:8001/eduservice/edu-teacher/findAll
    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 1、查询教师所有数据
     * rest风格
     */
    @ApiOperation(value = "查询所有教师")
    @GetMapping("findAll")
//    public List<EduTeacher> findAllTeacher(){
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        //链式编程
        return R.ok().data("teacherList", list);
    }

    /**
     * 2、逻辑删除教师的方法
     */
    @ApiOperation(value = "逻辑删除教师")
    @DeleteMapping("deleteById/{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "教师ID", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 3、分页查询教师的方法
     * current 当前页
     * limit 每页记录数
     */
    @ApiOperation(value = "分页查询教师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);


        //调用方法实现分页 
        //调用方法的时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(eduTeacherPage, null);


        //总记录数
        long total = eduTeacherPage.getTotal();
        //数据list集合
        List<EduTeacher> records = eduTeacherPage.getRecords();
//        Map map = new HashMap();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        System.out.println(records);
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 4、条件查询
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @PathVariable long current,
            @PathVariable long limit,
            // @RequestBody得用post方法，GET方式无请求体
            // @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)
            @RequestBody(required = false) TeacherQuery teacherQuery
    ) {
        //创建Page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

//        try {
//            int i = 1/0;
//
//        }catch (Exception e){
//            throw new LuException(20001,"执行了自定义异常。。。");
//        }
        //多条件组合查询
        //mybatis学过动态sql
        String name = teacherQuery.getName();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        teacherService.page(eduTeacherPage, wrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> records = eduTeacherPage.getRecords();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 5、新增教师的方法
     */
    @ApiOperation(value = "新增教师的方法")
    @PostMapping(value = "/addTeacher")
    public R addTeacher(@ApiParam(name = "teacher", value = "教师对象", required = true) @RequestBody EduTeacher eduTeacher) {
        //调用service的方式实现添加教师
        boolean flag = teacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 6、根据教师ID获取教师的方法
     */
    @ApiOperation(value = "根据教师ID获取教师的方法")
    @GetMapping(value = "/getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "教师ID", required = true) @PathVariable("id") String id) {
        //调用service的方法实现根据ID查询教师
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("eduTeacher", eduTeacher);
    }

    /**
     * 7、修改教师的方法
     */
    @ApiOperation(value = "修改教师的方法")
    @PostMapping(value = "/updateTeacher")
    public R updateTeacher(@ApiParam(name = "teacher", value = "教师对象", required = true) @RequestBody EduTeacher eduTeacher) {
        //调用service方法实现修改教师
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

