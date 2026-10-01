<script setup>
const features = [
  {
    title: '前后端分离',
    desc: '前端 Vue 3 通过 axios 调用后端 REST API,完全解耦',
    icon: 'Promotion'
  },
  {
    title: '统一响应格式',
    desc: '后端 Controller 返回 {code, message, data},前端响应拦截器统一处理',
    icon: 'Connection'
  },
  {
    title: '代理转发',
    desc: 'Vite 配置 /api 代理到后端 8080,避免开发环境跨域问题',
    icon: 'Position'
  },
  {
    title: '参数校验',
    desc: '前端表单校验 + 后端 @Valid 双层校验,提升用户体验和数据安全',
    icon: 'CircleCheck'
  },
  {
    title: '统一异常处理',
    desc: '后端 @RestControllerAdvice 全局异常,前端响应拦截器弹窗提示',
    icon: 'WarningFilled'
  },
  {
    title: 'AOP 日志',
    desc: '后端 AOP 切面自动记录所有 Service 方法的调用和耗时',
    icon: 'Document'
  }
]

const stack = [
  { name: 'Vue', version: '3.5.x', desc: '渐进式 JavaScript 框架' },
  { name: 'Vue Router', version: '4.4.x', desc: '官方路由管理器' },
  { name: 'Element Plus', version: '2.8.x', desc: '基于 Vue 3 的组件库' },
  { name: 'Axios', version: '1.7.x', desc: 'HTTP 客户端' },
  { name: 'Vite', version: '6.0.x', desc: '下一代前端构建工具' },
  { name: 'Spring Boot', version: '3.3.5', desc: 'Java 后端框架' }
]
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-title">关于本项目</div>
      <div class="page-subtitle">Java Web 前后端协作学习项目</div>
    </div>

    <el-card shadow="never" style="margin-bottom: 20px">
      <h3 style="margin-bottom: 16px">📚 项目简介</h3>
      <p>
        本项目演示了一个完整的 Java Web 前后端分离架构。
        前端使用 <strong>Vue 3 + Element Plus</strong> 构建用户管理界面,
        后端使用 <strong>Spring Boot 3.3 + JPA</strong> 提供 REST API。
      </p>
      <p style="margin-top: 12px">
        通过本项目,你可以学到企业级 Java Web 项目中最常用的技术栈和协作模式。
      </p>
    </el-card>

    <h3 style="margin: 20px 0">✨ 核心特性</h3>
    <el-row :gutter="16">
      <el-col :span="8" v-for="f in features" :key="f.title" style="margin-bottom: 16px">
        <el-card shadow="hover" class="feature-card">
          <div class="feature-icon">
            <el-icon size="28" color="#409eff">
              <component :is="f.icon" />
            </el-icon>
          </div>
          <div class="feature-title">{{ f.title }}</div>
          <div class="feature-desc">{{ f.desc }}</div>
        </el-card>
      </el-col>
    </el-row>

    <h3 style="margin: 20px 0">🛠 技术栈</h3>
    <el-table :data="stack" border>
      <el-table-column label="技术" prop="name" width="180" />
      <el-table-column label="版本" prop="version" width="120" />
      <el-table-column label="说明" prop="desc" />
    </el-table>

    <h3 style="margin: 20px 0">📂 项目结构</h3>
    <pre class="code-block">frontend-learning/
├── package.json                # 依赖配置
├── vite.config.js              # 构建配置(含代理)
├── index.html                  # 入口 HTML
└── src/
    ├── main.js                 # 应用入口
    ├── App.vue                 # 根组件
    ├── router/
    │   └── index.js            # 路由
    ├── api/
    │   └── user.js             # 用户 API
    ├── utils/
    │   └── request.js          # axios 封装
    ├── components/
    │   └── Layout.vue          # 整体布局
    └── views/
        ├── Home.vue            # 首页
        ├── UserList.vue        # 用户列表
        └── About.vue           # 关于</pre>

    <h3 style="margin: 20px 0">🚀 快速开始</h3>
    <pre class="code-block"># 1. 安装依赖
npm install

# 2. 启动开发服务器
npm run dev

# 3. 浏览器访问
http://localhost:5173</pre>

    <el-alert
      title="⚠️ 注意"
      type="warning"
      show-icon
      :closable="false"
      style="margin-top: 16px"
    >
      启动前端前,请确保后端 Spring Boot 项目已经启动(<code>cd ../spring-boot-learning && mvn spring-boot:run</code>)。
      前端通过 Vite 代理将 /api 请求转发到后端 8080 端口。
    </el-alert>
  </div>
</template>

<style scoped>
.feature-card {
  height: 100%;
  text-align: center;
  padding: 16px 0;
}

.feature-icon {
  margin-bottom: 8px;
}

.feature-title {
  font-size: 15px;
  font-weight: 500;
  margin-bottom: 4px;
}

.feature-desc {
  font-size: 13px;
  color: #909399;
  line-height: 1.5;
}

.code-block {
  background: #f5f7fa;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 16px;
  font-family: 'Courier New', Courier, monospace;
  font-size: 13px;
  line-height: 1.6;
  color: #303133;
  overflow-x: auto;
  white-space: pre;
}

h3 {
  color: #303133;
  font-weight: 600;
}

p {
  color: #606266;
  line-height: 1.7;
}

code {
  background: #f5f7fa;
  padding: 2px 6px;
  border-radius: 3px;
  color: #e6a23c;
  font-family: 'Courier New', Courier, monospace;
}
</style>