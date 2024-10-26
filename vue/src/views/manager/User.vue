<template>
<div>
  <div style="display: flex;">
    <el-input placeholder="用户名" style="width: 200px" v-model="username"></el-input>
    <el-input placeholder="姓名" style="width: 200px" v-model="name"></el-input>
    <el-button @click="load(1)">查询</el-button>
  </div>
  <div>
    <el-button @click="createUser">新增</el-button>
    <el-button @click="delBatchUser">批量删除</el-button>
    <el-button @click="exportData">数据导出</el-button>
    <el-upload :action= "$baseUrl+'/user/importData'" :headers="{token:localUser.token}"
               :on-success="handleImport" :show-file-list="false" style="display: inline;margin-left: 10px">
      <el-button>数据导入</el-button>
    </el-upload>
  </div>
  <el-card>
    <div slot="header" style="width: 100%;display: flex;justify-content: center">
      User Table
    </div>
    <div  style="display: flex;justify-content: center">
      <el-table :data="tableData" style="width: 1000px;" @selection-change="handleSelection">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column label="ID" prop="id" align="center"></el-table-column>
        <el-table-column label="User Name" prop="username"></el-table-column>
        <el-table-column label="Name" prop="name"></el-table-column>
        <el-table-column label="phone" prop="phone"></el-table-column>
        <el-table-column label="role" prop="role"></el-table-column>
        <el-table-column label="avatar" prop="avatar">
          <template v-slot="scope">
            <el-image v-if="scope.row.avatar" style="width: 50px; height: 50px" :src="scope.row.avatar"></el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="180">
          <template v-slot="scope">
            <el-button @click="editUser(scope.row)">编辑</el-button>
            <el-button @click="delUser(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>

  <div class="block">
    <span class="demonstration"></span>
    <el-pagination
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[100, 200, 300, 400]"
        :page-size="pageSize"
        layout="total, prev, pager, next"
        :total="total">
    </el-pagination>
  </div>
  <el-dialog title="用户数据" :visible.sync="dialogFormVisible">
    <el-form ref="form" :rules="rules" :model="formData" label-width="100px">
      <el-form-item label="User Name">
        <el-input v-if="formData.id" v-model="formData.username" disabled></el-input>
        <el-input v-else v-model="formData.username"></el-input>
      </el-form-item>
      <el-form-item label="Name">
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="formData.password"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="formData.phone"></el-input>
      </el-form-item>
      <el-form-item prop="role" size="default">
        <el-radio-group v-model="formData.role">
          <el-radio label="student" ></el-radio>
          <el-radio label="teacher" ></el-radio>
        </el-radio-group>
      </el-form-item>
      <div style="display: flex;justify-content: center">
        <el-button @click="updateUser">save</el-button>
      </div>
    </el-form>
  </el-dialog>
</div>
</template>

<script>

export default {
  name: "User",
  data() {
    return {
      localUser:JSON.parse(localStorage.getItem('user')),
      tableData:[],
      pageNum: 1,
      pageSize:5,
      username:'',
      name:'',
      total:0,
      dialogFormVisible:false,
      formData:{},
      rules:{
        username:[{ required: true, message: 'Please enter your user name!', trigger: 'blur'}]
      },
      selection:[]
    }
  },
  created() {
    this.load(1)
  },
  methods: {
    handleImport(res,file,fileList){
      if(res.code === '200'){
        this.$message.success('导入成功')
      }else{
        this.$message.error(res.msg)
      }
      this.load(1)
    },
    exportData(){
      let ids = this.selection.map(val=>val.id)
      if(!ids){
        window.open(this.$baseUrl+'/user/exportData/'+'?token='+this.localUser.token+
            '&username='+this.username+'&name='+this.name)
      }else{
        window.open(this.$baseUrl+'/user/exportData/'+'?token='+this.localUser.token+
            '&username='+this.username+'&name='+this.name+'&idStr='+ids.join(','))
      }
    },
    handleSelection(selection){
      this.selection = selection
    },
    delUser(row){
      if(this.selection.map(sel=>sel.id).includes(row.id)){
        this.$confirm('确认删除吗？','确认删除').then(response=>{
          this.$request.delete('user/delete/'+row.id.toString()).then(()=>{
            if(res.code === '200'){
              this.$message.success('删除成功')
              this.load()
            }else{
              this.$message.error(res.msg)
            }
          })
        }).catch(()=>{

        })}else{
        this.$message.error('请选择删除对象')
      }
    },
    delBatchUser(){
      let ids = this.selection.map(sel => sel.id)
      if(ids){
        this.$confirm('确认删除吗？','确认删除').then(()=>{
          this.$request.delete('user/deleteBatch', {data:ids}).then(res=>{
            if(res.code === '200'){
              this.$message.success('批量删除成功')
              this.load()
            }else{
              this.$message.error(res.msg)
            }
          })
        }).catch(()=>{})
      }else{
        this.$message.error('请选择删除对象')
      }
    },
    updateUser(){
      this.$refs['form'].validate((valid) => {
        if(valid){
          this.$request({
            method: this.formData.id ? 'PUT' : 'POST',
            url: this.formData.id ? 'user/update' : 'user/add',
            data: this.formData
          }).then(res=>{
            if(res.code === '200'){
              this.$message.success('保存成功')
              this.load()
            }else{
              this.$message.error(res.msg)
            }
          })
        }
      })
      this.dialogFormVisible=false
    },
    editUser(row){
      this.formData = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
    },
    createUser(){
      this.formData = {role:'student'}
      this.dialogFormVisible=true
    },
    load(pageNum){
      if(pageNum) this.pageNum = pageNum
      this.$request.get('/user/selectPage',{params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          name:this.name
        }}).then(res =>{
        this.tableData = res.data?.records
        this.total = res.data?.total
      })
    },
    handleCurrentChange(pageNum){
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>

<style scoped>
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
