package io.smallbird.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import io.smallbird.common.exception.GeneralRuntimeException;
import io.smallbird.common.model.EhcacheService;
import io.smallbird.common.utils.Constant;
import io.smallbird.common.utils.PageUtils;
import io.smallbird.common.utils.Query;
import io.smallbird.modules.sys.dao.SysConfigDao;
import io.smallbird.modules.sys.entity.SysConfigEntity;
import io.smallbird.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Autowired
    private EhcacheService ehcacheService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");

        IPage<SysConfigEntity> page = this.page(
                new Query<SysConfigEntity>().getPage(params),
                new QueryWrapper<SysConfigEntity>()
                        .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
    public void saveConfig(SysConfigEntity config) {
        this.save(config);
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.COMMON.name(), config.getParamKey(), config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigEntity config) {
        this.updateById(config);
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.COMMON.name(), config.getParamKey(), config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
        SysConfigEntity config = ehcacheService.getSysConfigByKey(key);
        if (Objects.nonNull(config)) {
            config.setParamValue(value);
        }
        config = this.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, key));
        ehcacheService.saveOrUpdate(Constant.EhcacheDataName.COMMON.name(), config.getParamKey(), config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
        ehcacheService.removeAll(Constant.EhcacheDataName.COMMON.name(), Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) {
        SysConfigEntity configEntity = ehcacheService.getSysConfigByKey(key);
        if (Objects.nonNull(configEntity)) return configEntity.getParamValue();
        SysConfigEntity config = baseMapper.queryByKey(key);
        if (Objects.nonNull(config)) {
            ehcacheService.saveOrUpdate(Constant.EhcacheDataName.COMMON.name(), config.getParamKey(), config);
            return config.getParamValue();
        }
        return null;
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new GeneralRuntimeException("获取参数失败");
        }
    }
}