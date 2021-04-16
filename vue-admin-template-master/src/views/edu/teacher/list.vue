<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="教师名" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="teacherQuery.sex" clearable placeholder="教师性别">
          <el-option :value="0" label="未知" />
          <el-option :value="1" label="男" />
          <el-option :value="2" label="女" />
        </el-select>
      </el-form-item>

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getPageList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (page - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="name" label="名称" width="150" />

      <!-- 根据后台0、1、2数据显式对应中文 -->
      <el-table-column label="性别" width="80" prop="sex">
        <template slot-scope="scope">
          <el-tag type="success" effect="dark" v-if="scope.row.sex==0">未知</el-tag>
          <el-tag type="warning" effect="dark" v-if="scope.row.sex==1">男</el-tag>
          <el-tag type="danger" effect="dark" v-if="scope.row.sex==2">女</el-tag>
        </template>
        <!-- <template v-if="0">未知</template>
        <template v-else-if="1">男</template>
        <template v-else-if="2">女</template>-->
      </el-table-column>

      <el-table-column prop="intro" label="资历" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="sort" label="排序" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">

          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>

          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getPageList"
    />
  </div>
</template>

<script>
import teacherApi from "@/api/edu/teacher";
export default {
  //核心代码位置

  data() {
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页显示的记录数
      total: 0, //总记录数
      teacherQuery: {} //条件封装的对象,可以不定义单个变量
    };
  },

  created() {
    //1、查询所有的教师列表带分页查询初始化
    this.getPageList();
  },

  methods: {
    /**
     *  1、查询所有的教师列表带分页查询
     */
    getPageList(page = 1) {
      this.page = page;
      teacherApi
        .getPageList(this.page, this.limit, this.teacherQuery)
        .then(response => {
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(response);
          console.log(this.list);
        });
    },

    /**
     * 2、逻辑删除教师的方法
     */
    removeDataById(id) {
      this.$confirm("此操作将永久删除教师记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        teacherApi.deleteTeacherId(id).then(response => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          //回到列表页面
          this.getPageList();
        });
      }); //点击取消，执行catch方法
    },

    /**
     * 3、清空表单的方法
     */
    resetData() {
      //表单输入项数据清空
      this.teacherQuery = {};

      //查询所有教师的数据
      this.getPageList();
    }
  }
};
</script>