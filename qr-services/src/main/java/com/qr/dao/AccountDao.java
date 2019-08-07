package com.qr.dao;

import java.util.List;

import com.qr.model.DO.Account;

public interface AccountDao {
	Account create(Account account);

	List<Account> viewAll();

	Account findById(Integer accountId);

	Account update(Account account,Account newAccount);

	Account deleteById(Integer accountId);

}
