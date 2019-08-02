
package com.xiaohe.xplatform.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xiaohe.xplatform.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Set;

/**
 * 系统用户
 * 
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	SysUserEntity  findByUsername(String username);

	List<String> queryAllSubUserId(Long[] longs);
}
