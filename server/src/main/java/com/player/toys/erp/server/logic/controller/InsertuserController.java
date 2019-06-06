package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.Insertuser;
import com.player.toys.erp.server.logic.bean.Picture;
import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.logic.service.impl.InsertuserServiceImpl;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 销票员工表 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-06-04
 */
@RestController
@Slf4j
public class InsertuserController {
    @Autowired
    InsertuserServiceImpl insertuserService;


    /*
     * 账号+密码登陆
     * */
    @RequestMapping(value = "/zyx/iuserlogin")
    public synchronized  Result iuserlogin(HttpServletRequest request, String iname, String upassword){
        Insertuser user= insertuserService.userlogin(iname);
        if (user.getIname().equals(iname)&&user.getIpassword().equals(upassword)){
            request.getSession().setAttribute("iname",iname);
            request.getSession().setAttribute("id",user.getId());
            log.info(user.getIname()+"登陆成功");
            return  Result.failure(ResultCode.SUCCESS,user);
        }
        return  Result.failure(ResultCode.ERROR,"登陆失败");
    }


    /*
     * 人员查询
     * */
    @RequestMapping(value = "/zyx/insselect")
    public Result getsch(){
        log.info("人员查询");
        return Result.failure(ResultCode.SUCCESS,insertuserService.list());
    }
    //添加
    @RequestMapping(value = "/zyx/insadd",method = RequestMethod.POST)
    public String schadd(Insertuser insertuser){
        if ( insertuserService.save(insertuser)){;
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/zyx/insup",method = RequestMethod.POST)
    public String schup(Insertuser insertuser){
        if ( insertuserService.updateById(insertuser)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/insdel",method = RequestMethod.POST)
    public String schdel(String id) {
        try {
            List<String> list = getList(id);
            if (insertuserService.removeByIds(list)) {
                log.info("删除成功");
                return "SUCCESS";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("删除失败");
        return "ERROR";
    }
    /*
     * 分割ID串
     * */
    public List<String> getList(String id){
        List<String> list=new ArrayList<String>();
        String[] str=id.split(",");
        for (int i=0; i<str.length;i++){
            list.add(str[i]);
        }
        return list;
    }
}
