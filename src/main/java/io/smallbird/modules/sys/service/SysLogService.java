package io.smallbird.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 *
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
