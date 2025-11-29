package cn.edu.sju.ws.userservice.web;

import cn.edu.sju.ws.userservice.pojo.User;
import cn.edu.sju.ws.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> queryById(@PathVariable("id") Long id) {
        try {
            User user = userService.queryById(id);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        // 参数验证
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("地址不能为空");
        }

        try {
            String result = userService.addUser(user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("添加用户失败: " + e.getMessage());
        }
    }

    @PutMapping("/users/{uid}")
    public ResponseEntity<String> updateUser(@PathVariable("uid") Long uid, @RequestBody User user) {
        try {
            String result = userService.updateUser(uid, user);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("更新用户失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/users/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable("uid") Long uid) {
        try {
            String result = userService.deleteUser(uid);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("删除用户失败: " + e.getMessage());
        }
    }
}