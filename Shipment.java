package com.fdmgroup.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Shipment {

	@Id
	@SequenceGenerator(name = "shipmentIdSeq", sequenceName = "SHIPMENT_ID_SEQ", initialValue = 344567, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shipmentIdSeq")
	private int shipmentId;

	@Temporal(TemporalType.DATE)
	private Date shipmentDate;

	@OneToOne(mappedBy = "shipment", orphanRemoval = true)
	private Order order;

	public Shipment() {
	}

	public Shipment(int shipmentId) {
		super();
		this.shipmentId = shipmentId;
	}

	public Shipment(int shipmentId, Date shipmentDate) {
		super();
		this.shipmentId = shipmentId;
		this.shipmentDate = shipmentDate;
	}

	public int getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(int shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Date getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(Date shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
