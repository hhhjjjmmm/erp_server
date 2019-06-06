package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.Orders;
import com.player.toys.erp.server.logic.bean.ToOrder;
import com.player.toys.erp.server.logic.dao.ToOrderMapper;
import com.player.toys.erp.server.logic.service.ToOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
public class ToOrderServiceImpl extends ServiceImpl<ToOrderMapper, ToOrder> implements ToOrderService {

    @Autowired
    ToOrderMapper toOrderMapper;

    public List<ToOrder> getorderto(){
    List<ToOrder> list=toOrderMapper.selectList(
           new QueryWrapper<ToOrder>()
                   .eq("ostatus","已支付")
    );
    return list;
    }

    /*
     * 个人订单验票
     * */
    public  ToOrder yanpiao(int id,String number){
        try {
            ToOrder orders= toOrderMapper.selectOne(new QueryWrapper<ToOrder>().lambda().eq(ToOrder::getNumber,number));
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

}
