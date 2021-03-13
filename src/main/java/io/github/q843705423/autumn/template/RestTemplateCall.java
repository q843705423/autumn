package io.github.q843705423.autumn.template;

import io.github.q843705423.autumn.AbstractRequester;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

/**
 * @author qiqi.chen
 */
public interface RestTemplateCall {

    /**
     * RestTemplate的接口调用
     *
     * @param invocation 调用
     * @return 返回结果
     */
    ResponseWrapper call(
                         Invocation invocation,
                         AbstractRequester.AbstractRequestHandlerFactory abstractRequestHandlerFactory,
                         RestTemplate restTemplate,
                         AbstractRequester abstractRequester
    ) throws AutumnException, UnsupportedEncodingException;

    RequestMethod getRequestMethod();
}
