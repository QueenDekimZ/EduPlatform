package com.lu.tech.eduservice.controller;


import com.lu.tech.commonutils.R;
import com.lu.tech.eduservice.entity.EduChapter;
import com.lu.tech.eduservice.entity.chapter.ChapterVo;
import com.lu.tech.eduservice.service.EduChapterService;
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
@Api(description = "章节管理模块")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin //跨域
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    /**
     * 1、课程大纲列表，根据课程ID进行查询
     */
    @ApiOperation(value = "课程大纲列表，根据课程ID进行查询")
    @GetMapping(value = "/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     *2、添加章节
     */
    @ApiOperation(value = "添加章节")
    @PostMapping(value = "/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 3、根据章节ID进行查询
     */
    @ApiOperation(value = "根据章节ID进行查询")
    @GetMapping(value = "/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 4、修改章节
     */
    @ApiOperation(value = "修改章节")
    @PostMapping(value = "/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 5、删除章节的方法
     */
    @ApiOperation(value = "删除章节的方法")
    @DeleteMapping(value = "{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}