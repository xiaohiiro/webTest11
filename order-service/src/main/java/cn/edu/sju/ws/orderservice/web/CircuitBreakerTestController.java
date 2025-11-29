package cn.edu.sju.ws.orderservice.web;

import cn.edu.sju.ws.orderservice.pojo.User;
import cn.edu.sju.ws.orderservice.service.UserFeignService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order/test")
public class CircuitBreakerTestController {

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/circuit-breaker-status")
    public ResponseEntity<Map<String, Object>> getCircuitBreakerStatus() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userServiceCircuitBreaker");

        Map<String, Object> status = new HashMap<>();
        status.put("name", circuitBreaker.getName());
        status.put("state", circuitBreaker.getState());
        status.put("metrics", circuitBreaker.getMetrics());
        status.put("failureRate", circuitBreaker.getMetrics().getFailureRate());
        status.put("numberOfFailedCalls", circuitBreaker.getMetrics().getNumberOfFailedCalls());
        status.put("numberOfSuccessfulCalls", circuitBreaker.getMetrics().getNumberOfSuccessfulCalls());

        return ResponseEntity.ok(status);
    }

    @PostMapping("/simulate-failure")
    public ResponseEntity<String> simulateFailure() {
        // 模拟一个会失败的用户对象
        User user = new User();
        user.setUsername("test-user");
        user.setAddress("test-address");

        String result = userFeignService.addUser(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/test-fallback")
    public ResponseEntity<String> testFallback() {
        try {
            // 这里会触发熔断降级
            var users = userFeignService.getAllUsers();
            return ResponseEntity.ok("调用成功，用户数量: " + users.size());
        } catch (Exception e) {
            return ResponseEntity.ok("调用失败，降级生效: " + e.getMessage());
        }
    }
}