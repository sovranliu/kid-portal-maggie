package com.xyzq.kid.portal.action.book.portal;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.StringUtils;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.kid.logic.dateUnviable.entity.DateUnviableEntity;
import com.xyzq.kid.logic.dateUnviable.service.DateUnviableService;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;

@MaggieAction(path="kid/portal/isViableDate")
public class IsVisableDate extends PortalUserAjaxAction {
	
	@Autowired
	DateUnviableService dateUnviableService;

	@Override
	public String doExecute(Visitor visitor, Context context) throws Exception {
		String unviableDate=(String)context.parameter("unviableDate");
		if(!StringUtils.isNullOrEmpty(unviableDate)){
			DateUnviableEntity po=dateUnviableService.findBy(unviableDate);
			if(po!=null){
				context.set("code", "0");
			}
		}
		
		return "success.json";
	}
	
	
}
