package cn.xfakir.saber.core.config;

import cn.xfakir.saber.core.event.SaberEvent;
import cn.xfakir.saber.core.event.SaberListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeTest implements TestInterface{
    static class SaberTest implements SaberListener<SaberEvent>{

        @Override
        public void onSaberEvent(SaberEvent event) {

        }
    }
    static class Shirou implements SaberListener<SaberEvent>{

        @Override
        public void onSaberEvent(SaberEvent event) {

        }
    }
    public static Class<?> getInterfaceT(Object o, int index) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);

    }
    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }
    public static Class<?> getClassT(Object o, int index) {
        Type type = o.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type actType = parameterizedType.getActualTypeArguments()[index];
            return checkType(actType, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }

    public static void main(String[] args) {
        TypeTest t = new TypeTest();
        System.out.println(t instanceof TestInterface);
        SaberTest saber = new SaberTest();
        Shirou shirou = new Shirou();
        Type saberType = getType(saber);
        Type shirouType = getType(shirou);
        SaberEvent s = new SaberEvent("a");
        System.out.println(saberType);
        System.out.println(shirouType);
        System.out.println(saberType == s.getClass());

    }

    private static Type getType(Object object) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) types[0];
        Type actualTypeArgument = parameterized.getActualTypeArguments()[0];
        return actualTypeArgument;
    }
}
