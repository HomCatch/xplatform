# Xplatform后台模板

## 项目版本切换
1.直接

### 自动生成代码功能
#### 生成代码比如`order`
##### 前端操作：
1. 进入生成代码菜单，选择页面依赖表，点击生成代码。
2. 将`E:\temps\com\xiaohe\xplatform\modules\orders\js\order`目录拷贝到前端项目`src/views`下，然后在`src/route/routes.js`中添加该路由 `{ path: 'orders', name: '订单列表', component: () => import("@/views/orders/orders") }`
##### 后台操作：
1. `E:\temps\com\xiaohe\xplatform\modules`目录下对应文件拷贝至后台项目`src/main/java/com.xiaohe.xplatform/modules`下，然后删除`js/`和`sql/`两个目录
##### 数据库操作：

###


### 
# Xplatform后台模板

## 项目版本切换
1.直接在POM中切换SpringBoot版本 ，maven clean下 即可生效

### 自动生成代码功能
#### 生成代码比如`orders`表
##### 前端操作：
1. 进入生成代码菜单，勾选页面依赖表，点击生成代码。
2. 将`E:\temps\com\xiaohe\xplatform\modules\orders\js\order`目录拷贝到前端项目`src/views`下，然后在`src/route/routes.js`中添加该路由 `{ path: 'orders', name: '订单列表', component: () => import("@/views/orders/orders") }`
##### 后台操作：
1. `E:\temps\com\xiaohe\xplatform\modules`目录下对应的orders文件夹拷贝至后台项目`src/main/java/com.xiaohe.xplatform/modules`下，然后删除`js/`和`sql/`两个目录
##### 数据库操作：
1.编辑在对应模块下的生成的sql脚本，名称为menu.sql，默认有插入四条数据到菜单表
2.示例说明
参数说明：
menu_id:菜单主键
parent_id:父目录ID 没有填0
name：菜单名称 页面显示用
url：web端url路径 ，增删改默认NULL
perms：授权信息
icon：菜单图标
order_num：菜单顺序 按大小排序
child_type：0:下级为按钮 1:下级为菜单
INSERT INTO `xplatform`.`sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`,`child_type`) VALUES (1, 0, 'orders', 'orders', NULL, 0, NULL, 1, 0);
INSERT INTO `xplatform`.`sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`,`child_type`) VALUES (11, 1, '新增', NULL, NULL, 0, NULL, 1, 0);
INSERT INTO `xplatform`.`sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`,`child_type`) VALUES (12, 1, '修改', NULL, NULL, 0, NULL, 2, 0);
INSERT INTO `xplatform`.`sys_menu`(`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`,`child_type`) VALUES (13, 1, '删除', NULL, NULL, 0, NULL, 3, 0);

###


### 
