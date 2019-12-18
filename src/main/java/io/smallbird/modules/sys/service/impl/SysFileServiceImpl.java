package io.smallbird.modules.sys.service.impl;

import io.smallbird.modules.sys.dao.SysFileDao;
import io.smallbird.modules.sys.entity.SysFileEntity;
import io.smallbird.modules.sys.service.SysFileService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Query;


@Service("sysFileService")
public class SysFileServiceImpl extends ServiceImpl<SysFileDao, SysFileEntity> implements SysFileService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysFileEntity> page = this.page(
                new Query<SysFileEntity>().getPage(params),
                new QueryWrapper<SysFileEntity>()
        );

        return new PageUtils(page);
    }

}
