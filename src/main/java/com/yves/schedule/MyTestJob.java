package com.yves.schedule;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yves.smpp.service.InvoiceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyTestJob extends BaseJob implements Runnable {
	
	@Resource
	InvoiceService invoiceService;
	
	MyTestJob(){
		setCronExpression("0/3 * * * * *");
	}
	
	@Override
	public void run() {
		System.out.println( "[MyTestJob] " + 
				new Date() + " Runnable Task on thread " + Thread.currentThread().getName());
		
		try {
//			Thread.sleep(5000);
//			System.out.println(invoiceService);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		int r = (int)(Math.random()*5);
//		
//		if(r == 3) {
//			r = r/0;
//		}
	}

}
