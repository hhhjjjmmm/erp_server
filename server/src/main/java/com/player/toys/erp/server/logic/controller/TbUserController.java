package com.player.toys.erp.server.logic.controller;


import com.player.toys.erp.server.logic.bean.TbUser;
import com.player.toys.erp.server.logic.service.impl.TbUserServiceImpl;
import com.player.toys.erp.server.util.Result;
import com.player.toys.erp.server.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 后台用户表; InnoDB free: 3072 kB 前端控制器
 * </p>
 *
 * @author Player
 * @since 2019-05-22
 */
@RestController
@Slf4j
public class TbUserController {

    @Autowired
    TbUserServiceImpl tbUserService;

    // 用户图片绝对地址
    String paths="E:\\img\\";
    /*
     * 手机号码+密码登陆
     * */
    @RequestMapping(value = "/zyx/userlogin")
    public synchronized  Result userLogin(HttpServletRequest request,String uphone,String upasswrod){
        TbUser user= tbUserService.userlogin(uphone);
        if (user.getUphone().equals(uphone)&&user.getUpasswrod().equals(upasswrod)){
            request.getSession().setAttribute("uphone",uphone);
            request.getSession().setAttribute("id",user.getUid());
            request.getSession().setAttribute("uname",user.getUname());
            log.info(user.getUname()+"登陆成功");
            return  Result.failure(ResultCode.SUCCESS,user);
        }
        return  Result.failure(ResultCode.ERROR,"登陆失败");
    }

    /*
     * 手机号码+短信验证
     * */
    @RequestMapping(value = "/zyx/userloginto")
    public synchronized  Result userLoginTo(HttpServletRequest request,String uphone,String number){
        TbUser user= tbUserService.userlogin(uphone);
        if (user!=null){
            String numbers= (String) request.getSession().getAttribute("number");
            if (user.getUphone().equals(uphone)&&numbers.equals(number)){
                request.getSession().setAttribute("uphone",uphone);
                request.getSession().setAttribute("id",user.getUid());
                request.getSession().setAttribute("uname",user.getUname());
                return  Result.failure(ResultCode.SUCCESS,user);
            }
            return  Result.failure(ResultCode.ERROR,"登陆失败,手机号或验证码异常！");
        }
        return  Result.failure(ResultCode.ERROR,"登陆失败,手机号未注册！");
    }

    /*
     * 登出
     * */
    @RequestMapping(value = "/zyx/dengchu")
    public void dengChu(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 干掉cookie和session
        request.getSession().invalidate();

        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if ("autoLogin".equals(c.getName())) {
                    //设置cookie存活时间为0
                    c.setMaxAge(0);
                    //将cookie响应到前台
                    response.addCookie(c);
                    break;
                }
            }
        }
        // 重定向到首页
        // response.sendRedirect(request.getContextPath() + "/index.html");
    }

    //用户头像修改
    @RequestMapping(value = "/zyx/imgUpLoad")
    @ResponseBody
    public String imgUpLoad(@RequestParam("file") MultipartFile file,TbUser tbUser) {
        if (file.isEmpty()) {
            return new String("文件为空");
        }
        String fileName = file.getOriginalFilename();
        fileName=  UUID.randomUUID().toString().replace("-", "")+".jpg";
        LunboAction lunboAction=new LunboAction();
        lunboAction.uploadImg(file,fileName);
        tbUser.setUimg(paths+fileName);
        tbUserService.updateById(tbUser);
        return "修改成功";
    }
    /*
     * 图片上传接口
     * */
    @RequestMapping(value = "/zyx/setimg")
    public Result setImg(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return Result.failure(ResultCode.ERROR);
        }
        String filename=file.getOriginalFilename();
        // 文件上传后的路径
        String filePath = paths;//服务器路径
        File dest=new File(filePath+filename);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.failure(ResultCode.ERROR,"上传的不是图片！");
    }

    /*
     * 全体用户接口
     * */
    @RequestMapping(value = "/zyx/gettbu")
    public Result getTbu(){
        System.out.println("========1");
        Map map=new HashMap();
        List list= tbUserService.list();
        map.put("list",list);
        log.info("个人用查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }

    /*
     * 个人用户接口
     * */
    @RequestMapping(value = "/zyx/tbuselect")
    public Result getUserNoe(HttpServletRequest request){
        System.out.println("========1");
        Map map=new HashMap();
        int id= (int) request.getSession().getAttribute("id");
        TbUser tbUser= tbUserService.getusernoeByid(id);
        map.put("tbUser",tbUser);
        log.info("个人用查询");
        return Result.failure(ResultCode.SUCCESS,map);
    }

    /*
     * 团员注册
     * */
    @RequestMapping(value = "/zyx/tbuadd")
    public  Result tbuAdd(HttpServletRequest request){
        int id= (int) request.getSession().getAttribute("id");
        TbUser tbUser= tbUserService.getusernoeByid(id);
        if (tbUser.getStuts()!=0){

        }
        return Result.failure(ResultCode.SUCCESS,"注册成功");
    }

    //手机号码绑定
    @RequestMapping(value = "/zyx/tbubinding")
    public Result tbuBinDing(HttpServletRequest request,String openid,String uphone,String upasswrod,String number){
        try {
            String numbers= (String) request.getSession().getAttribute("number");
            TbUser tbUser=tbUserService.userfind(openid);
            Integer id=tbUser.getUid();
            if (numbers.equals(number)){
                TbUser user=new TbUser();
                user.setUid(id);
                user.setUphone(uphone);
                user.setUpasswrod(upasswrod);
                if ( tbUserService.updateById(user)){;
                    log.info("新增成功");
                    request.getSession().setAttribute("uphone",uphone);
                    request.getSession().setAttribute("id",user.getUid());
                    request.getSession().setAttribute("uname",user.getUname());
                    return Result.failure(ResultCode.SUCCESS,"绑定成功");}
                log.error("新增失败");
                return Result.failure(ResultCode.ERROR,"系统异常请稍后再试！");
            }
        }catch (Exception e){
            log.error("系统异常错误");
            e.printStackTrace();
        }
        log.error("验证码错误");
        return Result.failure(ResultCode.ERROR,"验证码错误");
    }


    //修改
    @RequestMapping(value = "/zyx/tbuup",method = RequestMethod.POST)
    public String tbuUp(TbUser tbUser){
        if ( tbUserService.updateById(tbUser)){;
            log.info("更新成功");
            return "SUCCESS";}
        log.error("更新失败");
        return "ERROR";
    }

    //删除
    @RequestMapping(value = "/zyx/tbudel",method = RequestMethod.POST)
    public String tbuDel(String id) {

        List<String> list = getList(id);
        if (tbUserService.removeByIds(list)) {
            log.info("删除成功");
            return "SUCCESS";
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
