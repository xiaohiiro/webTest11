package cn.edu.sju.ws.orderservice.service;

import cn.edu.sju.ws.orderservice.client.UserFeignClient;
import cn.edu.sju.ws.orderservice.pojo.User;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@Service
public class UserFeignService {

    private static final Logger logger = LoggerFactory.getLogger(UserFeignService.class);

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    public List<User> getAllUsers() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");

        Supplier<List<User>> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> {
                    logger.info("Feign - 获取所有用户");
                    try {
                        List<User> users = userFeignClient.getAllUsers();
                        logger.info("Feign获取用户成功，数量: {}", users.size());
                        return users;
                    } catch (Exception e) {
                        logger.error("Feign获取用户失败: {}", e.getMessage(), e);
                        throw e;
                    }
                });

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            logger.warn("服务调用失败，执行降级逻辑");
            return getFallbackUsers();
        }
    }

    public String addUser(User user) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> {
                    logger.info("Feign - 添加用户: {}", user);
                    try {
                        String result = userFeignClient.addUser(user);
                        logger.info("Feign添加用户结果: {}", result);
                        return result;
                    } catch (Exception e) {
                        logger.error("Feign添加用户失败: {}", e.getMessage(), e);
                        throw e;
                    }
                });

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            logger.warn("服务调用失败，执行降级逻辑");
            return "添加用户失败（降级）: " + e.getMessage();
        }
    }

    public String updateUser(Long uid, User user) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> {
                    logger.info("Feign - 更新用户 ID: {}", uid);
                    try {
                        String result = userFeignClient.updateUser(uid, user);
                        logger.info("Feign更新用户结果: {}", result);
                        return result;
                    } catch (Exception e) {
                        logger.error("Feign更新用户失败: {}", e.getMessage(), e);
                        throw e;
                    }
                });

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            logger.warn("服务调用失败，执行降级逻辑");
            return "更新用户失败（降级）: " + e.getMessage();
        }
    }

    public String deleteUser(Long uid) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");

        Supplier<String> decoratedSupplier = CircuitBreaker
                .decorateSupplier(circuitBreaker, () -> {
                    logger.info("Feign - 删除用户 ID: {}", uid);
                    try {
                        String result = userFeignClient.deleteUser(uid);
                        logger.info("Feign删除用户结果: {}", result);
                        return result;
                    } catch (Exception e) {
                        logger.error("Feign删除用户失败: {}", e.getMessage(), e);
                        throw e;
                    }
                });

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            logger.warn("服务调用失败，执行降级逻辑");
            return "删除用户失败（降级）: " + e.getMessage();
        }
    }

    // 降级方法
    private List<User> getFallbackUsers() {
        logger.info("执行降级方法，返回空用户列表");
        return Collections.emptyList();
    }
}