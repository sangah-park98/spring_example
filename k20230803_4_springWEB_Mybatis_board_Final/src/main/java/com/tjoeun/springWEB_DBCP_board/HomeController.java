package com.tjoeun.springWEB_DBCP_board;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.dao.MybatisDAO;
import com.tjoeun.vo.MvcBoardList;
import com.tjoeun.vo.MvcBoardVO;

@Controller
public class HomeController {
   
   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
   
   @Autowired
   private SqlSession sqlSession; 
   
   @RequestMapping("/")
   public String home(HttpServletRequest request, Model model) {
      logger.info("MvcBoard 게시판 실행");
      return "redirect:list"; 
   }
   
   @RequestMapping("/insert")
   public String insert(HttpServletRequest request, Model model) {
      logger.info("insert() 메소드 실행"); 
      return "insert";
   }
   
   @RequestMapping("/insertOK")
   public String insertOK(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {
      logger.info("insertOK() 메소드 실행 -  커맨드 객체 사용"); 
      
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      
      mapper.insert(mvcBoardVO);
      
      return "redirect:list"; 
   }
   
   @RequestMapping("/list")
   public String insertOK(HttpServletRequest request, Model model) {
      logger.info("list() 메소드 실행"); 
      
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      int pageSize = 10;
      int currentPage = 1;
      try {
         currentPage = Integer.parseInt(request.getParameter("currentPage"));
      } catch(NumberFormatException e) {}
      
      int totalCount = mapper.selectCount();
      
      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      MvcBoardList mvcBoardList = ctx.getBean("mvcBoardList", MvcBoardList.class);
      mvcBoardList.initMvcBoardList(pageSize, totalCount, currentPage);
      
      HashMap<String, Integer> hmap = new HashMap<String, Integer>();
      hmap.put("startNo", mvcBoardList.getStartNo());
      hmap.put("endNo", mvcBoardList.getEndNo());
      mvcBoardList.setList(mapper.selectList(hmap));
      
      model.addAttribute("boardList", mvcBoardList);
      
      return "list";
   }
   
   @RequestMapping("/increment")
   public String increment(HttpServletRequest request, Model model) {
      logger.info("increment() 메소드 실행");
      
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      
      int idx = Integer.parseInt(request.getParameter("idx"));
      
      mapper.increment(idx);
      
      model.addAttribute("idx", request.getParameter("idx"));
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      
      return "redirect:contentView";
   }
   
   @RequestMapping("/contentView")
   public String contentView(HttpServletRequest request, Model model) {
      logger.info("contentView() 메소드 실행");
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      int idx = Integer.parseInt(request.getParameter("idx"));
      MvcBoardVO mvcBoardVO = mapper.selectByIdx(idx);
      logger.info("{}", mvcBoardVO);
      model.addAttribute("vo", mvcBoardVO);
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      model.addAttribute("enter", "\r\n");
      
      return "contentView";
   }
   
   @RequestMapping("/update")
   public String update(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {
      logger.info("update() 메소드 실행");
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      mapper.update(mvcBoardVO);
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      
      return "redirect:list";
   }
   
   @RequestMapping("/delete")
   public String delete(HttpServletRequest request, Model model) {
      logger.info("delete() 메소드 실행");
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      int idx = Integer.parseInt(request.getParameter("idx"));
      mapper.delete(idx);
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      
      return "redirect:list";
   }
   
   @RequestMapping("reply")
   public String reply(HttpServletRequest request, Model model) {
      logger.info("reply(); 메소드 실행");
      
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      int idx = Integer.parseInt(request.getParameter("idx"));
   
      MvcBoardVO mvcBoardVO = mapper.selectByIdx(idx);
   
      model.addAttribute("vo", mvcBoardVO);
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      model.addAttribute("enter", "\r\n");
      return "reply";  
   }
   
   @RequestMapping("/replyInsert")
   public String replyInsert(HttpServletRequest request, Model model, MvcBoardVO mvcBoardVO) {
      logger.info("replyInsert() 메소드 실행"); 
      
      MybatisDAO mapper = sqlSession.getMapper(MybatisDAO.class);
      
      mvcBoardVO.setLev(mvcBoardVO.getLev() + 1);
      mvcBoardVO.setSeq(mvcBoardVO.getSeq() + 1);
      
      // 답글이 삽입될 위치를 정하기 위해 조건에 만족하는 seq를 1씩 증가시키는 메소드를 실행한다.
      HashMap<String, Integer> hmap = new HashMap<String, Integer>();
      hmap.put("gup", mvcBoardVO.getGup() + 1);
      hmap.put("seq", mvcBoardVO.getSeq() + 1);
      mapper.replyIncrement(hmap);
      
      // 답글을 저장하는 메소드를 실행한다.
      mapper.replyInsert(mvcBoardVO);
      model.addAttribute("currentPage", request.getParameter("currentPage"));
      return "redirect:list";
   }
   
}


















