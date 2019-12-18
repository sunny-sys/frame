package io.smallbird.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.validator.group.AddGroup;
import io.smallbird.common.validator.group.UpdateGroup;
import io.smallbird.modules.sys.vo.PermListVo;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 *
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel("用户实体对象")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;

	/**
	 * 用户名
	 */
//	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 密码
	 */
//	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ApiModelProperty("密码")
	private String password;


	/**
	 * 确认密码
	 */
	@TableField(exist = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ApiModelProperty("确认密码")
	private String confirmPwd;

	/**
	 * 盐
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String salt;

	/**
	 * 邮箱
	 */
//	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
//	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty("邮箱")
	private String email;

	/**
	 * 手机号
	 */
	@TableField("telephone_num")
	@NotBlank(message="手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty(name = "手机号",required=true)
	private String telephoneNum;

	@TableField(exist = false)
	private String verificationCode;

	/**
	 * 登录方式:
	 * 	手机号+验证码登录 --> telephoneNum
	 * 	用户名+密码 --> userName
	 */
	@NotBlank(message="登录方式不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty(name = "登录方式",allowableValues = "telephoneNum,userName")
	private String loginType;

	/**
	 * 状态  0：禁用   1：正常
	 */
	@ApiModelProperty(name = "状态  0：禁用   1：正常")
	private Integer status;

	/**
	 * 用户类型：vip/common
	 */
	@NotBlank(message="用户类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty(name = "用户类型",allowableValues = "vip,common")
	private String userType;
	/**
	 * 用户真实姓名
	 */
	@NotBlank(message="用户姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@ApiModelProperty(name = "用户(真实)姓名")
	private String realName;
	/**
	 * 身份证正面文件id
	 */
	@ApiModelProperty(name = "身份证正面文件id")
	private String identityCardsFrontPic;
	/**
	 * 身份证反面文件id
	 */
	@ApiModelProperty(name = "身份证反面文件id")
	private String identityCardsBackPic;
	/**
	 * 营业执照文件id
	 */
	@ApiModelProperty(name = "营业执照文件id")
	private String businessLicensePic;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(name = "公司名称")
	private String companyName;

	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	@ApiModelProperty(name = "角色ID列表")
	private List<Long> roleIdList;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 部门ID
	 */
//	@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;

	/**
	 * 部门名称
	 */
	@TableField(exist=false)
	private String deptName;

	@ApiModelProperty("用户积分")
	private Integer integral;
	/*
	 *审核状态
	 */
	private Integer auditStatus;

	@ApiModelProperty(name = "openid")
	private String openid;

	@ApiModelProperty(name = "头像")
	private String headimgurl;

	@ApiModelProperty(name = "性别")
	private Integer sex;

	@ApiModelProperty(name = "所在城市")
	private String city;

	@ApiModelProperty(name = "用户关注时间。如果用户曾多次关注，则取最后关注时间")
	private String subscribeTime;

	@ApiModelProperty(name = "微信二维码")
	private String twoDimensionCode;

	@ApiModelProperty(name = "权限列表")
	@TableField(exist=false)
	private List<PermListVo> permsList;

	@ApiModelProperty(name = "法人代表")
	private String legalName;
	@ApiModelProperty(name = "法人身份证正面文件id")
	private String legalFrontPic;
	@ApiModelProperty(name = "法人身份证反面文件id")
	private String legalBackPic;
	@ApiModelProperty(name = "登录状态0未登录1已登录")
	private Integer loginStatus;

	@ApiModelProperty("我的地址")
	@TableField("address")
	private String address;

	@ApiModelProperty("生日")
	@TableField("birthday")
	private String birthday;
}
