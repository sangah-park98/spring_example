package com.tjoeun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.tjoeun.dao.TransactionDAO;
import com.tjoeun.vo.CardVO;

public class TicketInsert implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TicketInsert.class);
	
	private TransactionDAO dao;
	public void setDao(TransactionDAO dao) {
			this.dao = dao;
	}

	//	외부 트랜잭션	
	private TransactionTemplate transactionTemplate2;
	public void setTransactionTemplate2(TransactionTemplate transactionTemplate2) {
		this.transactionTemplate2 = transactionTemplate2;
	}

	@Override
	public void execute(final CardVO cardVO) {
		// ticket.jsp의 입력과 상관없이 외부 트랜잭션이 있을 때와 없을 때를 구분해서 트랜잭션을 실행해보자!
		
		// REQUIRED(기본값, 0) <=> REQUIRES_NEW(3)
		// 외부 트랜잭션이 존재한다면 외부 트랜잭션으로 합류하고 외부 트랜잭션이 없다면 새로운 트랜잭션을 생성한다.
		// SUPPORTS(1) <=> NOT_SUPPORTED(4)
		// MANDATORY(2) <=> NEVER(5)
		
		// propagationBehavior 속성 지정에 따라 트랜잭션 처리가 달라진다.
		// 외부 트랜잭션도(transactionTemplate2) 0, 내부 트랜잭션(transactionTemplate) 0이면 모두 적용된다.
		// 외부 트랜잭션이 없고, 내부 트랜잭션이 0이면 내부 트랜잭션이 정상 처리된다.
		// 외부 트랜잭션이 있고, 내부 트랜잭션이 1이면 모두 적용된다.
		// 외부 트랜잭션이 없고, 내부 트랜잭션이 1이면 모두 적용되지 않는다.
		// 외부 트랜잭션이 있고, 내부 트랜잭션이 2이면 모두 적용된다.
		// 외부 트랜잭션이 없고, 내부 트랜잭션이 2이면 예외가 발생된다.
		
		// 개별적으로 실행되는 경우 = 외부 트랜잭션이 없는 경우 => TransactionDAO의 트랜잭션을 바로 실행한다.
		cardVO.setAmount("1");
		dao.buyTicket(cardVO);
		
		cardVO.setAmount("5");
		dao.buyTicket(cardVO);
		
		// 외부 트랜잭션이 있는 경우
//		logger.info("외부 트랜잭션 실행");
//		try {
//			
//			transactionTemplate2.execute(new TransactionCallbackWithoutResult() {
//				@Override
//				protected void doInTransactionWithoutResult(TransactionStatus status) {
//					
//					cardVO.setAmount("1");
//					dao.buyTicket(cardVO);
//					
//					cardVO.setAmount("5");
//					dao.buyTicket(cardVO);
//				}
//			});
//			
//		} catch (Exception e) {
//			// e.printStackTrace();
//		}
		
	}

}
