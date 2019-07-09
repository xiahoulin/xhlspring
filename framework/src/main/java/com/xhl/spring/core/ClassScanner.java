package com.xhl.spring.core;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
//根据路径名称找出所有class
public class ClassScanner {
    public static List<Class<?>> scanClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace(".","/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()){
            URL resource = resources.nextElement();
            //如果是资源是jar包，则找出jar包中所有的class
            if (resource.getProtocol().contains("jar")){
                JarURLConnection jarURLConnection = (JarURLConnection) resource.openConnection();
                String jarFilePath = jarURLConnection.getJarFile().getName();
                classes.addAll(getClassesFromJar(jarFilePath, path));
            }else{

            }
        }
        return classes;
    }

    //
    private static List<Class<?>> getClassesFromJar(String jarFilePath, String path) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        JarFile jarFile = new JarFile(jarFilePath);
        //取出jar包中所有的类（JarEntry）
        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while(jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String entryName = jarEntry.getName();
            if(entryName.startsWith(path) && entryName.endsWith(".class")){
                //取出class的全限定名称
                String classFullName = entryName.replace("/",".").substring(0,entryName.length() - 6);
                classes.add(Class.forName(classFullName));
            }
        }

        return classes;
    }
}
