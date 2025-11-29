package cn.edu.sju.ws.userservice.mapper;

import cn.edu.sju.ws.userservice.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("select id, username, address from tb_user where id = #{id}")
    User findById(@Param("id") Long id);

    @Select("select id, username, address from tb_user")
    List<User> findAll();

    @Insert("insert into tb_user(username, address) values(#{username}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Update("update tb_user set username=#{username}, address=#{address} where id=#{id}")
    int update(User user);

    @Delete("delete from tb_user where id=#{id}")
    int deleteById(@Param("id") Long id);
}