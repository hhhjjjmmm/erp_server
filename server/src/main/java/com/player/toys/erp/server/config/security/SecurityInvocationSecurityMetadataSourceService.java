package com.player.toys.erp.server.config.security;
import com.player.toys.erp.server.logic.bean.ServerMenu;
import com.player.toys.erp.server.logic.service.ServerMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class SecurityInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ServerMenuService serverMenuService;


    private List<String> allAuthUrlList;


    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (allAuthUrlList == null) {
            allAuthUrlList=serverMenuService.list().stream().map(ServerMenu::getMenuUrl).collect(Collectors.toList());
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;

        for(String allAuthUrl : allAuthUrlList){
            if(allAuthUrl != null){
                matcher = new AntPathRequestMatcher(allAuthUrl);
                if(matcher.matches(request)) {
                    Collection<ConfigAttribute> arry = new ArrayList<>();
                    arry.add(new SecurityConfig(allAuthUrl));
                    return arry;
                }
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
