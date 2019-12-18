package io.smallbird.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* @Description:    角色权限菜单
* @Author:         chenshao
* @CreateDate:     2019/4/24 15:59
*/
@Data
@ApiModel("角色权限菜单")
public class RoleAndMenuDto {

	@ApiModelProperty("角色id")
	private Long roleId;

	@ApiModelProperty("权限菜单集合")
	private List<Long> menuIdList;


}
