<template>
    <div class="box">
        <!-- 搜索 -->
        <z-search :searchItems="searchItems" @search="search"></z-search>
        <!-- 功能+table -->
        <z-table :tableData="tableData" :tableColumns="tableColumns" :page="page" :funcs="funcs" @func="func"
                 @handleCurrentChange="handleCurrentChange" v-loading="tableLoading"></z-table>
        <!-- 新增/编辑dialog -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
            <el-form :model="dialogData" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                                <!--不生成创建时间字段-->
                                                                                                                            <el-form-item label="0:使用中，1：未使用:" prop="status">
                                <el-input v-model="dialogData.status"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="该设备对应的代理商（用户）ID,默认值为1是小荷用户:" prop="bindedUserId">
                                <el-input v-model="dialogData.bindedUserId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="二级代理商Id:" prop="bindedSubUserId">
                                <el-input v-model="dialogData.bindedSubUserId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="经销商ID（用户Id）:" prop="userId">
                                <el-input v-model="dialogData.userId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="方案Id:" prop="schemaId">
                                <el-input v-model="dialogData.schemaId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="所属代理商名称:" prop="brandname">
                                <el-input v-model="dialogData.brandname"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="deviceId">
                                <el-input v-model="dialogData.deviceId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="备注:" prop="remark">
                                <el-input v-model="dialogData.remark"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="shakeHandTime">
                                <el-input v-model="dialogData.shakeHandTime"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="记录时间:" prop="updateTime">
                                <el-input v-model="dialogData.updateTime"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化前tds 值:" prop="tds">
                                <el-input v-model="dialogData.tds"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化前色度:" prop="color">
                                <el-input v-model="dialogData.color"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化前温度:" prop="trt">
                                <el-input v-model="dialogData.trt"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化前水浊度:" prop="tbdt">
                                <el-input v-model="dialogData.tbdt"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化后的tds:" prop="purifyTds">
                                <el-input v-model="dialogData.purifyTds"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化后的色度:" prop="purifyColor">
                                <el-input v-model="dialogData.purifyColor"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化后的温度:" prop="purifyTrt">
                                <el-input v-model="dialogData.purifyTrt"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="净化后的水浊度:" prop="purifyTbdt">
                                <el-input v-model="dialogData.purifyTbdt"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="水量:" prop="amount">
                                <el-input v-model="dialogData.amount"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="经度:" prop="lng">
                                <el-input v-model="dialogData.lng"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="纬度:" prop="lat">
                                <el-input v-model="dialogData.lat"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="province">
                                <el-input v-model="dialogData.province"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="city">
                                <el-input v-model="dialogData.city"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="设备别名:" prop="deviceName">
                                <el-input v-model="dialogData.deviceName"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="区:" prop="area">
                                <el-input v-model="dialogData.area"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="地址:" prop="address">
                                <el-input v-model="dialogData.address"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="customer">
                                <el-input v-model="dialogData.customer"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label=":" prop="phoneNumber">
                                <el-input v-model="dialogData.phoneNumber"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="机型 :" prop="model">
                                <el-input v-model="dialogData.model"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="imei:" prop="imei">
                                <el-input v-model="dialogData.imei"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="商家:" prop="merchantName">
                                <el-input v-model="dialogData.merchantName"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="通道:" prop="passageId">
                                <el-input v-model="dialogData.passageId"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="显示温度:" prop="showTemp">
                                <el-input v-model="dialogData.showTemp"></el-input>
                            </el-form-item>
                                                                                                                                    <el-form-item label="硬件版本:" prop="hardwareVersion">
                                <el-input v-model="dialogData.hardwareVersion"></el-input>
                            </el-form-item>
                                                                        </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitForm('ruleForm')" :loading="btnLoading">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import ZTable from "@/components/z-table/z-table";
    import ZSearch from "@/components/z-search/z-search";
    import {
        getList,
        del,
        add,
        edit
    } from "./api.js";
    import {tableColumns, searchItems} from "./data";
    import * as util from "@/common/js/util";

    export default {
        data() {
            return {
                tableData: [],
                page: {
                    total: 0,
                    currentPage: 1
                },
                tableColumns,
                searchItems,
                tableLoading: false,
                btnLoading: false,
                searchForm: {},
                dialogTitle: "新增",
                dialogVisible: false,
                labelWidth: "100px",
                dialogData: {
                    schemalist: []
                },
                allDev: []
            };
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.tableLoading = true;
                const params = {page: this.page.currentPage,...this.searchForm
            };
                getList(params).then(res => {
                this.tableLoading = false;
                this.tableData = res.datas.list;
                this.page.total = res.datas.itemCounts;
            })
                ;
            },
            search(searchForm) {
                console.log(searchForm);
                this.page.currentPage = 1;
                this.searchForm = searchForm;
                this.getList();
            },
            func({opera, row}) {
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
            add() {
                this.dialogTitle = "新增";
                this.dialogData = {};
                this.dialogVisible = true;
            },
            edit(row) {
                this.dialogTitle = "编辑";
                this.dialogData = {...row};
                this.dialogVisible = true;
            },
            del(row) {
                row = row.map(item => item.id)
                this.$confirm("此操作将永久删除所选数据, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                })
                        .then(() => {
                    del({id: row}).then(res => {
                    console.log("shanchu ");
                this.$message({
                    message: res.ret === 0 ? "删除成功" : res.msg,
                    type: res.ret === 0 ? "success" : "error"
                });
                this.getList();
            });
            })
            .catch(() => {
                    this.$message({
                    type: "info",
                    message: "已取消删除"
                });
            });
            },
            handleCurrentChange(val) {
                this.page.currentPage = val;
                this.getList();
            },
            submitForm(formName) {
                //不解析下面代码
            
                this.$refs[formName].validate(valid => {
                if (valid) {
                const params = this.dialogData;
                if (this.dialogTitle === "编辑") {
                edit({
                id: this.dialogData.id,
                data: {...this.dialogData}
                }).then(res => {
                if (res.ret == 0) {
                this.$message({
                message: "修改成功",
                type: "success"
                });
                this.dialogVisible = false;
                this.getList();
                } else {
                this.$message({
                message: res.msg,
                type: "error"
                });
                }
                });
                } else if (this.dialogTitle === "新增") {
                add(params).then(res => {
                if (res.ret == 0) {
                this.$message({
                message: "新增成功",
                type: "success"
                });
                this.dialogVisible = false;
                this.getList();
                } else {
                this.$message({
                message: res.msg,
                type: "error"
                });
                }
                });
                }
                } else {
                return false;
                }
                });
            }
        },
        computed: {
            funcs() {
                return util.funcsParse(this.$route, this.$store.state.auth.menus);
            }
        },
        components: {ZTable, ZSearch}
    
    };
</script>

<style scoped lang="scss">
    @import "./index.scss";
</style>
