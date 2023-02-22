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
public class BillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	private boolean paymentMade;
	
	@Column(nullable=false)
	@JsonView(Views.Administator.class)
	private boolean paymentCanceled;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonView(Views.Administator.class)
	private Date billCreated;
	
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	@JsonManagedReference
	UserEntity user;
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="offer")
	@JsonManagedReference
	OfferEntity offer;
	public BillEntity(Integer id, boolean paymentMade, boolean paymentCanceled, Date billCreated) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}

	public BillEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(boolean paymentMade) {
		this.paymentMade = paymentMade;
	}

	public boolean isPaymentCanceled() {
		return paymentCanceled;
	}

	public void setPaymentCanceled(boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}

	public Date getBillCreated() {
		return billCreated;
	}

	public void setBillCreated(Date billCreated) {
		this.billCreated = billCreated;
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
