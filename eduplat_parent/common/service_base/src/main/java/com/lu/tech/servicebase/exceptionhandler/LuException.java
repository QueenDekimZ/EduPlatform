package com.lu.tech.servicebase.exceptionhandler;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor //生成有参构造方法
@NoArgsConstructor //生成无参构造方法
public class LuException extends RuntimeException{
    //状态码
    private Integer code;

    //异常信息
    private String msg;
}
