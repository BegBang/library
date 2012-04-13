package com.ibm.ils.library.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.ibm.ils.library.jpa.entity.Patron;
import com.ibm.xml.b2b.util.entity.EntityManager;

/**
 * Session Bean implementation class PatronEJB
 */
@Stateless
@LocalBean
public class PatronEJB implements PatronEJBRemote, PatronEJBLocal {
	Patron patron;
	EntityManager em;
    /**
     * Default constructor. 
     */
    public PatronEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public void add(Patron p) {
    	
    }

}
