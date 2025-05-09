// @ts-ignore
/* eslint-disable */
import request from '@/request'

/** 此处后端没有提供注释 GET /health */
export async function health(options?: { [key: string]: any }) {
  return request<string>('/health', {
    method: 'GET',
    ...(options || {}),
  })
}
