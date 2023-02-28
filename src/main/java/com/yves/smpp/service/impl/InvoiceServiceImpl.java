package com.yves.smpp.service.impl;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yves.smpp.dao.InvoiceRepository;
import com.yves.smpp.entity.Invoice;
import com.yves.smpp.service.InvoiceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Resource
	InvoiceRepository invoiceRepository;

	@Override
	public Invoice findById(String invNo) {
		Optional<Invoice> option = invoiceRepository.findById(invNo);
		return option.isPresent() ? option.get() : null;
	}
}
