package com.iktpreobuka.Project_example.entites;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class OfferEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@Column(nullable=false)
	@NotNull(message="offer_name name must be provided")
	protected String offer_name;
	
	@Column(nullable=false)
	@NotNull(message="offer_description must be provided")
	@Size(min=5,max=20, message= "offer_description must be beetwen {min} and {max} characters long.")
	protected String offer_description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable=false)
	protected Date offer_created;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	protected Date offer_expires;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="regular_price must be provided")
	@Min(value =1, message= "Regular price can't have a value a smaller one")
	protected Double regular_price;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="action_price must be provided")
	@Min(value =1, message= "Action price can't have a value a smaller one")
	protected Double action_price;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="image_path must be provided")
	protected String image_path;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="available_offer must be provided")
	@Min(value =0, message= "minimum available offer must be 0")
	protected Integer available_offer;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="bought_offers must be provided")
	@Min(value =0, message= "minimum bought offer must be 0")
	protected Integer bought_offers;
	
	@NotNull(message = "offer status can not be null")
	@JsonView(Views.Public.class)
	protected OfferStatus offer_status;
	
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="category")
	@JsonIgnore
	CategoryEntity category;
	@ManyToOne(cascade= {CascadeType.REFRESH, CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	@JsonIgnore
	UserEntity user;
	@OneToMany(mappedBy="offer",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	@JsonIgnore
	List<BillEntity> bill;
	@OneToMany(mappedBy="offer",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	@JsonIgnore
	List<VoucherEntity>voucher;
	
	
	public List<BillEntity> getBill() {
		return bill;
	}


	public void setBill(List<BillEntity> bill) {
		this.bill = bill;
	}


	public List<VoucherEntity> getVoucher() {
		return voucher;
	}


	public void setVoucher(List<VoucherEntity> voucher) {
		this.voucher = voucher;
	}


	public OfferEntity(Integer id, @NotNull(message = "offer_name name must be provided") String offer_name,
			@NotNull(message = "offer_description must be provided") @Size(min = 5, max = 20, message = "offer_description must be beetwen {min} and {max} characters long.") String offer_description,
			Date offer_created, Date offer_expires,
			@NotNull(message = "regular_price must be provided") @Min(value = 1, message = "Regular price can't have a value a smaller one") Double regular_price,
			@NotNull(message = "action_price must be provided") @Min(value = 1, message = "Action price can't have a value a smaller one") Double action_price,
			@NotNull(message = "image_path must be provided") String image_path,
			@NotNull(message = "available_offer must be provided") @Min(value = 0, message = "minimum available offer must be 0") Integer available_offer,
			@NotNull(message = "bought_offers must be provided") @Min(value = 0, message = "minimum bought offer must be 0") Integer bought_offers,
			OfferStatus offer_status, CategoryEntity category, UserEntity user, List<BillEntity> bill,
			List<VoucherEntity> voucher) {
		super();
		this.id = id;
		this.offer_name = offer_name;
		this.offer_description = offer_description;
		this.offer_created = offer_created;
		this.offer_expires = offer_expires;
		this.regular_price = regular_price;
		this.action_price = action_price;
		this.image_path = image_path;
		this.available_offer = available_offer;
		this.bought_offers = bought_offers;
		this.offer_status = offer_status;
		this.category = category;
		this.user = user;
		this.bill = bill;
		this.voucher = voucher;
	}


	public OfferEntity(Integer id, String offer_name, String offer_description, Date offer_created, Date offer_expires,
			Double regular_price, Double action_price, String image_path, Integer available_offer,
			Integer bought_offers, OfferStatus offer_status) {
		super();
		this.id = id;
		this.offer_name = offer_name;
		this.offer_description = offer_description;
		this.offer_created = offer_created;
		this.offer_expires = offer_expires;
		this.regular_price = regular_price;
		this.action_price = action_price;
		this.image_path = image_path;
		this.available_offer = available_offer;
		this.bought_offers = bought_offers;
		this.offer_status = offer_status;
	}
	
	
	public OfferEntity() {
			super();
		}
	
	

	public OfferEntity(String offer_name, String offer_description, Double regular_price, Double action_price,
			String image_path, Integer available_offer, Integer bought_offers, OfferStatus offer_status) {
		super();
		this.offer_name = offer_name;
		this.offer_description = offer_description;
		this.regular_price = regular_price;
		this.action_price = action_price;
		this.image_path = image_path;
		this.available_offer = available_offer;
		this.bought_offers = bought_offers;
		this.offer_status = offer_status;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOffer_name() {
		return offer_name;
	}
	public void setOffer_name(String offer_name) {
		this.offer_name = offer_name;
	}
	public String getOffer_description() {
		return offer_description;
	}
	public void setOffer_description(String offer_description) {
		this.offer_description = offer_description;
	}
	public Date getOffer_created() {
		return offer_created;
	}
	public void setOffer_created(Date offer_created) {
		this.offer_created = offer_created;
	}
	public Date getOffer_expires() {
		return offer_expires;
	}
	public void setOffer_expires(Date offer_expires) {
		this.offer_expires = offer_expires;
	}
	public Double getRegular_price() {
		return regular_price;
	}
	public void setRegular_price(Double regular_price) {
		this.regular_price = regular_price;
	}
	public Double getAction_price() {
		return action_price;
	}
	public void setAction_price(Double action_price) {
		this.action_price = action_price;
	}
	public String getImage_path() {
		return image_path;
	}
	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}
	public Integer getAvailable_offer() {
		return available_offer;
	}
	public void setAvailable_offer(Integer available_offer) {
		this.available_offer = available_offer;
	}
	public Integer getBought_offers() {
		return bought_offers;
	}
	public void setBought_offers(Integer bought_offers) {
		this.bought_offers = bought_offers;
	}
	public OfferStatus getOffer_status() {
		return offer_status;
	}
	public void setOffer_status(OfferStatus offer_status) {
		this.offer_status = offer_status;
	}


	public CategoryEntity getCategory() {
		return category;
	}


	public void setCategory(CategoryEntity category) {
		this.category = category;
	}


	public UserEntity getUser() {
		return user;
	}


	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	

}
