package com.player.toys.erp.server.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeUtils {
    private static final int RED = 0xFF000000;// 用于设置二维码的颜色
    private static final int WHITE = 0xFFFFFFFF; // 用于背景色

    /**
     *
     *
     * @param content
     *            二维码内容
     * @return 生成的二维码的图片字节流
     * @throws IOException
     * @throws WriterException
     */
    public static BufferedImage CreatQrImage( String content,int width,int height) throws IOException, WriterException {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 内容所使用字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);// 设置二维码边的空度，非负数

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, // 要编码的内容
                BarcodeFormat.QR_CODE, width, // 条形码的宽度
                height, // 条形码的高度
                hints);// 生成条形码时的一些配置,此项可选

        return toBufferedImage(bitMatrix);
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (matrix.get(x, y) ? RED : WHITE));
            }
        }
        return image;
    }


}
