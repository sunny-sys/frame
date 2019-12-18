package io.smallbird.modules.job.task;

import io.smallbird.common.model.EhcacheService;
import io.smallbird.common.utils.Constant;
import io.smallbird.common.utils.Util;
import io.smallbird.modules.sys.entity.SysConfigEntity;
import io.smallbird.modules.sys.entity.SysDictEntity;
import io.smallbird.modules.sys.service.SysConfigService;
import io.smallbird.modules.sys.service.SysDictService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component("runTask")
public class RunTask implements ITask {

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private EhcacheService ehcacheService;
    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public void run(String params) {
        log.info("加载字典项数据到内存中......");
        if (Util.isEmpty(sysDictService)) {
            log.error("无法加载字典项数据到内存......找不到操作数据库的dicItemMapper对象.....");
            return;
        }
        List<SysDictEntity> list = sysDictService.list();
        if (!Util.isEmpty(list)) {
            ehcacheService.removeAll(Constant.EhcacheDataName.DICITEM.name(),ehcacheService.getKeys(Constant.EhcacheDataName.DICITEM.name()));
            list.forEach(item -> ehcacheService.set(Constant.EhcacheDataName.DICITEM.name(), item.getId().toString(), item));
        }
        log.info("加载系统配置数据到内存中......");
        if (Util.isEmpty(sysConfigService)) {
            log.error("无法加载系统配置数据到内存......找不到操作数据库的sysConfigMapper对象.....");
            return;
        }
        List<SysConfigEntity> list2 = sysConfigService.list();
        if (!Util.isEmpty(list2)) {
            ehcacheService.removeAll(Constant.EhcacheDataName.COMMON.name(),ehcacheService.getKeys(Constant.EhcacheDataName.COMMON.name()));
            list2.forEach(config -> ehcacheService.set(Constant.EhcacheDataName.COMMON.name(), config.getId().toString(), config));
        }
    }
}
