import request from '../utils/request'

// GET /api/permissions
export const listPermissions = () =>
  request({ url: '/permissions', method: 'get' })

// POST /api/permissions
export const createPermission = (data) =>
  request({ url: '/permissions', method: 'post', data })

// DELETE /api/permissions/{id}
export const deletePermission = (id) =>
  request({ url: `/permissions/${id}`, method: 'delete' })