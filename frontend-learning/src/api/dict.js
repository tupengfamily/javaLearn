import request from '../utils/request'

// 类型
export const listDictTypes = () =>
  request({ url: '/dicts/types', method: 'get' })

export const createDictType = (data) =>
  request({ url: '/dicts/types', method: 'post', data })

export const updateDictType = (id, data) =>
  request({ url: `/dicts/types/${id}`, method: 'put', data })

export const deleteDictType = (id) =>
  request({ url: `/dicts/types/${id}`, method: 'delete' })

// 项
export const listDictItems = (typeCode) =>
  request({ url: '/dicts/items', method: 'get', params: { typeCode } })

export const createDictItem = (data) =>
  request({ url: '/dicts/items', method: 'post', data })

export const updateDictItem = (id, data) =>
  request({ url: `/dicts/items/${id}`, method: 'put', data })

export const deleteDictItem = (id) =>
  request({ url: `/dicts/items/${id}`, method: 'delete' })