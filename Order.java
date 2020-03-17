package com.fdmgroup.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product_orders")
public class Order {
	@Id
	@SequenceGenerator(name = "orderIdSeq", sequenceName = "ORDER_ID_SEQ", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderIdSeq")
	private int orderId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	@OneToOne(orphanRemoval = true)
	private Shipment shipment;
	
	@Temporal(TemporalType.DATE)
	private Date OrderDate;

	public Order() {

	}
	
	

	public Order(int orderId) {
		super();
		this.orderId = orderId;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderId() {
		return orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}



	public Date getOrderDate() {
		return OrderDate;
	}



	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}
	
	
	

}
