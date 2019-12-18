package io.smallbird.modules.sys.shiro;

import io.smallbird.common.utils.Constant;
import io.smallbird.modules.sys.dao.SysMenuDao;
import io.smallbird.modules.sys.dao.SysUserDao;
import io.smallbird.modules.sys.entity.SysMenuEntity;
import io.smallbird.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class ShiroAuthorUtils {

    public static Set<String> getPermsSet(SysUserEntity user, SysUserDao sysUserDao, SysMenuDao sysMenuDao) {
        Long userId = user.getUserId();

        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }
}
