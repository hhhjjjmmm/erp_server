package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.Items;
import com.player.toys.erp.server.logic.service.impl.ItemsServiceImpl;
import com.player.toys.erp.server.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@RestController
@Slf4j
public class ItemsController {
    @Autowired
    ItemsServiceImpl itemsService;

    static BufferedImage realImageLocal;
    /*
     * 商品服务接口
     * */
    @RequestMapping(value = "/zyx/itemselect")
    public Result getcity(){
        Map map=new HashMap();
        List list= itemsService.list();
        int num=list.size();
        map.put("list",list);
        map.put("num",num);
        System.out.println("num"+map);
        log.info("商品查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }
    /*
    *
    * */

    /*
     * 商品详情查询
     * */
    @RequestMapping(value = "/zyx/itemsgetid")
    public Result getcityid(String id){
        Map map=new HashMap();
        Items items= itemsService.getitems(id);
        map.put("items",items);
        log.info("商品详情查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }

    /*
     * 商品模糊查询
     *
     * */
    @RequestMapping(value = "/zyx/itemsmohu")
    public Result getcitymohu(String tname){
        log.info("商品模糊查询");
        return Result.failure(ResultCode.SUCCESS,itemsService.getmohu(tname));
    }

    /*
     * 商品二维码分享
     * */
    @RequestMapping(value = "/zyx/ewm")
    public Result getewm() throws Exception {
        String text="https://www.baidu.com/?tn=62095104_19_oem_dg";
        //QRCodeUtil.encode(text,null,"E:\\二维码",true);
        log.info("商品二维码分享");
        return Result.failure(ResultCode.SUCCESS,QRCodeUtil.encode(text,null,"E:\\二维码",true)
);
    }

    /*
    * 推广
    * */
    @RequestMapping(value = "/zyx/imag")
    public synchronized Result outAllImage(HttpServletRequest request,String id) throws Exception {
        Items items= itemsService.getitems(id);
        String path=items.getPic();
        //SecurityContextHolder.getContext().getAuthentication();
       // User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String s="这是地址！！！";
        String name= (String) request.getSession().getAttribute("uname");
        Zxing z=new Zxing();
       String paths= z.outAllImage(s,name,path);
        return Result.failure(ResultCode.SUCCESS,paths);
    }

    /*
     * 获取日期
     * */
    public String getDate(int day) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +day);
        date = calendar.getTime();
        return  sdf.format(date);
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

    //添加
    @RequestMapping(value = "/zyx/itemsadd",method = RequestMethod.POST)
    public String cityadd(@RequestParam("file") MultipartFile file, Items city) throws Exception {
        /*
        * 自动获取地址
        * */
        String addes= AddressUtils.getAddressByIp();
        int a=Integer.parseInt(city.getSdtime());
        String fileName = file.getOriginalFilename();
        fileName=  UUID.randomUUID().toString().replace("-", "")+".jpg";
        LunboAction lunboAction=new LunboAction();
        lunboAction.uploadImg(file,fileName);
        city.setPic("E:\\img\\"+fileName);
        city.setAddress(addes);
        city.setSdtime(getDateAfter(a));
        if ( itemsService.save(city)){;
            log.info("新增成功");
            return "SUCCESS";}
        log.error("新增失败");
        return "ERROR";
    }
    //修改
    @RequestMapping(value = "/zyx/itemsup",method = RequestMethod.POST)
    public String cityup(Items city){
        if ( itemsService.updateById(city)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/itemsdel",method = RequestMethod.POST)
    public String citydel(String id) {
        try {
            List<String> list = getList(id);
            if (itemsService.removeByIds(list)) {
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
