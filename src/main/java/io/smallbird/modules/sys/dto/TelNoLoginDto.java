package io.smallbird.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@ApiModel(description = "手机登录入参实体")
public class TelNoLoginDto extends BaseEntity {

    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号",required = true)
    private String telephoneNum;

    @NotEmpty(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String verificationCode;

}

