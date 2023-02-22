package com.iktpreobuka.Project_example.entites;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class VoucherEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonView(Views.Public.class)
	private Date expirationDate;
	
	@Column(nullable=false)
	
	private boolean isUsed;
	
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="offer")
	@JsonView(Views.Private.class)
	@JsonManagedReference
	OfferEntity offer;
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	@JsonView(Views.Private.class)
	@JsonManagedReference
	UserEntity user;
	public VoucherEntity(Integer id, Date expirationDate, boolean isUsed, OfferEntity offer, UserEntity user) {
		super();
		this.id = id;
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
		this.offer = offer;
		this.user = user;
	}
	public VoucherEntity() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public OfferEntity getOffer() {
		return offer;
	}
	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}

	
	
}
