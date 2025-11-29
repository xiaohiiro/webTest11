package cn.edu.sju.ws.orderservice.mapper;

import cn.edu.sju.ws.orderservice.pojo.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {

    @Select("select * from tb_order where id = #{id}")
    Order findById(Long id);
}
