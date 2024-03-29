package cn.xfakir.saber.core.ioc;

import cn.xfakir.saber.core.annotation.Component;
import cn.xfakir.saber.core.annotation.Controller;
import cn.xfakir.saber.core.annotation.RestController;
import cn.xfakir.saber.core.annotation.Service;
import cn.xfakir.saber.core.util.ClassLoaderUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassPathBeanScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassLoaderUtil.class);

    public static Set<BeanDefinitionHolder> scanPackage(String classPackage) {
        Set<BeanDefinitionHolder> beanDefinitionSet = new LinkedHashSet<>();
        Enumeration<URL> urls = null;
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources(classPackage.replace(".", "/"));

            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", " ");
                        addClass(beanDefinitionSet, packagePath, classPackage);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null) {
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(beanDefinitionSet, className,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        return beanDefinitionSet;
    }

    private static void addClass(Set<BeanDefinitionHolder>  beanDefinitionSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String beanName = fileName.substring(0, fileName.lastIndexOf("."));
                String beanClassName = "";
                if (StringUtils.isNotEmpty(packageName)) {
                    beanClassName = packageName + "." + beanName;
                }
                doAddClass(beanDefinitionSet, beanClassName,beanName);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(beanDefinitionSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<BeanDefinitionHolder>  beanDefinitionSet, String beanClassName, String beanName) {
        Class<?> cls = loadClass(beanClassName, false);
        if (supportClass(cls)) {
            //
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(beanName,createBeanDefinition(beanClassName,cls));
            beanDefinitionSet.add(beanDefinitionHolder);
        }
    }

    private static BeanDefinition createBeanDefinition(String beanClassName, Class<?> cls) {
        BeanDefinition beanDefinition = new DefaultBeanDefinition(beanClassName,"singleton",cls);

        Field[] declaredFields = cls.getDeclaredFields();
        beanDefinition.setFields(declaredFields);
        return beanDefinition;
    }

    private static boolean supportClass(Class<?> cls) {
        return cls.isAnnotationPresent(Service.class) || cls.isAnnotationPresent(Component.class)
                || cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(RestController.class);
    }


    public static Class<?> loadClass(String className) {
        return loadClass(className,false);
    }

    /**
     * 加载类
     *
     * @param className
     * @param isInitialized 如果 initalize 这个参数传了 true，那么给定的类如果之前没有被初始化过，那么会被初始化。
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, ClassLoaderUtil.getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }

}
