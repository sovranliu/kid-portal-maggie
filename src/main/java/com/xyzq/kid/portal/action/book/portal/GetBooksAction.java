package com.xyzq.kid.portal.action.book.portal;

import java.text.ParseException;
import java.util.*;

import com.xyzq.simpson.base.text.Text;
import com.xyzq.simpson.base.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.xyzq.kid.portal.action.user.portal.PortalUserAjaxAction;
import com.xyzq.kid.logic.book.dao.po.Book;
import com.xyzq.kid.logic.book.dao.po.BookTimeRepository;
import com.xyzq.kid.logic.book.dao.po.BookTimeSpan;
import com.xyzq.kid.logic.book.service.BookRepositoryService;
import com.xyzq.kid.logic.book.service.BookService;
import com.xyzq.kid.logic.book.service.BookTimeSpanService;
import com.xyzq.kid.logic.ticket.entity.TicketEntity;
import com.xyzq.kid.logic.ticket.service.TicketService;
import com.xyzq.simpson.maggie.access.spring.MaggieAction;
import com.xyzq.simpson.maggie.framework.Context;
import com.xyzq.simpson.maggie.framework.Visitor;

@MaggieAction(path="kid/portal/getBooks")
public class GetBooksAction extends PortalUserAjaxAction{
	
	static Logger logger = LoggerFactory.getLogger(GetBooksAction.class);
	
	@Autowired
	BookService bookService;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	BookRepositoryService bookRepositoryService;
	
	@Autowired
	BookTimeSpanService bookTimeSpanService;
	
	Gson gson=new Gson();
	
	@Override
	public String doExecute(Visitor visitor, Context context) throws Exception {
		String mobileNo=(String)context.get(CONTEXT_KEY_MOBILENO);
		List<TicketEntity> ticketList=ticketService.getTicketsInfoByOwnerMobileNo(mobileNo);
		List<Map<String,Object>> mapList=new ArrayList<>();
		if(ticketList!=null&&ticketList.size()>0){
			for(int i=0;i<ticketList.size();i++){
				TicketEntity ticket=ticketList.get(i);
				Book book=bookService.queryBookRecByTicketId(Integer.valueOf(ticket.id));
				if(book!=null){
					Map<String,Object> bookMap=new HashMap<>();
					int type=ticket.type.equals("1")?1:0;//1:个人票，0：团体票
					bookMap.put("type", type);
					bookMap.put("id", book.getId());
					String bookStatus=book.getBookstatus();//1：已预约，2：改期申请中，3：改期通过，4：改期拒绝，5：核销完成，6：撤销申请中，7：撤销通过，8：拒绝撤销
					Integer status=0;//0：已预约 1：已过期 2：已核销 3：改期审核中 4：已撤销 5：撤销申请中
					if(checkExpire(book.getBookdate(), book.getBooktime())){
						status=1;
					}else{
						if(bookStatus.equals("1")||bookStatus.equals("3")||bookStatus.equals("4")||bookStatus.equals("8")){
							status=0;
						}else if(bookStatus.equals("2")){
							status=3;
						}else if(bookStatus.equals("6")){
							status=5;
						}else if(bookStatus.equals("7")){
							status=4;
						}else if(bookStatus.equals("5")){
							status=2;
						}
					}
					BookTimeRepository repo=bookRepositoryService.queryByPrimaryKey(book.getBooktimeid());
					if(repo!=null){
						BookTimeSpan span=bookTimeSpanService.queryByPrimaryKey(repo.getBooktimespanid());
						if(span!=null){
							String time=span.getTimespan();
							if(time.contains("-")){
								String[] times=time.split("-");
								bookMap.put("start", times[0]);
								bookMap.put("end", times[1]);
							}
						}
					}
					bookMap.put("status", status);
					bookMap.put("expire", book.getBookdate());
					bookMap.put("serialNumber", String.valueOf(ticket.serialNumber));
					mapList.add(bookMap);
				}
			}
		}
		Collections.sort(mapList, c);
		context.set("code", 0);
		context.set("data", gson.toJson(mapList));
		return "success.json";
	}
	
	static Comparator<Map<String,Object>> c=new Comparator<Map<String,Object>>() {
		@Override
		public int compare(Map<String, Object> o1,
				Map<String, Object> o2) {
			if((Integer)o1.get("status")<(Integer)o2.get("status")){
				return -1;
			}else{
				return 1;
			}
		}
	};

	/**
	 * 验证预约时间是否已过期
	 *
	 * @param bookDate
	 * @param bookTime
	 * @return
	 */
	private static boolean checkExpire(String bookDate, String bookTime){
		bookTime = Text.substring(bookTime, null, "-");
		if(null == bookTime) {
			logger.error("parse date and time failed, string = " + bookDate + " " + bookTime);
			return true;
		}
		bookTime = bookTime.trim() + ":00";
		String timeString = bookDate + " " + bookTime;
		try {
			DateTime dateTime = DateTime.parse(timeString);
			return (dateTime.compareTo(DateTime.now())) < 0;
		}
		catch (ParseException e) {
			logger.error("parse date failed, string = " + timeString, e);
			return true;
		}
	}
}
