package io.smallbird.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.smallbird.common.validator.ValidatorUtils;
import io.smallbird.modules.sys.entity.SysMessageEntity;
import io.smallbird.modules.sys.service.SysMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Result;



/**
 * 
 *
 * @author xml
 * @email xxx
 * @date 2019-04-11 15:34:20
 */
@RestController
@RequestMapping("logic/sysmessage")
public class SysMessageController {
    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("logic:sysmessage:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMessageService.queryPage(params);

        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("logic:sysmessage:info")
    public Result info(@PathVariable("id") Long id){
        SysMessageEntity sysMessage = sysMessageService.getById(id);

        return Result.ok().put("sysMessage", sysMessage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("logic:sysmessage:save")
    public Result save(@RequestBody SysMessageEntity sysMessage){
        sysMessageService.save(sysMessage);

        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("logic:sysmessage:update")
    public Result update(@RequestBody SysMessageEntity sysMessage){
        ValidatorUtils.validateEntity(sysMessage);
        sysMessageService.updateById(sysMessage);
        
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("logic:sysmessage:delete")
    public Result delete(@RequestBody Long[] ids){
        sysMessageService.removeByIds(Arrays.asList(ids));

        return Result.ok();
    }

}
