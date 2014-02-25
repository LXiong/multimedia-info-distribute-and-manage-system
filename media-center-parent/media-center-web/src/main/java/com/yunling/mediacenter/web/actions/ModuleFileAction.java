/**
 * 
 */
package com.yunling.mediacenter.web.actions;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
import com.yunling.mediacenter.db.model.ModuleFile;
import com.yunling.mediacenter.db.service.ModuleFileService;
import com.yunling.mediacenter.db.service.PackageFilesService;
import com.yunling.mediacenter.web.baseaction.AbstractUserAwareAction;
import com.yunling.mediacenter.web.struts2.SelectUtils;

/**
 * @author LingJun
 * @date 2010-12-3
 * 
 */
@Results({@Result(name="redirect", location="module-file.action", type="redirect")})
public class ModuleFileAction extends AbstractUserAwareAction {

	@Override
	public String execute() throws Exception {
		Map<String,String> module = SelectUtils.getOptionMap(this, moduleMap);
		ActionContext.getContext().put("module", module);
		ModuleFile moduleFile = new ModuleFile();
		int pageSize = moduleFileService.getSize();
		int counts = moduleFileService.selectModuleCount();
		pageNums = counts%pageSize==0 ? counts/pageSize : counts/pageSize+1;
		if(page == 0) {
			page = 1;
		}
		if(pageNums == 0) {
			page = 0;
			return SUCCESS;
		}
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
		int begin = (page-1)* pageSize + 1;
		int end = page*pageSize;
		moduleFiles = moduleFileService.getModuleFiles(moduleFile, begin, end);
		return SUCCESS;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() {
		Map<String,String> module = SelectUtils.getOptionMap(this, moduleMap);
		ActionContext.getContext().put("module", module);
		return "add";
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addModuleFile() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		ModuleFile moduleFile = new ModuleFile();
		moduleFile.setModule(module);
		moduleFile.setVersion(version);
		moduleFile.setFile_comment(comment);
		moduleFile.setFilePath(filePath);
		moduleFile.setVerflyCode(verifyCode);
		moduleFileService.addModuleFile(moduleFile);
		response.getWriter().print("<script>location='module-file.action'</script>");
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String edit() throws Exception {
		ModuleFile moduleFile = new ModuleFile();
		moduleFile.setId(id);
		moduleFile.setModule(module);
		moduleFile.setVersion(version);
		moduleFile.setFile_comment(comment);
		moduleFile.setFilePath(filePath);
		moduleFile.setVerflyCode(verifyCode);
		moduleFileService.update(moduleFile);
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
		response.setContentType("text/html;charset=utf-8");
		try {
			int count = packageFilesService.countByFileId(id);
			if(count<=0) {
				moduleFileService.deleteById(id);
			} else {
				response.getWriter().print(this.getText("moduleFile.del.failure.tip"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return the moduleFileService
	 */
	public ModuleFileService getModuleFileService() {
		return moduleFileService;
	}
	
	/**
	 * @param moduleFileService the moduleFileService to set
	 */
	public void setModuleFileService(ModuleFileService moduleFileService) {
		this.moduleFileService = moduleFileService;
	}

	/**
	 * @return the packageFilesService
	 */
	public PackageFilesService getPackageFilesService() {
		return packageFilesService;
	}
	/**
	 * @param packageFilesService the packageFilesService to set
	 */
	public void setPackageFilesService(PackageFilesService packageFilesService) {
		this.packageFilesService = packageFilesService;
	}
	/**
	 * @return the moduleFiles
	 */
	public List<ModuleFile> getModuleFiles() {
		return moduleFiles;
	}

	/**
	 * @param moduleFiles the moduleFiles to set
	 */
	public void setModuleFiles(List<ModuleFile> moduleFiles) {
		this.moduleFiles = moduleFiles;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageNums
	 */
	public int getPageNums() {
		return pageNums;
	}

	/**
	 * @param pageNums the pageNums to set
	 */
	public void setPageNums(int pageNums) {
		this.pageNums = pageNums;
	}

	/**
	 * @return the pageArr
	 */
	public int[] getPageArr() {
		return pageArr;
	}

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the verifyCode
	 */
	public String getVerifyCode() {
		return verifyCode;
	}

	/**
	 * @param verifyCode the verifyCode to set
	 */
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	/**
	 * @return the moduleMap
	 */
	public Map<String, String> getModuleMap() {
		return moduleMap;
	}
	/**
	 * @param moduleMap the moduleMap to set
	 */
	public void setModuleMap(Map<String, String> moduleMap) {
		this.moduleMap = moduleMap;
	}

	private ModuleFileService moduleFileService;
	private PackageFilesService packageFilesService;
	
	private List<ModuleFile> moduleFiles;
	
	private int page;
	private int pageNums;
	private int[] pageArr;
	
	private Long id;
	private String module;
	private String version;
	private String comment;
	private String filePath;
	private String verifyCode;
	
	private Map<String, String> moduleMap;
	
	private HttpServletResponse response = ServletActionContext.getResponse();
}
