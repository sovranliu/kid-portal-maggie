package com.xyzq.kid.portal.action.user.portal;

import com.xyzq.kid.logic.user.entity.SessionEntity;
import com.xyzq.kid.logic.user.service.UserService;
import com.xyzq.simpson.base.text.Text;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;
import com.xyzq.simpson.utility.cache.core.ITimeLimitedCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;

/**
 * 门户用户Ajax基类
 */
public abstract class PortalUserAjaxAction implements IAction {
    /**
     * 上下文中的键
     */
    public final static String CONTEXT_KEY_SID = "sid";
    /**
     * 手机号码在上下文中的键
     */
    public final static String CONTEXT_KEY_MOBILENO = "mobileNo";
    /**
     * 用户微信开放ID在上下文中的键
     */
    public final static String CONTEXT_KEY_OPENID = "openId";
    /**
     * PC站点登录页地址
     */
    @Value("${KID.URL_PAGE_LOGIN_PORTAL}")
    public String url_page_login_portal;
    /**
     * 用户服务
     */
    @Autowired
    protected UserService userService;
    /**
     * 缓存访问对象
     */
    @Resource(name = "cache")
    protected ITimeLimitedCache<String, String> cache;


    /**
     * 动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    @Override
    public String execute(Visitor visitor, Context context) throws Exception {
        String sId = visitor.cookie("sid");
        if(!Text.isBlank(sId)) {
            SessionEntity sessionEntity = userService.fetchSession(sId);
            if(null != sessionEntity) {
                context.put(CONTEXT_KEY_MOBILENO, sessionEntity.mobileNo);
                context.put(CONTEXT_KEY_OPENID, sessionEntity.openId);
                context.put(CONTEXT_KEY_SID, sId);
                return doExecute(visitor, context);
            }
        }
        context.set("redirect", url_page_login_portal);
        return "fail.json";
    }

    /**
     * 派生类动作执行
     *
     * @param visitor 访问者
     * @param context 请求上下文
     * @return 下一步动作，包括后缀名，null表示结束
     */
    public abstract String doExecute(Visitor visitor, Context context) throws Exception;
}
