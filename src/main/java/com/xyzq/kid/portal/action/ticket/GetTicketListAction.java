package com.xyzq.kid.portal.action.ticket;

import com.xyzq.kid.logic.Page;
import com.xyzq.kid.logic.ticket.entity.TicketEntity;
import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.simpson.base.json.JSONArray;
import com.xyzq.simpson.base.json.JSONNumber;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.base.json.JSONString;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 增票
 */
@MaggieAction(path = "kid/portal/getTickets")
public class GetTicketListAction extends PortalUserAjaxAction{
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private TicketService ticketService;

    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(GetTicketListAction.class);

    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        String telephone = (String) context.get(PortalUserAjaxAction.CONTEXT_KEY_MOBILENO);
        logger.info("[kid/portal/getTickets]-in:" + telephone);
        Page<TicketEntity> page = ticketService.queryTicketByCond(null, telephone, null, null, null, 0, 1000);
        JSONArray jsonArray = new JSONArray();
        for(TicketEntity ticketEntity : page.getResultList()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", new JSONNumber(ticketEntity.id));
            jsonObject.put("serialNumber", new JSONString(ticketEntity.serialNumber));
            jsonObject.put("type", new JSONNumber(ticketEntity.type));
            jsonObject.put("price", new JSONNumber(ticketEntity.price));
            jsonObject.put("expire", new JSONString(ticketEntity.expire));
            jsonObject.put("status", new JSONNumber(ticketEntity.status));
            //
            jsonArray.add(jsonObject);
        }
        context.set("data", jsonArray);
        return "success.json";
    }
}
