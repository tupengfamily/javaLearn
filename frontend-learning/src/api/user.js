/**
 * 用户 API
 * <p>
 * 所有与后端 user 相关的请求都在这里集中管理。
 * 后端地址: http://localhost:8080 (由 Vite 代理转发)
 */
import request from '../utils/request'

// GET /api/users - 查询所有
export function listAllUsers() {
  return request({
    url: '/users',
    method: 'get'
  })
}

// GET /api/users/{id} - 按 ID 查询
export function getUserById(id) {
  return request({
    url: `/users/${id}`,
    method: 'get'
  })
}

// POST /api/users - 创建
export function createUser(data) {
  return request({
    url: '/users',
    method: 'post',
    data
  })
}

// PUT /api/users/{id} - 更新
export function updateUser(id, data) {
  return request({
    url: `/users/${id}`,
    method: 'put',
    data
  })
}

// DELETE /api/users/{id} - 删除
export function deleteUser(id) {
  return request({
    url: `/users/${id}`,
    method: 'delete'
  })
}

// GET /api/users/search?keyword=xxx - 模糊查询
export function searchUsers(keyword) {
  return request({
    url: '/users/search',
    method: 'get',
    params: { keyword }
  })
}

// GET /api/users/age?min=&max= - 按年龄范围
export function findByAgeRange(min, max) {
  return request({
    url: '/users/age',
    method: 'get',
    params: { min, max }
  })
}