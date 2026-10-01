import request from '../utils/request'

// POST /api/auth/login
export const login = (data) =>
  request({ url: '/auth/login', method: 'post', data })

// POST /api/auth/register
export const register = (data) =>
  request({ url: '/auth/register', method: 'post', data })

// GET /api/auth/me
export const me = () =>
  request({ url: '/auth/me', method: 'get' })

// POST /api/auth/logout
export const logout = () =>
  request({ url: '/auth/logout', method: 'post' })