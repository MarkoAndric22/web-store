package com.iktpreobuka.Project_example.entites.dto;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class BillDTO {
	
	@NotNull(message="Payment Made_offer must be provided")
	@AssertFalse
	private boolean paymentMade;
	
	@NotNull(message="Payment Canceled must be provided")
	@AssertFalse
	private boolean paymentCanceled;
	
	@NotNull(message="Bill Created must be provided")
	@PastOrPresent
	private Date billCreated;

	public BillDTO() {
		super();
	}

	public BillDTO(@NotNull(message = "Payment Made_offer must be provided") @AssertFalse boolean paymentMade,
			@NotNull(message = "Payment Canceled must be provided") @AssertFalse boolean paymentCanceled,
			@NotNull(message = "Bill Created must be provided") @PastOrPresent Date billCreated) {
		super();
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
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

}
