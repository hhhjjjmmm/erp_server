package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.Items;
import com.player.toys.erp.server.logic.dao.ItemsMapper;
import com.player.toys.erp.server.logic.service.ItemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * InnoDB free: 3072 kB 服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@Service
public class ItemsServiceImpl extends ServiceImpl<ItemsMapper, Items> implements ItemsService {
        @Autowired
        ItemsMapper itemsMapper;

        public Items getitems(String id){
            Items items= itemsMapper.selectOne(new QueryWrapper<Items>().lambda().eq(Items::getId,id));
            return items;
        }

        public List getmohu(String tname){
            List items=itemsMapper.selectList(new QueryWrapper<Items>().like("name",tname));
            return items;
        }
}
