import request from '../utils/request'

// GET /api/dashboard/summary
export const getSummary = () =>
  request({ url: '/dashboard/summary', method: 'get' })

// GET /api/dashboard/registrations
export const getRegistrations = () =>
  request({ url: '/dashboard/registrations', method: 'get' })

// GET /api/dashboard/operations
export const getOperations = () =>
  request({ url: '/dashboard/operations', method: 'get' })

// GET /api/dashboard/status-distribution
export const getStatusDistribution = () =>
  request({ url: '/dashboard/status-distribution', method: 'get' })