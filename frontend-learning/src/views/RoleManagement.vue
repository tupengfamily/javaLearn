<script setup>
/**
 * 角色管理
 */
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { listRoles, createRole, updateRole, deleteRole, assignPermissions } from '../api/role'
import { listPermissions } from '../api/permission'

const tableData = ref([])
const loading = ref(false)
const allPermissions = ref([])

const loadData = async () => {
  loading.value = true
  try {
    const r = await listRoles()
    tableData.value = r.data || []
  } catch (_) {} finally { loading.value = false }
}

const loadPerms = async () => {
  try {
    const r = await listPermissions()
    allPermissions.value = r.data || []
  } catch (_) {}
}

onMounted(() => { loadData(); loadPerms() })

// 编辑对话框
const dialogVisible = ref(false)
const dialogMode = ref('create')
const formRef = ref(null)
const form = reactive({
  id: null, code: '', name: '', description: '', permissionIds: []
})
const rules = {
  code: [{ required: true, message: '请输入编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }]
}

const openCreate = () => {
  dialogMode.value = 'create'
  Object.assign(form, { id: null, code: '', name: '', description: '', permissionIds: [] })
  dialogVisible.value = true
}
const openEdit = (row) => {
  dialogMode.value = 'edit'
  Object.assign(form, {
    id: row.id, code: row.code, name: row.name, description: row.description,
    permissionIds: allPermissions.value.filter(p => (row.permissionCodes || []).includes(p.code)).map(p => p.id)
  })
  dialogVisible.value = true
}
const submitForm = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    try {
      if (dialogMode.value === 'create') {
        await createRole({ code: form.code, name: form.name, description: form.description, permissionIds: form.permissionIds })
      } else {
        await updateRole(form.id, { code: form.code, name: form.name, description: form.description, permissionIds: form.permissionIds })
      }
      ElMessage.success('保存成功')
      dialogVisible.value = false
      loadData()
    } catch (_) {}
  })
}

const onDelete = async (row) => {
  try {
    await ElMessageBox.confirm(`确认删除角色 "${row.code}" ?`, '删除确认', { type: 'warning' })
    await deleteRole(row.id)
    ElMessage.success('删除成功')
    loadData()
  } catch (_) {}
}

// 权限分配对话框
const permDialogVisible = ref(false)
const permForm = reactive({ id: null, permissionIds: [] })
const openPerm = (row) => {
  permForm.id = row.id
  permForm.permissionIds = allPermissions.value
    .filter(p => (row.permissionCodes || []).includes(p.code))
    .map(p => p.id)
  permDialogVisible.value = true
}
const submitPerms = async () => {
  try {
    await assignPermissions(permForm.id, permForm.permissionIds)
    ElMessage.success('权限已更新')
    permDialogVisible.value = false
    loadData()
  } catch (_) {}
}
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-title">角色管理</div>
      <div class="page-subtitle">管理后台角色及权限分配</div>
    </div>

    <div class="toolbar">
      <el-button type="primary" @click="openCreate"><el-icon><Plus /></el-icon>新增角色</el-button>
      <el-button @click="loadData"><el-icon><Refresh /></el-icon>刷新</el-button>
    </div>

    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="code" label="编码" width="120" />
      <el-table-column prop="name" label="名称" width="160" />
      <el-table-column prop="description" label="描述" min-width="200" />
      <el-table-column label="权限" min-width="200">
        <template #default="{ row }">
          <el-tag v-for="c in row.permissionCodes" :key="c" size="small" type="info" style="margin:2px;">{{ c }}</el-tag>
          <span v-if="!row.permissionCodes?.length" style="color:#c0c4cc;">无</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="openEdit(row)">编辑</el-button>
          <el-button size="small" type="primary" @click="openPerm(row)">分配权限</el-button>
          <el-button size="small" type="danger" :disabled="row.code === 'ADMIN'" @click="onDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑 -->
    <el-dialog v-model="dialogVisible" :title="dialogMode === 'create' ? '新增角色' : '编辑角色'" width="560px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="编码" prop="code">
          <el-input v-model="form.code" :disabled="dialogMode === 'edit'" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" />
        </el-form-item>
        <el-form-item label="权限">
          <el-checkbox-group v-model="form.permissionIds">
            <el-checkbox v-for="p in allPermissions" :key="p.id" :value="p.id">
              {{ p.code }} ({{ p.name }})
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 权限分配 -->
    <el-dialog v-model="permDialogVisible" title="分配权限" width="560px">
      <el-form :model="permForm" label-width="100px">
        <el-form-item label="权限">
          <el-checkbox-group v-model="permForm.permissionIds">
            <el-checkbox v-for="p in allPermissions" :key="p.id" :value="p.id">
              {{ p.code }} ({{ p.name }})
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="permDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPerms">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>