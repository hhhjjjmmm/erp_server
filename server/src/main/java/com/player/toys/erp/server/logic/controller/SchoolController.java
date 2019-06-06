package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.School;
import com.player.toys.erp.server.logic.service.impl.SchoolServiceImpl;
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
 * 文章表; InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@RestController
@Slf4j
public class SchoolController {
        @Autowired
    SchoolServiceImpl schoolService;
    /*
     * 文章接口
     * */
    @RequestMapping(value = "/zyx/schselect")
    public Result getsch(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= schoolService.list();
        map.put("list",list);
        log.info("文章查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }
    //添加
    @RequestMapping(value = "/zyx/schadd",method = RequestMethod.POST)
    public String schadd(School school){
        if ( schoolService.save(school)){;
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/zyx/schup",method = RequestMethod.POST)
    public String schup(School school){
        if ( schoolService.updateById(school)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/schdel",method = RequestMethod.POST)
    public String schdel(String id) {
        try {
            List<String> list = getList(id);
            if (schoolService.removeByIds(list)) {
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
