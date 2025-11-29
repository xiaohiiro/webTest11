package cn.edu.sju.ws.userservice.service;

import cn.edu.sju.ws.userservice.mapper.UserMapper;
import cn.edu.sju.ws.userservice.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public User queryById(Long id) {
        logger.info("根据ID查询用户: {}", id);
        User user = userMapper.findById(id);
        logger.info("查询结果: {}", user);
        return user;
    }

    public List<User> findAll() {
        logger.info("查询所有用户");
        List<User> users = userMapper.findAll();
        logger.info("查询到 {} 个用户", users.size());
        return users;
    }

    public String addUser(User user) {
        try {
            logger.info("添加用户: {}", user);
            int result = userMapper.insert(user);
            String message = result > 0 ? "用户添加成功" : "用户添加失败";
            logger.info("添加结果: {}", message);
            return message;
        } catch (Exception e) {
            logger.error("添加用户失败: {}", e.getMessage(), e);
            return "用户添加失败: " + e.getMessage();
        }
    }

    public String updateUser(Long uid, User user) {
        try {
            user.setId(uid);
            logger.info("更新用户 ID: {}, 数据: {}", uid, user);
            int result = userMapper.update(user);
            String message = result > 0 ? "用户更新成功" : "用户更新失败，用户不存在";
            logger.info("更新结果: {}", message);
            return message;
        } catch (Exception e) {
            logger.error("更新用户失败: {}", e.getMessage(), e);
            return "用户更新失败: " + e.getMessage();
        }
    }

    public String deleteUser(Long uid) {
        try {
            logger.info("删除用户 ID: {}", uid);
            int result = userMapper.deleteById(uid);
            String message = result > 0 ? "用户删除成功" : "用户删除失败，用户不存在";
            logger.info("删除结果: {}", message);
            return message;
        } catch (Exception e) {
            logger.error("删除用户失败: {}", e.getMessage(), e);
            return "用户删除失败: " + e.getMessage();
        }
    }
}