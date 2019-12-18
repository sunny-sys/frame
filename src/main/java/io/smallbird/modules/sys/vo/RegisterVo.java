package io.smallbird.modules.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.model.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
* @Description:    注册列表出参
* @Author:         chenshao
* @CreateDate:     2019/4/24 15:59
*/
@Data
@ApiModel("注册列表出参")
public class RegisterVo extends BaseEntity {

	@ApiModelProperty("主键id")
	private Long userId;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("手机号")
	private String telephoneNum;

	@ApiModelProperty("用户类型: vip/common")
	private String userType;

	@ApiModelProperty("用户(真实)姓名")
	private String realName;

	@ApiModelProperty(name = "身份证正面文件id")
	private String identityCardsFrontPic;

	@ApiModelProperty(name = "身份证反面文件id")
	private String identityCardsBackPic;

	@ApiModelProperty(name = "营业执照文件id")
	private String businessLicensePic;

	@ApiModelProperty("公司名称")
	private String companyName;

	@ApiModelProperty("创建时间")
	private Date createTime;

//    @ApiModelProperty(name = "部门ID")
//	private Long deptId;
//
//	@ApiModelProperty(name = "部门名称")
//	private String deptName;
//
//	@ApiModelProperty("用户积分")
//	private Integer integral;
    @ApiModelProperty("审核状态")
	private Integer auditStatus;
    @ApiModelProperty(name = "法人代表")
    private String legalName;
    @ApiModelProperty(name = "法人身份证正面文件id")
    private String legalFrontPic;
    @ApiModelProperty(name = "法人身份证反面文件id")
    private String legalBackPic;
	@ApiModelProperty(name = "用户角色类型")
	private String roleName;
}
