package cn.xfakir.saber.core.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodParameter {
    private Method method;

    private int parameterIndex;

    private Class<?> parameterType;

    private Class<?> containingClass;

    private Annotation annotation;

    private String parameterName;

    public MethodParameter(Method method, int parameterIndex, Class<?> parameterType, Class<?> containingClass,
                           Annotation annotation, String parameterName) {
        this.method = method;
        this.parameterIndex = parameterIndex;
        this.parameterType = parameterType;
        this.containingClass = containingClass;
        this.annotation = annotation;
        this.parameterName = parameterName;
    }

    public Method getMethod() {
        return method;
    }

    public int getParameterIndex() {
        return parameterIndex;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public Class<?> getContainingClass() {
        return containingClass;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public String getParameterName() {
        return parameterName;
    }

    public boolean hasAnnotation(Class<?> annotationType) {
        return this.annotation.annotationType() == annotationType;
    }
}
