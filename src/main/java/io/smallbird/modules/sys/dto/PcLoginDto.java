package io.smallbird.modules.sys.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@ApiModel(description = "pc端登录入参实体")
public class PcLoginDto extends BaseEntity {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty("验证码")
    private String captcha;
}
