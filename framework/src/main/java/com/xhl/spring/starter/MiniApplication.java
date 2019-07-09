package com.xhl.spring.starter;

import com.xhl.spring.core.ClassScanner;
import com.xhl.spring.web.server.TomcatServer;
import org.apache.catalina.LifecycleException;

import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls, String[] args){
        System.out.println("Hello mini-spring!");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scanClasses(cls.getPackage().getName());

            classes.forEach(it ->
                System.out.println(it.getName())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
