package io.smallbird.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "所有需要分页的基类")
public class BasePageQuery extends BaseEntity {

    private static final long serialVersionUID = -511521441294653347L;

    @ApiModelProperty("起始页(不传或者传值小于等于0,则表示不分页)")
    private Integer startRow = -1;

    @ApiModelProperty("每页显示的记录数")
    private Integer maxSize = -1;
}
