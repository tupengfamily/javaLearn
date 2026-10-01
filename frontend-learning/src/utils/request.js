/**
 * Axios 封装
 * <p>
 * 演示:
 * - 统一基础地址
 * - 请求拦截器(自动加 token)
 * - 响应拦截器(统一处理错误)
 * - 业务状态码处理
 */
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建 axios 实例
const request = axios.create({
  baseURL: '/api', // 配合 Vite 代理转发到后端
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 实际项目中可在此处添加 token
    // const token = localStorage.getItem('token')
    // if (token) config.headers.Authorization = `Bearer ${token}`
    console.log(`[Request] ${config.method.toUpperCase()} ${config.url}`)
    return config
  },
  (error) => {
    console.error('[Request Error]', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const data = response.data

    // 后端统一返回 { code, message, data }
    if (data.code !== 200) {
      ElMessage.error(data.message || '请求失败')
      return Promise.reject(new Error(data.message || '请求失败'))
    }

    return data
  },
  (error) => {
    console.error('[Response Error]', error)

    if (error.response) {
      const status = error.response.status
      const message = error.response.data?.message || error.message

      if (status === 400) {
        ElMessage.error(`请求错误: ${message}`)
      } else if (status === 401) {
        ElMessage.error('未授权,请重新登录')
      } else if (status === 404) {
        ElMessage.error('资源不存在')
      } else if (status === 500) {
        ElMessage.error(`服务器错误: ${message}`)
      } else {
        ElMessage.error(`请求失败 (${status}): ${message}`)
      }
    } else {
      ElMessage.error('网络异常,请检查后端服务是否启动')
    }

    return Promise.reject(error)
  }
)

export default request