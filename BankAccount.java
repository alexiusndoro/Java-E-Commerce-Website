package com.fdmgroup.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class BankAccount {

	@Id
	@SequenceGenerator(name = "accountIdSeq", sequenceName = "ACCOUNT_ID_SEQ", initialValue = 12267893, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountIdSeq")
	private int accountId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	private double balance;

	public BankAccount() {
	};

	public BankAccount(int accountId, Customer customer, double balance) {
		super();
		this.accountId = accountId;
		this.customer = customer;
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
