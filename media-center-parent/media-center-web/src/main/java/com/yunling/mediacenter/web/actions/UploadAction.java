package com.yunling.mediacenter.web.actions;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.yunling.mediacenter.db.model.City;
import com.yunling.mediacenter.db.model.District;
import com.yunling.mediacenter.db.model.GroupType;
import com.yunling.mediacenter.db.model.Groups;
import com.yunling.mediacenter.db.model.Province;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.CityService;
import com.yunling.mediacenter.db.service.DistrictService;
import com.yunling.mediacenter.db.service.GroupTypeService;
import com.yunling.mediacenter.db.service.GroupsService;
import com.yunling.mediacenter.db.service.ProvinceService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;

@Results( { @Result(name = "redirect-uploadFile", location = "upload!uploadFile.action", type = "redirect") })
public class UploadAction extends AbstractUserAwareAction {
	private final static String UPLOADDIR = "/upload"; // upload path
	private StbService stbService;
	private ProvinceService provService;
	private CityService cityService;
	private DistrictService districtService;
	private GroupsService groupsService;
	private GroupTypeService groupTypeService;
	private File file;
	private String fileFileName;
	private String fileContentType;
	private String stbMac;
	private String provinceId;
	private String cityId;
	private String districtId;
	private String stbStatus;
	private String activePolicy;
	private String shopNo;
	private String shopName;
	private String urlPath;
	private HttpServletRequest rawRequest = ServletActionContext.getRequest();
	private Long groupId;
	private Long typeId;
	private int pageNums;
	private int page;
	private int pageSize;
	private InputStream inputStream;
	private String contentLength;
	private String contentDisposition;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 
	 * @return Import informations of STBs form a XML file
	 * @throws Exception
	 */
	public String uploadFile() throws Exception {
		int start = fileFileName.lastIndexOf('.');
		BufferedInputStream bis = null;
		OutputStream out = null;
		File uploadFile = null;
		byte[] buffer = new byte[4 * 1024];
		int readBytes;

		if (!fileFileName.substring(start + 1).equalsIgnoreCase("xls")) {
			request.put("msg", this.getText("FileError"));
		} else {
			InputStream inStream = null;
			try {
				inStream = new FileInputStream(file);
				bis = new BufferedInputStream(inStream);
				uploadFile = new File(ServletActionContext.getServletContext()
						.getRealPath(UPLOADDIR), fileFileName);
				out = new FileOutputStream(uploadFile);
				while ((readBytes = bis.read(buffer)) > 0) {
					out.write(buffer, 0, readBytes);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Close Input/Output Stream
				IOUtils.closeQuietly(bis);
				IOUtils.closeQuietly(out);
				IOUtils.closeQuietly(inStream);
			}
			// Read file, update Data
			this.updateData(uploadFile);
			// delete data file
			uploadFile.deleteOnExit();
		}
		
		return "uploadFile";
	}

	/**
	 * export data
	 * 
	 * @return
	 * @throws Exception
	 */
	public String exportData() throws Exception {
		String date = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new java.util.Date());
		String str = stbStatus;
		if ("".equals(str) || str == null) {
			str = "all";
		}
		// exportFileName = date + "-" + str + ".xls";
		HSSFWorkbook wbook = null;
		// WritableWorkbook wbook = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try {
			wbook = this.getXls();
			wbook.write(bout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bout);
		}
		this.contentLength = String.valueOf(bout.size());
		this.contentDisposition = "attachment;filename=" + date + "-" + str
				+ ".xls";
		if (bout.size() > 0) {
			this.inputStream = new ByteArrayInputStream(bout.toByteArray());
		}
		return "excel";
	}

	/**
	 * 
	 * @param os
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getXls() throws Exception {
		HSSFWorkbook wbook = null;
		try {
			// wbook = Workbook.createWorkbook(bout);
			wbook = new HSSFWorkbook();
			HSSFSheet wsheet = wbook.createSheet("STB Infos");
			HSSFRow srow = wsheet.createRow(0);
			String[] title = { "MAC", this.getText("Province"),
					this.getText("City"), this.getText("District"),
					this.getText("Address"), this.getText("top.level"),
					this.getText("second.level"), this.getText("shop.no"),
					this.getText("shop.name"), this.getText("stb.contacts"),
					this.getText("stb.phone"), this.getText("PolicyVersion"), 
					this.getText("terminal.ip"), this.getText("Status"), 
					this.getText("stb.media.download.status"), 
					this.getText("stb.last.offline.time") };
			HSSFCell rcell = null;
			// set Excel table header
			for (int i = 0; i < title.length; i++) {
				rcell = srow.createCell(i);
				rcell.setCellValue(title[i]);
			}
			int row = 1;
			List<Stb> stbs = this.stbList();
			Iterator<Stb> it = stbs.iterator();
			Stb stb = null;
			Groups groups = null;
			GroupType groupType = null;
			Long groupId = 0L;
			while (it.hasNext()) {
				stb = it.next();
				srow = wsheet.createRow(row);
				srow.createCell(0).setCellValue(stb.getStbMac());
				srow.createCell(1).setCellValue(stb.getProvinceName());
				srow.createCell(2).setCellValue(stb.getCityName());
				srow.createCell(3).setCellValue(stb.getDistrictName());
				srow.createCell(4).setCellValue(stb.getAddrDetail());
				groups = new Groups();
				groupId = stb.getGroupId();
				if (groupId != null && groupId != 0L) {
					groups.setGroupId(groupId);
					groups.setTypeId(0L);
					groups = groupsService.getGroup(groups);
					if (groups != null) {
						groupType = new GroupType();
						groupType.setTypeId(groups.getTypeId());
						groupType = groupTypeService.getGroupType(groupType);
						if (groupType != null) {
							srow.createCell(5).setCellValue(
									groupType.getTypeName());
						}
					}
				}
				srow.createCell(6).setCellValue(stb.getGroupName());
				srow.createCell(7).setCellValue(stb.getShopNo());
				srow.createCell(8).setCellValue(stb.getShopName());
				srow.createCell(9).setCellValue(stb.getContacts());
				srow.createCell(10).setCellValue(stb.getPhone());
				srow.createCell(11).setCellValue(stb.getActivePolicy());
				srow.createCell(12).setCellValue(stb.getTerminalIp());
				srow.createCell(13).setCellValue(this.getText(stb.getStbStatus()));
				if(stb.getActivePolicyFailedNum()!= null && stb.getActivePolicyFailedNum() > 0) {
					srow.createCell(14).setCellValue(this.getText("stb.media.download.part"));
				} else {
					srow.createCell(14).setCellValue(this.getText("stb.media.download.total"));
				}
				if(stb.getLastOfflineTime() != null) {
					srow.createCell(15).setCellValue(stb.getLastOfflineTime());
				}
				row++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wbook;
	}

	/**
	 * Read the uploaded file, and update to database
	 * 
	 * @param uploadFile
	 * @param response
	 * @throws Exception
	 */
	private void updateData(File uploadFile) throws Exception {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		int cols = 0, rows = 0;
		List<String[]> list = null;
		HSSFWorkbook book = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		String[] strs = null;
		try {
			fis = new FileInputStream(uploadFile);
			bis = new BufferedInputStream(fis);
			book = new HSSFWorkbook(bis);
			sheet = book.getSheetAt(0);
			row = sheet.getRow(0);
			cols = row.getLastCellNum();
			rows = sheet.getPhysicalNumberOfRows();
			if (cols != 11) {
				rawRequest.setAttribute("msg", this.getText("FileError"));
				return;
			}
			if (rows <= 1) {
				rawRequest.setAttribute("msg", this.getText("FileEmpty"));
				return;
			}
			list = new ArrayList<String[]>();
			for (int j = 1; j < rows; j++) {
				strs = new String[cols + 1];
				for (int i = 0; i < cols; i++) {
					cell = sheet.getRow(j).getCell(i);
					if (cell != null) {
						strs[i] = cell.getStringCellValue();
					} else {
						strs[i] = null;
					}
				}
				switch (this.update(strs)) {
				case 'u':
					break;
				case 'e':
					strs[cols] = "mac.empty";
					list.add(strs);
					break;
				case 'p':
					strs[cols] = "province.wrong";
					list.add(strs);
					break;
				case 'c':
					strs[cols] = "city.wrong";
					list.add(strs);
					break;
				case 'd':
					strs[cols] = "district.wrong";
					list.add(strs);
					break;
				case 't':
					strs[cols] = "type.wrong";
					list.add(strs);
					break;
				case 'g':
					strs[cols] = "group.wrong";
					list.add(strs);
					break;
				case 'x':
					strs[cols] = "mac.conflict";
					list.add(strs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(fis);
		}
		String msg = this.getText("Upload") + this.getText("Success") + ": "
				+ fileFileName + "<br/>" + this.getText("Update")
				+ this.getText("Records") + this.getText("Number") + ": "
				+ (rows - 1) + "<br/>" + this.getText("Update")
				+ this.getText("Failure") + this.getText("Number") + ": "
				+ list.size() + "<br/>";
		if (list.size() > 0) {
			msg = msg + this.getText("Update") + this.getText("Failure")
					+ this.getText("Records") + ": <br/>";
			rawRequest.setAttribute("list", list);
		}
		rawRequest.setAttribute("msg", msg);
	}

	/**
	 * Update STB infos
	 * 
	 * @param strs
	 */
	private char update(String[] strs) {
		String provinceId = null;
		String cityId = null;
		String districtId = null;
		Long typeId = 0L;
		Long groupId = 0L;
		String mac = StringUtils.trim(strs[0]);
		if (!StringUtils.isEmpty(mac)) {
			Stb stb = new Stb();
			stb.setStbMac(mac);
			// get the province ID
			if (strs[1] != null && strs[1] != "") {
				Province province = new Province();
				province.setProvinceName(strs[1].trim());
				province = provService.findProvinceByName(province);
				if (province != null) {
					provinceId = province.getProvinceId();
				} else {
					return 'p';
				}
			}
			// get the city ID
			if (strs[2] != null && strs[2] != "") {
				City city = new City();
				city.setCityName(strs[2].trim());
				city.setProvinceId(provinceId);
				city = cityService.findByCityName(city);
				if (city != null) {
					cityId = city.getCityId();
				} else {
					return 'c';
				}
			}
			// get the district ID
			if (strs[3] != null && strs[3] != "") {
				District district = new District();
				district.setDistrictName(strs[3].trim());
				district.setCityId(cityId);
				district = districtService.findByDistrictName(district);
				if (district != null) {
					districtId = district.getDistrictId();
				} else {
					return 'd';
				}
			}
			stb.setProvinceId(provinceId);
			stb.setCityId(cityId);
			stb.setDistrictId(districtId);
			// set address
			stb.setAddrDetail(StringUtils.trim(strs[4]));
			// get the group type ID
			if (strs[5] != null && strs[5] != "") {
				GroupType groupType = new GroupType();
				groupType.setTypeId(0L);
				groupType.setTypeName(strs[5].trim());
				groupType = groupTypeService.getGroupType(groupType);
				if (groupType != null) {
					typeId = groupType.getTypeId();
				} else {
					return 't';
				}
			}
			stb.setTypeId(typeId);
			// get the group ID
			if (strs[6] != null && strs[6] != "") {
				Groups groups = new Groups();
				groups.setGroupId(0L);
				groups.setTypeId(typeId);
				groups.setGroupName(strs[6].trim());
				groups = groupsService.getGroup(groups);
				if (groups != null) {
					groupId = groups.getGroupId();
				} else {
					return 'g';
				}
			}
			stb.setGroupId(groupId);
			stb.setShopNo(getArrayValue(strs, 7));
			stb.setShopName(getArrayValue(strs, 8));
			stb.setContacts(getArrayValue(strs, 9));
			stb.setPhone(getArrayValue(strs, 10));
			stb.setStbDisabled("f");
			Stb existingStb = stbService.findByMac(mac);
			if (existingStb == null) {
				if (typeId == 0L) {
					stb.setTypeId(-1L);
				}
				stb.setStbStatus("notinstalled");
				stbService.insert(stb);
			} else {
				/*
				 * Modified on 2011/3/2 stb.setStbStatus(""); if
				 * (StringUtils.equals("notinstalled",
				 * existingStb.getStbStatus())) { stbService.updateInfos(stb); }
				 */
				return 'x';
				// modified on 2011/2/28, not updating existing STB
				// stbService.updateInfos(stb);
			}

			return 'u';
		}
		return 'e';
	}

	private String getArrayValue(String[] strs, int i) {
		if (strs.length > i) {
			if (!StringUtils.isEmpty(strs[i])) {
				return strs[i];
			}
		}
		return null;
	}

	/**
	 * get STBs by conditions
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<Stb> stbList() throws Exception {
		Stb stb = new Stb();
		stb.setStbStatus(stbStatus);
		/*
		 * if( !("".equals(districtId) || districtId == null) ) {
		 * stb.setDistrictId(districtId); } else if( !("".equals(cityId) ||
		 * cityId == null) ) { stb.setCityId(cityId); } else if(
		 * !("".equals(provinceId) || provinceId == null) ) {
		 * stb.setProvinceId(provinceId); }
		 */
		stb.setStbMac(stbMac);
		stb.setStbStatus(stbStatus);
		// stb.setDistrictId(districtId);
		// stb.setCityId(cityId);
		// stb.setProvinceId(provinceId);
		stb.setActivePolicy(activePolicy);
		stb.setShopNo(shopNo);
		stb.setShopName(shopName);
		log.debug("Type {}, Group {}", typeId, groupId);
		if (typeId == null) {
			typeId = 0L;
		}
		if (groupId == null) {
			groupId = 0L;
		}
		stb.setTypeId(typeId);
		stb.setGroupId(groupId);
		if (groupId != null && groupId != 0L) {
			stb.setGroupId(groupId);
		}

		return stbService.searchStbs(stb, 0, 0, this.getCurrentUserId());
	}

	/**
	 * @return the stbService
	 */
	public StbService getStbService() {
		return stbService;
	}

	/**
	 * @param stbService
	 *            the stbService to set
	 */
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}

	/**
	 * @return the provinceService
	 */
	public ProvinceService getProvService() {
		return provService;
	}

	/**
	 * @param provinceService
	 *            the provinceService to set
	 */
	public void setProvService(ProvinceService provService) {
		this.provService = provService;
	}

	/**
	 * @return the cityService
	 */
	public CityService getCityService() {
		return cityService;
	}

	/**
	 * @param cityService
	 *            the cityService to set
	 */
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	/**
	 * @return the districtService
	 */
	public DistrictService getDistrictService() {
		return districtService;
	}

	/**
	 * @param districtService
	 *            the districtService to set
	 */
	public void setDistrictService(DistrictService districtService) {
		this.districtService = districtService;
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
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the provinceId
	 */
	public String getProvinceId() {
		return provinceId;
	}

	/**
	 * @param provinceId
	 *            the provinceId to set
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	/**
	 * @return the cityId
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * @param cityId
	 *            the cityId to set
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * @return the districtId
	 */
	public String getDistrictId() {
		return districtId;
	}

	/**
	 * @param districtId
	 *            the districtId to set
	 */
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	/**
	 * @return the stbStatus
	 */
	public String getStbStatus() {
		return stbStatus;
	}

	/**
	 * @param stbStatus
	 *            the stbStatus to set
	 */
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}

	/**
	 * @return the stbMac
	 */
	public String getStbMac() {
		return stbMac;
	}

	/**
	 * @param stbMac
	 *            the stbMac to set
	 */
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
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
	 * @return the activePolicy
	 */
	public String getActivePolicy() {
		return activePolicy;
	}

	/**
	 * @param activePolicy
	 *            the activePolicy to set
	 */
	public void setActivePolicy(String activePolicy) {
		this.activePolicy = activePolicy;
	}

	/**
	 * @return the shopNo
	 */
	public String getShopNo() {
		return shopNo;
	}

	/**
	 * @param shopNo
	 *            the shopNo to set
	 */
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName
	 *            the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	 * @return the urlPath
	 */
	public String getUrlPath() {
		return urlPath;
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

	/**
	 * @param urlPath
	 *            the urlPath to set
	 */
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public String getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

}
