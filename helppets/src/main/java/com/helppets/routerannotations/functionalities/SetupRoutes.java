package com.helppets.routerannotations.functionalities;

import com.helppets.routerannotations.annotations.Controller;
import com.helppets.routerannotations.annotations.Route;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;

public class SetupRoutes {

    public static void  setupRoutes() throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        try {
            String currentPackage = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().getCanonicalName();

            List<Class<?>> classList = getClasssesInPackage("helppets.src.main.java." + currentPackage);

            for (Class<?> controllerClass : classList) {
                String routePrefix = controllerClass.getAnnotation(Controller.class).prefix();

                Object controller = controllerClass.getDeclaredConstructor(String.class).newInstance(routePrefix);

                Method[] methods = controllerClass.getMethods();

                for (Method m : methods) {
                    try {
                        if (m.isAnnotationPresent(Route.class)) {
                            m.invoke(controller);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("setupRoutes() - Exception: ".concat(e.getCause().toString()));
        }
    }

    private static List<Class<?>> getClasssesInPackage(String currentPackage) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        String controllersPath = System.getProperty("user.dir").concat("/src/main/java/com/helppets/app/controllers");

        String[] malformatedControllersPath = controllersPath.split("/");
        boolean hasInitPackage = false;

        StringBuilder controllerPackageBuilder = new StringBuilder();

        for (String s : malformatedControllersPath) {
            if (s.equalsIgnoreCase("com")) {
                hasInitPackage = true;
            }

            if (hasInitPackage) {
                controllerPackageBuilder.append(s).append(".");
            }
        }

        String controllerPackage = controllerPackageBuilder.toString();

        File dir = new File(controllersPath);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (Objects.nonNull(files)) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        String className = controllerPackage + file.getName().substring(0, file.getName().length() - 5);
                        Class<?> controllerClass = Class.forName(className);

                        if (controllerClass.isAnnotationPresent(Controller.class)) {
                            classes.add(controllerClass);
                        }
                    }
                }
             }
        }

        return classes;
    }
}
