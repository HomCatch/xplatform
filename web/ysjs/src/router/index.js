import Vue from 'vue'
import Router from 'vue-router'
import { routes } from './routes'
import store from '@/store'
// import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

const router = new Router({ routes })

export default router

router.beforeEach((to, from, next) => {
  if (to.name !== 'api') {
    store.dispatch("setMenus");
    store.dispatch('setfuncs', to);
  }
  next()
})

router.afterEach((to) => {
  window.scrollTo(0, 0)
})
