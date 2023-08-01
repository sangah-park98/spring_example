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
	
//	------------------------------------------------------------------------------ 여기부터
	
//	private DataSource dataSource;
//	
//	public MvcBoardDAO() {
//		try {
//			Context context = new InitialContext();
//			dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/oracle");
//			// logger.info("연결 성공 ><");
//		} catch (NamingException e) {
//			// e.printStackTrace();
//			logger.info("연결 실패ㅠ.ㅠ");
//		}
//	}
//	------------------------------------------------------------------------------ 여기까지
	
//	DBCP 방식을 사용하는 객체를 초기화하는 부분이므로 JdbcTemplate 으로 코드 변환이 완료되면
//	모두 주석으로 처리한다.	
	
//	insert, delete, update sql 명령을 실행하는 메소드의 인수로 넘어온 데이터가 중간에 값이 변경되면
//	안되기 때문에 JdbcTemplate에서는 insert, delete, update sql 명령을 실행하는 메소드의 인수를 선언할 때
//	final를 붙여야 한다.	
	public void insert(final MvcBoardVO mvcBoardVO) {
		logger.info("MvcBoardDAO클래스의 insert() 메소드 실행"); 
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " + 
//							"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcBoardVO.getName());
//			pstmt.setString(2, mvcBoardVO.getSubject());
//			pstmt.setString(3, mvcBoardVO.getContent());
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		String sql = "insert into mvcboard (idx, name, subject, content, gup, lev, seq) " + 
						"values (mvcboard_idx_seq.nextval, ?, ?, ?, mvcboard_idx_seq.currval, 0, 0)";
		
		// update(): 테이블의 내용이 갱신되는 sql 명령 => insert, delete, update
		// query(): 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 여러 건일 경우
		// queryForInt(): 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 정수일 경우
		// queryForObject(): 테이블의 내용이 갱신되지 않는 sql 명령 => select => 결과가 1건일 경우
		
		// update(sql 명령, "?"를 채운다.)
		// update() 메소드의 2번째 인수로 PreparedStatementSetter 인터페이스 객체를 익명으로 구현해서
		// setValues() 메소드에서 실행할 sql 명령의 "?"를 채운다.
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
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int result = 0; // 테이블에 저장된 전체 글의 개수를 저장해서 리턴시킬 변수를 선언한다.
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select count(*) from mvcboard";
//			pstmt = conn.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			// 테이블에 저장된 글의 개수는 null이 나올리가 없으므로 조건 비교는 필요 없다.
//			rs.next(); 
//			result = rs.getInt(1);
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
//		
//		return result;
		
		String sql = "select count(*) from mvcboard";
		// return template.queryForInt(sql);
		// queryForObject(sql 명령, 리턴타입.class)
		return template.queryForObject(sql, Integer.class);
		
	}

