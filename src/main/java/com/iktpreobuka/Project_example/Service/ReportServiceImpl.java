package com.iktpreobuka.Project_example.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.Project_example.entites.BillEntity;
import com.iktpreobuka.Project_example.entites.dto.ReportDto;
import com.iktpreobuka.Project_example.entites.dto.ReportItem;
import com.iktpreobuka.Project_example.repositories.BillRepository;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;

@Service
public class ReportServiceImpl implements ReportService{
	
	 @Autowired
	 BillRepository billRepository;
	 @Autowired
	 CategoryRepository categoryRepository;
	
	 
	 public List<ReportItem> generateReportItems(Date startDate, Date endDate) {

        List<ReportItem> reportItems = new ArrayList<>();
      
       
        for (Date d = startDate; d.before(new Date(endDate.getTime() + (1000 * 60 * 60 * 24))); d = new Date(d.getTime() + (1000 * 60 * 60 * 24))) {
        	List<BillEntity> billsForDate = billRepository.findByBillCreated(d);
            ReportItem reportItem = new ReportItem();
            reportItem.setDate(d);
            double sum=0;
            for(BillEntity b:billsForDate) {
            	sum+=b.getOffer().getAction_price();
            }
            reportItem.setIncome(sum);
            reportItem.setNumberOfOffers(billsForDate.size());
            
            reportItems.add(reportItem);
        }

        return reportItems;
        
    }
	 
	
	 public List<ReportItem> generateReportItems(Date startDate, Date endDate, Integer categoryId) {
	        
	        List<ReportItem> reportItems = new ArrayList<>();
	        
	        for (Date d =  startDate; d.before(new Date(endDate.getTime() + (1000 * 60 * 60 * 24))); d = new Date(d.getTime() + (1000 * 60 * 60 * 24))) {
	            List<BillEntity> billsForDate = billRepository.findByBillCreated(d);

	            ReportItem reportItem = new ReportItem();
	            reportItem.setDate(d);
	            reportItem.setNumberOfOffers(billsForDate.size());
	            double sum=0;
	            for(BillEntity b:billsForDate) {
	            	if(b.getOffer().getCategory().getId().equals(categoryId)) {
	            		sum+=b.getOffer().getAction_price();
	            	}
	            }
	            reportItem.setIncome(sum);
	            reportItems.add(reportItem);
	        }
	        
	        return reportItems;
	        
	    }
	 
	
	  public ReportDto generateReport(Date startDate, Date endDate, List<ReportItem> items, Integer categoryId) {
	        
		  	ReportDto report = new ReportDto();
	        
	        report.setCategoryName(categoryId == null ? "All" : categoryRepository.findById(categoryId).get().getCategory_name());
	        int sumOfSoldOffers = 0;
	        for(ReportItem ri:items) {
	        	sumOfSoldOffers+=ri.getNumberOfOffers();
	        }
	        report.setTotalNumberOfSoldOffers(sumOfSoldOffers);
	        report.setListaReportltem(items);
	        int sumOfIncomes=0;
	        for(ReportItem ri:items) {
	        	sumOfIncomes+=ri.getIncome();
	        }
	        report.setSumOflncomes(sumOfIncomes);
	        
	        return report;
	    }

	 
	 @Override
	    public ReportDto generateReportByDatesBetween(Date startDate, Date endDate) {
	        
	        return generateReport(startDate, endDate, generateReportItems(startDate, endDate), null);
	    }

	    @Override
	    public ReportDto generateReportByDatesBetweenAndCategory(Date startDate, Date endDate,
	            Integer categoryId) {
	        
	        return generateReport(startDate, endDate, generateReportItems(startDate, endDate, categoryId), categoryId);
	    }
//	    
//	private List<ReportItem> generateReportItems(Date startDate, Date endDate) {
//	        
//	        List<ReportItem> reportItems = new ArrayList<>();
//	        
//	        for (Date d = startDate; d.before(new Date(endDate.getTime() + (1000 * 60 * 60 * 24))); d = new Date(d.getTime() + (1000 * 60 * 60 * 24))) {
//	            List<BillEntity> billsForDate = billRepository.findByBillCreated(d);
//	            ReportItem reportItem = new ReportItem();
//	            reportItem.setDate(d);
//	            reportItem.setNumberOfOffers((int) billsForDate.size());
//	            reportItem.setIncome(billsForDate.stream()
//	                    .map((e) -> e.getOffer().getAction_price())
//	                    .reduce(0.0, (a, b) -> a + b));
//	            reportItems.add(reportItem);
//	        }
//	        
//	        return reportItems;
//	        
//	    }
//	    
//	    private List<ReportItem> generateReportItems(Date startDate, Date endDate, Integer categoryId) {
//	        
//	        List<ReportItem> reportItems = new ArrayList<>();
//	        
//	        for (Date d = startDate; d.before(new Date(endDate.getTime() + (1000 * 60 * 60 * 24))); d = new Date(d.getTime() + (1000 * 60 * 60 * 24))) {
//	            List<BillEntity> billsForDate = billRepository.findByBillCreated(d).stream()
//	                    .filter((e) -> e.getOffer().getCategory().getId().equals(categoryId))
//	                    .toList();
//	            ReportItem reportItem = new ReportItem();
//	            reportItem.setDate(d);
//	            reportItem.setNumberOfOffers((int) billsForDate.size());
//	            reportItem.setIncome(billsForDate.stream()
//	                    .map((e) -> e.getOffer().getAction_price())
//	                    .reduce(0.0, (a, b) -> a + b));
//	            reportItems.add(reportItem);
//	        }
//	        
//	        return reportItems;
//	        
//	    }
//	    
//	    private ReportDto generateReport(Date startDate, Date endDate, List<ReportItem> items, Integer categoryId) {
//	        
//	    	ReportDto report = new ReportDto();
//	        
//	        report.setCategoryName(categoryId == null ? "All" : categoryRepository.findById(categoryId).get().getCategory_name());
//	        report.setTotalNumberOfSoldOffers(
//	                items.stream()
//	                .map(ReportItem::getNumberOfOffers)
//	                .reduce(0, (a, b) -> a + b)
//	                );
//	        report.setListaReportltem(items);
//	        report.setSumOflncomes(
//	                items.stream()
//	                .map(ReportItem::getIncome)
//	                .reduce(0.0, (a, b) -> a + b)
//	                );
//	        
//	        return report;
//	    }


}
