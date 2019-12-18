package io.smallbird.common.model;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@ApiModel(description = "分页返回实体类")
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class PageListResp<T> extends BaseResp<T> {

    @ApiModelProperty("数据总数")
    private Integer totalCount;

    @ApiModelProperty("数据列表")
    private List<T> list;

    @ApiModelProperty("每页记录数")
    private int pageSize;

    @ApiModelProperty("总页数")
    private int totalPage;

    @ApiModelProperty("当前页数")
    private int currPage;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     */
    public PageListResp(List<T> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public PageListResp(IPage<T> page) {
        this(page.getRecords(), (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    public PageListResp<T> ok(IPage<T> page) {
        return new PageListResp<>(page);
    }

    public PageListResp<T> fail(String msg) {
        super.fail(msg);
        return this;
    }

    public PageListResp<T> fail() {
        fail(null);
        return this;
    }

    public static <T> PageListResp success(IPage<T> page) {
        return new PageListResp().ok(page);
    }

    public static PageListResp error(String msg) {
        return new PageListResp().fail(msg);
    }

    public static PageListResp error() {
        return new PageListResp().fail();
    }
}
