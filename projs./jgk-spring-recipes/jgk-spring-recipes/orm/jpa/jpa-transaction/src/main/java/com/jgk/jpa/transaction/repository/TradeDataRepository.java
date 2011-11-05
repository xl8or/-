package com.jgk.jpa.transaction.repository;

import com.jgk.jpa.transaction.domain.TradeData;

public interface TradeDataRepository {
	TradeData findById(Long id);

	TradeData makePersistent(TradeData td);
}
