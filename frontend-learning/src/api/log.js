import request from '../utils/request'

// GET /api/logs/page
export const pageLogs = (params) =>
  request({ url: '/logs/page', method: 'get', params })

// DELETE /api/logs/clean?before=
export const cleanLogs = (before) =>
  request({ url: '/logs/clean', method: 'delete', params: { before } })