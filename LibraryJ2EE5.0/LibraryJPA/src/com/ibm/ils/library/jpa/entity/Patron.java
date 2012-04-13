package com.ibm.ils.library.jpa.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the PATRON database table.
 * 
 */
@Entity
public class Patron implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PATRON_ID")
	private int patronId;

	private String email;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	private String password;

	//bi-directional many-to-one association to Onloan
	@OneToMany(mappedBy="patron")
	private Set<Onloan> onloans;

    public Patron() {
    }

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

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

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

	public Set<Onloan> getOnloans() {
		return this.onloans;
	}

	public void setOnloans(Set<Onloan> onloans) {
		this.onloans = onloans;
	}
	
}