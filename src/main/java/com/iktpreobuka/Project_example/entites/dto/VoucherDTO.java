package com.iktpreobuka.Project_example.entites.dto;

import java.util.Date;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

public class VoucherDTO {
	
	@NotNull(message="Payment Made_offer must be provided")
	@FutureOrPresent
	private Date expirationDate;
	
	@NotNull(message="Payment Made_offer must be provided")
	@AssertFalse
	private boolean isUsed;
	
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

	public VoucherDTO() {
		super();
	}

	public VoucherDTO(@NotNull(message = "Payment Made_offer must be provided") @FutureOrPresent Date expirationDate,
			@NotNull(message = "Payment Made_offer must be provided") @AssertFalse boolean isUsed) {
		super();
		this.expirationDate = expirationDate;
		this.isUsed = isUsed;
	}

	

}
