package io.smallbird.modules.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.smallbird.modules.sys.dao.SysMenuDao;
import io.smallbird.modules.sys.dao.SysRoleDao;
import io.smallbird.modules.sys.dao.SysUserDao;
import io.smallbird.modules.sys.dao.SysUserRoleDao;
import io.smallbird.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证
 */
@Component
public class UserNamePwdRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(ShiroAuthorUtils.getPermsSet(user, sysUserDao, sysMenuDao));
//        info.setRoles(ShiroAuthorUtils.getRolesSet(user,sysRoleDao,sysUserRoleDao));
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

        UserNamePasswordTelphoneToken token = (UserNamePasswordTelphoneToken) authcToken;

        //查询用户信息
        SysUserEntity user = sysUserDao.selectOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUsername, token.getUsername()));
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = null;
        if (StringUtils.isEmpty(user.getLoginType()) || "userName".equalsIgnoreCase(user.getLoginType())) {
            info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), user.getUsername());
        }
        return info;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
