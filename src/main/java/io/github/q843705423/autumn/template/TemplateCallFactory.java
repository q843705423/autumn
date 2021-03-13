package io.github.q843705423.autumn.template;

import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class TemplateCallFactory {

    private TemplateCallFactory() {

    }


    private static Map<RequestMethod, RestTemplateCall> map = new HashMap<>();

    static {
        add(new RestTemplateGetCall());
        add(new RestTemplatePostCall());

    }

    private static void add(RestTemplateCall restTemplateCall) {
        map.put(restTemplateCall.getRequestMethod(), restTemplateCall);
    }

    public static RestTemplateCall find(RequestMethod requestMethod, Invocation invocation) throws AutumnException {
        RestTemplateCall orDefault = map.getOrDefault(requestMethod, null);
        if (orDefault == null) {
            throw new AutumnException("找不到合适的请求方式", invocation);
        }
        return orDefault;

    }
}
