package com.xyzq.kid.portal.action.base;

import com.xyzq.kid.portal.utility.QRCodeHelper;
import com.xyzq.simpson.base.etc.Base64;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 二维码渲染动作
 */
@MaggieAction(path = "kid/portal/qrcode/route.action")
public class QRCodeAction implements IAction {
    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        Pattern pattern = Pattern.compile("kid/portal/qrcode/([\\w=\\+/]+)\\.(PNG|png|JPEG|jpeg|JPG|jpg)$");
        Matcher matcher = pattern.matcher(context.path());
        if(matcher.find()) {
            String url = Base64.decode(matcher.group(1));
            String suffix = matcher.group(2);
            writeImage(visitor, url, suffix);
        }
        return null;
    }

    /**
     * 输出图片
     *
     * @param visitor 访问者
     * @param content 二维码内容
     * @param suffix 后缀名
     */
    public void writeImage(Visitor visitor, String content, String suffix) throws IOException {
        byte[] bytes = QRCodeHelper.generateQRCode(content, suffix, 300);
        if("png".equalsIgnoreCase(suffix)) {
            visitor.response().setHeader("Content-Type", "image/png");
        }
        else {
            visitor.response().setHeader("Content-Type", "image/jpeg");
        }
        writeImage(visitor.response(), bytes);
    }

    /**
     * 输出图片
     *
     * @param response 输入
     * @param bytes 字节流
     */
    public void writeImage(HttpServletResponse response, byte[] bytes) throws IOException {
        InputStream inputStream = null;
        inputStream = new ByteArrayInputStream((byte[]) bytes);
        transfer(inputStream, response.getOutputStream());
        inputStream.close();
    }

    /**
     * 输入转换输出
     *
     * @param source 源
     * @param target 目标
     * @return 传输字节数
     */
    public static long transfer(InputStream source, OutputStream target) throws IOException {
        long result = 0L;
        byte[] buffer = new byte[1024];
        int bytes = 0;
        while((bytes = source.read(buffer)) != -1) {
            if(0 == bytes) {
                continue;
            }
            target.write(buffer, 0, bytes);
            target.flush();
            result += bytes;
        }
        return result;
    }
}
