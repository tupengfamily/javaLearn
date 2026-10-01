import request from '../utils/request'

// GET /api/users/page
export const pageUsers = (params) =>
  request({ url: '/users/page', method: 'get', params })

// GET /api/users/{id}
export const getUserById = (id) =>
  request({ url: `/users/${id}`, method: 'get' })

// GET /api/users
export const listAllUsers = () =>
  request({ url: '/users', method: 'get' })

// POST /api/users
export const createUser = (data) =>
  request({ url: '/users', method: 'post', data })

// PUT /api/users/{id}
export const updateUser = (id, data) =>
  request({ url: `/users/${id}`, method: 'put', data })

// PUT /api/users/{id}/status
export const updateStatus = (id, status) =>
  request({ url: `/users/${id}/status`, method: 'put', data: { status } })

// PUT /api/users/{id}/password
export const changePassword = (id, data) =>
  request({ url: `/users/${id}/password`, method: 'put', data })

// PUT /api/users/{id}/roles
export const assignRoles = (id, roleCodes) =>
  request({ url: `/users/${id}/roles`, method: 'put', data: { roleCodes } })

// DELETE /api/users/{id}
export const deleteUser = (id) =>
  request({ url: `/users/${id}`, method: 'delete' })

// GET /api/users/search?keyword=
export const searchUsers = (keyword) =>
  request({ url: '/users/search', method: 'get', params: { keyword } })

// GET /api/users/age?min=&max=
export const findByAgeRange = (min, max) =>
  request({ url: '/users/age', method: 'get', params: { min, max } })