package com.yunling.mediacenter.web.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yunling.commons.utils.PageUtil;
import com.yunling.mediacenter.db.model.BoxPlayLog;
import com.yunling.mediacenter.db.model.GroupDownStatusReport;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.Statistics;
import com.yunling.mediacenter.db.model.StbDownStatusReport;
import com.yunling.mediacenter.db.model.VideoPlayTimes;
import com.yunling.mediacenter.db.service.BoxDownStatusService;
import com.yunling.mediacenter.db.service.BoxPerfLogService;
import com.yunling.mediacenter.db.service.BoxPlayLogService;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

@Results({ @Result(name = "stb", location = "stb.action", type = "redirect") })
public class ReportListAction extends AbstractUserAwareAction {
	private BoxPerfLogService perflogservice;
	private BoxPlayLogService playlogservice;
	private BoxDownStatusService downStatusService;
	private GroupTypeService groupTypeService;
	private GroupsService groupsService;
	private String startTime;
	private String endTime;
	private Long typeId;
	private Long groupId;
	private String policyNumber;
	private String mac;
	private String mediaType;
	private String name;

	private InputStream inputStream;
	private String contentLength;
	private String contentDisposition;

	public String execute() throws Exception {
		return SUCCESS;
	}

	public String perfList() {
		Integer sum = perflogservice.selectPerfCount();
		Integer page = 0;
		try {
			page = Integer.parseInt(ServletActionContext.getRequest()
					.getParameter("page"));
		} catch (Exception e) {
			page = 1;
		}
		request.put("map", perflogservice.list(page, sum));
		return "perf";
	}

