package com.gaia.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "sales_quote_address")
@DynamicUpdate(true)
public class SalesQuoteAdrsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684735919350585273L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "quote_id")
	private String quoteId;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "postcode")
	private String postCode;
	
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	@Column(name = "created_at")
	private LocalDateTime createdDt;
	
	@Type(type = "org.hibernate.type.LocalDateTimeType")
	@Column(name = "updated_at")
	private LocalDateTime updatedDt;
	
	
	@PrePersist
	public void oncreate() {
		setCreatedDt(Optional.ofNullable(this.createdDt).map(m->m).orElse(LocalDateTime.now()));
		setUpdatedDt(Optional.ofNullable(this.updatedDt).map(m->m).orElse(LocalDateTime.now()));
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getQuoteId() {
		return quoteId;
	}


	public void setQuoteId(String quoteId) {
		this.quoteId = quoteId;
	}

	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getRegion() {
		return region;
	}


	public void setRegion(String region) {
		this.region = region;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getPostCode() {
		return postCode;
	}


	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}


	public LocalDateTime getCreatedDt() {
		return createdDt;
	}


	public void setCreatedDt(LocalDateTime createdDt) {
		this.createdDt = createdDt;
	}


	public LocalDateTime getUpdatedDt() {
		return updatedDt;
	}


	public void setUpdatedDt(LocalDateTime updatedDt) {
		this.updatedDt = updatedDt;
	}
	
	


}
