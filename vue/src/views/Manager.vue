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
          <el-dropdown placement="bottom" style="display: flex;align-items: center">
            <div style="margin-right: 10px">
              <img v-if='localUser.avatar' :src="localUser.avatar" style="width: 30px;height: 30px;border-radius: 20px">
            </div>
            <i class="el-icon-user"></i>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="$router.push('/person')">personal</el-dropdown-item>
              <el-dropdown-item @click.native="logout">log out</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
<!--        主体-->
        <el-main>
<!--        动态显示页面并接收子级变量-->
          <router-view @update:localUser="updateUser"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'Manager',
  data(){
    return {
      localUser: JSON.parse(localStorage.getItem('user')||'{}'),
    }
  },
  methods:{
    updateUser(user){
      this.localUser=JSON.parse(JSON.stringify(user))
    },
    logout(){
      localStorage.removeItem('user')
      this.$router.push('/login')
    }
  }
}
</script>

<style>

</style>
