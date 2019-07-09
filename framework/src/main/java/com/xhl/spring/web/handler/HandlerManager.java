package com.xhl.spring.web.handler;

import com.xhl.spring.web.mvc.Controller;
import com.xhl.spring.web.mvc.RequestMapping;
import com.xhl.spring.web.mvc.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class HandlerManager {
    public static List<MappingHandler> mappingHandlers = new ArrayList<>();

    public static void resolveMappingHandler(List<Class<?>> classList){
        classList.forEach(it -> {
            //被Controller注解类
            if (it.isAnnotationPresent(Controller.class)){
                parseHandlerFromController(it);
            }
        });
    }

    private static void parseHandlerFromController(Class<?> cls){
        Method[] methods = cls.getDeclaredMethods();
        for (Method method: methods){
            //被RequestMapping注解的方法
            if (!method.isAnnotationPresent(RequestMapping.class)){
                continue;
            }
            String uri = method.getDeclaredAnnotation(RequestMapping.class).value();
            List<String> paramNameList = new ArrayList<>();
            for (Parameter parameter : method.getParameters()){
                if (parameter.isAnnotationPresent(RequestParam.class)){
                    paramNameList.add(parameter.getDeclaredAnnotation(RequestParam.class).value());
                }
            }
            String[] params = paramNameList.toArray(new String[paramNameList.size()]);
            MappingHandler mappingHandler = new MappingHandler(uri, method, cls, params);
            HandlerManager.mappingHandlers.add(mappingHandler);
        }
    }
}
