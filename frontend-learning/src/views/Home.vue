<script setup>
import { ref, onMounted } from 'vue'
import { listAllUsers, searchUsers, findByAgeRange } from '../api/user'
import { ElMessage } from 'element-plus'

const stats = ref({
  totalUsers: 0,
  totalAdult: 0,
  youngUsers: 0
})

const loading = ref(false)

// 首页加载时获取统计数据
onMounted(async () => {
  loading.value = true
  try {
    // 总用户数
    const res = await listAllUsers()
    stats.value.totalUsers = res.data.length

    // 成年人(>=18)
    const adults = await findByAgeRange(18, 150)
    stats.value.totalAdult = adults.data.length

    // 年轻人(<=30)
    const young = await findByAgeRange(0, 30)
    stats.value.youngUsers = young.data.length
  } catch (e) {
    // 错误已被拦截器处理
  } finally {
    loading.value = false
  }
})

// 卡片信息
const cards = [
  {
    title: '后端技术',
    icon: 'Promotion',
    color: '#409eff',
    items: ['Spring Boot 3.3', 'Spring MVC', 'Spring Data JPA', 'H2 数据库']
  },
  {
    title: '前端技术',
    icon: 'Monitor',
    color: '#67c23a',
    items: ['Vue 3 (Composition API)', 'Element Plus', 'Vue Router 4', 'Axios']
  },
  {
    title: '核心功能',
    icon: 'Tools',
    color: '#e6a23c',
    items: ['用户 CRUD', '参数校验', '全局异常', 'AOP 日志']
  }
]
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-title">欢迎来到 Java Web 学习项目</div>
      <div class="page-subtitle">
        本项目演示前后端分离架构,前端 Vue 3 + Element Plus,后端 Spring Boot
      </div>
    </div>

    <!-- 统计卡片 -->
    <el-row :gutter="16" v-loading="loading">
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="stat-header">
              <el-icon size="20" color="#409eff"><User /></el-icon>
              <span>用户总数</span>
            </div>
          </template>
          <div class="stat-value">{{ stats.totalUsers }}</div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="stat-header">
              <el-icon size="20" color="#67c23a"><UserFilled /></el-icon>
              <span>成年人数(≥18)</span>
            </div>
          </template>
          <div class="stat-value">{{ stats.totalAdult }}</div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>
            <div class="stat-header">
              <el-icon size="20" color="#e6a23c"><Avatar /></el-icon>
              <span>30岁以下</span>
            </div>
          </template>
          <div class="stat-value">{{ stats.youngUsers }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 技术栈展示 -->
    <el-row :gutter="16" style="margin-top: 20px">
      <el-col :span="8" v-for="card in cards" :key="card.title">
        <el-card shadow="hover" class="tech-card">
          <template #header>
            <div class="tech-header">
              <el-icon size="22" :color="card.color">
                <component :is="card.icon" />
              </el-icon>
              <span class="tech-title">{{ card.title }}</span>
            </div>
          </template>
          <ul class="tech-list">
            <li v-for="item in card.items" :key="item">{{ item }}</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>

    <!-- 学习路径 -->
    <el-card shadow="never" style="margin-top: 20px">
      <template #header>
        <div class="tech-header">
          <el-icon size="22" color="#f56c6c"><Reading /></el-icon>
          <span class="tech-title">学习路径建议</span>
        </div>
      </template>
      <ol class="learning-steps">
        <li>启动后端 Spring Boot 项目(<code>mvn spring-boot:run</code>)</li>
        <li>打开浏览器访问 <code>http://localhost:8080/h2-console</code> 查看数据</li>
        <li>回到前端(<code>npm run dev</code>),进入"用户管理"页面试试 CRUD</li>
        <li>打开浏览器 DevTools → Network,观察请求/响应</li>
        <li>修改前端组件或后端 Controller,刷新看效果</li>
      </ol>
    </el-card>
  </div>
</template>

<style scoped>
.stat-header,
.tech-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
}

.stat-value {
  font-size: 32px;
  font-weight: 600;
  color: #303133;
  text-align: center;
  padding: 8px 0;
}

.tech-title {
  font-size: 15px;
  font-weight: 500;
}

.tech-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.tech-list li {
  padding: 6px 0;
  color: #606266;
  border-bottom: 1px dashed #f0f0f0;
}

.tech-list li:last-child {
  border-bottom: none;
}

.tech-card {
  height: 100%;
}

.learning-steps {
  padding-left: 20px;
  line-height: 1.8;
  color: #606266;
}

.learning-steps code {
  background-color: #f5f7fa;
  padding: 2px 6px;
  border-radius: 3px;
  color: #e6a23c;
  font-family: 'Courier New', Courier, monospace;
}
</style>