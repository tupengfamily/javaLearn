import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// Vite 构建配置
// 重点: 配置前端开发服务器代理(/api 请求转发到后端 localhost:8080)
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    open: true,
    // 代理配置: 解决前端开发时的跨域问题
    // 浏览器访问 http://localhost:5173/api/users
    // 实际请求转发到 http://localhost:8080/api/users
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  },
  build: {
    outDir: 'dist',
    sourcemap: false
  }
})