	/**
	 * video play times statistics by group
	 * 
	 * @return
	 */
	public String groupPlayList() {
		if (typeId == null) {
			typeId = 0L;
		}
		if (groupId == null) {
			groupId = 0L;
		}
		if (groupId != null && groupId != 0L) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", groupId);
			int total = playlogservice.countList(groupId);
			PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
					webConfig.getPageSize());
			map.put("begin", pageUtil.getBegin());
			map.put("end", pageUtil.getEnd());
			map.put("startDate", startTime);
			map.put("endDate", endTime);
			List<Statistics> list = playlogservice.list(map);
			// ActionContext.getContext().put("list",list);
			request.put("list", list);
			request.put("pageBean", pageUtil);
		}
		this.addJavascriptRef("jqueryui");
		this.addCssRef("jqueryui");
		return "groupplay";
	}

	/**
	 * 
	 * @return create by L.J. on 6/14/2011
	 */
	public String groupDownStatus() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		// map.put("groupId", groupId);
		map.put("policyNumber", policyNumber);
		int total = downStatusService.countBy(map);
		log.debug("typeId: {}", typeId);
		// log.debug("groupId: {}", groupId);
		log.debug("total: {}", total);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<GroupDownStatusReport> downStatusList = downStatusService
				.getReport(map);
		request.put("downStatusList", downStatusList);
		request.put("pageBean", pageUtil);
		return "downreport";
	}

	/**
	 * export data create by L.J. on 7/26/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportDownStatus() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		// map.put("groupId", groupId);
		map.put("policyNumber", policyNumber);
		List<GroupDownStatusReport> downStatusList = downStatusService
				.allDownStatus(map);
		// exportFileName = date + "-" + str + ".xls";
		HSSFWorkbook wbook = null;
		// WritableWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getDownStatusXls(downStatusList);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=TopLevleDownloadStatusReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * create by L.J. on 7/26/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getDownStatusXls(List<GroupDownStatusReport> list)
			throws Exception {
		HSSFWorkbook wbook = null;
		try {
			// wbook = Workbook.createWorkbook(bout);
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("downloadStatus");
			GroupType type = new GroupType();
			if (typeId != null && typeId != 0L) {
				type.setTypeId(typeId);
				type = groupTypeService.getGroupType(type);
			}
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("Top Level:");
			srow.createCell(1).setCellValue(type.getTypeName());
			srow.createCell(2).setCellValue("Policy Version:");
			srow.createCell(3).setCellValue(policyNumber);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("policy.version"),
					this.getText("stb.download.finished.num"),
					this.getText("stb.num"),
					this.getText("stb.download.finished.percent") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<GroupDownStatusReport> it = list.iterator();
			GroupDownStatusReport entity = null;
			float perc = 0.0f;
			int finishedStb, notFinishedStb;
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getPolicyNumber());
				if (entity.getFinishedStb() == null) {
					finishedStb = 0;
				} else {
					finishedStb = entity.getFinishedStb().intValue();
				}
				srow.createCell(1).setCellValue(finishedStb);
				if (entity.getNotfinishedStb() == null) {
					notFinishedStb = 0;
				} else {
					notFinishedStb = entity.getNotfinishedStb().intValue();
				}
				srow.createCell(2).setCellValue(finishedStb + notFinishedStb);
				perc = (float) (finishedStb * 1.0
						/ (finishedStb + notFinishedStb) * 100);
				srow.createCell(3).setCellValue(perc + "%");

				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	/**
	 * 
	 * @return create by L.J. on 6/14/2011
	 */
	public String groupDownStatus2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		// map.put("groupId", groupId);
		map.put("policyNumber", policyNumber);
		int total = downStatusService.countBy2(map);
		log.debug("typeId: {}", typeId);
		// log.debug("groupId: {}", groupId);
		log.debug("total: {}", total);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<GroupDownStatusReport> downStatusList = downStatusService
				.getReport2(map);
		request.put("downStatusList", downStatusList);
		request.put("pageBean", pageUtil);
		return "downreport2";
	}

	/**
	 * create by L.J. on 8/19/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportDownStatus2() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", typeId);
		map.put("policyNumber", policyNumber);
		List<GroupDownStatusReport> downStatusList = downStatusService
				.allDownStatus2(map);
		HSSFWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getDownStatusXls2(downStatusList);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=SecondLevleDownloadStatusReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * create by L.J. on 8/19/2011
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getDownStatusXls2(List<GroupDownStatusReport> list)
			throws Exception {
		HSSFWorkbook wbook = null;
		try {
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("downloadStatus");
			GroupType type = new GroupType();
			if (typeId != null && typeId != 0L) {
				type.setTypeId(typeId);
				type = groupTypeService.getGroupType(type);
			}
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("Top Level:");
			srow.createCell(1).setCellValue(type.getTypeName());
			srow.createCell(2).setCellValue("Policy Version:");
			srow.createCell(3).setCellValue(policyNumber);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("policy.version"),
					this.getText("second.level"),
					this.getText("stb.download.finished.num"),
					this.getText("stb.num"),
					this.getText("stb.download.finished.percent") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<GroupDownStatusReport> it = list.iterator();
			GroupDownStatusReport entity = null;
			float perc = 0.0f;
			int finishedStb, notFinishedStb;
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getPolicyNumber());
				srow.createCell(1).setCellValue(entity.getGroupName());
				if (entity.getFinishedStb() == null) {
					finishedStb = 0;
				} else {
					finishedStb = entity.getFinishedStb().intValue();
				}
				srow.createCell(2).setCellValue(finishedStb);
				if (entity.getNotfinishedStb() == null) {
					notFinishedStb = 0;
				} else {
					notFinishedStb = entity.getNotfinishedStb().intValue();
				}
				srow.createCell(3).setCellValue(finishedStb + notFinishedStb);
				perc = (float) (finishedStb * 1.0
						/ (finishedStb + notFinishedStb) * 100);
				srow.createCell(4).setCellValue(perc + "%");

				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	/**
	 * 
	 * @return create by L.J. on 6/15/2011
	 */
	public String stbDownStat() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (typeId == null) {
			typeId = 0L;
		}
		if (groupId == null) {
			groupId = 0L;
		}
		map.put("typeId", typeId);
		map.put("groupId", groupId);
		map.put("policyNumber", policyNumber);
		int total = downStatusService.countForDownStat(map);
		log.debug("typeId: {}", typeId);
		log.debug("groupId: {}", groupId);
		log.debug("total: {}", total);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<StbDownStatusReport> downStatusList = downStatusService
				.stbDownStat(map);
		request.put("downStatusList", downStatusList);
		request.put("pageBean", pageUtil);
		return "stbdownstat";
	}

	/**
	 * create by L.J. on 8/24/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportStbDownStat() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		if (typeId == null) {
			typeId = 0L;
		}
		if (groupId == null) {
			groupId = 0L;
		}
		map.put("typeId", typeId);
		map.put("groupId", groupId);
		map.put("policyNumber", policyNumber);
		List<StbDownStatusReport> downStatusList = downStatusService
				.stbAllDownStat(map);
		HSSFWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getAllDownStatusXls(downStatusList);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=StbDownloadStatusReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * create by L.J. on 8/24/2011
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getAllDownStatusXls(List<StbDownStatusReport> list)
			throws Exception {
		HSSFWorkbook wbook = null;
		try {
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("downloadStatus");
			GroupType type = new GroupType();
			if (typeId != null && typeId != 0L) {
				type.setTypeId(typeId);
				type = groupTypeService.getGroupType(type);
			}
			Groups group = new Groups();
			if (groupId != null && groupId != 0L) {
				group.setGroupId(groupId);
				group = groupsService.getGroup(group);
			}
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("Top Level:");
			srow.createCell(1).setCellValue(type.getTypeName());
			srow.createCell(2).setCellValue("Secend Level:");
			srow.createCell(3).setCellValue(group.getGroupName());
			srow.createCell(4).setCellValue("Policy Version:");
			srow.createCell(5).setCellValue(policyNumber);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("policy.version"),
					this.getText("stb.device"), this.getText("start.time"),
					this.getText("end.time"),
					this.getText("policy.media.download.status") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<StbDownStatusReport> it = list.iterator();
			StbDownStatusReport entity = null;
			Date time1 = null;
			Date time2 = null;
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getPolicyNumber());
				srow.createCell(1).setCellValue(entity.getMac());
				time1 = entity.getStartTime();
				if (time1 != null) {
					srow.createCell(2).setCellValue(format.format(time1));
				} else {
					srow.createCell(2).setCellValue("");
				}
				time2 = entity.getEndTime();
				if (time2 != null) {
					srow.createCell(3).setCellValue(format.format(time2));
				} else {
					srow.createCell(3).setCellValue("");
				}
				srow.createCell(4).setCellValue(
						this.getText(entity.getStatus()));
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	/**
	 * 
	 * @return create by L.J. on 6/16/2011
	 */
	public String playHistoryReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		map.put("beginTime", startTime);
		map.put("endTime", endTime);
		map.put("mediaType", mediaType);
		log.debug("start: {}, end: {}", startTime, endTime);
		int total = playlogservice.countBy(map);
		log.debug("total: {}", total);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<BoxPlayLog> playHistReport = playlogservice.getPlayHistReport(map);
		request.put("playHistReport", playHistReport);
		request.put("pageBean", pageUtil);
		return "playhistory";
	}

	/**
	 * create by L.J. on 8/25/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportPlayHistoryReport() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		map.put("beginTime", startTime);
		map.put("endTime", endTime);
		List<BoxPlayLog> playHistReport = playlogservice
				.getAllPlayHistReport(map);
		HSSFWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getAllPlayHistXls(playHistReport);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=StbPlayHistoryReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * create by L.J. on 8/25/2011
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getAllPlayHistXls(List<BoxPlayLog> list)
			throws Exception {
		HSSFWorkbook wbook = null;
		try {
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("PlayHistory");
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("MAC:");
			srow.createCell(1).setCellValue(mac);
			srow.createCell(2).setCellValue("Start Time:");
			srow.createCell(3).setCellValue(startTime);
			srow.createCell(4).setCellValue("End Time:");
			srow.createCell(5).setCellValue(endTime);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("MAC"), this.getText("time"),
					this.getText("file.name"),
					this.getText("file.origin.name"),
					this.getText("file.align") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<BoxPlayLog> it = list.iterator();
			BoxPlayLog entity = null;
			Date logTime = null;
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getMac());
				logTime = entity.getLogTime();
				if (logTime != null) {
					srow.createCell(1).setCellValue(format.format(logTime));
				} else {
					srow.createCell(1).setCellValue("");
				}
				srow.createCell(2).setCellValue(entity.getVideoName());
				srow.createCell(3).setCellValue(entity.getOriginName());
				srow.createCell(4).setCellValue(entity.getName());
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	/**
	 * 
	 * @return create by L.J. on 6/16/2011
	 */
	public String playTimesReport() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		map.put("beginTime", startTime);
		map.put("endTime", endTime);
		map.put("name", name);
		log.debug("start: {}, end: {}", startTime, endTime);
		int total = playlogservice.countBy2(map);
		log.debug("total: {}", total);
		PageUtil pageUtil = new PageUtil(getSingleParam("page"), total,
				webConfig.getPageSize());
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		List<VideoPlayTimes> playTimesReport = playlogservice
				.playTimesReport(map);
		request.put("playTimesReport", playTimesReport);
		request.put("pageBean", pageUtil);
		return "playtimes";
	}

	/**
	 * create by L.J. on 8/25/2011
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportPlayTimesReport() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mac", mac);
		map.put("beginTime", startTime);
		map.put("endTime", endTime);
		List<VideoPlayTimes> playTimesReport = playlogservice
				.getAllPlayTimesReport(map);
		HSSFWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getAllPlayTimesXls(playTimesReport);
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=StbPlayTimesReport-"
				+ date + ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * create by L.J. on 8/25/2011
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getAllPlayTimesXls(List<VideoPlayTimes> list)
			throws Exception {
		HSSFWorkbook wbook = null;
		try {
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("PlayTimes");
			HSSFRow srow = wsheet.createRow(0);
			srow.createCell(0).setCellValue("MAC:");
			srow.createCell(1).setCellValue(mac);
			srow.createCell(2).setCellValue("Start Time:");
			srow.createCell(3).setCellValue(startTime);
			srow.createCell(4).setCellValue("End Time:");
			srow.createCell(5).setCellValue(endTime);
			srow = wsheet.createRow(1);
			String[] title = { this.getText("Mac"), this.getText("file.name"),
					this.getText("file.origin.name"),
					this.getText("file.align"),
					this.getText("video.paly.times") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 2;
			Iterator<VideoPlayTimes> it = list.iterator();
			VideoPlayTimes entity = null;
			while (it.hasNext()) {
				entity = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(entity.getMac());
				srow.createCell(1).setCellValue(entity.getVideoName());
				srow.createCell(2).setCellValue(entity.getOriginName());
				srow.createCell(3).setCellValue(entity.getName());
				srow.createCell(4).setCellValue(entity.getTimes());
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	public String standAlone() {
		return "stb";
	}

	public BoxPerfLogService getPerflogservice() {
		return perflogservice;
	}

	public void setPerflogservice(BoxPerfLogService perflogservice) {
		this.perflogservice = perflogservice;
	}

	public BoxPlayLogService getPlaylogservice() {
		return playlogservice;
	}

	public void setPlaylogservice(BoxPlayLogService playlogservice) {
		this.playlogservice = playlogservice;
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
	 * @return the groupTypeService
	 */
	public GroupTypeService getGroupTypeService() {
		return groupTypeService;
	}

	/**
	 * @param groupTypeService
	 *            the groupTypeService to set
	 */
	public void setGroupTypeService(GroupTypeService groupTypeService) {
		this.groupTypeService = groupTypeService;
	}

	/**
	 * @return the groupsService
	 */
	public GroupsService getGroupsService() {
		return groupsService;
	}

	/**
	 * @param groupsService
	 *            the groupsService to set
	 */
	public void setGroupsService(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the typeId
	 */
	public Long getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId
	 *            the typeId to set
	 */
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the groupId
	 */
	public Long getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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
	 * @return the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType
	 *            the mediaType to set
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	public String getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}
}
