package com.xyzq.kid.portal.action.ticket;

import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.simpson.base.etc.Base64;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.base.json.JSONString;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 范例动作
 */
@MaggieAction(path = "kid/portal/buyTicket")
public class BuyTicketAction extends PortalUserAjaxAction {
    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(BuyTicketAction.class);
    /**
     * 微信购票页面地址
     */
    @Value("${KID.URL_PAGE_BUYTICKET_WECHAT}")
    private String url_page_buyticket_wechat;
    /**
     * 微信购票页面地址
     */
    @Value("${KID.URL_IMAGE_QRCODE}")
    private String url_image_qrcode;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        String url = url_page_buyticket_wechat + "?type=" + context.parameter("ticketType") + "&mobileNo=" + context.get(PortalUserAjaxAction.CONTEXT_KEY_MOBILENO);
        String qrCodeUrl = url_image_qrcode + Base64.encode(url) + ".jpg";
        logger.info("[kid/portal/buyTicket]-in:url[" + url + "],qrCodeUrl[" + qrCodeUrl + "]");
        JSONObject json = new JSONObject();
        json.put("qrcode", new JSONString(qrCodeUrl));
        context.set("data", json);
        return "success.json";
    }
}
