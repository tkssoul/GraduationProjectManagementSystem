import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import viteCompression from 'vite-plugin-compression'

// https://vite.dev/config/
export default defineConfig({
  base: '/GraduationProjectManagementSystem/', // 设置基础路径，适用于 GitHub Pages
  plugins: [
    vue(),
    vueDevTools(),
    viteCompression({
      verbose: true, // 是否在控制台输出压缩结果
      disable: false, // 是否禁用压缩，默认为 false
      threshold: 10240, // 对大于 10kb 的文件进行压缩
      algorithm: 'brotliCompress', // 压缩算法
      ext: '.br', // 生成的压缩包后缀
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  build: {
    target: 'esnext',
    outDir: 'docs', // 输出目录，用于GitHub Pages
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true,
        drop_debugger: true,
      },
    },
    rollupOptions: {
      output: {
        manualChunks(id) {
          // 将 Ant Design Vue 单独打包
          if (id.includes('ant-design-vue')) {
            return 'ant-design-vue'
          }
          // 将 Pinia 相关库打包到一起
          if (id.includes('pinia')) {
            return 'pinia'
          }
          // 将 Vue Router 单独打包
          if (id.includes('vue-router')) {
            return 'vue-router'
          }
          // 其他 node_modules 中的依赖统一打包到 vendor
          if (id.includes('node_modules')) {
            return 'vendor'
          }
        },
      },
    },
    // Vite 在生产构建时默认会进行摇树优化和代码压缩。
    // CSS 压缩默认使用 esbuild，JS 压缩默认使用 Terser (可以通过 build.minify 配置)。
    // 如果需要更细致的 Terser 配置，可以参考 Vite 官方文档。
  },
})
