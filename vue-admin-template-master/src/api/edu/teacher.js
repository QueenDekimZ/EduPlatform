import request from '@/utils/request'

export default{
    /**
     * 1、查询所有的教师列表带分页查询
     * page当前页 limit每页记录数 teacherQuery条件对象
     */ 
    getPageList(page, limit, teacherQuery){
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${page}/${limit}`,
            method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转换json进行传递到接口里面
            data: teacherQuery
        })
    },

    /**
     * 2、逻辑删除教师的方法
     */
    deleteTeacherId(id){
        return request({
            url: `/eduservice/teacher/deleteById/${id}`,
            method: 'delete'
        })
    },

    /**
     * 3、新增教师的方法
     */
    addTeacher(eduTeacher){
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: eduTeacher
        })
    },

    /**
     * 4、根据教师ID获取教师的方法
     */
    getTeacherById(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get'
        })
    },

    /**
     * 5、修改教师的方法
     */
    updateTeacherById(eduTeacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: eduTeacher
        })
    }
}