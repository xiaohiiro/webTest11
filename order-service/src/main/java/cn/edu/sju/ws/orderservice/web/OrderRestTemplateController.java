package cn.edu.sju.ws.orderservice.web;

import cn.edu.sju.ws.orderservice.pojo.User;
import cn.edu.sju.ws.orderservice.service.UserRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/rest")
public class OrderRestTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(OrderRestTemplateController.class);

    @Autowired
    private UserRestService userRestService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersByRest() {
        logger.info("RestTemplate - 获取所有用户");
        try {
            List<User> users = userRestService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("RestTemplate获取用户失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUserByRest(@RequestBody User user) {
        logger.info("RestTemplate - 添加用户: {}", user);
        try {
            String result = userRestService.addUser(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("RestTemplate添加用户失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("添加用户失败: " + e.getMessage());
        }
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<String> updateUserByRest(@PathVariable("uid") Long uid, @RequestBody User user) {
        logger.info("RestTemplate - 更新用户 ID: {}", uid);
        try {
            String result = userRestService.updateUser(uid, user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("RestTemplate更新用户失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("更新用户失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity<String> deleteUserByRest(@PathVariable("uid") Long uid) {
        logger.info("RestTemplate - 删除用户 ID: {}", uid);
        try {
            String result = userRestService.deleteUser(uid);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("RestTemplate删除用户失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("删除用户失败: " + e.getMessage());
        }
    }
}