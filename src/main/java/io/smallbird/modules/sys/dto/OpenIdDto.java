package io.smallbird.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

@Data
@Accessors(chain = true)
@ApiModel(description = "wx端openid")
public class OpenIdDto{

    @ApiModelProperty(value = "微信的用户唯一凭证",required = true)
    @NotEmpty(message = "openid不能为空")
    private String openid;
}
