package com.player.toys.erp.server.logic.controller;

import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import com.player.toys.erp.server.util.Zxings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LeagueController {

    //背景图片绝对地址
    String paths="E:\\imgtest\\292812099.jpg";

    /*
    * 朋友团员推广
    * */
    @RequestMapping(value = "/zyx/getleague")
    public Result getleague(HttpServletRequest request,String id) throws Exception {
        TbUser tbUser=new TbUser();
        Zxings zxings=new Zxings();
        String path=paths;
        // User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String s="这是地址！！！";
        String name= (String) request.getSession().getAttribute("uname")+"的邀请函";
        String pathto= zxings.outAllImage(s,name,path);
        return Result.failure(ResultCode.SUCCESS,pathto);
    }

    /*
    * 店铺推广
    * */
    @RequestMapping(value = "/zyx/getdianpu")
    public Result getdianpu(HttpServletRequest request,String id) throws Exception {
        Zxings zxings=new Zxings();
        String path=paths;
        // User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String s="这是地址！！！";
        String name=request.getSession().getAttribute("uname")+"的店";
        String pathto= zxings.outAllImage(s,name,path);
        return Result.failure(ResultCode.SUCCESS,pathto);
    }

}
