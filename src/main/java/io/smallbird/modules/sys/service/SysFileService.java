package io.smallbird.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.modules.sys.entity.SysFileEntity;

import java.util.Map;

/**
 * 
 *
 * @author xml
 * @email xxx
 * @date 2019-04-19 16:45:03
 */
public interface SysFileService extends IService<SysFileEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

