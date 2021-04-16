package com.lu.tech.servicebase.exceptionhandler;


import com.lu.tech.commonutils.R;
import com.lu.tech.servicebase.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 1、全局异常的处理
     * @param e
     * @return
     */
    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常的处理...");
    }

    /**
     * 2、特定异常的处理
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException特定异常的处理...");
    }

    /**
     * 3、自定义异常处理
     */
    @ExceptionHandler(LuException.class)
    @ResponseBody
    public R error(LuException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().message(e.getMsg()).code(e.getCode());
    }

}