<template>
  <div style="height:100vh;display: flex; justify-content: center;align-items: center;">
    <div style="height: 50vh; width: 35%;">
      <div style="height:20%;width: 550px;display: flex; justify-content: center;align-items: center">
        <span style="font-size: x-large;font-weight: bold">Welcome to Register System!</span>
      </div>
      <div style="width: 550px;height: 80%">
        <el-form ref="form" :model="user" :rules="rules">
          <el-form-item prop="username" label="User Name:" label-width="110px" size="default">
            <el-input v-model="user.username" placeholder="Please input your user name"></el-input>
          </el-form-item>
          <el-form-item prop="password" label="Password:" label-width="110px" size="default">
            <el-input v-model="user.password" placeholder="Please input your password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword" label="Password:" label-width="110px" size="default">
            <el-input v-model="user.confirmPassword" placeholder="Please enter your password again"></el-input>
          </el-form-item>
          <el-form-item prop="role" size="default">
            <el-radio-group v-model="user.role">
              <el-radio label="student" ></el-radio>
              <el-radio label="teacher" ></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" @click="register">Register</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Register",
  data(){
    var validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter your password again'))
      }
      else if(this.user.confirmPassword !== this.user.password){
        callback(new Error('The two password do not match'))
      }
      else{
        callback()
      }
    };
    return {
      user:{
        "username":"",
        "password":"",
        "confirmPassword":"",
        role:''
      },
      rules:{
        username:[
          { required: true, message: 'Please enter your user name!', trigger: 'blur' }
        ],
        password:[
          { required: true, message: 'Please enter your password!', trigger: 'blur' }
        ],
        confirmPassword:[
          { validator: validatePassword, trigger: 'blur' }
        ],
        role:[
          { required: true, message: 'Please select your role!', trigger: 'blur' }
        ],
      },
    }
  },
  methods:{
    register(){
      this.$refs["form"].validate((valid) =>{
        if(valid){
          this.$request.post('/register',this.user).then(res => {
            if(res.code === '200'){
              this.$router.push('/login')
              this.$message.success("Register success")
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  },
}
</script>


<style>

</style>
