<template>
  <div class="home">
    <el-container>
<!--      侧边栏隐藏滚动条-->
      <el-aside style="overflow: hidden">
        <div index="firstPage" style="height: 45px;display: flex;justify-content: center;align-items: center">
          <img src="@/assets/sdu_logo.jpg" alt="" style="width: 30px;height: 30px">
          <span>系统首页</span>
        </div>
        <el-menu>
          <el-submenu index="/">
            <template slot="title">
              <i class="el-icon-position"></i>
              <span>读者专区</span>
            </template>
          </el-submenu>
          <el-submenu index="element">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>管理员专区</span>
            </template>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-container>
<!--        头部区域-->
        <el-header style="height: 45px;display: flex;align-items:center;justify-content: flex-end">
          <i class="el-icon-user" @click="login"></i>
        </el-header>
<!--        主体-->
        <el-main>
          <el-card>
            <div slot="header" style="width: 100%;display: flex;justify-content: center">
              User Table
            </div>
            <div  style="display: flex;justify-content: center">
              <el-table :data="user" style="width: 1000px;">
                  <el-table-column label="ID" prop="id" ></el-table-column>
                  <el-table-column label="User Name" prop="username"></el-table-column>
                  <el-table-column label="Name" prop="name"></el-table-column>
                  <el-table-column label="Phone" prop="phone"></el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import axios from "axios";
import request from "@/utils/request.js";

export default {
  name: 'HomeView',
  data(){
    return {
      user: []
    }
  },
  mounted() {
    // axios.get("http://localhost:9090/user/selectAll").then(res => {
    //   this.user = res.data.data
    // })
    request.get("/user/selectAll").then(res => {
      this.user = res.data
    })
  },
  methods:{
    login(){
      console.log('1')
    }
  }
}
</script>

<style>

</style>
