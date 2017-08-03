package com.xyzq.kid.portal.action.ticket;

import com.xyzq.kid.common.action.CustomerAction;
import com.xyzq.kid.logic.config.common.ConfigCommon;
import com.xyzq.kid.logic.config.service.ConfigService;
import com.xyzq.kid.logic.ticket.entity.TicketEntity;
import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 增票
 */
@MaggieAction(path = "kid/wechat/giveTicket")
public class GiveTicketAction extends CustomerAction {
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


        int ticketId = (Integer) context.parameter("serialNo", -1);
        String mobileNo = (String)context.parameter("phone");

        ticketService.handselTickets(ticketId, mobileNo);

        return "success.json";
    }
}
