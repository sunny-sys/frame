package io.smallbird.modules.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.smallbird.modules.sys.dao.SysMenuDao;
import io.smallbird.modules.sys.dao.SysUserDao;
import io.smallbird.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证
 *
 *
 */
@Component
public class TelephoneNumVerificationCodeShopRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;
    
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(ShiroAuthorUtils.getPermsSet(user,sysUserDao,sysMenuDao));
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UserNamePasswordTelphoneToken token = (UserNamePasswordTelphoneToken)authcToken;

		//查询用户信息
		SysUserEntity user = sysUserDao.selectOne(new QueryWrapper<SysUserEntity>()
				.lambda().eq(SysUserEntity::getTelephoneNum, token.getTelphoneNum())
				.eq(SysUserEntity::getUserType,"vip")
		);
		//账号不存在
		if(user == null) {
			throw new UnknownAccountException("手机号不存在");
		}

		//账号锁定
		if(user.getStatus() == 0){
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = null;
		if (StringUtils.isEmpty(user.getLoginType()) || "userName".equalsIgnoreCase(user.getLoginType())) {

		}else{
			info = new SimpleAuthenticationInfo(user, "123456", user.getTelephoneNum());// !!
		}
		return info;
	}
}
