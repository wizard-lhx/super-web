<template>
<div>
  <el-card>
    <div slot="header" style="width: 100%;display: flex;justify-content: center">
      User Table
    </div>
    <div  style="display: flex;justify-content: center">
      <el-table :data="user" style="width: 1000px;">
        <el-table-column label="ID" prop="id" ></el-table-column>
        <el-table-column label="User Name" prop="username"></el-table-column>
        <el-table-column label="Name" prop="name"></el-table-column>
        <el-table-column label="File Upload">
          <template v-slot="scope">
            <el-upload
                class="upload-demo"
                action="http://localhost:9090/files/upload"
                :headers="{token:localUser.token}"
                :on-success="(response, file, fileList) => handleTableFileUpload(scope.row,file)">
              <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
          </template>
        </el-table-column>
        <el-table-column label="avatar">
          <template v-slot="scope">
            <el-image v-if="scope.row.avatar" style="width: 50px; height: 50px" :src="scope.row.avatar"></el-image>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>
  <div style="width: 50%">
    <el-card>
      <div slot="header" style="display: flex;justify-content: center">
        File Upload
      </div>
      <div>
        <el-upload
            class="upload-demo"
            action="http://localhost:9090/files/upload"
            :headers="{token:localUser.token}"
            :on-success="handleFileUpload"
            list-type="picture">
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </div>
    </el-card>
  </div>
</div>
</template>

<script>
import request from "@/utils/request.js";
import router from "@/router";

export default {
  name: 'Home',
  data(){
    return {
      user: [],
      localUser: JSON.parse(localStorage.getItem('user')||'{}'),
    }
  },
  mounted() {
    // axios.get("http://localhost:9090/user/selectAll").then(res => {
    //   this.user = res.data.data
    // })
    request.get("/user/selectAll").then(res => {
      if(res.code === '401'){
        router.push("/login")
      }
      this.user = res.data
    })
  },
  methods:{
    handleTableFileUpload(row,file){
      row.avatar = file.response.data
      this.$request.put("/user/update", row).then(res =>{
        if(res.code === '200'){
          this.$message.success('上传成功')
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    handleFileUpload(response, file, fileList){
      console.log(file, fileList)
    },
  }
}
</script>

<style scoped>

</style>
