<script setup>
/**
 * 数据字典
 * <p>
 * 左: 字典类型 / 右: 该类型下的字典项
 */
import { ref, reactive, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listDictTypes, createDictType, updateDictType, deleteDictType,
  listDictItems, createDictItem, updateDictItem, deleteDictItem
} from '../api/dict'

const types = ref([])
const items = ref([])
const loading = ref(false)
const selectedType = ref(null)

// 加载类型列表
const loadTypes = async () => {
  try {
    const r = await listDictTypes()
    types.value = r.data || []
    if (!selectedType.value && types.value.length) {
      selectedType.value = types.value[0]
      loadItems(selectedType.value.typeCode)
    }
  } catch (_) {}
}

const loadItems = async (code) => {
  if (!code) return
  loading.value = true
  try {
    const r = await listDictItems(code)
    items.value = r.data || []
  } catch (_) { items.value = [] } finally { loading.value = false }
}

watch(selectedType, (val) => {
  if (val) loadItems(val.typeCode)
})

onMounted(loadTypes)

// 新增类型
const typeDialog = ref(false)
const typeForm = reactive({ typeCode: '', typeName: '', description: '' })
const openCreateType = () => {
  Object.assign(typeForm, { typeCode: '', typeName: '', description: '' })
  typeDialog.value = true
}
const submitType = async () => {
  if (!typeForm.typeCode || !typeForm.typeName) {
    ElMessage.warning('请填写编码和名称')
    return
  }
  try {
    await createDictType(typeForm)
    ElMessage.success('创建成功')
    typeDialog.value = false
    loadTypes()
  } catch (_) {}
}
const onDeleteType = async (row) => {
  try {
    await ElMessageBox.confirm(`删除类型 "${row.typeCode}" 将同时删除其下所有项,确认?`, '删除确认', { type: 'warning' })
    await deleteDictType(row.id)
    ElMessage.success('删除成功')
    if (selectedType.value?.id === row.id) selectedType.value = null
    loadTypes()
  } catch (_) {}
}

// 新增项
const itemDialog = ref(false)
const itemForm = reactive({ id: null, typeId: null, itemCode: '', itemValue: '', sort: 0, status: 1, remark: '' })
const openCreateItem = () => {
  if (!selectedType.value) return ElMessage.warning('请先选择类型')
  Object.assign(itemForm, { id: null, typeId: selectedType.value.id, itemCode: '', itemValue: '', sort: 0, status: 1, remark: '' })
  itemDialog.value = true
}
const openEditItem = (row) => {
  Object.assign(itemForm, row)
  itemDialog.value = true
}
const submitItem = async () => {
  if (!itemForm.itemCode || !itemForm.itemValue) {
    ElMessage.warning('请填写编码和值')
    return
  }
  try {
    if (itemForm.id) {
      await updateDictItem(itemForm.id, {
        itemValue: itemForm.itemValue, sort: itemForm.sort, status: itemForm.status, remark: itemForm.remark
      })
    } else {
      await createDictItem({
        typeId: itemForm.typeId, itemCode: itemForm.itemCode, itemValue: itemForm.itemValue,
        sort: itemForm.sort, remark: itemForm.remark
      })
    }
    ElMessage.success('保存成功')
    itemDialog.value = false
    loadItems(selectedType.value.typeCode)
  } catch (_) {}
}
const onDeleteItem = async (row) => {
  try {
    await ElMessageBox.confirm(`确认删除项 "${row.itemCode}" ?`, '删除确认', { type: 'warning' })
    await deleteDictItem(row.id)
    ElMessage.success('删除成功')
    loadItems(selectedType.value.typeCode)
  } catch (_) {}
}
</script>

<template>
  <div class="page-container">
    <div class="page-header">
      <div class="page-title">数据字典</div>
      <div class="page-subtitle">系统通用字典: 左侧选类型,右侧维护项</div>
    </div>

    <div class="dict-layout">
      <!-- 左: 类型 -->
      <el-card shadow="never" class="left-pane">
        <template #header>
          <div style="display:flex;justify-content:space-between;align-items:center;">
            <strong>字典类型</strong>
            <el-button size="small" type="primary" @click="openCreateType"><el-icon><Plus /></el-icon></el-button>
          </div>
        </template>
        <el-menu :default-active="selectedType?.id?.toString()" @select="(idx) => selectedType = types.find(t => t.id == idx)">
          <el-menu-item v-for="t in types" :key="t.id" :index="t.id.toString()">
            {{ t.typeCode }} ({{ t.typeName }})
          </el-menu-item>
        </el-menu>
      </el-card>

      <!-- 右: 项 -->
      <el-card shadow="never" class="right-pane">
        <template #header>
          <div style="display:flex;justify-content:space-between;align-items:center;">
            <strong>{{ selectedType ? selectedType.typeCode + ' / ' + selectedType.typeName : '请选择类型' }}</strong>
            <div>
                <el-button size="small" @click="loadItems(selectedType.typeCode)" v-if="selectedType"><el-icon><Refresh /></el-icon></el-button>
                <el-button size="small" type="primary" @click="openCreateItem" v-if="selectedType"><el-icon><Plus /></el-icon>新增项</el-button>
              </div>
          </div>
        </template>
        <el-table :data="items" v-loading="loading" border stripe>
          <el-table-column prop="itemCode" label="编码" width="120" />
          <el-table-column prop="itemValue" label="值" />
          <el-table-column prop="sort" label="排序" width="80" />
          <el-table-column label="状态" width="80">
            <template #default="{ row }">
              <el-tag :type="row.status === 1 ? 'success' : 'danger'">
                {{ row.status === 1 ? '启用' : '禁用' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" />
          <el-table-column label="操作" width="160">
            <template #default="{ row }">
              <el-button size="small" @click="openEditItem(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="onDeleteItem(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 类型对话框 -->
    <el-dialog v-model="typeDialog" title="新增字典类型" width="450px">
      <el-form :model="typeForm" label-width="100px">
        <el-form-item label="编码">
          <el-input v-model="typeForm.typeCode" />
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="typeForm.typeName" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="typeForm.description" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="typeDialog = false">取消</el-button>
        <el-button type="primary" @click="submitType">确定</el-button>
      </template>
    </el-dialog>

    <!-- 项对话框 -->
    <el-dialog v-model="itemDialog" :title="itemForm.id ? '编辑字典项' : '新增字典项'" width="450px">
      <el-form :model="itemForm" label-width="100px">
        <el-form-item label="编码">
          <el-input v-model="itemForm.itemCode" :disabled="!!itemForm.id" />
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="itemForm.itemValue" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="itemForm.sort" :min="0" style="width:100%" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="itemForm.status">
            <el-radio :value="1">启用</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="itemForm.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="itemDialog = false">取消</el-button>
        <el-button type="primary" @click="submitItem">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.dict-layout { display: flex; gap: 16px; }
.left-pane { width: 240px; }
.right-pane { flex: 1; }
</style>