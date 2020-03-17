package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Product;

public class ProductDAO {

	private EntityManager em;

	public ProductDAO(EntityManager em) {
		super();
		this.em = em;
	}

	public Product findProductbyId(int productId) {
		Product product = em.find(Product.class, productId);
		return product;
	}

	public List<Product> listAllProducts() {
		TypedQuery<Product> queryProduct = em.createQuery("select p from Product p", Product.class);
		List<Product> listProduct = queryProduct.getResultList();

		return listProduct;
	}

	public void addProduct(Product product) {
		em.getTransaction().begin();
		em.persist(product);
		em.getTransaction().commit();
	}

}
