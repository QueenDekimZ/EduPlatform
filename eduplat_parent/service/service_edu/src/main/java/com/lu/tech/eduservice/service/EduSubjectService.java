package com.lu.tech.eduservice.service;

import com.lu.tech.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lu.tech.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;
import com.lu.tech.eduservice.entity.excel.ExcelSubjectData;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubjectData(MultipartFile file, EduSubjectService subjectService);
    //课程分类列表（树形）
    List<OneSubject> getAllOneTwoSubject();

}
