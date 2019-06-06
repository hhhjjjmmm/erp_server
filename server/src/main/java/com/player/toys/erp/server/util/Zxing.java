package com.player.toys.erp.server.util;

import com.google.zxing.WriterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

public class Zxing {
    private int width=150;
    private int height=150;

    public static void main(String[] args) throws Exception {
        Zxing zxing=new Zxing();
        //获取背景图
    }
    public String outAllImage(String s, String name,String path) throws Exception {
        File srcFile = new File(path);
        Image srcImg = ImageIO.read(srcFile);
        BufferedImage buffImg = null;
        buffImg = new BufferedImage(720, 1024, BufferedImage.TYPE_INT_RGB);
        buffImg.getGraphics().drawImage(
                srcImg.getScaledInstance(720, 1024, Image.SCALE_SMOOTH), 0,
                0, null);
        ImageIO.write(buffImg, "jpg", new File("E:\\img\\test.jpg"));
        realImageLocal = ImageIO.read(new File("E:\\img\\test.jpg"));
        String paths="";
        try {
           paths=  updateImage(QRCodeUtils.CreatQrImage(s,width,height),name);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
        return paths;
}
    //背景图片
    BufferedImage realImageLocal;
    //线程同步计数器
    volatile static Integer i = 1;

    //得到BufferedImage的clone对象
    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
    //根据二维码图片字节流合成背景图片生成图片
    String updateImage(BufferedImage bufferedImage,String name) throws IOException {
        BufferedImage imageLocal = deepCopy(realImageLocal);
        Graphics2D g = imageLocal.createGraphics();
        // 在模板上添加用户二维码(地址,左边距,上边距,图片宽度,图片高度,通知对象)
        g.drawImage(bufferedImage, 560, 790, 150, 150, null);
        g.setColor(Color.red);
        g.setBackground(Color.red);
        g.setFont(new Font(null,Font.BOLD,30)); //字体、字型、字号
        g.drawString(name,30,30); //画文字
        // 完成模板修改
        g.dispose();
        int num;
        synchronized (i) {
            num = i++;
        }
        // 获取新文件的地址
        File outputfile = new File("E:\\imgtest\\" + num + ".jpg");
        // 生成新的合成过的用户二维码并写入新图片
        ImageIO.write(imageLocal, "jpg", outputfile);
        return outputfile.toString();
    }


}

