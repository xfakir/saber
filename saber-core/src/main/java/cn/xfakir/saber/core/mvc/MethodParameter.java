package cn.xfakir.saber.core.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MethodParameter {
    private Method method;

    private int parameterIndex;

    private Class<?> parameterType;

    private Class<?> containingClass;

    private Annotation[] annotations;

    private String parameterName;

    public MethodParameter(Method method, int parameterIndex, Class<?> parameterType, Class<?> containingClass,
                           Annotation[] annotations, String parameterName) {
        this.method = method;
        this.parameterIndex = parameterIndex;
        this.parameterType = parameterType;
        this.containingClass = containingClass;
        this.annotations = annotations;
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

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public String getParameterName() {
        return parameterName;
    }

    public boolean hasAnnotation(Class<?> annotationType) {
        for (Annotation annotation : getAnnotations()) {
            if (annotation.annotationType() == annotationType) {
                return true;
            }
        }
        return false;
    }
}
