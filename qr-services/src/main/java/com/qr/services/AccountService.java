package com.qr.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.qr.dao.AccountDao;
import com.qr.dao.impl.AccountDaoImpl;
import com.qr.model.DO.Account;

/**
 * Session Bean implementation class AccountService
 */
@Stateless
@LocalBean
public class AccountService implements AccountServiceRemote {
	
	private AccountDao accountDao = new AccountDaoImpl();

	@Override
	public Account createAccount(Account account) {
		return accountDao.create(account);
	}

	@Override
	public List<Account> viewAllAccounts() {
		return accountDao.viewAll();
	}

	@Override
	public Account findAccountById(Integer accountId) {
		return accountDao.findById(accountId);
	}

	@Override
	public Account updateAccount(Account account, Account newAccount) {
		return accountDao.update(account, newAccount);
	}

	@Override
	public Account deleteAccountById(Integer accountId) {
		return accountDao.deleteById(accountId);
	}

	@Override
	public Account checkLogin(Account account) {
		List<Account> accountsList = accountDao.viewAll();
		
		for(Account fetchedAccount:accountsList) {
			if(account.getEmailDomain().equalsIgnoreCase(fetchedAccount.getEmailDomain()) &&account.getPassword().equals(fetchedAccount.getPassword())){
				return fetchedAccount;
			}
		}
		return null;
	}



}
