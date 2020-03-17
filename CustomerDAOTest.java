package com.fdmgroup.finalprojectests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.daos.CustomerDAO;
import com.fdmgroup.entities.Customer;

public class CustomerDAOTest {

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("finalsoloproject");
	private EntityManager em = entityManagerFactory.createEntityManager();

	@Before
	public void setup() {
		em.getTransaction().begin();

		TypedQuery<Customer> deleteCustomer = em.createQuery("Delete from Customer c", Customer.class);
		deleteCustomer.executeUpdate();

		em.getTransaction().commit();
	}

	@Test
	public void testThatWhenICallTheListCustomerMethodOnCustomerDAOThatItReturnsAnEmptyList() {

		CustomerDAO customerDAO = new CustomerDAO(em);
		List<Customer> listCustomers = customerDAO.listAllCustomers();
		int size = listCustomers.size();
		assertEquals(0, size);

	}

	@Test
	public void testWhenIaddUserTheSizeIncreasesToOne() {
		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customer = new Customer();
		customer.setUsername("Mark");
		customerDAO.addCustomer(customer);
		List<Customer> listCustomers = customerDAO.listAllCustomers();
		int size = listCustomers.size();
		assertEquals(1, size);
	}

	@Test
	public void testWhenIaddTwoCustomersTheSizeIncreasesTo2() {
		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customer = new Customer();
		customer.setUsername("Mark");

		Customer customer1 = new Customer();
		customer1.setUsername("Peter");

		customerDAO.addCustomer(customer);
		customerDAO.addCustomer(customer1);
		List<Customer> listCustomers = customerDAO.listAllCustomers();
		int size = listCustomers.size();
		assertEquals(2, size);
	}

	@Test
	public void ThatWhenIRemoveACustomerTheSizeIsZero() {
		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customer = new Customer();
		customer.setUsername("Mark");
		customerDAO.addCustomer(customer);
		customerDAO.removeCustomer("Mark");
		List<Customer> listCustomers = customerDAO.listAllCustomers();
		int size = listCustomers.size();
		assertEquals(0, size);

	}

	@Test
	public void testWhenIUseFindMethodItreturnsCorrectCustomer() {

		CustomerDAO customerDAO = new CustomerDAO(em);
		Customer customer = new Customer();
		customer.setUsername("Mark");

		System.out.println(customer.getUsername());

		Customer customer1 = new Customer();
		customer1.setUsername("Peter");

		customerDAO.addCustomer(customer1);
		customerDAO.addCustomer(customer);
		Customer customerToFind = customerDAO.findCustomer("Mark");
		String nameFound = customerToFind.getUsername();

		assertEquals(nameFound, "Mark");

	}

}
