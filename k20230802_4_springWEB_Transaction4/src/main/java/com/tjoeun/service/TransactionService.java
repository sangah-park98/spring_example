package com.tjoeun.service;

import com.tjoeun.vo.CardVO;

public interface TransactionService {

	abstract public void execute(CardVO cardVO);
	
}
