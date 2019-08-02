<template>
  <div class="account top-border">
    <!-- 搜索 -->
    <el-form :inline="true" :model="searchData" class="demo-form-inline">
      <el-form-item label="账户名：">
        <el-input v-model="searchData.name" placeholder="账户名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格 -->
    <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func" @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>

    <!-- 编辑/新增 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="dialogData" :rules="rules" ref="ruleForm" label-width="150px" class="demo-ruleForm">
        <el-form-item label="账户名：" prop="username">
          <el-input v-model="dialogData.username"></el-input>
        </el-form-item>
        <el-form-item label="角色：" prop="roleIdList">
          <!-- 角色列表 -->
          <!-- <el-input v-model="dialogData.roleIdList"></el-input> -->
          <el-select v-model="dialogData.roleIdList" multiple placeholder="请选择" style="width: 100%;">
            <el-option v-for="item in allRole" :key="item.roleId" :label="item.roleName" :value="item.roleId">
            </el-option>
          </el-select>
          <!-- <el-select v-model="dialogData.roleIdList">
            <el-option v-for="item in allRole" :label="item.roleName" :value="item.roleId" :key="item.roleId"></el-option>
          </el-select> -->
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input v-model="dialogData.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="手机号：" prop="mobile">
          <el-input v-model="dialogData.mobile"></el-input>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input v-model="dialogData.remark" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="ok" :loading="oking">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ZTable from "@/components/z-table/z-table";
import {
  users,
  getUser,
  addUser,
  editUser,
  delUser
} from "./api";
import { tableColumns } from "./data";
import { roleList } from "../role/api";
import Moment from "moment";
export default {
  data() {
    return {
      headers: {
        token: null
      },
      searchData: {},
      tableData: [],
      tableColumns,
      page: {
        total: 0,
        currentPage: 1
      },
      tableLoading: false,
      dialogTitle: "编辑",
      dialogFormVisible: false,
      dialogData: {
        wxConfig: {}
      },
      rules: {},
      defaultProps: {
        children: "children",
        label: "name"
      },
      allUnbindDev: [],
      allRole: [],
      oking: false,
      total: 0,
      currentPage: 1,
      user_id: null,
      selectedIds: []
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.headers.token = localStorage.getItem("token");
  },
  methods: {
    formatterTime(row, column, cellValue) {
      return Moment(cellValue).format("YYYY-MM-DD hh:mm:ss");
    },
    getList() {
      users({ limit: 10, page: this.page.currentPage }).then(res => {
        if (res.data.code === 0) {
          this.tableData = res.data.page.list;
          console.log(res.data.page)
          this.page.total = res.data.page.totalCount;
        }
      });
    },
    getRoles() {
      roleList().then(res => {
        this.allRole = res.data.page.list;
      });
    },
    func({ opera, row }) {
      switch (opera) {
        case "修改":
          this.edit(row);
          break;
        case "新增":
          this.add();
          break;
        case "删除":
          this.del(row);
          break;
      }
    },
    currentChange(val) {
      this.currentPage = val;
    },
    handleCurrentChange(val) {
      this.page.currentPage = val;
      this.getList();
    },
    // 保存编辑或新增
    ok() {
      this.oking = true;
      if (this.dialogTitle === "编辑") {
        editUser({
          ...this.dialogData
        })
          .then(res => {
            this.oking = false;
            if (res.data.code === 0) {
              this.getList();
              this.$message({
                message: "修改成功",
                type: "success"
              });
              this.dialogFormVisible = false;
            } else {
              this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          })
          .catch(() => {
            this.oking = false;
          });
      } else if (this.dialogTitle === "新增") {
        addUser({
          ...this.dialogData
        })
          .then(res => {
            this.oking = false;
            if (res.data.code === 0) {
              this.getList();
              this.$message({
                message: "新增成功",
                type: "success"
              });
              this.dialogFormVisible = false;
            } else {
              this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          })
          .catch(() => {
            this.oking = false;
          });
      }
    },
    edit(row) {
      this.dialogTitle = "编辑";
      this.currentPage = 1;
      this.user_id = row.userId;
      this.getRoles();
      getUser({ user_id: row.userId }).then(res => {
        if (res.data.code === 0) {
          this.dialogData = { ...res.data.user };
          this.dialogData.wxConfig = this.dialogData.wxConfig || {};
          this.selectedIds = this.dialogData.deviceIdList;
        }
      });
      this.dialogFormVisible = true;
    },
    add() {
      this.dialogTitle = "新增";
      this.currentPage = 1;
      this.user_id = null;
      this.selectedIds = [];
      this.getRoles();
      this.dialogData = { wxConfig: {} };
      this.dialogFormVisible = true;
    },
    search() {
      console.log("submit!");
    },
    del(row) {
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          delUser([row.userId]).then(res => {
            if (res.data.code === 0) {
              this.getList();
              this.$message({
                message: "删除成功",
                type: "success"
              });
            } else {
              this.$message({
                message: res.data.msg,
                type: "error"
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    confirmDel(row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.del(row);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  },
  computed: {
    funcs() {
      return this.$store.state.user.funcs;
    }
  },
  components: {  ZTable }
};
</script>

<style lang="less" scoped>
@import "./index.less";
</style>
