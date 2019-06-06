package com.player.toys.erp.server.config.security;

import com.player.toys.erp.server.util.JwtTokenUtil;
import com.player.toys.erp.server.common.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName JwtTokenFilter
 * @Description
 * @Author Player
 * @Date 2019-05-16 15:59
 * @Version 1.0
 **/
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader( Const.HEADER_STRING );
        if (authHeader != null && authHeader.startsWith( Const.TOKEN_PREFIX )) {
            final String authToken = authHeader.substring( Const.TOKEN_PREFIX.length() );
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)&&validateRole(httpServletRequest.getRequestURI(),userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /*校验是否拥有权限,必须在这里校验一次,不然投票器中会通过1,不通过1  平及通过*/
    private Boolean validateRole(@NotNull String requestURI,@NotNull UserDetails userDetails){
        List roles= userDetails.getAuthorities().stream().map(o->String.valueOf(o)).collect(Collectors.toList());
        for (int i = 0; i <roles.size() ; i++) {
            if(requestURI.equals(roles.get(i))){
                return  true;
            }
        }
        return true;
    }
}
