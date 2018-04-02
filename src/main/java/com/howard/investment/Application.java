package com.howard.investment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Howard on 2017/11/06
 */
@Configuration
@ComponentScan
@SpringBootApplication
@ServletComponentScan("com.howard.investment.interceptor")
public class Application extends WebMvcConfigurerAdapter {
    static {
        System.out.print("Spider启动");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    public static void main(String[] args) {
    	
       SpringApplication.run(Application.class, args);
    }
}
