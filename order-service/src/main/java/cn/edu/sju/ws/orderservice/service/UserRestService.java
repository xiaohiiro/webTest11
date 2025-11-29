package cn.edu.sju.ws.orderservice.service;

import cn.edu.sju.ws.orderservice.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRestService {

    private static final Logger logger = LoggerFactory.getLogger(UserRestService.class);

    @Autowired
    private RestTemplate restTemplate;

    private static final String USER_SERVICE_URL = "http://user-service/user";

    public List<User> getAllUsers() {
        try {
            logger.info("发送GET请求到: {}/users", USER_SERVICE_URL);

            ResponseEntity<List<User>> response = restTemplate.exchange(
                    USER_SERVICE_URL + "/users",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<User>>() {}
            );

            logger.info("GET请求成功，返回用户数量: {}", response.getBody().size());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("GET请求失败: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("获取用户列表失败: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error("GET请求异常: {}", e.getMessage(), e);
            throw new RuntimeException("获取用户列表异常: " + e.getMessage());
        }
    }

    public String addUser(User user) {
        logger.info("发送POST请求到: {}/users, 数据: {}", USER_SERVICE_URL, user);

        try {
            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<User> request = new HttpEntity<>(user, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    USER_SERVICE_URL + "/users",
                    request,
                    String.class
            );

            logger.info("POST请求成功: {}", response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            String errorMsg = "POST请求失败: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
            logger.error(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "POST请求异常: " + e.getMessage();
            logger.error(errorMsg, e);
            return errorMsg;
        }
    }

    public String updateUser(Long uid, User user) {
        logger.info("发送PUT请求到: {}/users/{}, 数据: {}", USER_SERVICE_URL, uid, user);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<User> request = new HttpEntity<>(user, headers);

            restTemplate.put(USER_SERVICE_URL + "/users/{uid}", request, uid);

            logger.info("PUT请求成功");
            return "用户更新成功";
        } catch (HttpClientErrorException e) {
            String errorMsg = "PUT请求失败: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
            logger.error(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "PUT请求异常: " + e.getMessage();
            logger.error(errorMsg, e);
            return errorMsg;
        }
    }

    public String deleteUser(Long uid) {
        logger.info("发送DELETE请求到: {}/users/{}", USER_SERVICE_URL, uid);

        try {
            restTemplate.delete(USER_SERVICE_URL + "/users/{uid}", uid);

            logger.info("DELETE请求成功");
            return "用户删除成功";
        } catch (HttpClientErrorException e) {
            String errorMsg = "DELETE请求失败: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
            logger.error(errorMsg);
            return errorMsg;
        } catch (Exception e) {
            String errorMsg = "DELETE请求异常: " + e.getMessage();
            logger.error(errorMsg, e);
            return errorMsg;
        }
    }
}