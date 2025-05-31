import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'https://at.alicdn.com/t/c/font_4937114_d9lxusciaop.js?spm=a313x.manage_type_myprojects.i1.10.78e33a81wkB7nW&file=font_4937114_d9lxusciaop.js' // iconfont Symbol引用

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

app.use(pinia)
app.use(router)
app.use(Antd)

app.mount('#app')
