<template>
<div style="height:100vh;display: flex; justify-content: center;align-items: center;">
  <div style="height: 50vh; width: 35%;">
    <div style="height:20%;width: 550px;display: flex; justify-content: center;align-items: center">
      <span style="font-size: x-large;font-weight: bold">Welcome to Login System!</span>
    </div>
    <div style="width: 550px;height: 80%">
      <el-form ref="form" :model="user" :rules="rules">
        <el-form-item prop="username" label="User Name:" label-width="110px" size="default">
          <el-input v-model="user.username" placeholder="Please input your user name"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="Password:" label-width="110px" size="default">
          <el-input v-model="user.password" placeholder="Please input your password"></el-input>
        </el-form-item>
        <el-form-item prop="validCode" label="Valid Code:" label-width="110px" size="default">
          <el-input v-model="user.validCode" placeholder="Please input your valid code"></el-input>
          <valid-code @update="getCode"/>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" @click="login">Log In</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</div>
</template>

<script>
import ValidCode from "@/components/ValidCode.vue";
import router from "@/router";

export default {
  name: "Login",
  data(){
    var validatCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please input your valid code'))
      }
      else if(this.user.validCode !== this.validCode){
        callback(new Error('Valid code has error'))
      }
      else{
        callback()
      }
    };
    return {
      validCode:"",
      user:{
        "username":"",
        "password":"",
        "validCode":""
      },
      rules:{
        username:[
          { required: true, message: 'Please enter your user name!', trigger: 'blur' }
        ],
        password:[
          { required: true, message: 'Please enter your password!', trigger: 'blur' }
        ],
        validCode:[
          { validator:validatCode, trigger: 'blur' }
        ],
      },
    }
  },
  methods:{
    getCode(validCode){
      this.validCode=validCode
      // console.log(validCode)
    },
    login(){
      this.$refs["form"].validate((valid) =>{
        if(valid){
          this.$request.post('/login',this.user).then(res => {
            if(res.code === '200'){
              this.$router.push('/')
              this.$message.success("Log in success")
              localStorage.getItem('user',JSON.stringify(res.user))//存储用户数据
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  },
  components:{
    ValidCode
  }
}
</script>


<style>

</style>
