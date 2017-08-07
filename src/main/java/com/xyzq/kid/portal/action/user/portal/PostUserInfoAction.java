package com.xyzq.kid.portal.action.user.portal;

import com.xyzq.kid.CommonTool;
import com.xyzq.kid.portal.action.ticket.GetTicketsAction;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.kid.logic.user.entity.UserEntity;
import com.xyzq.kid.logic.user.service.UserService;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 范例动作
 */
@MaggieAction(path = "kid/portal/postUserInfo")
public class PostUserInfoAction extends PortalUserAjaxAction {
    /**
     * Action中只支持Autowired注解引入SpringBean
     */
    @Autowired
    private UserService userService;

    /**
     * 日志对象
     */
    public static Logger logger = LoggerFactory.getLogger(PostUserInfoAction.class);

    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String doExecute(Visitor visitor, Context context) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.telephone = (String) context.get(CONTEXT_KEY_MOBILENO);
        userEntity.userName = (String)context.parameter("userName");
        userEntity.sex = (Integer)context.parameter("sex", 0);
        userEntity.address = (String)context.parameter("address", "");
        userEntity.subscribetime = CommonTool.dataToStringYMDHMS(new Date());
        logger.info("[kid/portal/postUserInfo]-in:" + userEntity.toString());
        userService.updateByMobileNo(userEntity);
        return "success.json";
    }
}
