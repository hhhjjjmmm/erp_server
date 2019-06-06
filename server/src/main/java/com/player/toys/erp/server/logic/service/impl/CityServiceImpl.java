package com.player.toys.erp.server.logic.service.impl;

import com.player.toys.erp.server.logic.bean.City;
import com.player.toys.erp.server.logic.dao.CityMapper;
import com.player.toys.erp.server.logic.service.CityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户表; InnoDB free: 3072 kB 服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-21
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
    @Autowired
    CityMapper cityMapper;
//    public List fingd(List<City> city){
//
//        List list=new ArrayList(city);
//       list= (List) cityMapper.selectList();
//        return list;
//    }list
}
