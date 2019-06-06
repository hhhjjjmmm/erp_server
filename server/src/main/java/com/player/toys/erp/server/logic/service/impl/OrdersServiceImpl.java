package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.player.toys.erp.server.logic.VO.OrdersVO;
import com.player.toys.erp.server.logic.bean.Orders;
import com.player.toys.erp.server.logic.dao.OrdersMapper;
import com.player.toys.erp.server.logic.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表; InnoDB free: 3072 kB; (`user_id`) REFER `boot/tb_user`(`uid`) ON DELETE NO  服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

        @Autowired
        OrdersMapper ordersMapper;
        /*
        * 个人订单验票
        * */
        public  Orders yanpiao(int id,String number){
            try {
          Orders orders= ordersMapper.selectOne(new QueryWrapper<Orders>().lambda().eq(Orders::getNumber,number));
                if (orders.getUserId().equals(id)){
                    log.info("验票成功");
                    return orders;
                }
            }catch (Exception e){
                log.error("系统异常");
            }
                log.error("验票失败");
                return null;
        }


        /*
        * 已支付订单
        * */
        public List<Orders> findlist(){
            List<Orders> list =new ArrayList<>();
            // 查询已支付订单
            QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("ostatus", "已支付");
            list = ordersMapper.selectList(queryWrapper);
            System.out.println(list);
            return list;
        }
        /*
        * 出售商品个数
        * */
        public List<OrdersVO> finday(String ostatus, String status){
            List<OrdersVO> list= new ArrayList<>();
            list=ordersMapper.selectday(ostatus,status);
            System.out.println(list);
            return  list;
}
}
