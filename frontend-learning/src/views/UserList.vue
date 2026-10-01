<script setup>
/**
 * 用户列表页
 * <p>
 * 演示:
 * - 列表展示
 * - 搜索 / 筛选
 * - 分页
 * - 新增 / 编辑 / 删除
 * - 对话框 / 表单校验
 */
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listAllUsers,
  createUser,
  updateUser,
  deleteUser,
  searchUsers,
  findByAgeRange
} from '../api/user'

// ========== 列表数据 ==========
const tableData = ref([])
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  keyword: '',
  ageMin: null,
  ageMax: null
})

// ========== 表单对话框 ==========
const dialogVisible = ref(false)
const dialogMode = ref('create') // 'create' | 'edit'
const formRef = ref(null)

// 表单数据
const formData = reactive({
  id: null,
  username: '',
  email: '',
  age: null,
  address: ''
})

// 表单校验规则
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2~50 之间', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
  ],
  age: [
    { required: true, message: '请输入年龄', trigger: 'blur' },
    { type: 'number', min: 0, max: 150, message: '年龄应在 0~150 之间', trigger: 'blur' }
  ]
}

// ========== 加载数据 ==========
const loadData = async () => {
  loading.value = true
  try {
    let result
    if (searchForm.ageMin !== null && searchForm.ageMax !== null) {
      // 按年龄范围
      result = await findByAgeRange(searchForm.ageMin, searchForm.ageMax)
    } else if (searchForm.keyword) {
      // 模糊查询
      result = await searchUsers(searchForm.keyword)
    } else {
      // 全部
      result = await listAllUsers()
    }
    tableData.value = result.data
  } catch (e) {
    tableData.value = []
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.keyword = ''
  searchForm.ageMin = null
  searchForm.ageMax = null
  loadData()
}

onMounted(loadData)

// ========== 新增 ==========
const handleCreate = () => {
  dialogMode.value = 'create'
  Object.assign(formData, {
    id: null,
    username: '',
    email: '',
    age: null,
    address: ''
  })
  dialogVisible.value = true
}

// ========== 编辑 ==========
const handleEdit = (row) => {
  dialogMode.value = 'edit'
  Object.assign(formData, row)
  dialogVisible.value = true
}

// ========== 删除 ==========
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确认删除用户 "${row.username}" 吗?`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    await deleteUser(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (e) {
    // 用户取消删除 / 删除失败
  }
}

// ========== 提交表单 ==========
const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return

    try {
      if (dialogMode.value === 'create') {
        await createUser(formData)
        ElMessage.success('创建成功')
      } else {
        await updateUser(formData.id, formData)
        ElMessage.success('更新成功')
      }
      dialogVisible.value = false
      loadData()
    } catch (e) {
      // 错误已被拦截器处理
    }
  })
}
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-title">用户管理</div>
      <div class="page-subtitle">
        演示前后端协作的 CRUD,数据来自 Spring Boot 后端
      </div>
    </div>

    <!-- 搜索栏 -->
    <el-form :inline="true" :model="searchForm" class="search-form">
      <el-form-item label="关键词">
        <el-input
          v-model="searchForm.keyword"
          placeholder="按用户名搜索"
          clearable
          style="width: 180px"
          @keyup.enter="loadData"
        />
      </el-form-item>
      <el-form-item label="年龄">
        <el-input-number v-model="searchForm.ageMin" :min="0" :max="150" placeholder="最小" style="width: 100px" />
        <span style="margin: 0 8px">-</span>
        <el-input-number v-model="searchForm.ageMax" :min="0" :max="150" placeholder="最大" style="width: 100px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">
          <el-icon><Search /></el-icon>查询
        </el-button>
        <el-button @click="resetSearch">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 工具栏 -->
    <div class="toolbar">
      <div class="toolbar-left">
        <el-button type="primary" @click="handleCreate">
          <el-icon><Plus /></el-icon>新增用户
        </el-button>
        <el-button @click="loadData">
          <el-icon><Refresh /></el-icon>刷新
        </el-button>
      </div>
      <div class="toolbar-right">
        <span class="record-count">共 {{ tableData.length }} 条记录</span>
      </div>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="username" label="用户名" min-width="120" />
      <el-table-column prop="email" label="邮箱" min-width="180" />
      <el-table-column prop="age" label="年龄" width="80" align="center" />
      <el-table-column prop="address" label="地址" min-width="150" />
      <el-table-column prop="createTime" label="创建时间" min-width="170" />
      <el-table-column label="操作" width="180" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleEdit(row)">
            <el-icon><Edit /></el-icon>编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(row)">
            <el-icon><Delete /></el-icon>删除
          </el-button>
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="暂无数据,请先启动后端服务" />
      </template>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogMode === 'create' ? '新增用户' : '编辑用户'"
      width="500px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formData.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="formData.age" :min="0" :max="150" style="width: 100%" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="formData.address" placeholder="请输入地址(可选)" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.search-form {
  margin-bottom: 16px;
  padding: 16px;
  background: #f5f7fa;
  border-radius: 4px;
}

.record-count {
  color: #909399;
  font-size: 13px;
}
</style>