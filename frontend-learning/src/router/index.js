import { createRouter, createWebHistory } from 'vue-router'

// Vue Router 配置
// 演示:
// - 嵌套路由
// - 路由懒加载
// - 命名路由
const routes = [
  {
    path: '/',
    component: () => import('../components/Layout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('../views/Home.vue'),
        meta: { title: '首页', icon: 'House' }
      },
      {
        path: 'users',
        name: 'UserList',
        component: () => import('../views/UserList.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'about',
        name: 'About',
        component: () => import('../views/About.vue'),
        meta: { title: '关于', icon: 'InfoFilled' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫: 设置页面标题
router.beforeEach((to, from, next) => {
  document.title = to.meta.title
    ? `${to.meta.title} - Java Web 学习项目`
    : 'Java Web 学习项目'
  next()
})

export default router