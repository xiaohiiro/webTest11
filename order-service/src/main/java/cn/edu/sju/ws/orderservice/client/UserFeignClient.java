package cn.edu.sju.ws.orderservice.client;

import cn.edu.sju.ws.orderservice.pojo.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "user-service", path = "/user")
@CircuitBreaker(name = "userServiceCircuitBreaker", fallbackMethod = "fallback")
public interface UserFeignClient {

    @GetMapping("/users")
    List<User> getAllUsers();

    @PostMapping("/users")
    String addUser(@RequestBody User user);

    @PutMapping("/users/{uid}")
    String updateUser(@PathVariable("uid") Long uid, @RequestBody User user);

    @DeleteMapping("/users/{uid}")
    String deleteUser(@PathVariable("uid") Long uid);

    // 降级方法
    default List<User> fallback(Throwable t) {
        System.out.println("熔断器触发，执行降级方法。异常: " + t.getMessage());
        return Collections.emptyList();
    }

    default String fallback(User user, Throwable t) {
        return "服务暂时不可用，请稍后重试。异常: " + t.getMessage();
    }

    default String fallback(Long uid, User user, Throwable t) {
        return "服务暂时不可用，请稍后重试。异常: " + t.getMessage();
    }

    default String fallback(Long uid, Throwable t) {
        return "服务暂时不可用，请稍后重试。异常: " + t.getMessage();
    }
}