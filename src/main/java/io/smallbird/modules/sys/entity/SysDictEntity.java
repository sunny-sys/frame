package io.smallbird.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典
 *
 *
 */
@Data
@ApiModel("字典实体类")
@TableName("sys_dict")
public class SysDictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * 字典名称
	 */
	@ApiModelProperty("字典名称")
	@NotBlank(message="字典名称不能为空")
	private String name;
	/**
	 * 字典类型
	 */
	@ApiModelProperty("字典类型")
	@NotBlank(message="字典类型不能为空")
	private String type;
	/**
	 * 字典码
	 */
	@ApiModelProperty("字典码(取字典的值的时候就取该值)")
	@NotBlank(message="字典码不能为空")
	private String code;
	/**
	 * 字典值
	 */
	@ApiModelProperty("字典值(跟字典名称的意思差不多)")
	@NotBlank(message="字典值不能为空")
	private String value;
	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true)
	private Integer orderNum;
	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;
	/**
	 * 删除标记  -1：已删除  0：正常
	 */
	@ApiModelProperty("删除标记  -1：已删除  0：正常")
	@TableLogic
	private Integer delFlag;

}
