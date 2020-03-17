package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Customer;

public class CustomerDAO {

	private EntityManager em;

	public CustomerDAO(EntityManager em) {
		this.em = em;
	}

	public Customer findCustomer(String username) {
		Customer customer = em.find(Customer.class, username);
		return customer;
	}

	public void addCustomer(Customer customer) {
		em.getTransaction().begin();
		em.persist(customer);
		em.getTransaction().commit();
	}

	public void removeCustomer(String username) {
		
		em.getTransaction().begin();
		Customer customer = em.find(Customer.class, username);
		em.remove(customer);
		em.getTransaction().commit();
	}

	public List<Customer> listAllCustomers() {
		TypedQuery<Customer> queryCustomer = em.createQuery("select c from Customer c", Customer.class);
		List<Customer> listCustomer = queryCustomer.getResultList();

		return listCustomer;
	}

}
