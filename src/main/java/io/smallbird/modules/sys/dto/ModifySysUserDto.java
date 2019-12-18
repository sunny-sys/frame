package io.smallbird.modules.sys.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.smallbird.common.model.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel("修改个人资料入参对象")
public class ModifySysUserDto extends BaseEntity {

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("所在城市")
    private String city;

    @ApiModelProperty("我的地址")
    private String address;
}
