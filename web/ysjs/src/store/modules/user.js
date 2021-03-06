import { getNav } from "@/service/api/header";
import store from '@/store'
const user = {
    state: {
        token: '',
        currentNav: '',     // 当前目录
        menus: [],
        funcs: [],          // 当前菜单功能按钮
        useragent: 'pc',    // 浏览器平台标识

    },
    mutations: {
        setToken(state, token) {
            state.token = token;
        },
        setCurrentNav(state, currentNav) {
            state.currentNav = currentNav;
        },
        setUseragent(state, useragent) {
            state.useragent = useragent;
        },
        setMenus(state, menus) {
            state.menus = menus
        },
        setfuncs(state, route) {
            let path = route.fullPath.split('/')[2];
            if(path === 'dev' && state.useragent === 'phone'){
                let _funcs = state.menus[0].list.map(item => item.name)
                _funcs = _funcs.filter(item => !['新增','修改','删除','商户管理'].includes(item))
                console.log(_funcs);
                state.funcs = _funcs
            }else{
                let funcs = state.menus.filter(item => {
                    if (path === 'coupon') {
                        return item.url === 'sales'
                    } else if (path === 'role' || path === 'account') {
                        return item.url === 'system'
                    } else if (path === 'ad') {
                        return item.url === 'market-manage'
                    } else {
                        let flag = item.url === path;
                        return flag
                    }
                })
                if (path === 'coupon' || path === 'role' || path === 'account' || path === 'ad') {
                    let _funcs = funcs[0].list.filter(item => {
                        return item.url === path
                    })
                    state.funcs = _funcs[0].list.map(item => item.name)
                } else {
                    if (funcs[0]) {
                        state.funcs = funcs[0].list.map(item => item.name);
                    } else {
                        state.funcs = []
                    }
                }
            }
        }
    },
    actions: {
        setMenus({ commit, state }) {
            getNav().then(res => {
                commit('setMenus', res.data.menuList)
            })
        },
        setfuncs({ commit, state }, route) {
            // 在此监测是否已获取menus，若未获取menus，一秒后重试
            fn(route)
        }
    }
}
function fn(route) {
    if (store.state.user.menus.length === 0) {
        window.s = setTimeout(() => {
            fn(route)
        }, 500);
    } else {
        store.commit('setfuncs', route)
    }
}
export default user
