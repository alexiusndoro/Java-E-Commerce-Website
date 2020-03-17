package com.fdmgroup.controllers;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.fdmgroup.daos.CustomerDAO;
import com.fdmgroup.daos.ProductDAO;
import com.fdmgroup.entities.Customer;
import com.fdmgroup.entities.Product;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalsoloproject");
		EntityManager em = emf.createEntityManager();

		Product product = new Product(45, "Text Book", new BigDecimal("45.00"));
		Product product1 = new Product(23, "Pencil", new BigDecimal("1.99"));
		Product product2 = new Product(65, "Ruler", new BigDecimal("2.99"));
		Product product3 = new Product(9, "Exercise Book", new BigDecimal("4.00"));
		Product product4 = new Product(32, "Calculator", new BigDecimal("12.99"));
		Product product5 = new Product(11, "Bag", new BigDecimal("19.99"));

		ProductDAO productDAO = new ProductDAO(em);
		productDAO.addProduct(product5);
		productDAO.addProduct(product4);
		productDAO.addProduct(product3);
		productDAO.addProduct(product2);
		productDAO.addProduct(product);
		productDAO.addProduct(product1);

		Customer customer = new Customer("alexius", "Alexius");
		customer.setPassword("a");
		customer.setLastName("Ndoro");
		Customer customer1 = new Customer("jonny134", "John");
		customer1.setPassword("j");
		customer1.setLastName("Legend");
		
		CustomerDAO customerDAO = new CustomerDAO(em);
		customerDAO.addCustomer(customer1);
		customerDAO.addCustomer(customer);

	}

}
