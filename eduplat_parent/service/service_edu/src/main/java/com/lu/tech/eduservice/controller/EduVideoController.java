package com.lu.tech.eduservice.controller;


import com.lu.tech.commonutils.R;
import com.lu.tech.eduservice.entity.EduVideo;
import com.lu.tech.eduservice.service.EduVideoService;
import com.lu.tech.servicebase.exceptionhandler.LuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-12
 */
@Api(description = "小节管理模块")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //注入vodClient
//    @Autowired
//    private VodClient vodClient;

    /**
     * 1、添加小节
     */
    @ApiOperation(value = "添加小节")
    @PostMapping(value = "/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);

        return R.ok();
    }

    /**
     * 2、删除小节
     * TODO 后面这个方法需要完善：删除小节时候，同时把里面视频删除
     */
    @ApiOperation(value = "删除小节")
    @DeleteMapping(value = "{id}")
    public R deleteVideo(@PathVariable("id") String id) {
        //根据小节id获取视频id，调用方法实现视频删除
//        EduVideo eduVideo = videoService.getById(id);
//        String videoSourceId = eduVideo.getVideoSourceId();
//        //判断小节里面是否有视频id
//        if(!StringUtils.isEmpty(videoSourceId)) {
//            //根据视频id，远程调用实现视频删除
//            R result = vodClient.removeAlyVideo(videoSourceId);
//            if(result.getCode() == 20001) {
//                throw new LuException(20001,"删除视频失败，熔断器...");
//            }
//        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    /**
     * 3、根据小节ID进行查询
     */
    @ApiOperation(value = "根据小节ID进行查询")
    @GetMapping(value = "/getVideoInfo/{videoId}")
    public R getChapterInfo(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("chapter",eduVideo);
    }

    /**
     * 4、修改小节
     */
    @ApiOperation(value = "修改小节")
    @PostMapping(value = "/updateVideo")
    public R updateChapter(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}
