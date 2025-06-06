import axios from 'axios'
import type { AxiosResponse } from 'axios'

interface responseType extends AxiosResponse {
  code: string
  msg: string
}

// 创建 axios 实例
const instance = axios.create({
  baseURL: 'http://114.55.146.90:8080',
  timeout: 1000,
  headers: { 'Content-Type': 'application/json' },
})

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    // 处理请求错误
    console.log('统一拦截请求错误：', error)
    return Promise.reject(error)
  },
)

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    return response as responseType
  },
  (error) => {
    // 处理响应错误
    console.log('统一响应拦截器报错：' + error)
    return Promise.reject(error)
  },
)

export default instance
