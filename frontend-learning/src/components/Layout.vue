<script setup>
// 整体布局: 左侧菜单 + 顶部栏 + 内容区
import { computed, ref } from 'vue'
import { RouterView, useRoute, useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 菜单项(与路由对应)
const allMenuItems = [
  { path: '/dashboard', title: '仪表盘',   icon: 'DataAnalysis' },
  { path: '/users',     title: '用户管理', icon: 'User' },
  { path: '/roles',     title: '角色管理', icon: 'UserFilled' },
  { path: '/dicts',     title: '数据字典', icon: 'Collection' },
  { path: '/logs',      title: '操作日志', icon: 'Document' },
  { path: '/about',     title: '关于',     icon: 'InfoFilled' }
]

// 角色 ADMIN 才能访问的角色/权限管理
const menuItems = computed(() => {
  if (userStore.roles.includes('ADMIN')) return allMenuItems
  return allMenuItems.filter(m => !['/roles'].includes(m.path))
})

const activePath = () => route.path
const goTo = (path) => router.push(path)

// 用户下拉菜单
const onCommand = async (cmd) => {
  if (cmd === 'logout') {
    try {
      await ElMessageBox.confirm('确定退出登录吗?', '提示', {
        confirmButtonText: '退出',
        cancelButtonText: '取消',
        type: 'warning'
      })
      await userStore.logout()
      ElMessage.success('已退出登录')
      router.push('/login')
    } catch (_) { /* 取消 */ }
  } else if (cmd === 'profile') {
    ElMessage.info('个人中心 - 待开发')
  }
}
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon size="22" color="#fff"><Promotion /></el-icon>
        <span class="logo-text">Java Web 管理系统</span>
      </div>
      <el-menu
        :default-active="activePath()"
        class="aside-menu"
        background-color="#001529"
        text-color="#fff"
        active-text-color="#409eff"
        @select="goTo"
      >
        <el-menu-item
          v-for="item in menuItems"
          :key="item.path"
          :index="item.path"
        >
          <el-icon><component :is="item.icon" /></el-icon>
          <span>{{ item.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <!-- 顶部 -->
      <el-header class="header">
        <div class="header-left">
          <el-breadcrumb>
            <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.title && route.path !== '/dashboard'">
              {{ route.meta.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-tag :type="userStore.roles.includes('ADMIN') ? 'danger' : 'success'" size="small">
            {{ userStore.roles.join(', ') || '用户' }}
          </el-tag>
          <el-dropdown trigger="click" @command="onCommand">
            <span class="user-trigger">
              <el-avatar :size="28" style="background:#409eff;margin-right:6px;">
                {{ userStore.username?.charAt(0)?.toUpperCase() || '?' }}
              </el-avatar>
              {{ userStore.username }}
              <el-icon style="margin-left:4px;"><ArrowDown /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>个人中心
                </el-dropdown-item>
                <el-dropdown-item command="logout" divided>
                  <el-icon><SwitchButton /></el-icon>退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 内容区 -->
      <el-main>
        <RouterView v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </RouterView>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container { height: 100vh; }
.aside { background-color: #001529; color: #fff; }
.logo {
  display: flex; align-items: center; gap: 8px;
  height: 60px; padding: 0 20px; background-color: #002140;
}
.logo-text { color: #fff; font-size: 15px; font-weight: 600; white-space: nowrap; }
.aside-menu { border-right: none; }
.aside-menu :deep(.el-menu-item) { margin: 4px 0; }

.header {
  display: flex; justify-content: space-between; align-items: center;
  background: #fff; border-bottom: 1px solid #eaeaea; padding: 0 20px;
}
.header-left { display: flex; align-items: center; }
.header-right { display: flex; align-items: center; gap: 12px; }

.user-trigger {
  display: inline-flex; align-items: center; cursor: pointer; color: #303133;
}
.user-trigger:hover { color: #409eff; }

.el-main { padding: 0; background-color: #f5f5f5; }

.fade-enter-active, .fade-leave-active { transition: opacity 0.2s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }
</style>