package com.feng;

import com.alibaba.fastjson.JSONObject;
import com.feng.dao.UserDao;
import com.feng.entity.User;
import com.feng.service.UserService;
import com.feng.test1.ConfigurationClass;
import com.feng.utils.StringUtil;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.apache.poi.hpsf.Decimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@SpringBootTest
class BootDemoApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(BootDemoApplicationTests.class);
    @Autowired
    private UserService userService;

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    //    @BeforeEach
    public void init() {
        logger.info("开始初始化数据:[{}], [{}]", "restTemplate", "headers");
        RestTemplateBuilder builder = new RestTemplateBuilder();
        restTemplate = builder.basicAuthentication("admin", "admin").setConnectTimeout(Duration.ofSeconds(2)).setReadTimeout(Duration.ofSeconds(2)).build();
        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void dateTest() throws Exception {
        try {
            test6();
            System.out.println("你好");
        } catch (Exception e) {
            logger.error("code have error ,but don't worry");
        } finally {
            System.out.println("finally");
        }
        System.out.println(123456);
    }

    @Test
    public void test2() {
        List<String> list1 = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list1.add(i + "");
        }

        List<String> list2 = new ArrayList<>(10);
        for (char c = 'a'; c < 'z'; c++) {
            list2.add(c + "");
        }
        list1.addAll(0, list2);
        System.out.println(list1);
    }

    private void method1(int i) {
        System.out.println("method1 : " + i);
        method2(i);
    }

    private void method2(int j) {
        j++;
        System.out.println("method2 : " + j);
    }

    @Test
    public void test1() {
        List<User> userList = new ArrayList<>(2);
        User u1 = new User();
        u1.setName("测试用户3");
        u1.setAge(12);
        u1.setGender("female");
        u1.setPassword("123456");
        User u2 = new User();
        u2.setName("root1");
        u2.setAge(34);
        u2.setGender("male");
        u2.setPassword("123456");
        userList.add(u1);
        userList.add(u2);
        userService.batchSaveUser(userList);
    }

    void test() {
//        LocalDate now = LocalDate.now();
//        LocalDate date2 = LocalDate.of(2018, 12, 21);
//        logger.info("two date is equals ? [{}]", now.equals(date2));
//        LocalTime now = LocalTime.now();
//        logger.info("current time is {}", now);
//        LocalTime time2 = now.plusHours(2);
//        logger.info("two hours after time is [{}]", time2);
//        LocalDate now = LocalDate.now();
//        LocalDate after3Month = now.plus(3, ChronoUnit.MONTHS);
//        logger.info("after 3 month is [{}]", after3Month);
    }

    @Test
    public void test3() throws Throwable {
        // 方式一
        String getUrl = "http://192.168.1.5:8090/user?name={1}";
        String name1 = "lisi";
        ResponseEntity<String> entity = restTemplate.getForEntity(getUrl, String.class, name1);
        String body = entity.getBody();
        HttpHeaders headers = entity.getHeaders();
        System.out.println("response body :" + body);
        headers.forEach((k, v) -> {
            System.out.println(k + "\t : " + v);
        });

        // 方式二
        getUrl = "http://192.168.1.5:8090/user?name={name}";
        Map<String, Object> paramsMap = new HashMap<>(1, 1.0F);
        paramsMap.putIfAbsent("name", "eopok");
        ResponseEntity<JSONObject> entity2 = restTemplate.getForEntity(getUrl, JSONObject.class, paramsMap);
        JSONObject body2 = entity2.getBody();
        List<Map<String, Object>> datas = (List<Map<String, Object>>) body2.get("data");
        datas.forEach(data -> data.forEach((k, v) -> {
            System.out.println(k + " == " + v);
        }));
        HttpHeaders headers2 = entity2.getHeaders();
        headers2.forEach((k, v) -> {
            System.out.println(k + "\t :" + v);
        });

        // 方式三
        String name2 = "浩南";
        getUrl = "http://192.168.1.5:8090/user?name=" + URLEncoder.encode(name2, StandardCharsets.UTF_8.name());
        URI uri = new URI(getUrl);
        ResponseEntity<String> entity3 = restTemplate.getForEntity(uri, String.class);
        System.out.println(entity3.getBody());
        System.out.println(entity3.getHeaders());

        String object = restTemplate.getForObject(uri, String.class);
        System.out.println("getForObject : " + object);
    }

    @Test
    public void test4() {
        String postUrl = "http://192.168.1.5:8090/user";
        MultiValueMap<String, Object> requestBody1 = new LinkedMultiValueMap<>();
        requestBody1.add("age", 20);
        requestBody1.add("name", "junit");
        requestBody1.add("password", "123456");
        requestBody1.add("gender", "female");
        HttpEntity request = new HttpEntity(requestBody1, headers);
        ResponseEntity<String> entity1 = restTemplate.postForEntity(postUrl, request, String.class);
        System.out.println(entity1.getBody());
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigurationClass.class);
        ConfigurationClass bean = context.getBean(ConfigurationClass.class);
        Integer age = bean.getAge();
        String name = bean.getName();
    }

    @Test
    public void test6() {
        int x = 121;
        StringBuilder reverse = new StringBuilder();
        String tempString = String.valueOf(x);
        char[] arrays = tempString.toCharArray();
        for (int i = arrays.length - 1; i >= 0; i--) {
            reverse.append(arrays[i]);
        }
        if (tempString.equals(reverse.toString())) {
            System.out.println(true);
        }
    }

    @Test
    public void test7() {
        DecimalFormat format = new DecimalFormat("####.000000");
        BigDecimal decimal = new BigDecimal("123211.25000000");
        String s = format.format(decimal);
        System.out.println(s);
    }

    @Test
    public void test8() {
        List<String> list = new ArrayList<>(10);
        for (int i = 1; i <= 10; i++) {
            list.add(i + "");
        }
        list.removeIf("5"::equals);
        list.forEach(System.out::println);
    }

}
