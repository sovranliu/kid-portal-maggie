package com.xyzq.kid.portal.action.ticket.portal;

import com.xyzq.kid.logic.config.service.ConfigService;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 范例动作
 */
@MaggieAction(path = "kid/portal/getTicketPrice")
public class GetTicketPriceAction extends PortalUserAjaxAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private ConfigService configService;


    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        Map result = configService.getPriceInfo();
        if(null != result) {
            context.set("data", JSONObject.convertFromTable(result));
        }
        return "success.json";
    }
}
