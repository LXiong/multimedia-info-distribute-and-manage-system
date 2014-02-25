package com.yunling.mediacenter.web.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.BoxDownStatus;
import com.yunling.mediacenter.db.model.Videos;
import com.yunling.mediacenter.db.service.BoxDownStatusService;
import com.yunling.mediacenter.db.service.PolicyMediaService;
import com.yunling.mediacenter.web.actions.vo.BoxDownStatusVo;
import com.yunling.mediacenter.web.actions.vo.MediaDownStatusVo;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

/**
 * @author LingJun
 * @date 2010-12-28
 * 
 */
public class PolicyDownloadStatusAction extends AbstractUserAwareAction {
	private PolicyMediaService policyMediaService;
	private BoxDownStatusService downStatusService;
	private List<BoxDownStatusVo> downStatus;
	private List<MediaDownStatusVo> mediaDownStatus;
	private List<BoxDownStatus> boxDownStatus;
	private String mac;
	private String policyNumber;
	private Long policyId;
	private int mediaNum;
	private String isFinished;
	private String videoName;
	private Long videoSize;
	private Logger log = LoggerFactory
			.getLogger(PolicyDownloadStatusAction.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {
		List<Videos> videos = policyMediaService.findMediaByPolicyid(policyId);
		if (videos != null) {
			mediaNum = videos.size();
		}
		log.info("contain files number: {}", mediaNum);
		BoxDownStatus status = new BoxDownStatus();
		status.setPolicyNumber(policyNumber);
		status.setIsFinished(isFinished);
		List<BoxDownStatus> logList = downStatusService.getMacs(status);
		Iterator<BoxDownStatus> it = logList.iterator();
		BoxDownStatusVo vo = null;
		downStatus = new Vector<BoxDownStatusVo>();
		while (it.hasNext()) {
			vo = new BoxDownStatusVo();
			status = it.next();
			// status.setMac(status.getMac());
			status.setPolicyNumber(policyNumber);
			Long finishedNum = downStatusService.getFinishedNum(status);
			vo.setStbMac(status.getMac());
			vo.setFinishedNum(finishedNum);
			vo.setNotFinishedNum(mediaNum - finishedNum);
			vo.setTypeId(status.getTypeId());
			vo.setTypeName(status.getTypeName());
			vo.setGroupId(status.getGroupId());
			vo.setGroupName(status.getGroupName());
			vo.setShopNo(status.getShopNo());
			vo.setShopName(status.getShopName());
			downStatus.add(vo);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mediaDownStatus() throws Exception {
		BoxDownStatus status = new BoxDownStatus();
		status.setPolicyNumber(policyNumber);
		status.setIsFinished(isFinished);
		status.setVideoName(videoName);
		List<BoxDownStatus> logList = downStatusService.getList(status);
		log.debug("there are {} records about media {}", logList.size(),
				videoName);
		Iterator<BoxDownStatus> it = logList.iterator();
		MediaDownStatusVo vo = null;
		mediaDownStatus = new Vector<MediaDownStatusVo>();
		while (it.hasNext()) {
			vo = new MediaDownStatusVo();
			status = it.next();
			vo.setStbMac(status.getMac());

			Long total = status.getTotalSize();
			this.videoSize = total;
			if (total != null) {
				if (total == -1L) {
					vo.setPercent("0%");
				} else if (total == 0L) {
					vo.setPercent("100%");
				} else {
					Long done = status.getDoneSize();
					if (done != null) {
						if (done != 0L) {
							if ("Y".equals(status.getIsFinished())) {
								vo.setPercent("100%");
							} else {
								vo.setPercent((int) (done.doubleValue()
												/ total * 100) + "%");
							}
							if (status.getEndTime() != null
									&& status.getStartTime() != null) {
								long spendTime = status.getEndTime().getTime()
										- status.getStartTime().getTime();
								if (spendTime != 0) {
									vo.setSpeed(total / spendTime);
								}
							}
						} else {
							vo.setPercent("0%");
						}
					}
				}
			}

			vo.setStartTime(status.getStartTime());
			vo.setEndTime(status.getEndTime());

			mediaDownStatus.add(vo);
		}
		return "media";
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String mediaDownDetail() throws Exception {
		setLayout(JSON);
		BoxDownStatus temp = new BoxDownStatus();
		temp.setMac(mac);
		temp.setPolicyNumber(policyNumber);
		boxDownStatus = downStatusService.getList(temp);
		return "details";
	}

	/**
	 * @return the downStatusService
	 */
	public BoxDownStatusService getDownStatusService() {
		return downStatusService;
	}

	/**
	 * @param downStatusService
	 *            the downStatusService to set
	 */
	public void setDownStatusService(BoxDownStatusService downStatusService) {
		this.downStatusService = downStatusService;
	}

	/**
	 * @return the downStatus
	 */
	public List<BoxDownStatusVo> getDownStatus() {
		return downStatus;
	}

	/**
	 * @param downStatus
	 *            the downStatus to set
	 */
	public void setDownStatus(List<BoxDownStatusVo> downStatus) {
		this.downStatus = downStatus;
	}

	/**
	 * @return the mediaDownStatus
	 */
	public List<MediaDownStatusVo> getMediaDownStatus() {
		return mediaDownStatus;
	}

	/**
	 * @param mediaDownStatus
	 *            the mediaDownStatus to set
	 */
	public void setMediaDownStatus(List<MediaDownStatusVo> mediaDownStatus) {
		this.mediaDownStatus = mediaDownStatus;
	}

	/**
	 * @return the boxDownStatus
	 */
	public List<BoxDownStatus> getBoxDownStatus() {
		return boxDownStatus;
	}

	/**
	 * @param boxDownStatus
	 *            the boxDownStatus to set
	 */
	public void setBoxDownStatus(List<BoxDownStatus> boxDownStatus) {
		this.boxDownStatus = boxDownStatus;
	}

	/**
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * @param mac
	 *            the mac to set
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * @return the policyNumber
	 */
	public String getPolicyNumber() {
		return policyNumber;
	}

	/**
	 * @param policyNumber
	 *            the policyNumber to set
	 */
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	/**
	 * @return the policyId
	 */
	public Long getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId
	 *            the policyId to set
	 */
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	/**
	 * @return the mediaNum
	 */
	public int getMediaNum() {
		return mediaNum;
	}

	/**
	 * @param mediaNum
	 *            the mediaNum to set
	 */
	public void setMediaNum(int mediaNum) {
		this.mediaNum = mediaNum;
	}

	/**
	 * @return the isFinished
	 */
	public String getIsFinished() {
		return isFinished;
	}

	/**
	 * @param isFinished
	 *            the isFinished to set
	 */
	public void setIsFinished(String isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * @return the videoName
	 */
	public String getVideoName() {
		return videoName;
	}

	/**
	 * @param videoName
	 *            the videoName to set
	 */
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	/**
	 * @return the videoSize
	 */
	public Long getVideoSize() {
		return videoSize;
	}

	/**
	 * @param videoSize the videoSize to set
	 */
	public void setVideoSize(Long videoSize) {
		this.videoSize = videoSize;
	}

	/**
	 * @return the policyMediaService
	 */
	public PolicyMediaService getPolicyMediaService() {
		return policyMediaService;
	}

	/**
	 * @param policyMediaService the policyMediaService to set
	 */
	public void setPolicyMediaService(PolicyMediaService policyMediaService) {
		this.policyMediaService = policyMediaService;
	}

}
