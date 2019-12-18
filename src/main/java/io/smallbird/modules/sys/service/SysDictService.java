package io.smallbird.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.modules.sys.entity.SysDictEntity;

import java.util.Map;

/**
 * 数据字典
 *
 *
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

