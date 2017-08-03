package com.xyzq.kid.portal.action.user;

import com.xyzq.kid.portal.action.base.QRCodeAction;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.springframework.beans.factory.annotation.Value;

/**
 * 二维码渲染动作
 */
@MaggieAction(path = "kid/portal/register/qrcode.jpg")
public class RegisterQRCodeAction extends QRCodeAction {
    @Value("${KID.URL_PAGE_REGISTER_WECHAT}")
    public String url_page_register;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        writeImage(visitor, url_page_register, "jpg");
        return null;
    }
}
