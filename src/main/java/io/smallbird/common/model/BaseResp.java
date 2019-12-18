package io.smallbird.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "统一数据返回实体类")
@AllArgsConstructor
@Accessors(chain = true)
public class BaseResp<T> extends BaseEntity {

    private static final long serialVersionUID = 196234563504881332L;

    @ApiModelProperty("调用是否成功，true：成功，false：失败")
    private boolean success;

    @ApiModelProperty("响应码,0：成功，1：失败")
    private Integer code;

    @ApiModelProperty("响应描述")
    private String msg;

    public BaseResp(){
        ok();
    }

    public BaseResp<T> ok() {
        this.success = true;
        this.code = 0;
        return this;
    }

    public BaseResp<T> fail() {
        return fail(null);
    }

    public BaseResp<T> fail(String msg) {
        this.success = false;
        this.code = 1;
        this.msg = msg;
        return this;
    }

    public static BaseResp success() {
        return new BaseResp();
    }

    public static BaseResp error(String msg) {
        return new BaseResp().fail(msg);
    }

    public static BaseResp error() {
        return new BaseResp().fail();
    }
}
