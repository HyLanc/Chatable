import { createApp } from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
const videoPlay  = require('vue3-video-play');
import 'vue3-video-play/dist/style.css' // 引入css
const app = createApp(App);
app.use(ElementUI);
app.use(VueViewer);
app.use(videoPlay);
app.use(router).mount('#app');

import Eloading from "@/utils/Eloading";


let Promise: any;
let loading: any;
axios.interceptors.request.use(config => {
    console.log("axios请求前处理...");
    loading = Eloading.initLoading();
    return config
}, error => {
    return Promise.error(error)
});

axios.interceptors.response.use(config => {
    console.log("axios请求后处理...");
    Eloading.closeLoading(loading);
    return config
}, error => {
    return Promise.error(error)
});
