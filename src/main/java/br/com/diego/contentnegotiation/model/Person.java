package br.com.diego.contentnegotiation.model;

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = 28L;

	private Long id;
	
	private String name;
	
	private String email;
	
	
	

	public Person(Long id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
