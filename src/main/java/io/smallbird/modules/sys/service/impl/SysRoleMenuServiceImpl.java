package io.smallbird.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.smallbird.common.model.BaseResp;
import io.smallbird.modules.sys.dao.SysRoleMenuDao;
import io.smallbird.modules.sys.entity.SysRoleMenuEntity;
import io.smallbird.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 *
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResp saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return BaseResp.error("请选择菜单权限");
		}

		//保存角色与菜单关系
		for(Long menuId : menuIdList){
			SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
			sysRoleMenuEntity.setMenuId(menuId);
			sysRoleMenuEntity.setRoleId(roleId);
			sysRoleMenuEntity.setUpdateTime(new Date());
			this.save(sysRoleMenuEntity);
		}
		return BaseResp.success();
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public List<Long> getMenuIdListByRoleIdList(List<Long> roleIdList) {
		return baseMapper.getMenuIdListByRoleIdList(roleIdList);
	}

}
