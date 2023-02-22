package com.iktpreobuka.Project_example.entites;

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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	protected Integer id;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="First name must be provided")
	@Size(min=2,max=30, message= "First name must be beetwen {min} and {max} characters long.")
	protected String first_name;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Last name must be provided")
	@Size(min=2,max=30, message= "Last name must be beetwen {min} and {max} characters long.")
	protected String last_name;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="username must be provided")
	@Size(min=5,max=20, message= "username must be beetwen {min} and {max} characters long.")
	protected String username;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Password must be provided")
	@Size(min=5,max=20, message= "password must be beetwen {min} and {max} characters long.")
	protected String password;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Email must be provided")
	@Size(min=2,max=30, message= "Email must be beetwen {min} and {max} characters long.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	protected String email;
	
	
	@JsonView(Views.Administator.class)
	protected UserRole user_role;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "role")
	private RoleEntity role;
	
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}
	public UserEntity(Integer id,
			@NotNull(message = "First name must be provided") @Size(min = 2, max = 30, message = "First name must be beetwen {min} and {max} characters long.") String first_name,
			@NotNull(message = "Last name must be provided") @Size(min = 2, max = 30, message = "Last name must be beetwen {min} and {max} characters long.") String last_name,
			@NotNull(message = "username must be provided") @Size(min = 5, max = 20, message = "username must be beetwen {min} and {max} characters long.") String username,
			@NotNull(message = "Password must be provided") @Min(value = 5, message = "Password must have 5 or more letters") String password,
			@NotNull(message = "Email must be provided") @Size(min = 2, max = 30, message = "Email must be beetwen {min} and {max} characters long.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			UserRole user_role, RoleEntity role, List<OfferEntity> offer, List<BillEntity> bill,
			List<VoucherEntity> voucher) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.user_role = user_role;
		this.role = role;
		this.offer = offer;
		this.bill = bill;
		this.voucher = voucher;
	}
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	@JsonBackReference
	List<OfferEntity>offer;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	@JsonBackReference
	List<BillEntity>bill;
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	@JsonBackReference
	List<VoucherEntity>voucher;
	
	
	public List<OfferEntity> getOffer() {
		return offer;
	}
	public void setOffer(List<OfferEntity> offer) {
		this.offer = offer;
	}
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
	public UserEntity(Integer id,
			@NotNull(message = "First name must be provided") @Size(min = 2, max = 30, message = "First name must be beetwen {min} and {max} characters long.") String first_name,
			@NotNull(message = "Last name must be provided") @Size(min = 2, max = 30, message = "Last name must be beetwen {min} and {max} characters long.") String last_name,
			@NotNull(message = "username must be provided") @Size(min = 5, max = 20, message = "username must be beetwen {min} and {max} characters long.") String username,
			@NotNull(message = "Password must be provided") @Min(value = 5, message = "Password must have 5 or more letters") String password,
			@NotNull(message = "Email must be provided") @Size(min = 2, max = 30, message = "Email must be beetwen {min} and {max} characters long.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			UserRole user_role, List<OfferEntity> offer, List<BillEntity> bill, List<VoucherEntity> voucher) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.user_role = user_role;
		this.offer = offer;
		this.bill = bill;
		this.voucher = voucher;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUser_role() {
		return user_role;
	}
	public void setUser_role(UserRole user_role) {
		this.user_role = user_role;
	}
	public UserEntity(Integer id, String first_name, String last_name, String username, String password, String email,
			UserRole user_role) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.user_role = user_role;
	}
	public UserEntity() {
		super();
	}
	public UserEntity(String first_name, String last_name, String username, String password, String email) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

}
