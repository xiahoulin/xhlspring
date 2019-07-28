package com.xhl.spring.starter;

import com.xhl.spring.beans.BeanFactory;
import com.xhl.spring.core.ClassScanner;
import com.xhl.spring.web.handler.HandlerManager;
import com.xhl.spring.web.server.TomcatServer;

import java.util.List;

public class MiniApplication {
    public static void run(Class<?> cls, String[] args){
        System.out.println("Hello mini-spring!");
        TomcatServer tomcatServer = new TomcatServer(args);
        try {
            tomcatServer.startServer();
            List<Class<?>> classes = ClassScanner.scanClasses(cls.getPackage().getName());
            BeanFactory.initBean(classes);

            HandlerManager.resolveMappingHandler(classes);
            classes.forEach(it ->
                System.out.println(it.getName())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
