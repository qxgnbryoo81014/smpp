package com.yves.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yves.smpp.service.InvoiceService;

@Service
public class TestProcessJob extends BaseJob implements Runnable {
	
	@Resource
	InvoiceService invoiceService;
	
	TestProcessJob(){
		setCronExpression("0/10 * * * * *");
	}

	@Override
	public void run() {
//		System.out.println("===== TestProcessJob run start =====");
		System.out.println( "[TestProcessJob] "+
				new Date() + " Runnable Task on thread " + Thread.currentThread().getName());
	}

}
