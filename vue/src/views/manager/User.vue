<template>
<div>
  <el-card>
    <div slot="header" style="width: 100%;display: flex;justify-content: center">
      User Table
    </div>
    <div  style="display: flex;justify-content: center">
      <el-table :data="users" style="width: 1000px;">
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
      </el-table>
    </div>
  </el-card>
</div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      users:[]
    }
  },
  created() {
    this.load()
  },
  methods: {
    load(){
      this.$request.get('/user/selectAll').then(res =>{
        this.users = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>
