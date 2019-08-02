import store from '@/store'
// import Main from '@/components/main/main'
import Content from '@/components/content/content'

// 根据不同环境切换是否开启懒加载
const _import = require('./_import_' + process.env.NODE_ENV)

// 这部分路由不作为main的子路由
const otherRoutes = [
  { path: '/', redirect: '/content/gene-code' },
  { path: '/login', name: 'login', meta: { title: '登录' }, component: _import('login/login') },
  { path: '/*', name: 'error', meta: { title: '404-页面不存在' }, component: _import('error/error') },
]

export const _routes = [
  // name  icon  path
  { path: 'role', name: 'role', icon: "icon-navicon-jsgl", meta: { title: '角色管理', icon: 'icon-navicon-jsgl', breadcrumbList: [{ title: '角色管理' }] }, component: _import('main/role/role') },
  { path: 'account', name: 'account', icon: "icon-zhanghaoguanli", meta: { title: '账号管理', icon: 'icon-zhanghaoguanli', breadcrumbList: [{ title: '账号管理' }] }, component: _import('main/account/account') },
  { path: 'gene-code', name: 'gene-code', component: _import('main/gene-code/gene-code') },
]

// content的子路由，并且在leftBar展示
export const appRoute = {
  path: '/content',
  component: Content,
  name: 'Content',
  redirect: '/content/gene-code',
  children: [..._routes]
}

// pc端router
let __routes = []
function isApp() {
  var userAgent = window.navigator.userAgent.toLowerCase();
  var bIsIpad = userAgent.match(/ipad/i) == "ipad";
  var bIsIphone = userAgent.match(/iphone os/i) == "iphone os";
  var bIsMidp = userAgent.match(/midp/i) == "midp";
  var bIsUc7 = userAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
  var bIsUc = userAgent.match(/ucweb/i) == "web";
  var bIsCE = userAgent.match(/windows ce/i) == "windows ce";
  var bIsWM = userAgent.match(/windows mobile/i) == "windows mobile";
  var bIsAndroid = userAgent.match(/android/i) == "android";

  if (
    bIsIpad ||
    bIsIphone ||
    bIsMidp ||
    bIsUc7 ||
    bIsUc ||
    bIsCE ||
    bIsWM ||
    bIsAndroid
  ) {
    __routes = [...phoneRouter]
    store.commit('setUseragent', 'phone')
  } else {
    __routes = [appRoute, ...otherRoutes]
    store.commit('setUseragent', 'pc')
  }
}
isApp()
export const routes = __routes
// phone端router
// export const routes = []
