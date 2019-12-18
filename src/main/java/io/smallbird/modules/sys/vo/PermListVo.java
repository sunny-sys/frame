package io.smallbird.modules.sys.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色
 *
 *
 */
@Data
@ApiModel("角色权限集合")
public class PermListVo {
	@ApiModelProperty("菜单ID")
	private Long menuId;

	@ApiModelProperty("菜单名称")
	private String menuName;

	@ApiModelProperty("权限集合")
	private String perms;
}
