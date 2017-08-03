package com.xyzq.kid.portal.utility;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * 二维码帮助类
 */
public class QRCodeHelper {
    /**
     * 根据字符串生成指定后缀的二维码字节
     *
     * @param text   待编码文本
     * @param format 生成二维码图片类型，jpg、png、gif、jpeg
     * @param pixel  二维码图片边长的像素数
     * @return 图片内容字节
     */
    public static byte[] generateQRCode(String text, String format, int pixel) throws IOException {
        byte[] byteOut = null;
        // 设置二维码的参数
        HashMap hints = new HashMap();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        try {
            // 将字符串编码生成矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, pixel, pixel, hints);
            // 将矩阵内容写入到字节流中
            MatrixToImageWriter.writeToStream(bitMatrix, format, byteArray);
            // 转成字节数组
            byteOut = byteArray.toByteArray();
        }
        catch (WriterException e) {
            throw new RuntimeException(e.getMessage());
        }
        finally {
            byteArray.close();
        }
        return byteOut;
    }
}
