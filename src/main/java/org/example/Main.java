package org.example;

import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

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

        //tomcat.getServer().await();
    }
}