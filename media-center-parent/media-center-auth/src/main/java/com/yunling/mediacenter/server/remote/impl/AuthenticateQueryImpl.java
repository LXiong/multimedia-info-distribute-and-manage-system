package com.yunling.mediacenter.server.remote.impl;

import java.util.List;

import com.caucho.hessian.server.HessianServlet;
import com.yunling.mediacenter.db.model.WatchServerStatus;
import com.yunling.mediacenter.server.remote.AuthenticateQuery;
import com.yunling.mediacenter.utils.Statistics;

public class AuthenticateQueryImpl extends HessianServlet implements AuthenticateQuery<WatchServerStatus> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7839767760946711858L;
	/**
	 * 
	 */
	transient Statistics statistics;
	public AuthenticateQueryImpl(Statistics s){
		this.setStatistics(s);
	}
	@Override
	public int getAuthenticateAuditCount() {
		return statistics.authenticateAuditCount();
	}

	@Override
	public int getAuthenticateFailureCount() {
		return statistics.authenticateFailureCount();
	}

	@Override
	public int getAuthenticateSuccessCount() {
		return statistics.authenticateSuccessCount();
	}

	@Override
	public String getAuthServerStartUpTime() {
		return statistics.getStartUpTime();
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}
	@Override
	public List<WatchServerStatus> getAllWatchServerStatus() {
		return statistics.allWatchServerStatus();
	}
	@Override
	public WatchServerStatus getWatchServerByIp(String ip) {
		return statistics.getWatchServerStatusByIp(ip);
	}
	
}
