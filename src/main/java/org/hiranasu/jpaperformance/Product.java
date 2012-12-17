package org.hiranasu.jpaperformance;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT")
@NamedNativeQuery(name = "thousands", query = "select * from product where id >= 1000 and id < 10000", resultClass = Product.class)
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
