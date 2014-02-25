package com.yunling.mediacenter.db.service;

import java.util.Map;

import com.yunling.mediacenter.db.model.Customers;

public interface CustomerService {


	Integer selectCustomerCount();

	Map<String,Object> list(Integer page, Integer sum);

	void addCustomer(Customers cust);

	void delById(long custid);

	Customers getCustomerById(long custid);

	void updateCustomer(Customers cust);
	
}
