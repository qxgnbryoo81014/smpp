package com.yves.smpp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// test
// test
// test

@Entity
@Table(name="invoice")
public class Invoice {
	
	@Id
	@Column(name="inv_no")
	private String invNo;
	
	@Column(name="inv_year")
	private String invYear;
	
	@Column(name="seller_compid")
	private String sellerCompid;

	public Invoice() {
	}

	public String getInvNo() {
		return invNo;
	}

	public void setInvNo(String invNo) {
		this.invNo = invNo;
	}

	public String getInvYear() {
		return invYear;
	}

	public void setInvYear(String invYear) {
		this.invYear = invYear;
	}

	public String getSellerCompid() {
		return sellerCompid;
	}

	public void setSellerCompid(String sellerCompid) {
		this.sellerCompid = sellerCompid;
	}
}
