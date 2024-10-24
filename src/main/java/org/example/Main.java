package org.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

@OpenAPIDefinition(servers = {@Server(url = "localhost:8888/", description = "Gym application")})
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
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}