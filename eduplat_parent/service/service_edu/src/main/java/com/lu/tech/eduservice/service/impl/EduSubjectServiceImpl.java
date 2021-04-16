package com.lu.tech.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lu.tech.eduservice.entity.EduSubject;
import com.lu.tech.eduservice.entity.excel.ExcelSubjectData;
import com.lu.tech.eduservice.entity.subject.OneSubject;
import com.lu.tech.eduservice.entity.subject.TwoSubject;
import com.lu.tech.eduservice.listener.SubjectExcelListener;
import com.lu.tech.eduservice.mapper.EduSubjectMapper;
import com.lu.tech.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lu.tech.servicebase.exceptionhandler.LuException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author QueenDekimZ
 * @since 2021-04-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubjectData(MultipartFile file, EduSubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new LuException(20002,"添加课程分类失败");
        }
    }

    //课程分类列表（树形）
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1、查询所有的一级分类   parent_id  = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
//        List<EduSubject> list = this.list(wrapperOne);

        //2、查询所有的二级分类 parent_id  != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //3、创建list集合，用于存储最终封装的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //4、封装一级分类
        //查询出来所有一级分类list集合遍历，得到每一个一级分类的对象，获取每个一级分类的对象值
        //封装到要求list集合里面 List<OneSubject> finalSubjectList

        for (int i = 0; i < oneSubjectList.size(); i++) {
            //遍历oneSubjectList集合
            EduSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面的值取出来，放到OneSubject对象里面
            //多个OneSubject放到finalSubjectList里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());

            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每一个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每一个二级分类
                EduSubject subject = twoSubjectList.get(m);

                //判断二级分类parentId和一级分类的id是否一样
                if(subject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject,twoSubject);

                    twoFinalSubjectList.add(twoSubject);
                }
            }

            //把一级分类下面的所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }

}
