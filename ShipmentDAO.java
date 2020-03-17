package com.fdmgroup.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.Shipment;

public class ShipmentDAO {

	private EntityManager em;

	public ShipmentDAO(EntityManager em) {
		this.em = em;
	}

	public void addShipment(Shipment shipment) {
		em.getTransaction().begin();
		em.merge(shipment);
		em.getTransaction().commit();
	}

	public Shipment findShipmentById(int shipmentId) {
		Shipment shipment = em.find(Shipment.class, shipmentId);
		return shipment;
	}

	public void cancelShipment(int shipmentId) {
		Shipment shipment = em.find(Shipment.class, shipmentId);
		em.getTransaction().begin();
		em.remove(shipment);
		em.getTransaction().commit();
	}

	public List<Shipment> listAllShipments() {
		TypedQuery<Shipment> queryShipment = em.createQuery("select s from Shipment s", Shipment.class);
		List<Shipment> listShipment = queryShipment.getResultList();

		return listShipment;
	}

}
