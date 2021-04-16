package com.lu.tech.oss.controller;

import com.lu.tech.commonutils.R;
import com.lu.tech.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/uploadOssFile")
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String uploadUrl = ossService.uploadFileAvatar(file);
        //返回R对象
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
