package com.iktpreobuka.Project_example.entites.dto;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ReportItem {
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Date date;
	
	private Double income;
	
	private Integer numberOfOffers;

	public ReportItem(Date date, Double income, Integer numberOfOffers) {
		super();
		this.date = date;
		this.income = income;
		this.numberOfOffers = numberOfOffers;
	}

	public ReportItem() {
		super();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Integer getNumberOfOffers() {
		return numberOfOffers;
	}

	public void setNumberOfOffers(Integer numberOfOffers) {
		this.numberOfOffers = numberOfOffers;
	}

}
