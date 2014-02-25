package com.yunling.mediacenter.web.actions;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunling.mediacenter.db.model.Conf;
import com.yunling.mediacenter.db.model.Config;
import com.yunling.mediacenter.db.service.ConfService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-11-12
 * 
 */
public class ConfAction extends AbstractUserAwareAction {
	private List<Conf> confs;
	private ConfService confService;
	private Long confId;
	private String confName;
	private int pageNums; // Total pages
	private int page; // Current page
	private int pageSize;
	private HttpServletResponse response = ServletActionContext.getResponse();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		Conf conf = new Conf();
		conf.setBegin((page - 1) * pageSize + 1);
		conf.setEnd(page * pageSize);
		confs = confService.getConfs(conf);
		return SUCCESS;
	}

	/**
	 * add a new configuration
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Conf conf = new Conf();
		conf.setConfName(confName);
		if (confService.getConf(conf) != null) {
			response.getWriter().print(this.getText("config.file.name.exist"));
			return null;
		}
		conf.setConfVersion("1.0");
		Date createDate = new Date();
		conf.setCreateTime(createDate);
		conf.setUpdateTime(createDate);

		confService.addConf(conf);
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getConf() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Conf conf = new Conf();
		conf.setConfName(confName);
		conf = confService.getConf(conf);
		if (conf != null) {
			response.getWriter().print(conf.getConfId());
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Conf conf = new Conf();
		conf.setConfId(confId);
		conf.setConfName(confName);
		conf.setUpdateTime(new Date());

		confService.upateConf(conf);
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteConf() throws Exception {
		Config config = new Config();
		config.setConfId(confId);
		Conf conf = new Conf();
		conf.setConfId(confId);
		confService.deleteConf(config, conf);
		return null;
	}

	/**
	 * @return the confs
	 */
	public List<Conf> getConfs() {
		return confs;
	}

	/**
	 * @param confs
	 *            the confs to set
	 */
	public void setConfs(List<Conf> confs) {
		this.confs = confs;
	}

	/**
	 * @return the confService
	 */
	public ConfService getConfService() {
		return confService;
	}

	/**
	 * @param confService
	 *            the confService to set
	 */
	public void setConfService(ConfService confService) {
		this.confService = confService;
	}

	/**
	 * @return the confId
	 */
	public Long getConfId() {
		return confId;
	}

	/**
	 * @param confId
	 *            the confId to set
	 */
	public void setConfId(Long confId) {
		this.confId = confId;
	}

	/**
	 * @return the confName
	 */
	public String getConfName() {
		return confName;
	}

	/**
	 * @param confName
	 *            the confName to set
	 */
	public void setConfName(String confName) {
		this.confName = confName;
	}

	/**
	 * @return the pageNums
	 */
	public int getPageNums() {
		return pageNums;
	}

	/**
	 * @param pageNums
	 *            the pageNums to set
	 */
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
