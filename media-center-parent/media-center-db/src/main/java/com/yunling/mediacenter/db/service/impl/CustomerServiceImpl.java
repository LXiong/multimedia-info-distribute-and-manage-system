package com.yunling.mediacenter.db.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yunling.mediacenter.db.mapper.CustomerMapper;
import com.yunling.mediacenter.db.model.Customers;
import com.yunling.mediacenter.db.service.CustomerService;

public class CustomerServiceImpl extends BaseServiceImpl implements
		CustomerService {
	private Integer size;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer selectCustomerCount() {
		return getMapper().selectCustomerCount();
	}

	public Map<String,Object> list(final Integer page, final Integer sum) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		final Map<String,Object> map = new HashMap<String,Object>();
		
		Integer count = sum % size == 0 ? sum / size : sum
				/ size + 1;
		Integer page2 = page;
		if (page < 1) {
			page2 = 1;
		}
		if (page > count) {
			page2 = count;
		}
		if (page2 == 1) {
			map.put("begin", page2);
			map.put("end", page2 * size);
		} else {
			map.put("begin", ((page2 - 1) * size) + 1);
			map.put("end", (page2 * size));
		}
		
		List<Customers> list = getMapper().list(map);
		resultMap.put("list", list);
		resultMap.put("page", page2);
		resultMap.put("count", count);
		resultMap.put("size",size);
		return resultMap;
	}

	public void addCustomer(final Customers cust) {
		getMapper().addCustomer(cust);
	}

	@Override
	public void delById(final long custid) {
		getMapper().delById(custid);
	}

	public Customers getCustomerById(final long custid) {
		return getMapper().getCustomerById(custid);
	}

	@Override
	public void updateCustomer(final Customers cust) {
		 getMapper().updateCustomer(cust);
	}
	
	private CustomerMapper getMapper() {
		return getMapper(CustomerMapper.class);
	}
}
