package io.smallbird.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置
 *
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
    }

    //显示声明CommonsMultipartResolver为mutipartResolver
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小 10M 5*1024*1024
        resolver.setMaxUploadSize(10 * 1024 * 1024);
        return resolver;
    }

    /**
     * <p>@Des: 解决跨域问题 </p>
     * <p>@Author: xupj </p>
     * <p>@Date: 2018/11/20 16:07 </p>
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods(RequestMethod.POST.name(), RequestMethod.GET.name(),
                        RequestMethod.DELETE.name(),RequestMethod.PUT.name())
                .maxAge(3600)
                .exposedHeaders("Origin, X-Requested-With, Content-Type, Accept,token");//暴露自定义请求头，允许前端获取自定义请求头
    }
}