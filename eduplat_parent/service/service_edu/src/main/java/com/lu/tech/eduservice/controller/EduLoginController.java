package com.lu.tech.eduservice.controller;


import com.lu.tech.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@Api(description = "后台登陆模块")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {
    //login
    @ApiOperation(value = "讲师模拟登录模块")
    @PostMapping(value = "/login")
    public R login(){
        return  R.ok().data("token","admin");
    }

    //info
    @ApiOperation(value = "获取用户信息")
    @GetMapping(value = "/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
