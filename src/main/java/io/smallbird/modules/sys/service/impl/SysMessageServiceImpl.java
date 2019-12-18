package io.smallbird.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Query;
import io.smallbird.modules.sys.dao.SysMessageDao;
import io.smallbird.modules.sys.entity.SysMessageEntity;
import io.smallbird.modules.sys.service.SysMessageService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysMessageService")
public class SysMessageServiceImpl extends ServiceImpl<SysMessageDao, SysMessageEntity> implements SysMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMessageEntity> page = this.page(
                new Query<SysMessageEntity>().getPage(params),
                new QueryWrapper<SysMessageEntity>()
        );

        return new PageUtils(page);
    }

}
