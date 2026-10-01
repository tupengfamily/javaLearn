<script setup>
// 整体布局: 左侧菜单 + 顶部栏 + 内容区
import { RouterView, useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 菜单项(与路由对应)
const menuItems = [
  { path: '/home', title: '首页', icon: 'House' },
  { path: '/users', title: '用户管理', icon: 'User' },
  { path: '/about', title: '关于', icon: 'InfoFilled' }
]

// 当前激活菜单
const activePath = () => route.path

// 跳转
const goTo = (path) => router.push(path)
</script>

<template>
  <el-container class="layout-container">
    <!-- 左侧菜单 -->
    <el-aside width="220px" class="aside">
      <div class="logo">
        <el-icon size="24" color="#fff"><Promotion /></el-icon>
        <span class="logo-text">Java Web 学习</span>
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
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-if="route.meta.title && route.path !== '/home'">
              {{ route.meta.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-tag type="success">Vue 3 + Element Plus</el-tag>
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
.layout-container {
  height: 100vh;
}

.aside {
  background-color: #001529;
  color: #fff;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  height: 60px;
  padding: 0 20px;
  background-color: #002140;
}

.logo-text {
  color: #fff;
  font-size: 16px;
  font-weight: 600;
  white-space: nowrap;
}

.aside-menu {
  border-right: none;
}

.aside-menu :deep(.el-menu-item) {
  margin: 4px 0;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border-bottom: 1px solid #eaeaea;
  padding: 0 20px;
}

.header-left {
  display: flex;
  align-items: center;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
}

.el-main {
  padding: 0;
  background-color: #f5f5f5;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>