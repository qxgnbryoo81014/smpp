package com.yves.smpp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yves.smpp.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, String>{

}
