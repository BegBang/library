package com.ibm.library.ejb;

import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import com.ibm.library.Patron;

/**
 * Session Bean implementation class PatronEJB
 */
@Stateless
@LocalBean
public class PatronEJB implements PatronEJBLocal {
	EntityManager em;

    /**
     * Default constructor. 
     */
    public PatronEJB() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryJPA");
    	em = emf.createEntityManager();
    }
    
    public void add(Patron p) throws EntityExistsException, RollbackException, IllegalStateException {
    	p.setPatronId(generateId());
    	
    	try {
    		em.getTransaction().begin();
    		em.persist(p);
    		em.getTransaction().commit();
    	} finally {
    		em.close();
    	}
    }

    public Patron findByEmail(String email) throws NoResultException, IllegalArgumentException, IllegalStateException, NonUniqueResultException {
    	try {
    		Patron p = (Patron) em.createQuery("SELECT p FROM Patron p WHERE p.email LIKE :email").setParameter("email", email).getSingleResult();
    		return p;
    	} finally {
    		em.close();
    	}
    }
    

	private int generateId() {
		Random r = new Random();
		return r.nextInt(Integer.MAX_VALUE - 1) + 1;
	}
}
