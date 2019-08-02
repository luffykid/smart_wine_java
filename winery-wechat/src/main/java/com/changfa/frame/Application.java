package com.changfa.frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
//@ComponentScan({"com.changfa.frame"})
@ServletComponentScan
@EnableAsync
@EnableScheduling
@MapperScan("com.changfa.frame.mapper")
public class Application extends SpringBootServletInitializer {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(final ObjectProvider<List<HttpMessageConverter<?>>> convertersProvider) {
        /*TimeZone tz = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(tz);*/
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                List<HttpMessageConverter<?>> converters = convertersProvider.getIfAvailable();
                System.out.print(converters.size());
            }
        };
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
