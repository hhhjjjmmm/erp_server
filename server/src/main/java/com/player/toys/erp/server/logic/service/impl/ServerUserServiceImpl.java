package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.player.toys.erp.server.logic.dao.ServerUserMapper;
import com.player.toys.erp.server.util.JwtTokenUtil;
import com.player.toys.erp.server.logic.bean.ServerUser;
import com.player.toys.erp.server.logic.service.ServerUserService;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-16
 */
@Service
public class ServerUserServiceImpl extends ServiceImpl<ServerUserMapper, ServerUser> implements ServerUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private ServerUserMapper serverUserMapper;

    /*
     * 获取时间
     * */
    public String getDateAfter(int day) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        date = calendar.getTime();
        return  sdf.format(date);
    }


    public String generatorToken(String username,String password) {
        //通过用户名查询出相应的用户
        ServerUser serverUser = serverUserMapper.selectOne(new QueryWrapper<ServerUser>().lambda().eq(ServerUser::getUsername, username));
        if (serverUser.getStatu().equals("启用")) {
            String salt = "";
            if (serverUser != null) {
                salt = serverUser.getSalt();
            }
            if (serverUser != null) {
                serverUser.setLastime(getDateAfter(0));
                Long id=serverUser.getId();
                serverUser.setId(id);
                serverUserMapper.updateById(serverUser);
            }
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password + salt);
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return jwtTokenUtil.generateToken(userDetails);
        }
        return null;
    }

}
