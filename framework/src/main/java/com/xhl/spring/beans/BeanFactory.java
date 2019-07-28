package com.xhl.spring.beans;

import com.xhl.spring.web.mvc.Controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {
    public static Map<Class<?>, Object> classToBean = new ConcurrentHashMap<>();

    public static Object getBean(Class<?> clazz) {
        return classToBean.get(clazz);
    }

    public static void initBean(List<Class<?>> classList) throws Exception {
        List<Class<?>> toCreate = new ArrayList<>(classList);
        while (toCreate.size() != 0) {
            int remindSize = toCreate.size();
            for (int i = 0; i < toCreate.size(); i++) {
                if (finishCreate(toCreate.get(i))) {
                    toCreate.remove(i);
                }

                if (toCreate.size() == remindSize) {
                    throw new Exception("Cycle dependency!");
                }
            }
        }
    }

    private static Boolean finishCreate(Class<?> cls) throws IllegalAccessException, InstantiationException {
        if (!cls.isAnnotationPresent(Bean.class) && !cls.isAnnotationPresent(Controller.class)) {
            return true;
        }

        Object bean = cls.newInstance();
        for (Field field: cls.getDeclaredFields()){
            if (field.isAnnotationPresent(AutoWired.class)){
                Class<?> fieldType = field.getType();
                Object reliantBean = BeanFactory.getBean(fieldType);
                if (reliantBean == null){
                    return false;
                }

                field.setAccessible(true);
                field.set(bean, reliantBean);
            }
        }
        classToBean.put(cls,bean);
        return true;
    }
}