package com.tjoeun.springWEB_DBCP_board;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.service.ContentViewService;
import com.tjoeun.service.DeleteService;
import com.tjoeun.service.IncrementService;
import com.tjoeun.service.InsertService;
import com.tjoeun.service.MvcBoardService;
import com.tjoeun.service.ReplyService;
import com.tjoeun.service.SelectService;
import com.tjoeun.service.UpdateService;
import com.tjoeun.vo.MvcBoardVO;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
//	JdbcTemplate을 사용하려면 servlet-context.xml 파일에서 프로젝트가 시작될 때 DriverManagerDataSource
//	클래스의 bean(데이터베이스 연결 정보)을 참조해서 생성한 JdbcTemplate 클래스의 bean을 컨트롤러에서
//	JdbcTemplate 클래스 타입의 객체를 생성하고 넣어줘야 한다.	
	private JdbcTemplate template;
	
//	JdbcTemplate 클래스 타입의 객체의 getter, setter를 만든다. => getter는 안 만들어도 된다.
	public JdbcTemplate getTemplate() {
		return template;
	}
	
//	프로젝트를 실행하면 스프링 환경 설정 파일인 servlet-context.xml 파일이 읽혀진 다음 JdbcTemplate
//	클래스 객체의 setter 메소드가 자동으로 실행되게 하기 위해서 @Autowired 어노테이션을 붙여준다.
//	@Autowired를 붙여준 메소드는 서버가 구동되는 단계에서 자동으로 실행되며 setter 메소드의 인수
//	template으로 스프링이 알아서 servlet-context.xml 파일에서 생성한 JdbcTemplate 클래스의 bean을
//	자동으로 전달한다.
//	자동으로 실행된 setter 메소드는 자동으로 전달받은 JdbcTemplate 클래스의 bean으로 JdbcTemplate
//	클래스 객체를 초기화시킨다.	
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		logger.info("꺄~~~");
		this.template = template;
		
		// 여기까지 정상적으로 실행되면 컨트롤러에서 JdbcTemplate을 사용할 수 있게 된다.
		// 데이터베이스 연결은 주로 DAO 클래스에서 하므로 컨트롤러 이외의 클래스에서 JdbcTemplate을
		// 사용할 수 있게 하기 위해서 적당한 이름의 패키지(base-package)에 적당한 이름의 클래스(Constant)를 만들고 
		// 적당한 이름으로 작성한 클래스의 정적 필드에 servlet-context.xml 파일에서 
		// 생성 시 컨트롤러의 JdbcTemplate 클래스 객체에 저장된 bean을 넣어준다.
		
		// 적당한 이름으로 클래스를 만들고 JdbcTemplate 객체를 static으로 선언한 후 코딩한다.
		Constant.template = this.template;
	}

	
	@RequestMapping("/")
	public String home(HttpServletRequest request, Model model) {
		logger.info("MvcBoard 게시판 실행");
		return "redirect:list"; // @RequestMapping("/list") 어노테이션이 지정된 메소드를 실행한다.
	}
	
	@RequestMapping("/insert")
	public String insert(HttpServletRequest request, Model model) {
		logger.info("insert() 메소드 실행"); 
		return "insert";
	}
	
	@RequestMapping("/insertOK")
	public String insertOK(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {
		logger.info("insertOK() 메소드 실행 -  Model 인터페이스 객체 사용"); 
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("insert", InsertService.class);
		service.execute(model);
		
		return "redirect:list"; 

	}
	
	@RequestMapping("/list")
	public String insertOK(HttpServletRequest request, Model model) {
		logger.info("list() 메소드 실행"); 
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("select", SelectService.class);
		service.execute(model);
		
		return "list";
	}
	
	@RequestMapping("/increment")
	public String increment(HttpServletRequest request, Model model) {
		logger.info("increment() 메소드 실행");
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("increment", IncrementService.class);
		service.execute(model);
		
		model.addAttribute("idx", request.getParameter("idx"));
		model.addAttribute("currentPage", request.getParameter("currentPage"));
		
		return "redirect:contentView";
	}
	
	@RequestMapping("/contentView")
	public String contentView(HttpServletRequest request, Model model) {
		logger.info("contentView() 메소드 실행");
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "contentView";
		
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Model model) {
		logger.info("update() 메소드 실행");
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("update", UpdateService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		logger.info("delete() 메소드 실행");
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("delete", DeleteService.class);
		service.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("reply")
	public String reply(HttpServletRequest request, Model model) {
		logger.info("reply(); 메소드 실행");
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("contentView", ContentViewService.class);
		service.execute(model);
		
		return "reply";  
	}
	
	@RequestMapping("/replyInsert")
	public String replyInsert(HttpServletRequest request, Model model) {
		logger.info("replyInsert() 메소드 실행"); 
		
		model.addAttribute("request", request);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardService service = ctx.getBean("reply", ReplyService.class);
		service.execute(model);
			
		return "redirect:list";
	}
	
}





































