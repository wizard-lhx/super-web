<script>
export default {
  name: "test_element"
}
</script>

<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div style="background-color: aqua;width: 100%;height: 100px"></div>
      </el-col>
    </el-row>
    <el-row :gutter='20'>
      <el-col :span="12">
        <div style="margin-top:20px;background-color: aqua;width: 100%;height: 100px"></div>
      </el-col>
      <el-col :span="12">
        <div style="margin-top:20px;background-color: aqua;width: 100%;height: 100px"></div>
      </el-col>
    </el-row>

    <el-container>
      <el-header style="display:flex;color: brown;align-items:center;justify-content: center;background-color: gray">Header</el-header>
      <el-main style="color: black;align-items: center;background-color: darkgreen">Main</el-main>
    </el-container>

<!--    <i class="el-icon-alarm-clock"></i>-->

    <el-row>
      <el-col>
        <el-button type="danger">danger button</el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <el-radio v-model="radio" @change="radioChange" label="男">男</el-radio>
        <el-radio v-model="radio" @change="radioChange" label="女">女</el-radio>
      </el-col>
    </el-row>

    <el-input style="width:300px" v-model="input" placeholder="please input your name"></el-input>

    <el-select v-model="select">
      <el-option v-for="option in fruits" :key="option.value" :value="option.value" :label="option.name"></el-option>
    </el-select>

    <el-date-picker v-model="date1" type="daterange" value-format="yyyy-MM-dd" @change="dateChange" ></el-date-picker>

    <el-table :data="fruits">
      <el-table-column label="Name" prop="name"></el-table-column>
      <el-table-column label="Num" prop="value"></el-table-column>
    </el-table>
  </div>
</template>


<script>
export default {
  data(){
    return {
      radio: '1',
      input: '',
      fruits:[
        {'name':'apple', 'value':'1'},
        {'name':'pineapple', 'value':'2'},
        {'name':'banana', 'value':'3'},
      ],
      select:'',
      date1:''
    }
  },
  methods:{
    radioChange(){
      console.log(this.radio)
    },
    dateChange(){
      console.log(this.date1)
    }
  }
}
</script>
