package com.yunling.mediaman.web.ctl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.utils.Ffprobe;
import com.yunling.mediaman.web.utils.MediaInfo;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.JqgridRow;
import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.model.helper.UploadingVideoHelper;
import com.yunling.policyman.db.service.UploadingVideoService;
import com.yunling.web.PageBean;

/**
 * @author LingJun
 * @date 2011-1-27
 * 
 */
@Controller
@RequestMapping("/videos")
public class UploadingVideoController {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	static String[] columns = {"id", "code", "file_name", "origin_name",
			"current_size", "expected_size", "last_modified_time", "uploaded" };
	@Autowired
	private UploadingVideoService uploadingService;
	
	@Autowired
	private Ffprobe ffprobe;
	
	@Autowired
	private WebConfig webConfig;
	
	@RequestMapping("/confirmuploaded")
	public @ResponseBody
	String confirmUploaded(@RequestParam("ids") String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			if (idArr.length != 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					UploadingVideo uvideo = uploadingService.getByKey(id);
					if (uvideo == null) {
						return "notfound";
					}
					File targetFile = new File(webConfig.getUploadPath(),
							uvideo.getOriginName());
					if (!targetFile.exists()) {
						return "file_miss";
					}
					if (targetFile.length() != uvideo.getCurrentSize()) {
						return "miss_match";
					}
					// ExpectedSize not use now
					//uvideo.setExpectedSize(targetFile.length());
					uvideo.setUploaded(Boolean.TRUE);

					InputStream in = null;
					try {
						in = new FileInputStream(targetFile);
						String code = DigestUtils.md5Hex(in);
						List<UploadingVideo> uploadingVideoList = uploadingService
								.listByCode(code);
						if (uploadingVideoList != null
								&& uploadingVideoList.size() > 0) {
							return "duplicated";
						}
						uvideo.setCode(code);
					} catch (IOException e) {
						e.printStackTrace();
						return "failed-md5";
					} finally {
						IOUtils.closeQuietly(in);
					}
					uploadingService.update(uvideo);
				}
			}
		}
		return "success";
	}
	
	@RequestMapping("/del-uploading")
	public @ResponseBody
	String deleteUploading(@RequestParam("ids") String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			if (idArr.length != 0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					UploadingVideo uvideo = uploadingService.getByKey(id);
					File videoFile = new File(webConfig.getUploadPath(),
							uvideo.getOriginName());
					if (videoFile.exists()) {
						log.info("File " + videoFile.getName() + " will be deleted!");
						if (!videoFile.delete()) {
							log.debug("Failed to delete file: " + videoFile.getName() + "!");
							return "failed-delete-file";
						}
					}
					try {
						log.info("A record about file " + videoFile.getName() + " will be deleted!");
						uploadingService.deleteByKey(id);
					} catch (Exception e) {
						log.debug("Failed to delete the record about file: " + videoFile.getName() + "!");
						return "failed-delete-record";
					}
				}
			}
		}
		return "success";
	}

	@RequestMapping("/uploadinglist")
	public ModelAndView uploadingVideo(ModelAndView mav) {
		return mav;
	}

	@RequestMapping(value = "/uploading")
	public @ResponseBody
	JqgridPage uploading(@RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		int count = uploadingService.countByUploaded(false);
		jpage.setRecords(count);
		PageBean pb = new PageBean(page, count, rows>0 ?rows : webConfig.getPageSize());
		jpage.setPage(pb.getCurPage());
		jpage.setTotal(pb.getTotalPage());
		List<UploadingVideo> list = uploadingService.listByUploadedPage(false,
				pb.getBegin(), pb.getEnd());
		List<JqgridRow> rowList = new ArrayList<JqgridRow>();
		for (UploadingVideo video : list) {
			JqgridRow row = new JqgridRow();
			row.setId(video.getId().toString());
			String[] arr = UploadingVideoHelper.toSimpleArray(video, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping("/uploadedlist")
	public ModelAndView uploadedVideo(ModelAndView mav) {
		return mav;
	}

	@RequestMapping(value = "/uploaded")
	public @ResponseBody
	JqgridPage uploaded(@RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		int count = uploadingService.countByUploaded(true);
		jpage.setRecords(count);
		int total = count % rows == 0 ? count
				/ rows : count / rows + 1;
		int curPage = page > total ? total : page;
		jpage.setPage(curPage);
		jpage.setTotal(total);
		List<UploadingVideo> list = uploadingService.listByUploadedPage(true,
				(curPage - 1) * rows + 1, curPage * rows);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>();
		for (UploadingVideo video : list) {
			JqgridRow row = new JqgridRow();
			row.setId(video.getId().toString());
			String[] arr = UploadingVideoHelper.toSimpleArray(video, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping("/video/edit")
	public @ResponseBody
	String edit(@RequestParam("id") Long id,
			@ModelAttribute UploadingVideo media) {
		UploadingVideo temp = uploadingService.getByKey(id);
		//temp.setExpectedSize(media.getExpectedSize());
		temp.setLastModifiedTime(new Date());
		uploadingService.update(temp);
		return "";
	}
	
	@RequestMapping("/getinfo")
	public @ResponseBody MediaInfo info(@RequestParam("id") Long id) {
		UploadingVideo uvideo = uploadingService.getByKey(id);
		String filepath = webConfig.getUploadPath() + uvideo.getOriginName();
		String ext = FilenameUtils.getExtension(uvideo.getOriginName());
		MediaInfo info;
		if ("txt".equalsIgnoreCase(ext)) {
			info = new MediaInfo();
			info.setFormat("text");
			return info;
		}
		info = ffprobe.getInfo(filepath);
		return info;
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

	public void setWebConfig(WebConfig webConfig) {
		this.webConfig = webConfig;
	}
}
