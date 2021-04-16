<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="教师名称">
        <el-input v-model="teacher.name"/>
      </el-form-item>
      <el-form-item label="教师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="教师性别">
        <el-select v-model="teacher.sex" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="0" label="未知"/>
          <el-option :value="1" label="男"/>
          <el-option :value="2" label="女"/>

        </el-select>
      </el-form-item>
      <el-form-item label="教师资历">
        <el-input v-model="teacher.career"/>
      </el-form-item>
      <el-form-item label="教师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
      </el-form-item>

      <!-- 教师头像：TODO -->
      <!-- 教师头像 -->
      <el-form-item label="教师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar"/>
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>

          <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 -->
      <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss/uploadOssFile'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      <!-- <input type="file" name="file"/> -->
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
//引入调用teacher.js文件
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        sex: 0,
        career: '',
        intro: '',
        avatar: ''
      },
      saveBtnDisabled: false, // 保存按钮是否禁用,
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传弹框组件--头像
      imagecropperKey: 0 // 上传组件id--头像
    }
  },

  watch:{ //监听路由的变化
    $route(to,from){ //路由变化的方式，，路由发生变化，这个方法就会执行
      this.init();
    }
  },

  created() {
    this.init();
  },

  methods: {

    saveOrUpdate() {
      this.saveBtnDisabled = true
      if (!this.teacher.id) {
          this.saveData()
      } else {
          this.updateData()
      }
  },

  //初始化
  init(){
    //判断路由上是否有ID ：http://localhost:9528/#/edu/teacher/edit/1351473558729908226
    if (this.$route.params && this.$route.params.id) {
      //获取路由上的ID ， 然后根据ID来查询教师
      const id = this.$route.params.id;
      //调用根据ID查询的方法
      this.fetchDataById(id);
    }else{
      //清空表单
      this.teacher = {}
    }
  },

    //1、新增教师的方法
    saveData() {
      teacherApi.addTeacher(this.teacher).then(response => {
        return this.$message({
          type: 'success',
          message: '保存成功!'
        })
      }).then(resposne => {
        // this.$router.push({ path: '/edu/teacher/list' })
        this.$router.push({ path: '/teacher/table' })

      }).catch((response) => {
        // console.log(response)
        this.$message({
          type: 'error',
          message: '保存失败'
        })
      })
    },

    //2、根据id查询记录
    fetchDataById(id) {
        teacherApi.getTeacherById(id).then(response => {
            console.log(response);
            this.teacher = response.data.eduTeacher;
        }).catch((response) => {
            this.$message({
                type: 'error',
                message: '获取数据失败'
            })
        })
    },

    //3、修改教师的方法
    // 根据id更新记录
  updateData() {
      this.saveBtnDisabled = true
      teacherApi.updateTeacherById(this.teacher).then(response => {
          return this.$message({
              type: 'success',
              message: '修改成功!'
          })
      }).then(resposne => {
          this.$router.push({ path: '/teacher/table' })
      }).catch((response) => {
          // console.log(response)
          this.$message({
              type: 'error',
              message: '保存失败'
          })
      })
  },

  // 上传头像成功后的回调函数
    cropSuccess(data) {
      console.log(data)
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    },

    // 关闭上传组件
    close() {
      this.imagecropperShow = false
      // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1
    }

  }
}
</script>