//package org.example.config;
//
//import jakarta.servlet.FilterRegistration;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletRegistration;
//import org.example.exception.handler.FilterExceptionHandler;
//import org.example.filter.AuthFilter;
//import org.example.filter.MdcFilter;
//import org.springdoc.core.configuration.SpringDocConfiguration;
//import org.springdoc.core.configuration.SpringDocUIConfiguration;
//import org.springdoc.core.properties.SpringDocConfigProperties;
//import org.springdoc.core.properties.SwaggerUiConfigProperties;
//import org.springdoc.core.properties.SwaggerUiOAuthProperties;
//import org.springdoc.webmvc.core.configuration.MultipleOpenApiSupportConfiguration;
//import org.springdoc.webmvc.core.configuration.SpringDocWebMvcConfiguration;
//import org.springdoc.webmvc.ui.SwaggerConfig;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.DispatcherServlet;
//
//public class MainWebInitializer implements WebApplicationInitializer {
//
//    @Override
//    public void onStartup(ServletContext servletContext) {
//
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(WebConfig.class);
//        rootContext.setConfigLocation("org.example.config");
//
//        servletContext.addListener(new ContextLoaderListener(rootContext));
//
//        rootContext.setServletContext(servletContext);
//
//        ServletRegistration.Dynamic dispatcher =
//            servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//
//        FilterRegistration.Dynamic encodingFilter =
//            servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
//        encodingFilter.setInitParameter("encoding", "UTF-8");
//        encodingFilter.setInitParameter("forceEncoding", "true");
//        encodingFilter.setInitParameter("Content-Type", "application/json");
//        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
//
//        rootContext.refresh();
//
//        FilterRegistration.Dynamic filterExceptionHandler =
//            servletContext.addFilter("FilterExceptionHandler", rootContext.getBean(FilterExceptionHandler.class));
//        filterExceptionHandler.addMappingForUrlPatterns(null, true, "/*");
//
//        FilterRegistration.Dynamic mdcFilter =
//            servletContext.addFilter("MdcFilter", rootContext.getBean(MdcFilter.class));
//        mdcFilter.addMappingForUrlPatterns(null, true, "/*");
//
//        FilterRegistration.Dynamic authFilter =
//            servletContext.addFilter("AuthFilter", rootContext.getBean(AuthFilter.class));
//        authFilter.addMappingForUrlPatterns(null, true, "/*");
//
//        rootContext.register(
//            this.getClass(),
//            CustomSwaggerConfig.class,
//            Config.class,
//            WebConfig.class,
//            HibernateConfig.class,
//            SpringDocConfiguration.class,
//            SpringDocConfigProperties.class,
//            SpringDocWebMvcConfiguration.class,
//            MultipleOpenApiSupportConfiguration.class,
//            SwaggerConfig.class,
//            SwaggerUiConfigProperties.class,
//            SwaggerUiOAuthProperties.class,
//            SpringDocUIConfiguration.class,
//            MdcFilter.class
//        );
//    }
//}
