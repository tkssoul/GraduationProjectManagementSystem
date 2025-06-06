import request from '@/utils/request'

/**
 * 重置密码所需的参数接口
 * @interface ResetPasswordParams
 * @property {number} id - 用户 ID
 * @property {string} newPassword - 新密码
 * @property {string} newPasswordAgain - 再次确认的新密码
 */
export interface ResetPasswordParams {
  id?: number
  newPassword: string
  newPasswordAgain: string
}

/**
 * 重置用户密码
 * @param {ResetPasswordParams} data - 重置密码所需的参数
 * @returns {Promise<any>} 返回重置密码结果的 Promise
 */
export function resetPassword(data: ResetPasswordParams) {
  return request({
    url: `/api/reset_password`,
    method: 'put',
    data,
  })
}

/**
 * 登录所需的参数接口
 * @interface loginParams
 * @property {number} [studentId] - 学生 ID（可选）
 * @property {number} [teacherId] - 教师 ID（可选）
 * @property {string} password - 密码
 */
interface loginParams {
  studentId?: number
  teacherId?: number
  password: string
}

/**
 * 学生登录
 * @param {loginParams} data - 登录所需的参数
 * @returns {Promise<any>} 返回学生登录结果的 Promise
 */
export function loginStudent(data: loginParams) {
  return request({
    url: '/api/student/login',
    method: 'post',
    data,
  }).then((response) => {
    return response.data
  })
}

/**
 * 学生退出登录接口，传递 Header 参数 token
 * @param {string} token - 用于认证的 token 字符串
 * @returns {Promise<any>} 返回学生退出登录结果的 Promise
 */
export function logoutStudent(token: string) {
  return request({
    url: '/api/student/logout',
    method: 'post',
    headers: {
      token: token,
    },
  }).then((response) => {
    return response.data
  })
}

/**
 * 教师登录
 * @param {loginParams} data - 登录所需的参数
 * @returns {Promise<any>} 返回教师登录结果的 Promise
 */
export function loginTeacher(data: loginParams) {
  return request({
    url: '/api/teacher/login',
    method: 'post',
    data,
  }).then((response) => {
    return response.data
  })
}

/**
 * 教师退出登录
 * @param {string} Authorization - 用于认证的 Authorization 字符串
 * @returns {Promise<any>} 返回教师退出登录结果的 Promise
 */
export function logoutTeacher(Authorization: string) {
  return request({
    url: '/api/student/logout',
    method: 'post',
    headers: {
      Authorization: Authorization,
    },
  }).then((response) => {
    return response.data
  })
}
