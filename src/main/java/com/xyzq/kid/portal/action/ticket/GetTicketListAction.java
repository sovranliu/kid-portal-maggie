package com.xyzq.kid.portal.action.ticket;

import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
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
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        String serialNumber  = (String) context.get("serialNumber ");
        String telephone = (String) context.get("telephone");
        String startTime = (String) context.get("startTime ");
        String endTime  = (String) context.get("endTime ");
        int status = (Integer) context.get("status");
        int begin = (Integer) context.get("begin");
        int limit = (Integer) context.get("limit");

        context.set("data", JSONObject.convertFromObject(ticketService.queryTicketByCond(serialNumber, telephone, startTime, endTime, status, begin, limit)));
        return "success.json";
    }
}
