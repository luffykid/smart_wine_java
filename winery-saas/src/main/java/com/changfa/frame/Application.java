package com.changfa.frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@MapperScan("com.changfa.frame.mapper.*")
//@EnableAsync
@Cacheable
public class Application {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(final ObjectProvider<List<HttpMessageConverter<?>>> convertersProvider) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                List<HttpMessageConverter<?>> converters = convertersProvider.getIfAvailable();
                System.out.print(converters.size());
            }
        };
    }

    //设置上传文件大小
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("5MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("5MB");
        return factory.createMultipartConfig();
    }


//    @Bean
//    public ObjectMapper objectMapper()
//    {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.findAndRegisterModules();
//        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        mapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
//                false);
//        return mapper;
//    }
}
