package com.iktpreobuka.Project_example.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	protected Integer id;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="Category name must be provided")
	@Size(min=2,max=30, message= "Category name must be beetwen {min} and {max} characters long.")
	protected String category_name;
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
//	@Max(value = 50, message= "maximum letters can be 50")
	@Size(min=2,max=30, message= "Category description must be beetwen {min} and {max} characters long.")
	protected String category_description;
	
	@JsonBackReference
	@OneToMany(mappedBy="category",fetch=FetchType.LAZY,cascade= {CascadeType.REFRESH})
	List<OfferEntity>offer;
	
	
	
	public List<OfferEntity> getOffer() {
		return offer;
	}
	public void setOffer(List<OfferEntity> offer) {
		this.offer = offer;
	}
	public CategoryEntity(Integer id,
			@NotNull(message = "Category name must be provided") @Size(min = 2, max = 30, message = "Category name must be beetwen {min} and {max} characters long.") String category_name,
			@Max(value = 50, message = "maximum letters can be 50") String category_description,
			List<OfferEntity> offer) {
		super();
		this.id = id;
		this.category_name = category_name;
		this.category_description = category_description;
		this.offer = offer;
	}
	public CategoryEntity() {
		super();
	}
	public CategoryEntity(Integer id, String category_name, String category_description) {
		super();
		this.id = id;
		this.category_name = category_name;
		this.category_description = category_description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getCategory_description() {
		return category_description;
	}
	public void setCategory_description(String category_description) {
		this.category_description = category_description;
	}
	

}
