package com.player.toys.erp.server.logic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.player.toys.erp.server.logic.bean.RoleMenu;
import com.player.toys.erp.server.logic.bean.UserRole;
import com.player.toys.erp.server.logic.dao.RoleMenuMapper;
import com.player.toys.erp.server.logic.dao.ServerMenuMapper;
import com.player.toys.erp.server.logic.bean.ServerMenu;
import com.player.toys.erp.server.logic.service.ServerMenuService;
import com.player.toys.erp.server.logic.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Player
 * @since 2019-05-16
 */
@Service
public class ServerMenuServiceImpl extends ServiceImpl<ServerMenuMapper, ServerMenu> implements ServerMenuService {

    private final UserRoleService userRoleService;
    @Resource
    private ServerMenuMapper serverMenuMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;

    public ServerMenuServiceImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }


    public List<ServerMenu> getServerMenuByUserId(Long userId){
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userId));
        List<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIdList));
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        return serverMenuMapper.selectList(new QueryWrapper<ServerMenu>().lambda().in(ServerMenu::getId,menuIds));
    }
}
