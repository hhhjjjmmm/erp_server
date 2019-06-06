package com.player.toys.erp.server.logic.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.player.toys.erp.server.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class LunboAction extends ActionSupport {

    /*
    * 图片上传
    * 2019年5月27日14:19:58
    * */
    @RequestMapping(value = "/api/imgUpdate", produces = "application/json; charset=utf-8" ,method = RequestMethod.POST)
    @ResponseBody
    public void uploadImg(@RequestParam("file") MultipartFile file,String fileName) {

        //设置文件上传路径
        String filePath = "E:\\img\\";
        try {
            FileUtils.uploadFile(file.getBytes(), filePath, fileName);
            log.info("上传成功");
         //   return "上传成功";
        } catch (Exception e) {
            log.error("上传失败");
           // return "上传失败";
        }
    }
}
