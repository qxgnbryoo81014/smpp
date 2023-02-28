package com.yves.smpp.service;

import com.yves.smpp.entity.Invoice;

public interface InvoiceService {
	
	Invoice findById(String invNo);
	
}
