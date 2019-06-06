package com.player.toys.erp.server.config.security;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.ServerUser;
import com.player.toys.erp.server.logic.service.ServerUserService;
import com.player.toys.erp.server.logic.service.impl.ServerMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityUserDetailsService implements UserDetailsService {


    @Autowired
    private ServerUserService serverUserService;


    @Autowired
    private ServerMenuServiceImpl serverMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ServerUser user = serverUserService.getOne(new QueryWrapper<ServerUser>().lambda().eq(ServerUser::getUsername, username));

        if(user!=null){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            serverMenuService.getServerMenuByUserId(user.getId()).forEach(
                    menu -> grantedAuthorities.add(
                                    new SimpleGrantedAuthority(menu.getMenuUrl()))
            );

            return new User(user.getUsername(),user.getPassword(),grantedAuthorities);
        }
        return null;
    }
}
