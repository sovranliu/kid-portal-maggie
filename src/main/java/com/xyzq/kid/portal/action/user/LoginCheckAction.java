package com.xyzq.kid.portal.action.user;

import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;
import com.xyzq.simpson.maggie.framework.action.core.IAction;
import com.xyzq.simpson.utility.cache.core.ITimeLimitedCache;

import javax.annotation.Resource;

/**
 * 登录时检测状态动作
 */
@MaggieAction(path = "kid/portal/login/check")
public class LoginCheckAction implements IAction {
    /**
     * 缓存访问对象
     *
     * 缓存中内容为：mobileNo,openId
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
        String code = (String) context.get("code");
        String sId = cache.get("login-" + code);
        if(null == sId) {
            return "success.json";
        }
        visitor.setCookie("sid", sId);
        context.set("data", sId);
        return "success.json";
    }
}
