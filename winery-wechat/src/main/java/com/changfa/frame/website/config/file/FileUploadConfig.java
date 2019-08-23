package com.changfa.frame.website.config.file;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 文件上传配置
 * @author wyy
 * @date 2019-08-22 19:20
 */
@Configuration
public class FileUploadConfig {

    /**
     * 重新定义文件上传对象【springBoot request转化成MultipartHttpServletRequest】
     * @return
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        resolver.setMaxUploadSize(512 * 1024 * 1024);
        return resolver;
    }
}
