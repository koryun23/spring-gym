package org.example;

import java.io.File;
import java.sql.Date;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.example.config.Config;
import org.example.config.MainWebInitializer;
import org.example.config.WebConfig;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.facade.core.TraineeFacade;
import org.example.facade.core.UserFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8888);
        StandardContext context = (StandardContext) tomcat.addWebapp("", new File("src/main/").getAbsolutePath());
        tomcat.getConnector();
        try {
            tomcat.start();
        } catch(LifecycleException e) {
            e.printStackTrace();
        }

        //tomcat.getServer().await();
    }
}