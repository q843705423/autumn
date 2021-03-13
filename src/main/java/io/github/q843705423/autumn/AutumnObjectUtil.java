package io.github.q843705423.autumn;

import cn.hutool.core.util.ReflectUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class AutumnObjectUtil {

    static HashSet<String> set;

    static {
        set = new HashSet<>();
        set.add(boolean.class.getName());
        set.add(byte.class.getName());
        set.add(short.class.getName());
        set.add(char.class.getName());
        set.add(int.class.getName());
        set.add(float.class.getName());
        set.add(double.class.getName());
        set.add(long.class.getName());
        set.add(Boolean.class.getName());
        set.add(Byte.class.getName());
        set.add(Short.class.getName());
        set.add(Character.class.getName());
        set.add(Integer.class.getName());
        set.add(Float.class.getName());
        set.add(Double.class.getName());
        set.add(Long.class.getName());
        set.add(String.class.getName());

    }

    public static boolean isSimpleType(Class<?> clazz) {
        return set.contains(clazz.getName());
    }

    public static List<Integer> getRequestBodyParamIndex(Method method) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        List<Integer> requestBodyParamIndex = new ArrayList<>();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            boolean containRequestBody = Arrays.stream(parameterAnnotation).anyMatch(s -> s.annotationType().getName().equals(RequestBody.class.getName()));
            if (containRequestBody) {
                requestBodyParamIndex.add(i);
            }
        }
        return requestBodyParamIndex;
    }

    public static List<Integer> findParamContainTypeFieldIndex(Class<?>[] paramType, Class<?> clazz) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < paramType.length; i++) {
            if (containTypeField(paramType[i], clazz)) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    public static boolean findParamContainTypeFieldIndex(Object root, Class<?> paramType) {
        Class<?> clazz = root.getClass();
        if (isSimpleType(clazz)) {
            return false;
        }
        if (isJavaDataStructure(clazz)) {
            return false;
        }
        if (isSpecialObject(clazz)) {
            return false;
        }
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            if (field.getType() == paramType) {
                return true;
            }
        }


        return false;
    }


    static HashSet<Class<?>> specialObject = new HashSet<>();

    static {
        specialObject.add(MultipartFile.class);
    }

    private static boolean isSpecialObject(Class<?> aClass) {
        return specialObject.contains(aClass);
    }

    static HashSet<Class<?>> dataStructure;

    static {
/*
        dataStructure.add(List.class);
        dataStructure.add(Map.class);
        dataStructure.add(Set.class);
*/


    }

    private static boolean isJavaDataStructure(Class<?> aClass) {
        return dataStructure.contains(aClass);
    }

    public static boolean containTypeField(Class<?> parameterType, Class<?> clazz) {
        if (isSimpleType(parameterType)) {
            return false;
        }
        if (List.class.getName().equals(parameterType.getName())) {
            return false;
        }
        if (Map.class.getName().equals(parameterType.getName())) {
            return false;
        }
        if (Set.class.getName().equals(parameterType.getName())) {
            return false;
        }
        Field[] declaredFields = parameterType.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Class<?> type = declaredField.getType();
            if (type.getName().equals(clazz.getName())) {
                return true;
            }
        }
        return false;


    }
}
