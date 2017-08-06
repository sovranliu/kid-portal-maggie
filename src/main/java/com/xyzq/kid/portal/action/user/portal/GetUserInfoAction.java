package com.xyzq.kid.portal.action.user.portal;

import com.xyzq.kid.logic.user.service.UserService;
import com.xyzq.simpson.base.json.JSONObject;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 范例动作
 */
@MaggieAction(path = "kid/portal/getUserInfo")
public class GetUserInfoAction extends PortalUserAjaxAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private UserService userService;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        String mobileNo = (String) context.get(CONTEXT_KEY_MOBILENO);
        context.set("data", JSONObject.convertFromObject(userService.selectByMolieNo(mobileNo)));
        return "success.json";
    }
}
