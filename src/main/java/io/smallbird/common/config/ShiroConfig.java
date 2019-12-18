package io.smallbird.common.config;

import io.smallbird.modules.sys.shiro.*;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置文件
 *
 *
 */
@Configuration
public class ShiroConfig {

    /**
     * 单机环境，session交给shiro管理
     */
    @Bean
    @ConditionalOnProperty(prefix = "yifu", name = "cluster", havingValue = "false")
    public DefaultSessionManager sessionManager(@Value("${yifu.globalSessionTimeout:3600}") long globalSessionTimeout,DataBaseSessionDao dataBaseSessionDao){
        DefaultSessionManager sessionManager = new DefaultHeaderSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
        sessionManager.setSessionValidationInterval(globalSessionTimeout * 1000);
        sessionManager.setGlobalSessionTimeout(globalSessionTimeout * 1000);

        sessionManager.setSessionDAO(dataBaseSessionDao);

        return sessionManager;
    }

    /**
     * 集群环境，session交给spring-session管理
     */
    @Bean
    @ConditionalOnProperty(prefix = "yifu", name = "cluster", havingValue = "true")
    public ServletContainerSessionManager servletContainerSessionManager() {
        return new ServletContainerSessionManager();
    }

    @Bean("securityManager")
    public SecurityManager securityManager(UserNamePwdRealm userNamePwdRealm,
                                           TelephoneNumVerificationCodeRealm telephoneNumVerificationCodeRealm,
                                           SessionManager sessionManager,
                                           TelephoneNumVerificationCodeShopRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        MyModularRealmAuthenticator myModularRealmAuthenticator = new MyModularRealmAuthenticator();
        myModularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());///!!!

        securityManager.setAuthenticator(myModularRealmAuthenticator);
        securityManager.setRealms(Arrays.asList(userNamePwdRealm,telephoneNumVerificationCodeRealm,realm));///!!!

        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(null);

        return securityManager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login.html");
        shiroFilter.setUnauthorizedUrl("/");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/sys/dict/query","anon");//查询字典的请求不拦截，方便前端查询

        filterMap.put("/weChat/**", "anon");
        filterMap.put("/sms/**", "anon");
        filterMap.put("/wx/pay/notify", "anon");
        filterMap.put("/sys/user/**", "anon");
        filterMap.put("/sys/validateToken", "anon");
        filterMap.put("/wx/auth/authorise", "anon");
        filterMap.put("/wx/auth/getOpenIdAndInDatabase/**", "anon");


        filterMap.put("/haoFeng/info", "anon");

        filterMap.put("/wx/wares/**","anon");

        filterMap.put("/sys/role/**","anon");
        filterMap.put("/wx/data/**","anon");
        filterMap.put("/wx/personal/**","anon");
        filterMap.put("/sys/menu/**","anon");
        filterMap.put("/sys/auth/**","anon");
        filterMap.put("/sys/data/**","anon");

        filterMap.put("/wx/connect","anon");

        filterMap.put("/logic/sysfile/download","anon");

        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/sys/codeLogin", "anon");
        filterMap.put("/sys/verificationCode", "anon");
        filterMap.put("/sys/user/regist", "anon");
        filterMap.put("/logic/sysfile/uploadFile", "anon");
        filterMap.put("/logic/sysfile/downloadImg", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
