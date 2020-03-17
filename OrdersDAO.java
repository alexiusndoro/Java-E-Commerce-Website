package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import com.fdmgroup.entities.Order;

public class OrdersDAO {

	private EntityManager em;

	public OrdersDAO(EntityManager em) {
		this.em = em;
	}

	public void addOrder(Order order) {

		em.getTransaction().begin();
		em.merge(order);
		em.getTransaction().commit();

	}

	public Order findOrderById(int orderId) {

		Order ord = em.find(Order.class, orderId);
		return ord;

	}

	public void cancelOrder(int orderId) {
		Order cancelledOrder = em.find(Order.class, orderId);
		em.getTransaction().begin();
		em.remove(cancelledOrder);
		em.getTransaction().commit();
	}

	public List<Order> listAllOrders() {
		TypedQuery<Order> queryOrder = em.createQuery("select p from Order p", Order.class);
		List<Order> listOrders = queryOrder.getResultList();

		return listOrders;
	}
	
	public List<Order> listAllOrdersForGivenCustomer(String username) {
		TypedQuery<Order> queryFindOrders = em.createQuery("select o from Order o where o.customer.username=:cname", Order.class);
		queryFindOrders.setParameter("cname", username);
		List<Order> listOfOrders = queryFindOrders.getResultList();
		return listOfOrders;

	}

}
