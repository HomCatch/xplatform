<template>
  <div class="login" @keyup.enter="onSubmit">
    <div class="container" :style="containerMobileStyle">
      <el-form :model="loginInfo" label-width="0" :style="formMobileStyle">
        <el-form-item>
          <p>用户登录</p>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="24">
              <el-input v-model="loginInfo.username" placeholder="账号"> <i slot="prefix" class="el-input__icon icon-yonghu1 iconfont"></i></el-input>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="24">
              <el-input v-model="loginInfo.password" placeholder="密码" type="password">
                <i slot="prefix" class="el-input__icon icon-mima1 iconfont"></i>
              </el-input>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="12">
              <el-input v-model="loginInfo.captcha" placeholder="验证码">
                <i slot="prefix" class="el-input__icon icon-ad80-copy iconfont"></i>
              </el-input>
            </el-col>
            <el-col :span="10" style="text-align: right;">
              <img :src="imgValid" alt="" width="112px" @click="refreshImg" style="cursor: pointer;">
            </el-col>
            <el-col :span="2" style="text-align: center;color: #aaa;">
              <i class="el-icon-refresh" @click="refreshImg" style="cursor: pointer;"></i>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-row>
            <el-col :span="12">
              <el-button type="primary" @click="onSubmit" style="width: 100%;">登录</el-button>
            </el-col>
            <el-col :span="12">
              <p style="text-align: center; color: #409eff;cursor: pointer;">忘记密码</p>
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import { login, captcha } from "./api";
import imgValid1 from "@/common/img/imgValid.png";

export default {
  data() {
    return {
      loginInfo: {
        username: "",
        password: "",
        captcha: ""
      },
      imgValid: imgValid1,
      key: null,
      camera: null,
      scene: null,
      renderer: null,
      modelContainer: null,
      _destory: false,
      containerMobileStyle: "",
      formMobileStyle: ""
    };
  },
  created() {
    this.refreshImg();
  },
  mounted() {
    // 手机端调整登录页布局
    if (this.$store.state.user.useragent === "phone") {
      //执行代码.....
      window.onresize = function() {
        if (
          document.activeElement.tagName == "input" ||
          document.activeElement.tagName == "textarea"
        ) {
          setTimeout(function() {
            var top = document.activeElement.getBoundingClientRect().top;
            window.scrollTo(0, top);
          }, 0);
        }
      };
      this.containerMobileStyle = "align-items: flex-start";
      this.formMobileStyle = "padding: 10px;margin-top: 10px;";
    }
  },
  methods: {
    onSubmit() {
      this.loginInfo.key = this.key;
      login({
        ...this.loginInfo,
        captcha: this.loginInfo.captcha.toLocaleLowerCase()
      }).then(res => {
        if (res.data.ret == 0) {
          this.$store.commit("setToken", JSON.stringify(res.data.datas.token)); // 将token存入store
          this.$store.dispatch("setMenus");
          localStorage.setItem("token", res.data.datas.token);
          localStorage.setItem("username", this.loginInfo.username);
          localStorage.setItem("userId", res.data.datas.userId);
          this.$router.push({ path: "/content/gene-code" });
        } else {
          this.$message({
            message: res.data.msg,
            type: "error"
          });
          this.refreshImg();
          this.loginInfo.captcha = null;
        }
      });
    },
    refreshImg() {
      this.key = Math.random();
      this.imgValid = `/api/access/captcha?key=${this.key}`;
    }
  }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
