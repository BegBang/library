package com.ibm.library;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.GenerationType.TABLE;
import static javax.persistence.GenerationType.IDENTITY;


/**
 * The persistent class for the PATRON database table.
 * 
 */
@Entity
public class Patron implements Serializable {
	private static final long serialVersionUID = 1L;
	private int patronId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private Set<Onloan> onloans;

    public Patron() {
    }


	@Id
	@Column(name="PATRON_ID")
	public int getPatronId() {
		return this.patronId;
	}

	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(name="FIRST_NAME")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="LAST_NAME")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	//bi-directional many-to-one association to Onloan
	@OneToMany(mappedBy="patron")
	public Set<Onloan> getOnloans() {
		return this.onloans;
	}

	public void setOnloans(Set<Onloan> onloans) {
		this.onloans = onloans;
	}
	
}