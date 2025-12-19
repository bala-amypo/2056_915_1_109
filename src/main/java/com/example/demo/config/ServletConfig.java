package com.example.demo.config;

import com.example.demo.servlet.HelloServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {

    @Bean
    public ServletRegistrationBean<HelloServlet> helloServlet() {
        ServletRegistrationBean<HelloServlet> bean =
                new ServletRegistrationBean<>(new HelloServlet(), "/hello-servlet");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
