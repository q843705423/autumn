package io.github.q843705423.autumn.template;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64.Encoder;

import static org.junit.Assert.*;

public class RestTemplateGetCallTest {

    @Test
    public void call() throws UnsupportedEncodingException {

        String url="http://192.168.0.19:8888/cas/login&a=1";
        String urlEncodee= URLEncoder.encode(url,"UTF-8");
        System.out.println(urlEncodee);
    }
}