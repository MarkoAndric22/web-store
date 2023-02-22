package com.iktpreobuka.Project_example.Service;

import java.util.Date;

import com.iktpreobuka.Project_example.entites.dto.ReportDto;

public interface ReportService {
	
	ReportDto generateReportByDatesBetween(Date startDate, Date endDate);
	
	ReportDto generateReportByDatesBetweenAndCategory(Date startDate, Date endDate,Integer categoryId);

}
