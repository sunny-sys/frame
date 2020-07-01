package io.smallbird.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.smallbird.modules.sys.entity.SysMenuEntity;
import io.smallbird.modules.sys.dto.SysRoleMenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理
 *
 *
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

    List<SysRoleMenuDto> getAllRoleMenuList();

	List<SysRoleMenuDto> getMenuListByRoleId(@Param("roleId") Long roleId);
}