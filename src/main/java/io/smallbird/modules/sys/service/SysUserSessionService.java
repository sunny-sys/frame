package io.smallbird.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.modules.sys.entity.SysUserSessionEntity;

import java.util.Map;

/**
 * 用户session
 *
 * @author xml
 * @email xxx
 * @date 2019-04-09 21:21:41
 */
public interface SysUserSessionService extends IService<SysUserSessionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    SysUserSessionEntity queryObjectByMap(Map<String, Object> param);
}

