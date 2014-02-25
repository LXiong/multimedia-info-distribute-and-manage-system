package com.yunling.mediacenter.web.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.yunling.mediacenter.db.model.Customers;
import com.yunling.mediacenter.db.service.CustomerService;
import com.yunling.mediacenter.web.actions.vo.CustomerVo;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

@Results({@Result(name="redirect-list", location="customer!list.action",type="redirect")})
public class CustomerAction extends AbstractUserAwareAction{
	private HttpServletResponse response = ServletActionContext.getResponse();
	private CustomerVo vo;
	private CustomerService customerService;
	private long custid;
	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public CustomerVo getVo() {
		return vo;
	}
	
	public void setVo(CustomerVo vo) {
		this.vo = vo;
	}

	public String execute() throws Exception {
		return null;
	}
	public String list() throws Exception {
		Integer sum = customerService.selectCustomerCount();
		Integer page=0;
		try{
			 page = Integer.parseInt(ServletActionContext.getRequest().getParameter("page"));
		}catch(Exception e){
			page=1;
		}
		super.request.put("map",customerService.list(page,sum));
		return "list";
	}
	public String add(){
		return "add";
	}
	public String addCustomer() throws Exception{
		Customers cust = new Customers();
		cust.setCustId(vo.getCustId());
		cust.setCustname(vo.getCustname());
		cust.setAddress(vo.getAddress());
		cust.setTelephone(vo.getTelephone());
		customerService.addCustomer(cust);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('添加成功!');location='customer!list.action'</script>"); 
		return null;
	}
	
	public String delById() throws IOException{
		customerService.delById(custid);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('删除成功!');location='customer!list.action'</script>"); 
		return null;
	}
	public String edit(){
		Customers customer = customerService.getCustomerById(custid);
		ActionContext.getContext().put("customer",customer);
		return "edit";
	}
	public String edit2() throws Exception{
		Customers cust = new Customers();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		cust.setCustId(vo.getCustId());
		cust.setCustname(vo.getCustname());
		cust.setAddress(vo.getAddress());
		cust.setTelephone(vo.getTelephone());
		customerService.updateCustomer(cust);
		out.print("<script>alert('修改信息成功!');location='customer!list.action'</script>"); 
		return null;
	}
}
