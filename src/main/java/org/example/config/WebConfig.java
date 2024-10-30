//package org.example.config;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = "org.example")
////@Import({org.springdoc.webmvc.ui.SwaggerConfig.class})
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui/**")
//            .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/4.14.3/");
//        registry.addResourceHandler("/v3/api-docs")
//            .addResourceLocations("classpath:/META_INF/resources/");
//    }
//}
