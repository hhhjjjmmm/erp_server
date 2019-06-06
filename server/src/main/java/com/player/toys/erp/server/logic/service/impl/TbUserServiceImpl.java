package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.logic.dao.TbUserMapper;
import com.player.toys.erp.server.logic.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户表; InnoDB free: 3072 kB 服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {
            @Autowired
            TbUserMapper tbUserMapper;

            public  TbUser getusernoeByid(int id){
                TbUser user=tbUserMapper.selectOne(new QueryWrapper<TbUser>().lambda().eq(TbUser::getUid,id));
                return user;
            }
            public  TbUser userlogin(String uphone){
                TbUser user=tbUserMapper.selectOne(
                        new QueryWrapper<TbUser>()
                                .lambda()
                                .eq(TbUser::getUphone,uphone));
                return user;
            }
             public  TbUser userfind(String openid){
                 TbUser user=tbUserMapper.selectOne(
                          new QueryWrapper<TbUser>()
                             .lambda()
                                  .eq(TbUser::getOpenid,openid));
                            return user;
    }

}