//	SelectService 클래스에서 호출되는 브라우저에 표시할 1페이지 분량의 글 목록을 얻어오기 위해서 시작
//  인덱스, 끝 인덱스가 저장된 HashMap 객체를 넘겨받고 1페이지 분량의 글 목록을 얻어오는 select sql
//  명령을 실행하는 메소드	
	public ArrayList<MvcBoardVO> selectList(HashMap<String, Integer> hmap) {
		logger.info("MvcBoardDAO클래스의 selectList() 메소드 실행"); 
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		ArrayList<MvcBoardVO> list = null; // 1페이지 분량의 글 목록을 저장해 리턴시킬 ArrayList
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from (" + 
//						"    select rownum rnum, GG.* from (" +
//						"        select * from mvcboard order by gup desc, seq" +
//						"    ) GG where rownum <= ?" + 
//						") where rnum >= ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, hmap.get("endNo"));
//			pstmt.setInt(2, hmap.get("startNo"));
//			rs = pstmt.executeQuery();
//			
//			// ResultSet 객체에 저장된 1페이지 분량의 글 목록을 저장할 ArrayList 객체 생성
//			list = new ArrayList<MvcBoardVO>();
//			// ResultSet 객체에 저장된 글의 개수만큼 반복하며 ArrayList에 저장한다.
//			while (rs.next()) {
//				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//				MvcBoardVO mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
//				mvcBoardVO.setIdx(rs.getInt("idx"));
//				mvcBoardVO.setName(rs.getString("name"));
//				mvcBoardVO.setSubject(rs.getString("subject"));
//				mvcBoardVO.setContent(rs.getString("content"));
//				mvcBoardVO.setGup(rs.getInt("gup"));
//				mvcBoardVO.setLev(rs.getInt("lev"));
//				mvcBoardVO.setSeq(rs.getInt("seq"));
//				mvcBoardVO.setHit(rs.getInt("hit"));
//				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
//				list.add(mvcBoardVO);
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
//		
//		
//		return list;
		
		// JdbcTemplate을 사용하는 경우 select sql 명령에는 "?"를 사용할 수 없다. => "?" 자리에 데이ㅓ가
		// 저장된 변수를 직접 사용한다.
		String sql = "select * from (" + 
				"    select rownum rnum, GG.* from (" +
				"        select * from mvcboard order by gup desc, seq" +
				"    ) GG where rownum <= " + hmap.get("endNo") +
				") where rnum >= " + hmap.get("startNo");
		
		// query(sql 명령, new BeanPropertyRowMapper(리턴할클래스.class))
		// select sql 명령 실행 결과를 BeanPropertyRowMapper 클래스 생성자의 인수인 MvcBoardVO 클래스로
		// 넘겨서 sql 명령 실행 결과를 저장시켜 리턴한다.
		// query() 메소드 실행 결과는 List 인터페이스 타입으로 리턴되기 때문에 메소드의 리턴 타입인
		// ArrayList<MvcBoardVO>로 형변환이 필요하다.
		// 데이터베이스 테이블의 필드 이름과 BeanPropertyRowMapper 클래스 생성자의 인수로 전달되는
		// 클래스의 필드 이름과 같아야 정상적으로 처리된다.
		return (ArrayList<MvcBoardVO>) template.query(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

//	IncrementService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시키는
//	update sql 명령을 실행하는 메소드	
	public void increment(final int idx) {
		logger.info("MvcBoardDAO 클래스의 increment() 메소드 실행");
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set hit = hit + 1 where idx = ?"; 
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		
		// ?를 채워야 할 게 많으면 아래가 효과적.
//		String sql = "update mvcboard set hit = hit + 1 where idx = ?"; 
//		template.update(sql, new PreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setInt(1, idx);
//			}
//		});
		
		// ?가 적으면 아래가 효과적.
		String sql = "update mvcboard set hit = hit + 1 where idx = " + idx; 
		template.update(sql);
	}

//	ContentViewService 클래스에서 호출되는 조회수를 증가시킬 글번호를 넘겨받고 조회수를 증가시킨 글 1건을
//	얻어오는 select sql 명령을 실행하는 메소드			
	public MvcBoardVO selectByIdx(int idx) {
		logger.info("MvcBoardDAO클래스의 selectByIdx() 메소드 실행"); 
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		MvcBoardVO mvcBoardVO = null; // 조회수를 증가시킨 글 1건을 얻어와 저장시켜 리턴할 객체 선언
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "select * from mvcboard where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, idx);
//			rs = pstmt.executeQuery();
//			
//			// ResultSet 객체에 저장된 글 1건을 리턴시키기 위해서 MvcBoardVO 클래스 객체에 저장한다.
//			if (rs.next()) {
//				AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//				mvcBoardVO = ctx.getBean("mvcBoardVO", MvcBoardVO.class);
//				mvcBoardVO.setIdx(rs.getInt("idx"));
//				mvcBoardVO.setName(rs.getString("name"));
//				mvcBoardVO.setSubject(rs.getString("subject"));
//				mvcBoardVO.setContent(rs.getString("content"));
//				mvcBoardVO.setGup(rs.getInt("gup"));
//				mvcBoardVO.setLev(rs.getInt("lev"));
//				mvcBoardVO.setSeq(rs.getInt("seq"));
//				mvcBoardVO.setHit(rs.getInt("hit"));
//				mvcBoardVO.setWriteDate(rs.getTimestamp("writeDate"));
//			} 
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
//		
//		return mvcBoardVO;
		
		String sql = "select * from mvcboard where idx = " + idx;
		return template.queryForObject(sql, new BeanPropertyRowMapper(MvcBoardVO.class));
	}

//	UpdateService 클래스에서 호출되는 글번호, 제목, 내용을 넘겨받아 1건의 글을 수정하는 update sql 명령을 실행하는 메소드		
	public void update(final int idx, final String subject, final String content) {
		logger.info("MvcBoardDAO클래스의 update() 메소드 실행"); 
		
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, subject);
//			pstmt.setString(2, content);
//			pstmt.setInt(3, idx);
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		
//		1. String sql = "update mvcboard set subject = '" + subject + "', content = '" + content + "' where idx = " + idx;
//		2. 
//		   	String sql = "update mvcboard set subject = ?, content = ? where idx = ?";
//			template.update(sql, new PreparedStatementSetter() {
//				@Override
//				public void setValues(PreparedStatement pstmt) throws SQLException {
//					pstmt.setString(1, mvcBoardVO.getSubject());
//					pstmt.setString(2, mvcBoardVO.getContent());
//					pstmt.setInt(3, mvcBoardVO.getIdx());
//			}
//		});
//		3.
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

//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			conn = dataSource.getConnection();
//			String sql = "insert into mvcboard(idx, name, subject, content, gup, lev, seq) values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, mvcBoardVO.getName());
//			pstmt.setString(2, mvcBoardVO.getSubject());
//			pstmt.setString(3, mvcBoardVO.getContent());
//			pstmt.setInt(4, mvcBoardVO.getGup());
//			pstmt.setInt(5, mvcBoardVO.getLev());
//			pstmt.setInt(6, mvcBoardVO.getSeq());
//			pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (conn != null) {try {conn.close();} catch (SQLException e) {e.printStackTrace();}}
//		}
		
//		1.		
//		String sql = "insert into mvcboard(idx, name, subject, content, gup, lev, seq) values (mvcboard_idx_seq.nextval, ?, ?, ?, ?, ?, ?)";
//		template.update(sql, new PreparedStatementSetter() {
//
//			@Override
//			public void setValues(PreparedStatement pstmt) throws SQLException {
//				pstmt.setString(1, mvcBoardVO.getName());
//				pstmt.setString(2, mvcBoardVO.getSubject());
//				pstmt.setString(3, mvcBoardVO.getContent());
//				pstmt.setInt(4, mvcBoardVO.getGup());
//				pstmt.setInt(5, mvcBoardVO.getLev());
//				pstmt.setInt(6, mvcBoardVO.getSeq());
//			}
//			
//		});
		
//		2.		
		String sql = String.format("insert into mvcboard(idx, name, subject, content, gup, lev, seq) " + 
				"values (mvcboard_idx_seq.nextval, '%s', '%s', '%s', %d, %d, %d)", mvcBoardVO.getName(), 
				mvcBoardVO.getSubject(), mvcBoardVO.getContent(), mvcBoardVO.getGup(), 
				mvcBoardVO.getLev(), mvcBoardVO.getSeq());
		template.update(sql);
	}

}



















