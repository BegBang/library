package com.ibm.library.ejb;
import javax.ejb.Local;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.RollbackException;

import com.ibm.library.Patron;

@Local
public interface PatronEJBLocal {
	public void add(Patron p) throws EntityExistsException, RollbackException, IllegalStateException;
	public Patron findByEmail(String email) throws NoResultException, IllegalArgumentException, IllegalStateException, NonUniqueResultException;
}
