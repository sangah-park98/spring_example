package com.tjoeun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.vo.CardVO;

public class TransactionDAO {

	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	
	private JdbcTemplate template;
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "TransactionDAO [template=" + template + "]";
	}
	
//	내부 트랜잭션	
	private TransactionTemplate transactionTemplate;
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void buyTicket(final CardVO cardVO) {
		logger.info("내부 트랜잭션 실행");
		try {
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					
					template.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							String sql = "insert into card (consumerId, amount) values (?, ?)";
							PreparedStatement pstmt = con.prepareStatement(sql);
							pstmt.setString(1, cardVO.getConsumerId());
							pstmt.setString(2, cardVO.getAmount());
							return pstmt;
						}
					});
					
					template.update(new PreparedStatementCreator() {
						@Override
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
							String sql = "insert into ticket (consumerId, countnum) values (?, ?)";
							PreparedStatement pstmt = con.prepareStatement(sql);
							pstmt.setString(1, cardVO.getConsumerId());
							pstmt.setString(2, cardVO.getAmount());
							return pstmt;
						}
					});
				}
			});
			logger.info("트랜잭션이 정상적으로 처리되었으므로 commit 합니다.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("트랜잭션이 정상적으로 처리되지 않았으므로 rollback 합니다.");
		}
		
	}

}




