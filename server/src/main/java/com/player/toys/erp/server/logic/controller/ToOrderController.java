package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.ToOrder;
import com.player.toys.erp.server.logic.service.impl.ToOrderServiceImpl;
import com.player.toys.erp.server.util.QRCodeUtil;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 订单表; InnoDB free: 3072 kB; (`user_id`) REFER `boot/tb_user`(`uid`) ON DELETE NO  前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@RestController
@Slf4j
public class ToOrderController {
    @Autowired
    ToOrderServiceImpl toOrderService;

    String paths="E:\\orders\\";

    /*
     * 订单服务接口
     * */
    @RequestMapping(value = "/zyx/toofind")
    public Result getcity(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= toOrderService.list();
        map.put("list",list);
        log.info("店铺订单查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }




    /*
     * 查询已付款
     * */
    @RequestMapping(value = "/zyx/ordedianp")
    public Result getfuk(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= toOrderService.getorderto();
        map.put("list",list);
        log.info("已付款的店铺订单查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }


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

    /*
     * 随机订单号
     * */
    public static String suiji(){
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsSSSS");
        long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子
        //  Random random = new Random(seed);// 调用种子生成随机数
        // StringBuffer randomString = new StringBuffer();// 装载生成的随机数
        //  randomString.append(random.nextInt(1));
        //  String result =sdf.format(new Date()) +randomString;
        String sj=""+seed;
        return sj;
    }

    //添加
    @RequestMapping(value = "/zyx/tooadd",method = RequestMethod.POST)
    public String cityadd(HttpServletRequest request, ToOrder city) throws Exception {
        city.setNumber(suiji());
        city.setStates("店铺订单");
        //获取当前用户id
        int id= (int) request.getSession().getAttribute("id");
        city.setUserId(id);
        String context="用户:"+city.getUserId()+"验证码："+city.getNumber()+"订单类型:"+city.getStates();
        city.setOpic(paths+QRCodeUtil.encode(context,paths));
        float price=city.getPrice();
        int num=city.getOnum();
        float zong=price*num;
        city.setPricenum(zong);
        if ( toOrderService.save(city)){
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }

    //修改
    @RequestMapping(value = "/zyx/tooup",method = RequestMethod.POST)
    public String cityup(ToOrder city){
        if ( toOrderService.updateById(city)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/toodel",method = RequestMethod.POST)
    public String citydel(String id) {
        try {
            List<String> list = getList(id);
            if (toOrderService.removeByIds(list)) {
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

