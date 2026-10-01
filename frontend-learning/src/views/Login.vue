<script setup>
/**
 * 登录页
 * <p>
 * - 居中卡片
 * - 调用 auth.js.login,Pinia store 保存 token
 * - 成功后跳到 redirect 或 /dashboard
 */
import { ref, reactive } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../store/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: 'admin123'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const onSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      await userStore.login({ username: form.username, password: form.password })
      ElMessage.success('登录成功')
      const redirect = route.query.redirect || '/dashboard'
      router.push(redirect)
    } catch (e) {
      // 拦截器已弹窗
    } finally {
      loading.value = false
    }
  })
}
</script>

<template>
  <div class="auth-page">
    <el-card class="auth-card">
      <div class="auth-header">
        <h2>Java Web 管理系统</h2>
        <p>Spring Boot 3 + Vue 3 + Element Plus + SQLite</p>
      </div>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" size="large" @submit.prevent="onSubmit">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="默认 admin" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="默认 admin123" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-button type="primary" :loading="loading" style="width:100%" @click="onSubmit">登 录</el-button>
        <div class="auth-tip">
          还没有账号? <router-link to="/register">立即注册</router-link>
        </div>
        <div class="auth-tip" style="margin-top:8px;color:#909399;font-size:12px;">
          默认账号: admin / admin123, user / user123
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex; align-items: center; justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.auth-card {
  width: 420px;
  border-radius: 8px;
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.18);
}
.auth-header { text-align: center; margin-bottom: 24px; }
.auth-header h2 { margin: 0 0 6px 0; color: #303133; }
.auth-header p { margin: 0; color: #909399; font-size: 13px; }
.auth-tip { text-align: center; margin-top: 16px; font-size: 13px; }
.auth-tip a { color: #409eff; text-decoration: none; }
</style>