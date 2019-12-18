package io.smallbird.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.smallbird.common.annotation.SysLog;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Result;
import io.smallbird.modules.sys.entity.SysRoleEntity;
import io.smallbird.modules.sys.service.SysRoleDeptService;
import io.smallbird.modules.sys.service.SysRoleMenuService;
import io.smallbird.modules.sys.service.SysRoleService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 *
 */
@Api("角色管理")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysRoleDeptService sysRoleDeptService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);

		return Result.ok().put("page", page);
	}
	
	/**
	 * 角色列表
	 */
	@PostMapping("/select")
	@ApiOperation(value = "注册类型列表")
	public Result select(@ApiParam(value = "是vip还是p",name = "remark") @RequestBody Map<String,String> dataMap){
		if (MapUtils.isEmpty(dataMap) || StringUtils.isEmpty(dataMap.get("remark"))){
			return Result.ok().put("list", sysRoleService.list());
		}
		List<SysRoleEntity> list = sysRoleService.list(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getRemark,dataMap.get("remark")));
		return Result.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);
		
		return Result.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@ApiOperation(value = "保存角色")
	public Result save(@RequestBody SysRoleEntity role){
//		ValidatorUtils.validateEntity(role);
		
		sysRoleService.saveRole(role);
		
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@ApiOperation(value = "修改角色")
	public Result update(@RequestBody SysRoleEntity role){
//		ValidatorUtils.validateEntity(role);
		
		sysRoleService.update(role);
		
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@ApiOperation(value = "删除角色")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}
