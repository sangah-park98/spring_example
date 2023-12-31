package com.tjoeun.springWEB_request;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/memberView")
//	HttpServletRequest 인터페이스 객체로 뷰 페이지에서 컨트롤러로 넘어오는 데이터를 받는다.
//	Model 인터페이스 객체에 컨트롤러에서 뷰 페이지로 넘겨줄 데이터를 저장한다.	
	public String memberView(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 memberView() 메소드 실행");
		
		// 뷰 페이지에서 컨트롤러로 넘어와 HttpServletRequest 인터페이스 객체에 저장되는 데이터를 받는다.
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		logger.info("id: {}, pw: {}", id, pw);
		
		// 컨트롤러에서 뷰 페이지로 넘겨줄 데이터를 Model 인터페이스 객체에 저장한다.
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "memberView";
	}
	
	@RequestMapping("/memberLogin")
//	@RequestParam("뷰 페이지에서 컨트롤러로 넘어오는 변수명") 자료형 변수명
//	@RequestParam("id") String id는	String id = request.getParameter("id")와 같은 기능이 실행된다.
	
//	HttpServletRequest와 @RequestParam의 차이
//	HttpServletRequest 인터페이스 객체로 뷰 페이지에서 넘어오는 데이터를 받을 때는 데이터 2개 중 1개만 던져도 에러가 발생하지 않지만
//  @RequestParam 어노테이션을 사용해서 데이터를 받을 때는 데이터가 2개 중 하나만 넘어오면 400 - Bad Request 에러가 발생한다.	
	
	public String memberLogin(/*HttpServletRequest request,*/ Model model,
			@RequestParam("id") String id, 
			@RequestParam("pw") String pw
			
		) {
		logger.info("컨트롤러의 memberLogin() 메소드 실행"); 
		logger.info("id: {}, pw: {}", id, pw);
		model.addAttribute("id", id);
		model.addAttribute("pw", pw);
		
		return "memberLogin";
	}
	
	@RequestMapping("/member")
	   public String member(HttpServletRequest request, Model model) {
	      logger.info("컨트롤러의 member() 메소드 실행");
	      return "member";
	   }
	
	/*
	@RequestMapping("/memberInsert")
	public String memberInsert(HttpServletRequest request, Model model) {
		logger.info("컨트롤러의 memberInsert() 메소드 실행");
		
		// 뷰 페이지에서 post 방식으로 컨트롤러로 넘어와서 HttpServletRequest 인터페이스 객체에 저장된
		// 데이터를 받는다.
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		// logger.info("name: {}, id: {}, password: {}, email: {}", name, id, password, email);
		// 데이터 2개 이상 출력하고 싶을 때는 String.format을 사용한다.
		// logger.info(String.format("name: %s, id: %s, password: %s, email: %s", name, id, password, email));
		
		// 기본 생성자로 VO 클래스의 객체를 만들고 setter 메소드로 데이터를 넣어준다.
		// MemberVO vo = new MemberVO();
		//	vo.setName(name);
		//	vo.setId(id);
		//	vo.setPassword(password);
		//	vo.setEmail(email);
		//	logger.info(vo + ""); // vo.toString()
		
		//	MemberVO vo = new MemberVO(name, id, password, email);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberVO vo = ctx.getBean("vo", MemberVO.class);
		vo.setName(name);
		vo.setId(id);
		vo.setPassword(password);
		vo.setEmail(email);
		logger.info(vo.toString());
		
		// memberInsert.jsp로 MemberVO 클래스 객체를 넘겨주기 위해 Model 인터페이스 객체에 저장한다.
		model.addAttribute("vo", vo);
		
		return "memberInsert";
	}
	*/
	
	/*
	@RequestMapping("/memberInsert")
//	커맨드(데이터) 객체(넘겨받은 데이터를 저장할 객체) 사용하기
//	뷰 페이지에서 컨트롤러로 넘어오는 데이터를 클래스 객체에 저장하려는 경우 커맨드 객체를 사용하면 편리하다.
//	커맨드 객체를 사용하면 HttpServletRequest 인터페이스 객체나 @RequestParam 어노테이션으로 넘겨받은 데이터를
//	클래스 객체에 setter 메소드를 사용해서 저장한 다음 Model 인터페이스 객체에 넣어주는 동작이 일괄적으로
//	처리된다.	
//	커맨드 객체는 뷰 페이지에서 컨트롤러로 넘어온 데이터를 저장할 클래스를 의미하며 뷰 페이지로 넘어가는 객체의
//	이름은 커맨드 객체의 이름이 첫 문자만 소문자로 변경돼서 넘어간다.
	public String memberInsert(Model model, MemberVO memberVO) {
		logger.info("컨트롤러의 memberInsert() 메소드 실행");
		logger.info("{}", memberVO);
		model.addAttribute("vo", memberVO);
		return "memberInsert";
	}
	*/
	
	@RequestMapping("/memberInsert")
//	커맨드 객체의 이름이 너무 길어서 사용하기 불편하면 뷰 페이지로 넘겨주는 커맨드 객체의 이름을 
//	@ModelAttribute 어노테이션을 이용해서 별도의 커맨드 객체의 이름을 지정해서 사용할 수 있다.	
//	이 경우 기존 커맨드 객체 이름은 뷰 페이지에서 사용할 수 없고 @ModelAttribute 어노테이션으로 지정한 이름을
//	사용해야 한다.	
	public String memberInsert(Model model, 
			@ModelAttribute("vo") MemberVO memberVO
		) {
		logger.info("컨트롤러의 memberInsert() 메소드 실행");
		logger.info("{}", memberVO);
		return "memberInsert";
	}
	
}



























