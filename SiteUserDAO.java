package com.fdmgroup.daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.fdmgroup.entities.SiteUser;

public class SiteUserDAO {

	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	private List<SiteUser> listUsers = new ArrayList<SiteUser>();

	public List<SiteUser> listUsers() {

		TypedQuery<SiteUser> querySiteUsers = entityManager.createQuery("Select u from SiteUser u", SiteUser.class);
		listUsers = querySiteUsers.getResultList();

		return listUsers;
	}

	public void addUser(SiteUser siteUser) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.persist(siteUser);
		entityTransaction.commit();

	}

	public SiteUser getUser(String username) {

		SiteUser siteUser = entityManager.find(SiteUser.class, username);
		return siteUser;
	}

	public void removeUser(String username) {

		SiteUser siteUser = entityManager.find(SiteUser.class, username);

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.remove(siteUser);
		entityTransaction.commit();

	}

	public void updateUser(SiteUser newSiteUser) {

		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();

		entityManager.merge(newSiteUser);
		entityTransaction.commit();

	}

}
