package com.player.toys.erp.server.logic.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.player.toys.erp.server.logic.bean.Orders;
import com.player.toys.erp.server.logic.bean.ToOrder;
import com.player.toys.erp.server.logic.service.impl.OrdersServiceImpl;
import com.player.toys.erp.server.logic.service.impl.ToOrderServiceImpl;
import com.player.toys.erp.server.util.QRCodeUtil;
import com.player.toys.erp.server.util.QRCodeUtils;
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
public class OrdersController {
    @Autowired
    OrdersServiceImpl ordersService;

    @Autowired
    ToOrderServiceImpl toOrderService;

    //订单二维码绝对地址
    String paths="E:\\orders\\";

    /*
     * 订单服务接口
     * */

    @RequestMapping(value = "/zyx/ordefind")
    public Result getcity(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= ordersService.list();
        map.put("list",list);
        log.info("个人订单查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }

    /*
    * 查询已付款
    * */
    @RequestMapping(value = "/zyx/ordeger")
    public Result getfuk(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= ordersService.findlist();
        map.put("list",list);
        log.info("已付款的个人订单查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }

    /*
     * 查询总出售
     * */
    @RequestMapping(value = "/zyx/ordeday")
    public Result getday( ){
        String ostatus = "已支付";
        String status="已核销";
        List list= ordersService.finday(ostatus,status);
        log.info("出售商品查询");
        return Result.failure(ResultCode.SUCCESS,list);
    }

    /*
    * 验票接口
    * */

    @RequestMapping(value = "/zyx/ordyan")
    public Result ordyan(String number,String states,int id){
        try {
            if (states.equals("个人订单")){
               Orders orders= ordersService.yanpiao(id,number);
               Integer ids= orders.getId();
               orders.setId(ids);
               orders.setStatus("已核销");
               if (orders.getOstatus().equals("已支付")){
                   ordersService.updateById(orders);
                   return Result.failure(ResultCode.SUCCESS);
               }
               return Result.failure(ResultCode.ERROR);
            }

            if (states.equals("店铺订单")){
                ToOrder toOrder= toOrderService.yanpiao(id,number);
                Integer ids= toOrder.getId();
                toOrder.setId(ids);
                toOrder.setStatus("已核销");
                if (toOrder.getOstatus().equals("已支付")){
                    toOrderService.updateById(toOrder);
                    return Result.failure(ResultCode.SUCCESS);
                }
                return Result.failure(ResultCode.ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.failure(ResultCode.ERROR);
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
    @RequestMapping(value = "/zyx/ordeadd",method = RequestMethod.POST)
    public synchronized String cityadd(HttpServletRequest request, Orders city) throws Exception {
        city.setNumber(suiji());
        city.setStates("个人订单");
        //获取当前用户id
        int id= (int) request.getSession().getAttribute("id");
        city.setUserId(id);
        String context="用户:"+city.getUserId()+"验证码："+city.getNumber()+"订单类型:"+city.getStates();
        city.setOpic(paths+QRCodeUtil.encode(context,paths));
        float price=city.getPrice();
        int num=city.getOnum();
        float zong=price*num;
        city.setPricenum(zong);
        if ( ordersService.save(city)){
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }

    //修改
    @RequestMapping(value = "/zyx/ordeup",method = RequestMethod.POST)
    public String cityup(Orders city){
        if ( ordersService.updateById(city)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/ordedel",method = RequestMethod.POST)
    public String citydel(String id) {
        try {
            List<String> list = getList(id);
            if (ordersService.removeByIds(list)) {
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
