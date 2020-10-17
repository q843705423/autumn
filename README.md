# Autumn

## 1. Introduction
This project is an interface calling framework based on springboot. It encapsulates RestTemplate and uses dynamic proxy technology to convert the call to controller into direct call to interface.

## 2. Example

The following procedure is the classic application of springboot:
```java
package io.github.q843705423;
import io.github.q843705423.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("demo")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostMapping("world")
    public String hello(Student student) {
        return "hello,"+student.getName();
    }
}

```
Curl or postman is a good tool if we want to test the interface. But if we want to solidify the test or conduct highly automated test, we can also write some interface tests in the test class. The code is as follows:
```java
package io.github;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
public class DemoApplicationTests {
    @Test
    public void test1() {
        RestTemplate restTemplate = new RestTemplate();
        String s = restTemplate.postForObject("http://localhost:8000/demo/hello?id=1&name=Tom", new HttpEntity<>(null), String.class);
        assert "hello,Tom".equals(s);
    }
}
```
+ It seems to work very well, but it's not concise enough

+ In fact, if the @Requestbody annotation is in the parameter, or there is an interface for file upload, we usually have to try to construct the appropriate parameter and then call RestTemplate

+ Why don't we ignore some details and make it simpler. That's why autumn was born.

+ Here's the code for autumn

```java
package io.github;

import io.github.q843705423.DemoApplication;
import io.github.q843705423.entity.Student;
import org.junit.Test;
import org.seventy.seven.autumn.DefaultContextFactory;
import org.seventy.seven.autumn.entity.Configuration;

public class DemoApplicationTests {
    @Test
    public void test() {
        DefaultContextFactory defaultContextFactory = new DefaultContextFactory(new Configuration("http://127.0.0.1:8000"));
        DemoApplication controller = defaultContextFactory.getBean(DemoApplication.class);
        String tom = controller.hello(new Student("1", "Tom"));
        assert "hello,Tom".equals(tom);
    }
}
```
Autumn provides the ability to convert a method call to an interface call, which means that you think you are calling a method, but you are actually calling the interface corresponding to the method. It's fun, isn't it



