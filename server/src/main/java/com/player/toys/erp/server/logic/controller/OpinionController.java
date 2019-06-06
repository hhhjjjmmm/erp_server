package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.Opinion;
import com.player.toys.erp.server.logic.service.impl.OpinionServiceImpl;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 意见表; InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-21
 */
@RestController
@Slf4j
public class OpinionController {

    @Autowired
    OpinionServiceImpl opinionService;

    /*
     * 意见接口
     * */
    @RequestMapping(value = "/zyx/opinfind")
    public Result getopin(Opinion opinion){
        System.out.println("========1");
        Map map=new HashMap();
        List list= opinionService.list();
        map.put("list",list);
        log.info("意见查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }
    //添加
    @RequestMapping(value = "/zyx/opinadd",method = RequestMethod.POST)
    public String opinadd(Opinion opinion){
        if ( opinionService.save(opinion)){;
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/zyx/opinup",method = RequestMethod.POST)
    public String opinup(Opinion opinion){
        if ( opinionService.updateById(opinion)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/opindel",method = RequestMethod.POST)
    public String opindel(String id){
        List<String> list = getList(id);
        if (opinionService.removeByIds(list)){;
            log.info("删除成功");
            return "SUCCESS";}
        log.error("删除失败");
        return "ERROR";
    }
    public List<String> getList(String id){
        List<String> list=new ArrayList<String>();
        String[] str=id.split(",");
        for (int i=0; i<str.length;i++){
            list.add(str[i]);
        }
        return list;
    }
}
