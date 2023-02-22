package com.iktpreobuka.Project_example.entites.dto;

import java.util.List;

public class ReportDto {
	
	private String categoryName;
	
	private List<ReportItem> listaReportltem;
	
	private Integer sumOflncomes;
	
	private Integer totalNumberOfSoldOffers;

	public ReportDto() {
		super();
	}

	public ReportDto(String categoryName, List<ReportItem> listaReportltem, Integer sumOflncomes,
			Integer totalNumberOfSoldOffers) {
		super();
		this.categoryName = categoryName;
		this.listaReportltem = listaReportltem;
		this.sumOflncomes = sumOflncomes;
		this.totalNumberOfSoldOffers = totalNumberOfSoldOffers;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<ReportItem> getListaReportltem() {
		return listaReportltem;
	}

	public void setListaReportltem(List<ReportItem> listaReportltem) {
		this.listaReportltem = listaReportltem;
	}

	public Integer getSumOflncomes() {
		return sumOflncomes;
	}

	public void setSumOflncomes(Integer sumOflncomes) {
		this.sumOflncomes = sumOflncomes;
	}

	public Integer getTotalNumberOfSoldOffers() {
		return totalNumberOfSoldOffers;
	}

	public void setTotalNumberOfSoldOffers(Integer totalNumberOfSoldOffers) {
		this.totalNumberOfSoldOffers = totalNumberOfSoldOffers;
	}

}
