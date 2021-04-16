package com.lu.tech.eduservice.mapper;

import com.lu.tech.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lu.tech.eduservice.entity.vo.CoursePublishVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    /**
     * 根据课程id查询课程确认信息
     *
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

//    /**
//     * 根据Id课程查看课程信息
//     */
//    EduCourse queryById(String courseId);


//    /**
//     * 查询所有的课程信息
//     */
//    List<EduCourse> findAll();
}
