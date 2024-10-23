<template>
<div>
  <el-card>
    <el-form :model="user" label-width="100px">
      <div style="display: flex;justify-content: center">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/files/upload"
            :headers="{token: user.token}"
            :on-success="handleAvatarSuccess">
          <img v-if="user.avatar" :src="user.avatar" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </div>
      <el-form-item label="User Name">
        <el-input v-model="user.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="Name">
        <el-input v-model="user.name"></el-input>
      </el-form-item>
      <el-form-item label="Password">
        <el-input v-model="user.password"></el-input>
      </el-form-item>
      <el-form-item label="Phone">
        <el-input v-model="user.phone"></el-input>
      </el-form-item>
      <div style="display: flex;justify-content: center">
        <el-button @click="update">save</el-button>
      </div>
    </el-form>
  </el-card>
</div>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      user: JSON.parse(localStorage.getItem('user')||'{}')
    }
  },
  created() {

  },
  methods: {
    update(){
      this.$request.put('/user/update',this.user).then(res =>{
        if(res.code === '200'){
          this.$message.success('保存成功')
        }else {
          this.$message.success(res.msg)
        }
      })
      localStorage.setItem('user',JSON.stringify(this.user))
      // 发送子级对象到父级
      this.$emit('update:localUser',this.user)
    },
    handleAvatarSuccess(res,file,fileList){
      this.user.avatar = res.data
    }
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
