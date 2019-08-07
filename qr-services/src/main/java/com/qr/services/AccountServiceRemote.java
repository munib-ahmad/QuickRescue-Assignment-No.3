package com.qr.services;

import java.util.List;

import javax.ejb.Remote;

import com.qr.model.DO.Account;

@Remote
public interface AccountServiceRemote {

	Account createAccount(Account account);

	List<Account> viewAllAccounts();

	Account findAccountById(Integer accountId);

	Account updateAccount(Account account,Account newAccount);

	Account deleteAccountById(Integer accountId);
	
	Account checkLogin(Account account);
}
