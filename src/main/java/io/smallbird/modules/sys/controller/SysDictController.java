package io.smallbird.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.smallbird.common.model.EhcacheService;
import io.smallbird.common.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.smallbird.common.model.PageListResp;
import io.smallbird.common.validator.ValidatorUtils;
import io.smallbird.modules.sys.entity.SysDictEntity;
import io.smallbird.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 数据字典
 *
 *
 */
@Api(description = "根据字典类型查询字典信息",tags = "字典管理")
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private EhcacheService ehcacheService;


    @ApiOperation(value = "字典类型查询",notes = "传字典的类型查询，不传表示查询所有的字典")
    @GetMapping("/query")
    public PageListResp<SysDictEntity> query(@ApiParam(value = "字典类型（反馈类型传：feedback_type）")
                            @RequestParam(value = "type",required = false) String type){
        IPage<SysDictEntity> page = sysDictService.page(new Query().startPage(type),
                new LambdaQueryWrapper<SysDictEntity>()
                .eq(Util.isNotEmpty(type),SysDictEntity::getType,type)
        );
        return new PageListResp<>(page);
    }

    /**
     * 列表
     */
    @ApiIgnore
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysDictService.queryPage(params);
        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiIgnore
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public Result info(@PathVariable("id") Long id){
        SysDictEntity entity = (SysDictEntity) ehcacheService.get(Constant.EhcacheDataName.DICITEM.name(),id.toString());
        if (Objects.nonNull(entity)) return Result.ok().put("dict", entity);
        SysDictEntity dict = sysDictService.getById(id);
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.DICITEM.name(),id.toString(),dict);
        return Result.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @ApiIgnore
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public Result save(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);
        sysDictService.save(dict);
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.DICITEM.name(),dict.getId().toString(),dict);
        return Result.ok();
    }

    /**
     * 修改
     */
    @ApiIgnore
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public Result update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.DICITEM.name(),dict.getId().toString(),dict);
        return Result.ok();
    }

    /**
     * 删除
     */
    @ApiIgnore
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public Result delete(@RequestBody Long[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));
        ehcacheService.removeAll(Constant.EhcacheDataName.DICITEM.name(),Arrays.asList(ids));
        return Result.ok();
    }

}
