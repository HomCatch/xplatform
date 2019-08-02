<template>
  <div class="left">
    <el-menu :default-active="defaultActive" class="el-menu-vertical-demo" :collapse="isCollapse" router background-color="#2d4767" text-color="#fff" active-text-color="#ffd04b">
      <div class="logo">
        <div style="line-height: 50px;" v-if="!isCollapse">
          <img class="logo-img" style="vertical-align: middle" src="../../common/img/new_logo.png" alt="" width="30"><span style="padding-left: 10px;font-size: 16px;font-weight: 700;color: #fff;">Xplatform</span>
        </div>
        <img class="logo-img" v-if="isCollapse" src="../../common/img/new_logo.png" alt="">
      </div>
      <template v-for="router in menus">
        <el-submenu :index="router.url" :key="router.url" v-if="router.list.length !== 0 && router.list[0].type === 1">
          <template slot="title"><i :class="'iconfont ' + router.icon"></i><span class="title">{{router.name}}</span></template>
          <!-- <el-menu-item :index="subRouter.url" v-for="subRouter in router.list" :key="subRouter.url">{{subRouter.name}}</el-menu-item> -->
          <el-menu-item v-for="subRouter in router.list" :index="subRouter.url" :key="subRouter.url">
            <i :class="'iconfont ' + subRouter.icon"></i>
            <span class="title" slot="title">{{subRouter.name}}</span>
          </el-menu-item>
        </el-submenu>
        <el-menu-item :index="'/content/'+router.url" :key="router.url" v-else>
          <i :class="'iconfont ' + router.icon"></i>
          <span class="title" slot="title">{{router.name}}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>

<script>
import { _menuList } from "./menuList";
// import { getNav } from "@/service/api/header";
export default {
  name: "left",
  data() {
    return {
      // menus: []
    };
  },
  created() {
    // this.getNav();
  },
  methods: {
    // getNav() {
    //   getNav().then(res => {
    //     this.menus = res.data.menuList;
    //     // this.$store.commit('setMenus', res.data.menuList);
    //     // this.$store.commit('setfuncs', this.$route)
    //   });
    // }
  },
  computed: {
    menus(){
      return this.$store.state.user.menus;
    },
    defaultActive() {
      const path =
        this.$route.path === "/content/set-panel"
          ? "/content/dev"
          : this.$route.path;
      return path;
    },
    isCollapse() {
      return this.$store.state.app.isCollapse;
    },
    username() {
      return "username";
      let username = sessionStorage.getItem("username");
      return username;
    }
  }
};
</script>

<style lang="less" scoped>
@import "./left.less";
</style>

