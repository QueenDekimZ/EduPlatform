package com.lu.tech.eduservice.service;

import com.lu.tech.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lu.tech.eduservice.entity.vo.CourseInfoVo;
import com.lu.tech.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
public interface EduCourseService extends IService<EduCourse> {

    List<EduCourse> findAll();

    /**
     * 添加课程基本信息
     * @param courseInfoForm
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoForm);

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     * @param id
     * @return
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 删除课程
     * @param courseId
     */
    void removeCourse(String courseId);
}
