package io.smallbird.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.modules.sys.entity.SysMessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author xml
 * @email xxx
 * @date 2019-04-11 15:34:20
 */
public interface SysMessageService extends IService<SysMessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

