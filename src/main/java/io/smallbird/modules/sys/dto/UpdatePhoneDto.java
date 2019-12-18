package io.smallbird.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.model.BaseEntity;
import io.smallbird.common.utils.AccountValidatorUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel("修改手机入参实体类")
@Data
@Accessors(chain = true)
public class UpdatePhoneDto extends BaseEntity {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(name = "手机号",required=true)
    @NotBlank(message="手机号不能为空")
    @Pattern(regexp = AccountValidatorUtil.REGEX_MOBILE,message = "请输入合法的手机号")
    private String telephoneNum;
}
