package io.smallbird.modules.sys.shiro;

import io.smallbird.common.utils.SerializableUtils;
import io.smallbird.modules.sys.entity.SysUserEntity;
import io.smallbird.modules.sys.entity.SysUserSessionEntity;
import io.smallbird.modules.sys.service.SysUserSessionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
 
/**
 * 存放session 到数据库
 */
@Component
public class DataBaseSessionDao extends EnterpriseCacheSessionDAO {

    public static final String COOKIE = "cookie";

    @Autowired
    private SysUserSessionService sysUserSessionService;


    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        Serializable cookie = super.doCreate(session);
        // 保存session到数据库
        sysUserSessionService.save(new SysUserSessionEntity()
                .setCookie(cookie.toString())
                .setSession(SerializableUtils.serializ(session)));
        return cookie;
    }

    //获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            HashMap<String, Object> param = new HashMap<>();
            param.put(COOKIE, sessionId);
            SysUserSessionEntity sysUserSessionEntity = sysUserSessionService.queryObjectByMap(param);
            // 如果不为空
            if (sysUserSessionEntity != null) {
                String sessionStr64 = sysUserSessionEntity.getSession();
                session = SerializableUtils.deserializ(sessionStr64);
            }

        }
        return session;
    }

    //更新session
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        //当是ValidatingSession 无效的情况下，直接退出
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return;
        }
        //检索到用户名
        HashMap<String, Object> param = new HashMap<>();
        param.put(COOKIE, session.getId());
        SysUserSessionEntity sysUserSessionEntity = sysUserSessionService.queryObjectByMap(param);
        if (sysUserSessionEntity != null) {
            sysUserSessionEntity.setSession(SerializableUtils.serializ(session));
            // 如果登录成功，更新用户id
            if (ShiroUtils.getSubject().isAuthenticated()) {
                SysUserEntity sysUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
                sysUserSessionEntity.setUserId(sysUser.getUserId());
            }
            sysUserSessionService.updateById(sysUserSessionEntity);
        }
    }

    //删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        HashMap<String, Object> param = new HashMap<>();
        param.put(COOKIE, session.getId());
        SysUserSessionEntity sysUserSessionEntity = sysUserSessionService.queryObjectByMap(param);
        if (sysUserSessionEntity != null) {
            sysUserSessionService.removeById(sysUserSessionEntity.getId());
        }
    }
}