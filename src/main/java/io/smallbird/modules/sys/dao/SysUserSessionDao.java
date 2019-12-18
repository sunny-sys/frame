package io.smallbird.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.smallbird.modules.sys.entity.SysUserSessionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户session
 * 
 * @author xml
 * @email xxx
 * @date 2019-04-09 21:21:41
 */
@Mapper
public interface SysUserSessionDao extends BaseMapper<SysUserSessionEntity> {
	
}
