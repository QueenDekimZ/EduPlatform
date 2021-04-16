package com.lu.tech.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lu.tech.eduservice.entity.EduCourse;
import com.lu.tech.eduservice.entity.EduCourseDescription;
import com.lu.tech.eduservice.entity.vo.CourseInfoVo;
import com.lu.tech.eduservice.entity.vo.CoursePublishVo;
import com.lu.tech.eduservice.mapper.EduCourseMapper;
import com.lu.tech.eduservice.service.EduChapterService;
import com.lu.tech.eduservice.service.EduCourseDescriptionService;
import com.lu.tech.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lu.tech.eduservice.service.EduVideoService;
import com.lu.tech.servicebase.exceptionhandler.LuException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    /**
     * 课程描述的注入
     */
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入小节和章节service
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;

    @Override
    public List<EduCourse> findAll() {
        return null;
    }

    /**
     * 添加课程信息的方法
     * @param courseInfoVo
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        EduCourse eduCourse1 = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse1);



        //1、想课程表中添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        //添加记录成功数，0表示失败
        int insert = baseMapper.insert(eduCourse);

        if(insert == 0){
            throw new LuException(20001,"课程添加信息失败");
        }

        //获取添加成功之后的课程ID,让课程表和课程描述表有一对一关系，而不用外键
        String cid  = eduCourse.getId();

        //2、向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述ID就是课程的ID
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    /**
     * 2、根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
//        EduCourse eduCourse = baseMapper.queryById(courseId);
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    /**
     * 3、修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        System.out.println(courseInfoVo);
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        System.out.println(eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0){
            throw new LuException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        System.out.println(description);
        courseDescriptionService.updateById(description);
    }

    /**
     * 4、根据课程id查询课程确认信息
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper xml中的sql语句
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

//    /**
//     * 5、测试根据ID查询课程信息
//     * @param courseId
//     * @return
//     */
//    @Override
//    public EduCourse queryById(String courseId) {
//        return baseMapper.queryById(courseId);
//    }
//
    /**
     *删除课程
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){
            throw new LuException(20001,"删除课程失败");
        }
    }

//    @Override
//    public List<EduCourse> findAll() {
//        return baseMapper.findAll();
//    }
//
//    @Override
//    public List<EduCourse> findByDescAll() {
//        return baseMapper.findByDescAll();
//    }
//
//    /**
//     * 根据课程老师ID查询所有的课程信息
//     * @param teacherId
//     * @return
//     */
//    @Override
//    public List<EduCourse> findByAll(String teacherId) {
//        List<EduCourse> eduCourseList = baseMapper.findByAll(teacherId);
//        System.out.println(eduCourseList);
//        return eduCourseList;
//    }

    //1 条件查询带分页查询课程
//    @Override
//    public Map<String, Object> pageTeacherCondition(
//            Page<EduCourse> pageParam,
//            CourseFrontVo courseFrontVo) {
//        //2 根据教师id查询所讲课程
//        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        //判断条件值是否为空，不为空拼接
//        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
//            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
//        }
//        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
//            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
//        }
//        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
//            wrapper.orderByDesc("buy_count");
//        }
//        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
//            wrapper.orderByDesc("gmt_create");
//        }
//
//        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
//            wrapper.orderByDesc("price");
//        }
//
//        baseMapper.selectPage(pageParam,wrapper);
//
//        List<EduCourse> records = pageParam.getRecords();
//        long current = pageParam.getCurrent();
//        long pages = pageParam.getPages();
//        long size = pageParam.getSize();
//        long total = pageParam.getTotal();
//        boolean hasNext = pageParam.hasNext();//下一页
//        boolean hasPrevious = pageParam.hasPrevious();//上一页
//
//        //把分页数据获取出来，放到map集合
//        Map<String, Object> map = new HashMap<>();
//        map.put("items", records);
//        map.put("current", current);
//        map.put("pages", pages);
//        map.put("size", size);
//        map.put("total", total);
//        map.put("hasNext", hasNext);
//        map.put("hasPrevious", hasPrevious);
//
//        //map返回
//        return map;
//    }

//    //根据课程id，编写sql语句查询课程信息
//    @Override
//    public CourseWebVo getBaseCourseInfo(String courseId) {
//        return baseMapper.getBaseCourseInfo(courseId);
//    }
}
