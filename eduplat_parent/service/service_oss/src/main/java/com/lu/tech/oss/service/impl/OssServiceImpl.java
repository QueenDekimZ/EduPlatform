package com.lu.tech.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lu.tech.oss.service.OssService;
import com.lu.tech.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Service
public class OssServiceImpl implements OssService {
    //上传头像到oss

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //工具类获取值

        // Endpoint以杭州为例，其它Region请按实际情况填写。

        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取文件的上传输入流
            InputStream inputStream = file.getInputStream();

            //获取上传文件的名称
            String fileName = file.getOriginalFilename();

            //1、在文件名称里面添加随机唯一的值 32位uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;

            //2、把文件按照日期进行分类
            // 2021/03/12/01.jpg
            //获取当前的日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            // 2021/03/12/01.jpg
            fileName = dataPath + "/" + fileName;

            //调用Oss方法实现文件的上传
            /**
             * 第一个参数表示 Bucket名称
             * 第二个参数表示 上传到oss文件路径和文件的名称 /aa/bb/1.jpg
             * 第三个参数表示 上传文件的输入流
             */
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接
            //https://guli-edu-upload-file.oss-cn-beijing.aliyuncs.com/0.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
