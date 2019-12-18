package io.smallbird.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.model.BaseResp;
import io.smallbird.modules.sys.entity.SysRoleMenuEntity;

import java.util.List;


/**
 * 角色与菜单对应关系
 *
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {

	BaseResp saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);

	List<Long> getMenuIdListByRoleIdList(List<Long> roleIdList);
}
