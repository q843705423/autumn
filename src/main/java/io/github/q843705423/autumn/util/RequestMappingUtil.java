package io.github.q843705423.autumn.util;

import io.github.q843705423.autumn.entity.RequestInfo;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @author qiqi.chen
 */
public class RequestMappingUtil {

    public static final String PREFIX = "/";

    public static List<RequestInfo> getTypeAndUrl(String[] allPrefix, Method method) {
        List<RequestInfo> list = new ArrayList<>();
        addRequestMapping(allPrefix, method, list);
        addPostMapping(allPrefix, method, list);
        addGetMapping(allPrefix, method, list);
        return list;
    }

    private static void addGetMapping(String[] allPrefix, Method method, List<RequestInfo> list) {

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping != null) {
            RequestMethod[] requestMethods = new RequestMethod[]{RequestMethod.GET};
            String[] allSuffix = getMapping.value().length == 0 ? new String[]{"/"} : getMapping.value();
            add(allPrefix, list, requestMethods, allSuffix);
        }

    }

    private static void addPostMapping(String[] allPrefix, Method method, List<RequestInfo> list) {

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (postMapping != null) {
            RequestMethod[] requestMethods = new RequestMethod[]{RequestMethod.POST,};
            String[] allSuffix = postMapping.value().length == 0 ? new String[]{"/"} : postMapping.value();
            add(allPrefix, list, requestMethods, allSuffix);

        }

    }

    private static void add(String[] allPrefix, List<RequestInfo> list, RequestMethod[] requestMethods, String[] allSuffix) {
        for (RequestMethod requestMethod : requestMethods) {
            for (String prefix : allPrefix) {
                for (String suffix : allSuffix) {
                    RequestInfo requestInfo = new RequestInfo();
                    requestInfo.setUrl(clean(prefix) + clean(suffix));
                    requestInfo.setRequestMethod(requestMethod);
                    list.add(requestInfo);
                }
            }

        }
    }

    private static void addRequestMapping(String[] allPrefix, Method method, List<RequestInfo> list) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            RequestMethod[] requestMethods = requestMapping.method().length == 0 ? new RequestMethod[]{
                    RequestMethod.GET,
                    RequestMethod.POST,
            } : requestMapping.method();
            String[] allSuffix = requestMapping.value().length == 0 ? new String[]{"/"} : requestMapping.value();
            add(allPrefix, list, requestMethods, allSuffix);

        }

    }

    public static String[] getUrl(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (requestMapping != null) {
            String[] value = requestMapping.value();
            if (value.length == 0) {
                return new String[]{PREFIX};
            }
            return value;
        }
        if (postMapping != null) {
            String[] value = postMapping.value();
            if (value.length == 0) {
                return new String[]{PREFIX};
            }
            return value;
        }
        if (getMapping != null) {
            String[] value = getMapping.value();
            if (value.length == 0) {
                return new String[]{PREFIX};
            }
            return value;
        }
        return new String[]{PREFIX};
    }

//    public static RequestMethod getType(Method method) {
//        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
//        PutMapping putMapping = method.getAnnotation(PutMapping.class);
//        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//        GetMapping getMapping = method.getAnnotation(GetMapping.class);
//        PostMapping postMapping = method.getAnnotation(PostMapping.class);
//        if (requestMapping != null) {
//            RequestMethod[] requestMethod = requestMapping.method();
//            if (requestMethod.length == 0) {
//                return RequestMethod.POST;
//            } else {
//                return requestMethod[0];
//            }
//        } else if (getMapping != null) {
//            return RequestMethod.GET;
//        } else if (postMapping != null) {
//            return RequestMethod.POST;
//        } else if (deleteMapping != null) {
//            return RequestMethod.DELETE;
//        } else if (putMapping != null) {
//            return RequestMethod.PUT;
//        } else {
//            return null;
//        }
//    }

    private static String clean(String uri) {
        if (!uri.startsWith(PREFIX)) {
            uri = PREFIX + uri;
        }
        if (uri.endsWith(PREFIX)) {
            uri = uri.substring(0, uri.lastIndexOf(PREFIX));
        }
        return uri;
    }

    public static String join(String left, String right) {
        if (left.endsWith(PREFIX)) {
            if (right.startsWith(PREFIX)) {
                return left + right.substring(1);
            } else {
                return left + right;
            }
        } else {
            if (right.startsWith(PREFIX)) {
                return left + right;
            } else {
                return left + "/" + right;
            }
        }

    }


    public static List<String> calculateUrl(String[] prefix, String[] suffix) {
        return Arrays.stream(prefix)
                .map(left -> Arrays.stream(suffix).map(right -> join(clean(left), clean(right))).collect(toList()))
                .reduce(new ArrayList<>(), (l, r) -> {
                    l.addAll(r);
                    return l;
                }).stream()
                .filter(s -> !s.isEmpty())
                .collect(toList());

    }

    public static boolean existContainRequestBody(Annotation[] parameterAnnotation) {
        return Arrays.stream(parameterAnnotation).anyMatch(s -> s.annotationType().getName().equals(RequestBody.class.getName()));
    }

    public static boolean existContainRequestBody(Annotation[] parameterAnnotation, Class clazz) {
        return Arrays.stream(parameterAnnotation).anyMatch(s -> s.annotationType().getName().equals(clazz.getName()));
    }

    public static boolean methodParamContainAnnotation(Method method, Class clazz) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            boolean b = existContainRequestBody(parameterAnnotation);
            if (b) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> getPathVariable(Method method,Object[] args) {
        Map<String, String> pathVariable = new HashMap<>();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(PathVariable.class.getName())) {
                    PathVariable pathVariable1 = (PathVariable) annotation;
                    pathVariable.put(pathVariable1.value(), args[i].toString());

                }
            }
        }
        return pathVariable;
    }
}
