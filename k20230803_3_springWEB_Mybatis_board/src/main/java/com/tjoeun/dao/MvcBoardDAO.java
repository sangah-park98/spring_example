package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.crypto.dsig.spec.HMACParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.tjoeun.springWEB_DBCP_board.Constant;
import com.tjoeun.vo.MvcBoardVO;

public class MvcBoardDAO {

	private static final Logger logger = LoggerFactory.getLogger(MvcBoardDAO.class);

//	JdbcTemplate 설정
//	DAO 클래스에서 JdbcTemplate을 사용하기 위해 JdbcTemplate 클래스 타입의 객체를 선언한다.
	private JdbcTemplate template;
	
//	DAO 클래스의 bean이 기본 생성자로 생성되는 순간 servlet-context.xml 파일에서 생성돼서
//	컨트롤러가 전달받아 Constant 클래스의 JdbcTemplate 클래스 타입의 static 객체에 저장한 bean으로 초기화한다.	
	public MvcBoardDAO() {
		template = Constant.template;
	}
	
//	insert, delete, update sql 명령을 실행하는 메소드의 인수로 넘어온 데이터가 중간에 값이 변경되면
//	안되기 때문에 JdbcTemplate에서는 insert, delete, update sql 명령을 실행하는 메소드의 인수를 선언할 때
//	final를 붙여야 한다.	
	public void insert(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO클래스의 insert() 메소드 실행"); 
		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " + 
						"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
		template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
			}
		});
	}

//	SelectService 클래스에서 호출되는 테이블에 저장된 전체 글의 개수를 얻어오는 select sql 명령을 실행하는
//	메소드	
	public int selectCount() {
		logger.info("MvcBoardDAO클래스의 selectCount() 메소드 실행"); 
		String sql = "select count(*) from mvcboard";
		return template.queryForObject(sql, Integer.class);
		
	}

//	SelectService 클래스에서 호출되는 브라우저에 표시할 1페이지 분량의 글 목록을 얻어오기 위해서 시작
//  인덱스, 끝 인덱스가 저장된 HashMap 객체를 넘겨받고 1페이지 분량의 글 목록을 얻어오는 select sql
//  명령을 실행하는 메소드	
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO클래스의 selectList() 메소드 실행"); 
		
		// JdbcTemplate을 사용하는 경우 select sql 명령에는 "?"를 사용할 수 없다. => "?" 자리에 데이ㅓ가
		// 저장된 변수를 직접 사용한다.
		String sql = "select * from (" + 
				"    select rownum rnum, GG.* from (" +
				"        select * from mvcboard order by gup desc, seq" +
				"    ) GG where rownum <= " + hmap.get("endNo") +
				") where rnum >= " + hmap.get("startNo");
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

//	IncrementService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는
//	update sql 명령을 실행하는 메소드	
	public void increment(final int idx) {
		logger.info("MvcBoardDAO 클래스의 increment() 메소드 실행");
		
		// ?를 채워야 할 게 많으면 아래가 효과적.
		String sql = "update mvcboard set hit = hit + 1 where idx = ?"; 
		template.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, idx);
			}
		});
	}

//	ContentViewService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시킨 글 1건을
//	얻어오는 select sql 명령을 실행하는 메소드			
	public MvcBoardVO selectByIdx(int idx) {
		logger.info("MvcBoardDAO클래스의 selectByIdx() 메소드 실행"); 
		String sql = "select * from mvcboard where idx = " + idx;
		return template.queryForObject(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

//	UpdateService 클래스에서 호출되는 글번호, 제목, 내용을 넘겨받아 1건의 글을 수정하는 update sql 명령을 실행하는 메소드		
	public void update(final int idx, final String subject, final String content) {
		logger.info("MvcBoardDAO클래스의 update() 메소드 실행"); 
		String sql = String.format("update mvcboard set subject = '%s', content = '%s' where idx = %d", subject, content, idx);
		template.update(sql);
	}

//	DeleteService 클래스에서 호출되는 글번호를 넘겨받아 1건의 글을 삭제하는 delete sql 명령을 실행하는 메소드		
	public void delete(final int idx) {
		logger.info("MvcBoardDAO클래스의 delete() 메소드 실행"); 
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "delete from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		
		String sql = "delete from mvcboard where idx = " + idx;
		template.update(sql);
	}

//	ReplyService 클래스에서 호출되는 글그룹과 글이 출력되는 순서가 저장된 HashMap 객체를 넘겨받고
//	조건에 만족하는 레코드의 seq를 1씩 증가시키는 update sql 명령을 실행하는 메소드	
	public void replyIncrement(final HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO클래스의 replyIncrement() 메소드 실행"); 
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, hmap.get("gup"));
//			pstmt.setInt(2, hmap.get("seq"));
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		
//		1. String sql = "update mvcboard set seq = seq + 1 where gup = " + hmap.get("gup") +
//						"and seq >=" + hmap.get("seq");
//			template.update(sql);		
		
		String sql = "update mvcboard set seq = seq + 1 where gup = ? and seq >= ?";
		template.update(sql, new PreparedStatementSetter () {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setInt(1, hmap.get("gup"));
				pstmt.setInt(2, hmap.get("seq"));
			}
			
		});
	}
	
//	ReplyService 클래스에서 호출되는 답글이 저장된 객체를 넘겨받고 답글을 저장하는 insert sql 명령을
//  실행하는 메소드	
	public void replyInsert(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO클래스의 replyInsert() 메소드 실행"); 
		String sql = "insert into mvcboard(idx, name, subject, content, gup, lev, seq) values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
		template.update(sql, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				pstmt.setString(1, mvcBoardVO.getName());
				pstmt.setString(2, mvcBoardVO.getSubject());
				pstmt.setString(3, mvcBoardVO.getContent());
				pstmt.setInt(4, mvcBoardVO.getGup());
				pstmt.setInt(5, mvcBoardVO.getLev());
				pstmt.setInt(6, mvcBoardVO.getSeq());
			}
			
		});
	}

}



















