package cn.edu.sju.ws.orderservice.web;

import cn.edu.sju.ws.orderservice.pojo.User;
import cn.edu.sju.ws.orderservice.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/feign")
public class OrderFeignController {

    @Autowired
    private UserFeignService userFeignService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersByFeign() {
        try {
            List<User> users = userFeignService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUserByFeign(@RequestBody User user) {
        try {
            String result = userFeignService.addUser(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("添加用户失败: " + e.getMessage());
        }
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<String> updateUserByFeign(@PathVariable("uid") Long uid, @RequestBody User user) {
        try {
            String result = userFeignService.updateUser(uid, user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新用户失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity<String> deleteUserByFeign(@PathVariable("uid") Long uid) {
        try {
            String result = userFeignService.deleteUser(uid);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("删除用户失败: " + e.getMessage());
        }
    }
}