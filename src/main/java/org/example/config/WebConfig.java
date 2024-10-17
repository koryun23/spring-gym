package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.example")
public class WebConfig implements WebMvcConfigurer {

//    @Bean
//    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
//        SimpleUrlHandlerMapping simpleUrlHandlerMapping
//            = new SimpleUrlHandlerMapping();
//
//        Map<String, Object> urlMap = new HashMap<>();
//        urlMap.put("/gym-spring/login", new AuthController());
//        simpleUrlHandlerMapping.setUrlMap(urlMap);
//        return simpleUrlHandlerMapping;
//    }
}
