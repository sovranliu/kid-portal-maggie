package com.xyzq.kid.portal.action.user;

import com.xyzq.kid.portal.action.base.QRCodeAction;
import com.xyzq.simpson.base.etc.Base64;
import com.xyzq.simpson.base.etc.Serial;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.base.json.JSONString;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.springframework.beans.factory.annotation.Value;

/**
 * 登录二维码渲染动作
 */
@MaggieAction(path = "kid/portal/login/qrcode")
public class LoginQRCodeAction extends QRCodeAction {
    @Value("${KID.URL_PAGE_CONFIRM_WECHAT}")
    public String url_page_confirm_wechat;
    @Value("${KID.URL_IMAGE_QRCODE}")
    public String url_image_qrcode;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        String code = Serial.makeRandomString(16);
        String urlConfirm = url_page_confirm_wechat + code;
        String qrCodeUrl = url_image_qrcode + Base64.encode(urlConfirm) + ".jpg";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("qrcode", new JSONString(qrCodeUrl));
        jsonObject.put("code", new JSONString(code));
        context.set("data", jsonObject);
        return "success.json";
    }
}
