package io.smallbird.modules.sys.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色
 *
 *
 */
@Data
@ApiModel("角色权限菜单")
public class SysRoleMenuVo  {
	@ApiModelProperty("角色ID")
	private Long roleId;

	@ApiModelProperty("角色名称")
	private String roleName;

	@ApiModelProperty("菜单id集合")
	private List<String> menuIdList;

	@ApiModelProperty("菜单姓名集合")
	private List<String> menuNameList;
}
