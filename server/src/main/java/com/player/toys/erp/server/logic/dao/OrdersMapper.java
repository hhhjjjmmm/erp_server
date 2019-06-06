package com.player.toys.erp.server.logic.dao;

import com.player.toys.erp.server.logic.VO.OrdersVO;
import com.player.toys.erp.server.logic.bean.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表; InnoDB free: 3072 kB; (`user_id`) REFER `boot/tb_user`(`uid`) ON DELETE NO  Mapper 接口
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    List<OrdersVO> selectday(@Param("ostatus") String ostatus, @Param("status") String status);
}
