package com.xiaohe.xplatform;

import com.xiaohe.xplatform.config.HttpServletRequestReplacedFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.servlet.MultipartConfigElement;

/**
 * @author wzq
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = {"com.xiaohe.xplatform.modules.*.dao"})
@EnableCaching
public class XplatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(XplatformApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean httpServletRequestReplacedRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestReplacedFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("httpServletRequestReplacedFilter");
        registration.setOrder(1);
        return registration;
    }
}
