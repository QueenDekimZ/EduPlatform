package com.lu.tech.eduservice.service;

import com.lu.tech.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lu.tech.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
