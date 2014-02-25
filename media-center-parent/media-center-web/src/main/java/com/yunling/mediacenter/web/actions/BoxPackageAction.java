package com.yunling.mediacenter.web.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.db.model.BoxPackage;
import com.yunling.mediacenter.db.model.ModuleFile;
import com.yunling.mediacenter.db.model.PackageFiles;
import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.db.service.BoxPackageService;
import com.yunling.mediacenter.db.service.ModuleFileService;
import com.yunling.mediacenter.db.service.PackageFilesService;
import com.yunling.mediacenter.db.service.StbService;
import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.mediacenter.web.Constants;
import com.yunling.mediacenter.web.SessionUser;
import com.yunling.mediacenter.web.actions.vo.BoxPackageVo;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;
import com.yunling.mediacenter.web.struts2.SelectUtils;

@Results({@Result(name="redirect-list", location="box-package!list.action",type="redirect")})
public class BoxPackageAction extends AbstractUserAwareAction{
	HttpServletResponse rawResponse = ServletActionContext.getResponse();
	HttpServletRequest  rawRequest  = ServletActionContext.getRequest();
	private BoxPackageService boxPackageService;
	private ModuleFileService moduleFileService; 
	private PackageFilesService packageFilesService;
	private WebControl localService;
	private Map<String, String> mapStatus;
	private BoxPackageVo vo;
	private StbService stbService;
	private String stbMac;
	private List<Stb> stbs;
	private String stbStatus;
	private String activePolicy;
	private String shopNo;
	private String shopName;
	private Long typeId;
	private Long groupId;
	private int pageNums;
	private int page;	
	private int[] pageArr;
	private	Map<String,String> moduleMap;
	private String boxname;
	private String module;
	private String version;
	private Date dateinput;
	private Date dateinput2;
	private long fileid;
	private long id;
	private Logger log = LoggerFactory.getLogger(BoxPackageAction.class);
	public String execute() throws Exception {
		return SUCCESS;
	}
	public String create(){
		return "create";
	}
	public String create2() throws Exception{
		rawResponse.setContentType("text/html;charset=utf-8");
		BoxPackage box = new BoxPackage();
		box.setUpdateAt(new Date());
		box.setDescription(vo.getDescription());
		box.setName(vo.getBoxname());
		boxPackageService.addBoxPackage(box);
//		ActionContext.getContext().put("alert",this.getText("stbpackage.add.success"));
//		ActionContext.getContext().put("location","box-package!list.action");
		request.put("alert",this.getText("stbpackage.add.success"));
		request.put("location","box-package!list.action");
		return "prompt";
	}
	public String list(){
		Integer sum = boxPackageService.selectBoxCount();
		Integer page=0;
			try{
				 page = Integer.parseInt(rawRequest.getParameter("page"));
			}catch(Exception e){
				page=1;
			}
		Map<String, Object> map = boxPackageService.list(page,sum);
//		ActionContext.getContext().put("map",map);
		request.put("map",map);
		return "list";
	}
	public String edit() throws Exception{
		int show =0;
			try {
				show = Integer.parseInt(rawRequest.getParameter("show"));
			} catch (Exception e) {
				show = 0;
			}
		BoxPackage box = boxPackageService.getBoxPackageById(id);
		List<ModuleFile> list = moduleFileService.selectModuleByBoxId(id);
		List<String> status = stbService.getStbStatus();
		if( status != null ) {
			mapStatus = new HashMap<String, String>();
			for(int i=0; i<status.size(); i++) {
				if( !("".equals(status.get(i))) && status.get(i) != null) {
					mapStatus.put(status.get(i).trim(), status.get(i).trim());
				}
			}
		}
		this.getList();
		request.put("list",list);
		request.put("box",box);
		request.put("show",show);
		return "edit";
	}
	public String save() throws Exception{
		rawResponse.setContentType("text/html;charset=utf-8");
		BoxPackage box = new BoxPackage(); 
		box.setDescription(vo.getDescription());
		box.setName(vo.getBoxname());
		box.setId(id);
		boxPackageService.updateBoxPackage(box);
//		ActionContext.getContext().put("alert",this.getText("update.stbpackage.success"));
//		ActionContext.getContext().put("location","box-package!list.action");
		request.put("alert",this.getText("update.stbpackage.success"));
		request.put("location","box-package!list.action");
		return "prompt";
	}
	public String add()throws Exception{
		setBoxname(new String(boxname.getBytes("iso8859-1"),"utf-8"));
		List<ModuleFile> list = moduleFileService.selectModuleByBoxId(id);
//		ActionContext.getContext().put("list",list);
		request.put("list",list);
		Integer sum = moduleFileService.selectModuleCount();
		Integer page=0;
		try{
		   page = Integer.parseInt(ServletActionContext.getRequest().getParameter("page"));
		}catch(Exception e){
			page=1;
		}
		if(module!=null){
			if(module.equals("0")){
				module=null;
			}
		}
		 Map<String,String> selectMaps = SelectUtils.getOptionMap(this,moduleMap);
		 request.put("map", moduleFileService.list(page,sum,module,version,dateinput,dateinput2));
		 request.put("optionMap", selectMaps);
		 this.addJavascriptRef("jqueryui");
		 this.addCssRef("jqueryui");
		 return "modulefile";
	}
	public String del()throws Exception{
		rawResponse.setContentType("text/html;charset=utf-8");
		setBoxname(new String(boxname.getBytes("iso8859-1"),"utf-8"));
		packageFilesService.deletePackFiles(id,fileid);
//		ActionContext.getContext().put("alert",this.getText("remove.file.success"));
//		ActionContext.getContext().put("location","box-package!add.action?id="+id+"&boxname="+boxname);
		request.put("alert",this.getText("remove.file.success"));
		request.put("location","box-package!add.action?id="+id+"&boxname="+boxname);
		return "prompt";
	}
	public String addFile()throws Exception{
		rawResponse.setContentType("text/html;charset=utf-8");
		setBoxname(new String(boxname.getBytes("iso8859-1"),"utf-8"));
		String ids =  rawRequest.getParameter("ids");
		String modules = rawRequest.getParameter("module");
		String [] module = modules.substring(1).split(",");
		String [] ids2= ids.substring(5).split(",");
		PackageFiles pp = new PackageFiles();
		pp.setPackageId(id);
		for(int i=0;i<ids2.length;i++){
			pp.setModule(module[i]);
			pp.setFileId(Long.parseLong(ids2[i]));
			try {
				packageFilesService.add(pp);
			} catch (Exception e) {
//				ActionContext.getContext().put("alert",this.getText("module.file.repeat"));
//				ActionContext.getContext().put("location","box-package!add.action?id="+id+"&boxname="+boxname);
				request.put("alert",this.getText("module.file.repeat"));
				request.put("location","box-package!add.action?id="+id+"&boxname="+boxname);
				return "prompt";
			}
		}
//		ActionContext.getContext().put("alert",this.getText("add.module.file.success"));
//		ActionContext.getContext().put("location","box-package!add.action?id="+id+"&boxname="+boxname);
		request.put("alert",this.getText("add.module.file.success"));
		request.put("location","box-package!add.action?id="+id+"&boxname="+boxname);
		return "prompt";
	}
	public String see()throws Exception{
		List<ModuleFile> list = moduleFileService.selectModuleByBoxId(id);
//		ActionContext.getContext().put("list",list);
		request.put("list",list);
		return "modulefilebyboxId"; 
	}
	public String updateStbPackageId()throws Exception{
		rawResponse.setContentType("text/html;charset=utf-8");
		List<ModuleFile> list = moduleFileService.selectModuleByBoxId(id);
		Map<String,String> map = new HashMap<String,String>();
		String str=null;
		String[] stbmac=null;
		String groupid = null;
		try {
			stbmac = rawRequest.getParameter("arry").split(",");
		} catch (Exception e) {
				groupid = rawRequest.getParameter("groupid");
				boxPackageService.updateGroupsPackageId(id,groupid);
				boxPackageService.updateStbPackageIByGroupId(id,groupid);
				List<Stb> stblist = boxPackageService.getStbByGroupId(groupid);
				List<String> maclist = new ArrayList<String>();
				if(stblist!=null && stblist.size()>0){
					for(Stb stb : stblist){
						maclist.add(stb.getStbMac());
					}
					stbmac = (String[]) maclist.toArray(new String[maclist.size()]);
					for (ModuleFile m : list) {
						map.put("md5",m.getVerflyCode());
						map.put("update_type",m.getModule());
						map.put("file_path",m.getFilePath());
						map.put("version", m.getVersion());
						log.info("=============start call webControll");
						try {
							str = localService.updateSoftware(map,stbmac);
						} catch (Exception e1) {
							log.error("error",e1);
						}
						log.info("=========result localService.updateSoftware{}" +str);
					}
				}
//				ActionContext.getContext().put("alert",this.getText("relevance.group.success"));
//				ActionContext.getContext().put("location","box-package!list.action");
				request.put("alert",this.getText("relevance.group.success"));
				request.put("location","box-package!list.action");
				return "prompt";
		}
		for (String s : stbmac) {
			boxPackageService.updateStbPackageId(id,s);
		}
		for (ModuleFile m : list) {
			map.put("md5",m.getVerflyCode());
			map.put("update_type",m.getModule());
			map.put("file_path",m.getFilePath());
			map.put("version", m.getVersion());
			log.info("=============start call webControll");
			try {
				str = localService.updateSoftware(map,stbmac);
			} catch (Exception e) {
				log.error("error",e);
			}
			log.info("=========result localService.updateSoftware {}" +str);
		}
//		ActionContext.getContext().put("alert",this.getText("relevance.device.success"));
//		ActionContext.getContext().put("location","box-package!list.action");
		request.put("alert",this.getText("relevance.device.success"));
		request.put("location","box-package!list.action");
		return "prompt";
	}
	public String delBoxPackageById(){
		boxPackageService.updatePackageIdForGroups(id);
		boxPackageService.updatePackageIdForStb(id);
		packageFilesService.delBoxPackageFilesByPackageId(id);
		boxPackageService.delBoxPackageById(id);
//		ActionContext.getContext().put("alert",this.getText("boxpackage.delete.success"));
//		ActionContext.getContext().put("location","box-package!list.action");
		request.put("alert",this.getText("boxpackage.delete.success"));
		request.put("location","box-package!list.action");
		return "prompt";
	}
	public String getStb(){
		
		
		return null;
	}
	private void getList() {
		int pageSize = webConfig.getPageSize();
		Stb stb = new Stb();
		stb.setStbMac(stbMac);
		stb.setStbStatus(stbStatus);
		stb.setActivePolicy(activePolicy);
		stb.setShopNo(shopNo);
		stb.setShopName(shopName);
		log.debug("Type {}, Group {}", typeId, groupId);
		if(typeId == null) {
			typeId = 0L;
		}
		if(groupId == null) {
			groupId = 0L;
		}
		stb.setTypeId(typeId);
		stb.setGroupId(groupId);
		int counts = stbService.countStbs(stb, getCurrentUserId()).getCounts();
		log.debug("user Id {}", getCurrentUserId());
		log.debug("stb list size: {}", counts);
		pageNums = counts%pageSize==0 ? counts/pageSize : counts/pageSize+1;
		if(page == 0) {
			page = 1;
		}
		if(pageNums == 0) {
			page = 0;
		} else {
			if(page == 1) {
				pageArr = new int[] {page, page+1, page+2, page+3, page+4};
			} else if(page == 2) {
				pageArr = new int[] {page-1, page, page+1, page+2, page+3};
			} else if(page+1 == pageNums){
				pageArr = new int[] {page-3, page-2, page-1, page, page+1};
			} else if(page == pageNums && page > 3){
				pageArr = new int[] {page-4, page-3, page-2, page-1, page};
			} else {
				pageArr = new int[] {page-2, page-1, page, page+1, page+2};
			}
			int begin = (page-1)*pageSize + 1;
			int end = page*pageSize;
			SessionUser su = (SessionUser) session.get(Constants.SESSION_USER_KEY);
			stbs = stbService.searchStbs(stb, begin, end, su.getUserId());
			if (log.isDebugEnabled()) {
				log.debug("result stb list: {}", stbs);
			}
		}
	}
	public BoxPackageService getBoxPackageService() {
		return boxPackageService;
	}
	public void setBoxPackageService(BoxPackageService boxPackageService) {
		this.boxPackageService = boxPackageService;
	} 
	public ModuleFileService getModuleFileService() {
		return moduleFileService;
	}
	public void setModuleFileService(ModuleFileService moduleFileService) {
		this.moduleFileService = moduleFileService;
	}
	public PackageFilesService getPackageFilesService() {
		return packageFilesService;
	}
	public void setPackageFilesService(PackageFilesService packageFilesService) {
		this.packageFilesService = packageFilesService;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public BoxPackageVo getVo() {
		return vo;
	}
	public void setVo(BoxPackageVo vo) {
		this.vo = vo;
	}
	public String getBoxname() {
		return boxname;
	}
	public void setBoxname(String boxname) {
		this.boxname = boxname;
	}
	public long getFileid() {
		return fileid;
	}
	public void setFileid(long fileid) {
		this.fileid = fileid;
	}
	public Map<String, String> getModuleMap() {
		return moduleMap;
	}
	public void setModuleMap(Map<String, String> moduleMap) {
		this.moduleMap = moduleMap;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getDateinput() {
		return dateinput;
	}
	public void setDateinput(Date dateinput) {
		this.dateinput = dateinput;
	}
	public Date getDateinput2() {
		return dateinput2;
	}
	public void setDateinput2(Date dateinput2) {
		this.dateinput2 = dateinput2;
	}
	public WebControl getLocalService() {
		return localService;
	}
	public void setLocalService(WebControl localService) {
		this.localService = localService;
	}
	public StbService getStbService() {
		return stbService;
	}
	public void setStbService(StbService stbService) {
		this.stbService = stbService;
	}
	public Map<String, String> getMapStatus() {
		return mapStatus;
	}
	public void setMapStatus(Map<String, String> mapStatus) {
		this.mapStatus = mapStatus;
	}
	public String getStbMac() {
		return stbMac;
	}
	public void setStbMac(String stbMac) {
		this.stbMac = stbMac;
	}
	public String getStbStatus() {
		return stbStatus;
	}
	public void setStbStatus(String stbStatus) {
		this.stbStatus = stbStatus;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public int getPageNums() {
		return pageNums;
	}
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int[] getPageArr() {
		return pageArr;
	}
	public void setPageArr(int[] pageArr) {
		this.pageArr = pageArr;
	}
	public String getActivePolicy() {
		return activePolicy;
	}
	public void setActivePolicy(String activePolicy) {
		this.activePolicy = activePolicy;
	}
	public List<Stb> getStbs() {
		return stbs;
	}
	public void setStbs(List<Stb> stbs) {
		this.stbs = stbs;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
