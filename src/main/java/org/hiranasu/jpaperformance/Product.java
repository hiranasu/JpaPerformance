package org.hiranasu.jpaperformance;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="findProduct", query="select p from Product p where p.id = :id")
public class Product {
	
	@Id
	private BigDecimal id;
	
	private String name;

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "{" + this.id + ", " + this.name + "}";
	}
}
