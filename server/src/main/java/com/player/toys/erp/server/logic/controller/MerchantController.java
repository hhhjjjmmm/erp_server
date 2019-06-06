package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.Merchant;
import com.player.toys.erp.server.logic.service.impl.MerchantServiceImpl;
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
 * 商户表; InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-21
 */
@RestController
@Slf4j
public class MerchantController {

    @Autowired
    MerchantServiceImpl merchantService;

    /*
     * 商户服务接口
     * */
    @RequestMapping(value = "/zyx/merfind")
    public Result getmer(Merchant merchant){
        System.out.println("========1");
        Map map=new HashMap();
        List list= merchantService.list();
        map.put("list",list);
        log.info("商户查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }
    //添加
    @RequestMapping(value = "/zyx/meradd",method = RequestMethod.POST)
    public String meradd(Merchant merchant){
        if ( merchantService.save(merchant)){
            log.info("新增成功");
            return "SUCCESS";}
            log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/zyx/merup",method = RequestMethod.POST)
    public String merup(Merchant merchant){
        if ( merchantService.updateById(merchant)){
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/merdel",method = RequestMethod.POST)
    public String merdel(String id){
        List<String> list = getList(id);
        if (merchantService.removeByIds(list)){;
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
