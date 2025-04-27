import { createApp } from 'vue'
import { createPinia } from 'pinia'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css';
import App from './App.vue'
import router from './router'
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
import '@/access'
const app = createApp(App)

dayjs().locale('zh-cn');
app.use(createPinia())
app.use(router)
app.use(Antd)
app.mount('#app')
