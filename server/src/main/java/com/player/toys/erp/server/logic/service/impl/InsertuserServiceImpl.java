package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.Insertuser;
import com.player.toys.erp.server.logic.dao.InsertuserMapper;
import com.player.toys.erp.server.logic.service.InsertuserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 销票员工表 服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-06-04
 */
@Service
public class InsertuserServiceImpl extends ServiceImpl<InsertuserMapper, Insertuser> implements InsertuserService {
    @Autowired
    InsertuserMapper insertuserMapper;
    //登陆
    public Insertuser userlogin(String iname){
        Insertuser user=insertuserMapper.selectOne(
                new QueryWrapper<Insertuser>()
                        .lambda()
                        .eq(Insertuser::getIname,iname));
        return user;
    }
}
