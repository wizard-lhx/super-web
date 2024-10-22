import Vue from 'vue'
import VueRouter from 'vue-router'

import test_element from "@/views/test_element.vue";
import Login from "@/views/Login.vue";
import Register from "@/views/Register.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    children: [{
      path:'home', name:'Home', component: ()=>import('../views/manager/Home.vue')
    },{
      path:'user', name:'User', component: ()=>import('../views/manager/User.vue')
    },{
      path:'403', name:'Auth', component: ()=>import('../views/manager/Auth.vue')
    },
    ]
  }, {
    path: '/login', name: 'login', component: Login
  }, {
    path: '/register', name: 'register', component: Register
  },{
    path: '/*', name: '404', component: ()=>import('../views/404.vue')
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{
  let localUser = JSON.parse(localStorage.getItem('user')||'{}')
  let adminPath=['/user']
  if(localUser.role !== 'admin' && adminPath.includes(to.path)){
    next('/403')
  }else {
    next()
  }
})

export default router
