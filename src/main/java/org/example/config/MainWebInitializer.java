package org.example.config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebConfig.class);
        rootContext.setConfigLocation("org.example.config");

        servletContext.addListener(new ContextLoaderListener(rootContext));

        rootContext.setServletContext(servletContext);

        ServletRegistration.Dynamic dispatcher =
            servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic encodingFilter =
            servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.setInitParameter("Content-Type", "application/json");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
        //dispatcher.setInitParameter("Content-Type", "application/json");


        //encodingFilter.setInitParameter("encoding", "UTF-8");
        //encodingFilter.setInitParameter("forceEncoding", "true");
        //encodingFilter.addMappingForUrlPatterns(null, true, "/*");
        //encodingFilter.setInitParameter("application/type", "json");
        //encodingFilter.setInitParameter("forceEncoding", "true");
        //encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }

//    @Override
//    protected String[] getServletMappings() {
//        return new String[]{"/"};
//    }
//
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[]{Config.class, HibernateConfig.class};
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[]{WebConfig.class};
//    }
}
