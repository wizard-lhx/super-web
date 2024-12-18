import axios from "axios";

const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL, // 后台默认端口
    timeout: 30000
})

//request 拦截器
request.interceptors.request.use(config =>{
    config.headers['Content-Type'] = "application/json;character=utf-8"
    let user = JSON.parse(localStorage.getItem("user") || '{}')
    config.headers['token'] = user.token; //将登录时存储的 token 拿出来作为请求头
    return config
},error => {
    console.error('request error:' + error)
    return Promise.reject(error)
})

//response 拦截器
request.interceptors.response.use(
    response => {
        let res = response.data

        if(typeof res === "string"){
            res = res ? JSON.phase(res) : res
        }
        return res;
    },
    error => {
        console.error('response error:' + error)
        return Promise.reject(error)
    }
)

export default request
