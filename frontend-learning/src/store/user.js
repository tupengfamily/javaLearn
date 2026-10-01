import { defineStore } from 'pinia'
import { login as apiLogin, register as apiRegister, me as apiMe, logout as apiLogout } from '../api/auth'

/**
 * 用户状态管理(Pinia)
 * <p>
 * 状态: token + userInfo(持久化到 localStorage)
 * Getter: isLogin / roles / permissions / username
 * Action: login / register / fetchMe / logout
 */
export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('token') || '',
    userInfo: safeJson(localStorage.getItem('userInfo'))
  }),
  getters: {
    isLogin: (s) => !!s.token,
    userId: (s) => s.userInfo?.userId,
    username: (s) => s.userInfo?.username || '',
    roles: (s) => s.userInfo?.roles || [],
    permissions: (s) => s.userInfo?.permissions || []
  },
  actions: {
    /**
     * 登录: 调用后端 /auth/login,保存 token + 用户信息
     */
    async login(payload) {
      const resp = await apiLogin(payload)
      const data = resp.data
      this.token = data.token
      this.userInfo = data
      localStorage.setItem('token', data.token)
      localStorage.setItem('userInfo', JSON.stringify(data))
      return data
    },

    /**
     * 注册: 调用 /auth/register,自动登录
     */
    async register(payload) {
      const resp = await apiRegister(payload)
      const data = resp.data
      this.token = data.token
      this.userInfo = data
      localStorage.setItem('token', data.token)
      localStorage.setItem('userInfo', JSON.stringify(data))
      return data
    },

    /** 拉取当前用户信息 */
    async fetchMe() {
      const resp = await apiMe()
      // 保留 token 不变,只更新 userInfo
      this.userInfo = { ...this.userInfo, ...resp.data }
      localStorage.setItem('userInfo', JSON.stringify(this.userInfo))
      return this.userInfo
    },

    /** 登出: 调用后端 + 清本地状态 */
    async logout() {
      try { await apiLogout() } catch (_) { /* ignore */ }
      this.clear()
    },

    /** 仅清本地状态(401 时使用) */
    clear() {
      this.token = ''
      this.userInfo = null
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    }
  }
})

function safeJson(s) {
  try {
    return s ? JSON.parse(s) : null
  } catch (_) {
    return null
  }
}