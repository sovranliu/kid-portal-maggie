package com.xyzq.kid.portal.action.ticket.wechat;

import com.xyzq.kid.common.action.CustomerAction;
import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 增票
 */
@MaggieAction(path = "kid/wechat/getTickets")
public class GetTicketsAction extends CustomerAction{
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
    public String execute(Visitor visitor, Context context) throws Exception {

        String result = super.execute(visitor, context);
        if(null != result) {
            return result;
        }
        String mobileNo = (String) context.get(CONTEXT_KEY_MOBILENO);

        context.set("data", JSONObject.convertFromObject(ticketService.getTicketsInfoByOwnerMobileNo(mobileNo)));
        return "success.json";
    }
}
