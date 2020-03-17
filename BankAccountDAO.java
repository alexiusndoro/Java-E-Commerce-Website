package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.BankAccount;

public class BankAccountDAO {

	private EntityManager em;

	public BankAccountDAO(EntityManager em) {
		super();
		this.em = em;
	}

	public void addAccount(BankAccount account) {
		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

	}

	public void deleteAccount(int accountNumber) {
		BankAccount account = em.find(BankAccount.class, accountNumber);
		em.getTransaction().begin();
		em.remove(account);
		em.getTransaction().commit();

	}

	public void deleteAllAccountsForGivenCustomer(String username) {
		TypedQuery<BankAccount> queryDeleteAccount = em
				.createQuery("delete  from Bankaccount b where b.customer.username=:cname", BankAccount.class);
		queryDeleteAccount.setParameter("cname", username);
		queryDeleteAccount.executeUpdate();

	}

	public List<BankAccount> listAllAccounts() {
		TypedQuery<BankAccount> queryBankAccount = em.createQuery("select b from BankAccount b", BankAccount.class);
		List<BankAccount> listBankAccount = queryBankAccount.getResultList();

		return listBankAccount;
	}

	public List<BankAccount> listAllAccountsForGivenUser(String username) {
		TypedQuery<BankAccount> queryFindAccounts = em
				.createQuery("select b from BankAccount b where b.customer.username=:bname", BankAccount.class);
		queryFindAccounts.setParameter("bname", username);
		List<BankAccount> listOfAccounts = queryFindAccounts.getResultList();
		return listOfAccounts;

	}

	public void updateBalance(int accountNumber, double amount) {
		BankAccount account = em.find(BankAccount.class, accountNumber);
		em.getTransaction().begin();
		double newBalance = account.getBalance() + amount;
		account.setBalance(newBalance);

		em.getTransaction().commit();

	}

	public BankAccount findAccountByAccountId(int accountId) {

		BankAccount account = em.find(BankAccount.class, accountId);
		return account;

	}
	
	

}
