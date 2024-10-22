<template>
  <div class="home">
    <el-container>
<!--      侧边栏隐藏滚动条-->
      <el-aside style="overflow: hidden">
        <div index="firstPage" style="height: 45px;display: flex;justify-content: center;align-items: center">
          <img src="../assets/sdu_logo.jpg" alt="" style="width: 30px;height: 30px">
          <span>系统首页</span>
        </div>
        <el-menu :default-active="$router.path" :router='true'>
          <el-submenu index="userRegion">
            <template slot="title">
              <i class="el-icon-position"></i>
              <span>读者专区</span>
            </template>
          </el-submenu>
          <el-submenu v-if="localUser.role === 'admin'" index="adminRegion">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员专区</span>
            </template>
            <el-menu-item index="/user">
              <i class="el-icon-user"></i>
              <span>用户管理</span>
            </el-menu-item>
          </el-submenu>
          <el-menu-item index="/home">
              <i class="el-icon-user"></i>
              <span>首页</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-container>
<!--        头部区域-->
        <el-header style="height: 45px;display: flex;align-items:center;justify-content: flex-end">
          <i class="el-icon-user" @click="login"></i>
        </el-header>
<!--        主体-->
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";
import request from "@/utils/request.js";
import router from "@/router";

export default {
  name: 'HomeView',
  data(){
    return {
      user: [],
      localUser: JSON.parse(localStorage.getItem('user')||'{}'),
      url: ""
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
    handleTableFileUpload(row,file,fileList){
      row.avatar = file.response.data
      console.log(row)
      this.$request.put("/user/update", row).then(res =>{
        if(res.code === '200'){
          this.$message.success('上传成功')
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    handleFileUpload(response, file, fileList){
      console.log(file)
    },
    login(){
      console.log('1')
    }
  }
}
</script>

<style>

</style>
