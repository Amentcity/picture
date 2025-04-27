export default {
  requestLibPath: `import request from '@/request'`,
  schemaPath: 'http://localhost:8002/api/v3/api-docs',
  serversPath: './src',
  // 添加命名配置
  naming: {
    operationId: (info: any) => {
      // 自定义操作ID生成逻辑
      return info.path.replace(/\//g, '_') + '_' + info.method.toLowerCase();
    },
    tag: (tagName: string) => {
      // 保留原始中文标签名
      return tagName;
    }
  }
}
