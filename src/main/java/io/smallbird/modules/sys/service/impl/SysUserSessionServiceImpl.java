package io.smallbird.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Query;
import io.smallbird.modules.sys.dao.SysUserSessionDao;
import io.smallbird.modules.sys.entity.SysUserSessionEntity;
import io.smallbird.modules.sys.service.SysUserSessionService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysUserSessionService")
public class SysUserSessionServiceImpl extends ServiceImpl<SysUserSessionDao, SysUserSessionEntity> implements SysUserSessionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return new PageUtils(this.page(
                new Query<SysUserSessionEntity>().getPage(params),
                new QueryWrapper<>()
        ));
    }

    @Override
    public SysUserSessionEntity queryObjectByMap(Map<String, Object> param) {
        return this.getOne(new QueryWrapper<SysUserSessionEntity>().allEq(param, false));
    }

}
