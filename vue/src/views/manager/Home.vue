<template>
<div>

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
