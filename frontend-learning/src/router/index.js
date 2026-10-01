import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../store/user'

// 公开路由(登录、注册)— 无需 token
// 受保护路由(在 Layout 下)— 必须登录
const routes = [
  { path: '/login',    component: () => import('../views/Login.vue'),    meta: { title: '登录', public: true } },
  { path: '/register', component: () => import('../views/Register.vue'), meta: { title: '注册', public: true } },

  {
    path: '/',
    component: () => import('../components/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '仪表盘', icon: 'DataAnalysis' }
      },
      {
        path: 'users',
        name: 'UserManagement',
        component: () => import('../views/UserManagement.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'roles',
        name: 'RoleManagement',
        component: () => import('../views/RoleManagement.vue'),
        meta: { title: '角色管理', icon: 'UserFilled' }
      },
      {
        path: 'dicts',
        name: 'DictManagement',
        component: () => import('../views/DictManagement.vue'),
        meta: { title: '数据字典', icon: 'Collection' }
      },
      {
        path: 'logs',
        name: 'OperationLog',
        component: () => import('../views/OperationLog.vue'),
        meta: { title: '操作日志', icon: 'Document' }
      },
      {
        path: 'about',
        name: 'About',
        component: () => import('../views/About.vue'),
        meta: { title: '关于', icon: 'InfoFilled' }
      }
    ]
  },
  { path: '/:pathMatch(.*)*', redirect: '/dashboard' }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫:
// 1. 设置页面标题
// 2. 公开路由直接放行
// 3. 受保护路由检查登录
router.beforeEach((to, from, next) => {
  document.title = to.meta.title
    ? `${to.meta.title} - Java Web 管理系统`
    : 'Java Web 管理系统'

  const userStore = useUserStore()

  // 公开路由
  if (to.meta.public) {
    // 已登录用户访问登录/注册页 → 跳到仪表盘
    if (userStore.isLogin && (to.path === '/login' || to.path === '/register')) {
      return next('/dashboard')
    }
    return next()
  }

  // 受保护路由: 检查登录
  if (!userStore.isLogin) {
    return next({ path: '/login', query: { redirect: to.fullPath } })
  }

  next()
})

export default router