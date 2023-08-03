package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.MvcBoardDAO;
import com.tjoeun.vo.MvcBoardVO;

public class UpdateService implements MvcBoardService {
	private static final Logger logger = LoggerFactory.getLogger(UpdateService.class);
	
	@Override
	public void execute(MvcBoardVO mvcBoardVO) {}

	@Override
	public void execute(Model model) {
		logger.info("UpdateService 클래스의 execute() 메소드 실행"); 
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		
		int idx = Integer.parseInt(request.getParameter("idx"));
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MvcBoardDAO mvcBoardDAO = ctx.getBean("mvcBoardDAO", MvcBoardDAO.class);
		
		mvcBoardDAO.update(idx, subject, content);
		
//		MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
//		mvcBoardVO.setIdx(idx);
//		mvcBoardVO.setSubject(subject);
//		mvcBoardVO.setContent(content);
		
		// 글 수정 작업 후 돌아갈 페이지 번호를 Model 인터페이스 객체에 넣어준다.
		model.addAttribute("currentPage", request.getParameter("currentPage"));
	}

}
