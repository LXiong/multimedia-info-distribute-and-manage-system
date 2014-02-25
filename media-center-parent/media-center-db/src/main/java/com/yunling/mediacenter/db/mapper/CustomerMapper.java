package com.yunling.mediacenter.db.mapper;

import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.model.Customers;

public interface CustomerMapper {

	Integer selectCustomerCount();

	List<Customers> list(Map<String, Object> map);

	Object addCustomer(Customers cust);

	Object delById(long custid);

	Customers getCustomerById(long custid);

	Object updateCustomer(Customers cust);

}
