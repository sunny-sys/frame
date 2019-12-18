package io.smallbird.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheManagerUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableCaching//开启ehcache缓存
public class CommonConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        //重新设置StringHttpMessageConverter字符集为UTF-8，解决中文乱码问题
        for (HttpMessageConverter<?> item : converterList) {
            if (item instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) item).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
        converterList.add(new FormHttpMessageConverter());
        converterList.add(new GsonHttpMessageConverter());
        converterList.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    @Autowired
    CacheProperties cacheProperties;

    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public net.sf.ehcache.CacheManager ehCacheCacheManager() {
        Resource location = cacheProperties.resolveConfigLocation(this.cacheProperties.getEhcache().getConfig());
        return location != null ? EhCacheManagerUtils.buildCacheManager(location) : EhCacheManagerUtils.buildCacheManager();
    }
}
