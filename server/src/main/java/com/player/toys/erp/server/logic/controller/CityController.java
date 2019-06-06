package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.City;
import com.player.toys.erp.server.logic.service.CityService;
import com.player.toys.erp.server.logic.service.impl.CityServiceImpl;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
 * 商户表; InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-21
 */
@Slf4j
@RestController
public class CityController {
    @Autowired
    CityServiceImpl cityService;
    /*
     * 城市服务接口
     * */
    @RequestMapping(value = "/city/select")
    public Result getcity(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= cityService.list();
        map.put("list",list);
        log.info("城市服务查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }
    //添加
    @RequestMapping(value = "/city/add",method = RequestMethod.POST)
    public String cityadd(City city){
        if ( cityService.save(city)){;
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/city/up",method = RequestMethod.POST)
    public String cityup(City city){
        if ( cityService.updateById(city)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/city/del",method = RequestMethod.POST)
    public String citydel(String id) {
        try {
            List<String> list = getList(id);
            if (cityService.removeByIds(list)) {
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
