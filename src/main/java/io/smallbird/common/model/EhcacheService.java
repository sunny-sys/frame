package io.smallbird.common.model;

import io.smallbird.common.utils.Constant;
import io.smallbird.common.utils.Util;
import io.smallbird.modules.sys.entity.SysConfigEntity;
import io.smallbird.modules.sys.entity.SysDictEntity;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>@Des: 增加缓存，一般基本配置，或者需要频繁去操作数据库的数据要放入缓存，
 * 放置给数据库造成很大压力,后期如果压力太大了，缓存换位redis做cluster,
 * 暂时先用这个简单的ehcache
 * </p>
 * <p>@Author: xupj </p>
 * <p>@Date: 2018/12/4 13:30 </p>
 **/
@Component
public class EhcacheService {

    @Autowired
    private CacheManager ehCacheCacheManager;

    public Cache getCache(String schemaName) {
        return ehCacheCacheManager.getCache(schemaName);
    }

    /**
     * <p>@Des: 根据对应的缓存地方和key存放数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:26 </p>
     **/
    public void set(String schemaName, String key, Object value) {
        Element element = new Element(key, value);
        getCache(schemaName).put(element);
    }

    /**
     * <p>@Des: 根据对应的缓存地方和key获取数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:26 </p>
     **/
    public Object get(String schemaName, String key) {
        Element element = getCache(schemaName).get(key);
        return element == null ? null : element.getObjectValue();
    }

    /**
     * <p>@Des: 根据对应的缓存地方保存或更新数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:26 </p>
     **/
    public void saveOrUpdate(String schemaName, String key, Object value) {
        Object object = get(schemaName, key);
        if (Util.isEmpty(object)) {
            set(schemaName, key, value);
            return;
        }
        remove(schemaName, key);
        set(schemaName, key, value);
    }

    /**
     * <p>@Des: 根据对应的缓存地方获取这个里面所有的key </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:26 </p>
     **/
    public List<String> getKeys(String schemaName) {
        return getCache(schemaName).getKeys();
    }

    /**
     * <p>@Des: 根据对应的缓存地方删除key对应的数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:25 </p>
     **/
    public void remove(String schemaName, String key) {
        getCache(schemaName).remove(key);
    }

    /**
     * <p>@Des: 根据对应的缓存地方批量删除key对应的数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:25 </p>
     **/
    public void removeAll(String schemaName, Collection<?> keys) {
        getCache(schemaName).removeAll(keys);
    }

    /**
     * <p>@Des: 获取所有的字典明细 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:24 </p>
     **/
    public List<SysDictEntity> getAllDicItem() {
        List<String> keys = getKeys(Constant.EhcacheDataName.DICITEM.name());
        if (Util.isEmpty(keys)) return Collections.emptyList();
        List<SysDictEntity> dicItems = new ArrayList<>();
        keys.forEach(key -> dicItems.add((SysDictEntity) get(Constant.EhcacheDataName.DICITEM.name(), key)));
        return dicItems;
    }

    /**
     * <p>@Des: 根据字典的类型获取字典明细数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:24 </p>
     **/
    public List<SysDictEntity> getDicItemByDicType(String dicType) {
        List<SysDictEntity> dicItems = getAllDicItem();
        if (Util.isEmpty(dicItems)) return Collections.emptyList();
        return dicItems.stream().filter(item -> item.getType().equals(dicType)).collect(Collectors.toList());
    }

    /**
     * <p>@Des: 根据字典的类型删除字典明细数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:24 </p>
     **/
    public void removeDicItemByDicType(String dicType) {
        List<SysDictEntity> dicItems = getDicItemByDicType(dicType);
        removeAll(Constant.EhcacheDataName.DICITEM.name(), dicItems.stream()
                .map(SysDictEntity::getId).collect(Collectors.toList()));
    }

    /**
     * <p>@Des: 根据一组字典的类型删除字典明细数据 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:24 </p>
     **/
    public void removeDicItemByDicTypes(List<String> dicTypes) {
        dicTypes.forEach(dicType -> {
            List<SysDictEntity> dicItems = getDicItemByDicType(dicType);
            removeAll(Constant.EhcacheDataName.DICITEM.name(), dicItems.stream()
                    .map(SysDictEntity::getId).collect(Collectors.toList()));
        });
    }

    /**
     * <p>@Des: 根据key获系统配置 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/12/4 23:24 </p>
     **/
    public SysConfigEntity getSysConfigByKey(String key) {
        return (SysConfigEntity) get(Constant.EhcacheDataName.COMMON.name(), key);
    }

}  