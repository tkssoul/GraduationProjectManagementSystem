/**
 * 从 '@/utils/request' 导入请求工具
 */
import request from '@/utils/request'

/**
 * 获取成绩列表
 * @param {string} gradeStatus - 成绩状态参数
 * @param {string} Authorization - 用于身份验证的授权令牌
 * @returns {Promise<any>} 返回一个 Promise，该 Promise 解析为请求的响应数据
 */
export function getGradeList(gradeStatus: string) {
  return request({
    url: '/api/teacher/grades/pending',
    method: 'get',
    params: { gradeStatus: gradeStatus },
  }).then((res) => {
    return res.data
  })
}

/**
 * 成绩信息接口
 * @interface Grade
 * @property {number} gradeId - 成绩 ID
 * @property {string} gradeStatus - 成绩状态
 */
export interface Grade {
  gradeId: number
  gradeStatus: string
}

/**
 * 审核成绩
 * @param {Grade} data - 包含成绩 ID 和成绩状态的对象
 * @param {string} Authorization - 用于身份验证的授权令牌
 * @returns {Promise<any>} 返回一个 Promise，该 Promise 解析为请求的响应数据
 */
export function reviewGrade(data: Grade[]) {
  return request({
    url: '/api/teacher/grades/audit',
    method: 'put',
    data,
  }).then((res) => {
    return res.data
  })
}

/**
 * 学生查看自己的成绩
 * @param {string} id - 学生 ID 参数
 * @param {string} token - 用于身份验证的令牌
 * @returns {Promise<any>} 返回一个 Promise，该 Promise 解析为请求的响应数据
 */
export function getStudentGrade(id: string, token: string) {
  return request({
    url: `/api/student/grades/published/${id}`,
    method: 'get',
    headers: {
      token: token,
    },
  }).then((res) => {
    return res.data
  })
}
