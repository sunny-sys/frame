package io.smallbird.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@ApiModel(description = "详情返回实体类")
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class DetailResp<T> extends BaseResp<T> {

    @ApiModelProperty("数据详情")
    private T detail;

    public DetailResp<T> ok(T detail) {
        super.ok();
        this.detail = detail;
        return this;
    }

    public DetailResp<T> fail(String msg) {
        super.fail(msg);
        return this;
    }

    public DetailResp<T> fail() {
        fail(null);
        return this;
    }

    public static <T> DetailResp success(T detail) {
        return new DetailResp().ok(detail);
    }

    public static DetailResp error(String msg) {
        return new DetailResp().fail(msg);
    }

    public static DetailResp error() {
        return new DetailResp().fail();
    }

    public static DetailResp error(Integer code,String msg) {
        DetailResp detailResp = new DetailResp();
        detailResp.setCode(code).setSuccess(false).setMsg(msg);
        return detailResp;
    }
}
