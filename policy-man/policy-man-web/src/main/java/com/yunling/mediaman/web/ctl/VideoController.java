package com.yunling.mediaman.web.ctl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.utils.UserUtil;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.JqgridRow;
import com.yunling.policyman.db.model.UploadingVideo;
import com.yunling.policyman.db.model.Video;
import com.yunling.policyman.db.model.VideoStatus;
import com.yunling.policyman.db.model.helper.VideoHelper;
import com.yunling.policyman.db.service.UploadingVideoService;
import com.yunling.policyman.db.service.VideoService;
import com.yunling.policyman.util.CharsetDetector;
import com.yunling.web.PageBean;

/**
 * @author LingJun
 * @date 2011-1-27
 * 
 */
@Controller
@RequestMapping("/videos")
public class VideoController {
	private static String[] columns = { "code", "origin_name", "file_name",
			"name", "tag", "description", "status", "live_time_start",
			"live_time_end", "submitter", "submit_at", "auditor", "audit_at",
			"width", "height", "play_time", "discrim", "media_type",
			"media_size" };

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private VideoService videoService;
	@Autowired
	private UploadingVideoService uploadingService;
	@Autowired
	private WebConfig webConfig;

	@RequestMapping(value = "/list")
	public ModelAndView video(ModelAndView mav) {
		return mav;
	}

	@RequestMapping(value = "/grid")
	public @ResponseBody
	JqgridPage grid(
			@RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows,
			@RequestParam(value = "searchCate", required = false) String searchCate,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to) {
		JqgridPage jpage = new JqgridPage();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("media_type", searchCate);
		map.put("name", name);
		// Date datefrom = null;
		// Date dateto = null;
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); try {
		 * if (!StringUtils.isEmpty(from)) { datefrom = format.parse(from); } if
		 * (!StringUtils.isEmpty(to)) { dateto = format.parse(to); } } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		map.put("auditAtStart", from);
		map.put("auditAtEnd", to);
		int count = videoService.countByNormal(map);
		jpage.setRecords(count);
		PageBean pb = new PageBean(page, count, rows > 0 ? rows
				: webConfig.getPageSize());
		jpage.setPage(pb.getCurPage());
		jpage.setTotal(pb.getTotalPage());
		map.put("begin", pb.getBegin());
		map.put("end", pb.getEnd());
		List<Video> videos = videoService.listByNormal(map);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>(videos.size());
		for (Video video : videos) {
			JqgridRow row = new JqgridRow();
			row.setId(video.getCode().toString());
			String[] arr = VideoHelper.toSimpleArray(video, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping("/reviewlist")
	public ModelAndView reviewVideo(ModelAndView mav) {
		return mav;
	}

	@RequestMapping(value = "/reviews")
	public @ResponseBody
	JqgridPage reviews(@RequestParam(value = "page") int page,
			@RequestParam(value = "rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = videoService.countByReview();
		jpage.setRecords(count);
		PageBean pb = new PageBean(page, count, rows > 0 ? rows
				: webConfig.getPageSize());
		jpage.setPage(pb.getCurPage());
		jpage.setTotal(pb.getTotalPage());
		map.put("begin", pb.getBegin());
		map.put("end", pb.getEnd());
		List<Video> list = videoService.listByReview(map);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>();
		for (Video video : list) {
			JqgridRow row = new JqgridRow();
			row.setId(video.getCode().toString());
			String[] arr = VideoHelper.toSimpleArray(video, columns);
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping(value = "/impt")
	public @ResponseBody
	String impt(@RequestParam("id") Long id, @RequestParam("oper") String oper,
			@ModelAttribute Video video) {
		UploadingVideo uvideo = uploadingService.getByKey(id);
		if (uvideo.getCode() != null) {
			Video existOne = videoService.getByKey(uvideo.getCode());
			if (existOne != null) {
				return "code_exist";
			}
		}
		File f = new File(webConfig.getUploadPath(), uvideo.getOriginName());
		Video newVideo = new Video();
		if ("edit".equalsIgnoreCase(oper)) {
			Date date = new Date();
			newVideo.setCode(uvideo.getCode());
			newVideo.setOriginName(uvideo.getOriginName());
			newVideo.setName(video.getName());
			newVideo.setTag(video.getTag());
			newVideo.setLiveTimeStart(video.getLiveTimeStart());
			newVideo.setLiveTimeEnd(video.getLiveTimeEnd());
			newVideo.setDescription(video.getDescription());
			newVideo.setWidth(video.getWidth());
			newVideo.setHeight(video.getHeight());
			newVideo.setPlayTime(video.getPlayTime());
			newVideo.setMediaType(StringUtils.trim(video.getMediaType()));
			newVideo.setSubmitAt(date);
			newVideo.setAuditAt(date);
			newVideo.setSubmitter(UserUtil.getUsername());
			newVideo.setAuditor(UserUtil.getUsername());
			newVideo.setMediaSize(f.length());
			newVideo.setStatus(VideoStatus.REVIEW);
			newVideo.setFileName(newVideo.getCodeName());
			// newVideo.setPlayTime(this.getTime(f).getNanoseconds());
			videoService.insert(newVideo);
			uvideo.setFileName(newVideo.getCodeName());
			uploadingService.update(uvideo);
		} else if ("del".equalsIgnoreCase(oper)) {
			File videoFile = new File(webConfig.getUploadPath(),
					uvideo.getOriginName());
			if (videoFile.exists()) {
				if (videoFile.delete()) {
					uploadingService.deleteByKey(id);
				} else {
					return "failed_delete";
				}
			} else {
				uploadingService.deleteByKey(id);
			}
		}
		return "";
	}

	@RequestMapping(value = "/video/review")
	public @ResponseBody
	String review(@RequestParam("id") String id, @ModelAttribute Video video) {
		Video temp = videoService.getByKey(id);
		Date date = new Date();
		video.setSubmitAt(date);
		video.setSubmitter(UserUtil.getUsername());
		temp.setName(video.getName());
		temp.setTag(video.getTag());
		temp.setDescription(video.getDescription());
		temp.setDiscrim(video.getDiscrim());
		temp.setLiveTimeStart(video.getLiveTimeStart());
		temp.setLiveTimeEnd(video.getLiveTimeEnd());
		temp.setPlayTime(video.getPlayTime());
		temp.setMediaType(video.getMediaType());
		temp.setStatus(VideoStatus.WAITING_MOVED);
		videoService.update(temp);
		return "";
	}

	@RequestMapping(value = "/video/save")
	public @ResponseBody
	String save(@RequestParam("id") String id,
			@RequestParam("oper") String oper, @ModelAttribute Video video) {
		if ("edit".equalsIgnoreCase(oper)) {
			Video temp = videoService.getByKey(id);
			Date date = new Date();
			// temp.setSubmitAt(date);
			// temp.setSubmitter(UserUtil.getUsername());
			temp.setName(video.getName());
			temp.setTag(video.getTag());
			temp.setDescription(video.getDescription());
			// temp.setDiscrim(video.getDiscrim());
			temp.setLiveTimeStart(video.getLiveTimeStart());
			temp.setLiveTimeEnd(video.getLiveTimeEnd());
			temp.setPlayTime(video.getPlayTime());
			temp.setMediaType(video.getMediaType());
			if (date.after(video.getLiveTimeEnd())) {
				temp.setStatus("TIMEOUT");
			} else {
				temp.setStatus(VideoStatus.NORMAL);
			}
			videoService.update(temp);
		} else if ("del".equalsIgnoreCase(oper)) {
			videoService.deleteByKey(id);
		}
		return "";
	}

	@RequestMapping(value = "/jselect")
	public @ResponseBody
	JqgridPage jselect(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10") int rows,
			@RequestParam(value = "searchCate", required = false) String searchCate,
			@RequestParam(value = "name", required = false) String name) {
		JqgridPage jpage = new JqgridPage();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("media_type", searchCate);
		if (name != null) {
			map.put("name", "%" + name + "%");
		}
		int count = videoService.countByNormal(map);
		jpage.setRecords(count);
		PageBean pb = new PageBean(page, count, rows > 0 ? rows
				: webConfig.getPageSize());
		jpage.setPage(pb.getCurPage());
		jpage.setTotal(pb.getTotalPage());
		map.put("begin", pb.getBegin());
		map.put("end", pb.getEnd());
		List<Video> videos = videoService.listByNormal(map);
		List<JqgridRow> rowList = new ArrayList<JqgridRow>(videos.size());
		for (Video video : videos) {
			JqgridRow row = new JqgridRow();
			row.setId(video.getCode().toString());
			// String[] arr = VideoHelper.toSimpleArray(video, selectColumns);
			String[] arr = {
					video.getMediaType(),
					video.getName(),
					video.getFileName(),
					video.getPlayTime() == null ? "1" : String.valueOf(video
							.getPlayTime()) };
			row.setCell(arr);
			rowList.add(row);
		}
		jpage.setRows(rowList);
		return jpage;
	}

	@RequestMapping(value = "/uploadform")
	public ModelAndView uploadform(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "postBack", required = false) String postBack,
			ModelAndView mav) {
		if (StringUtils.equals("1", postBack)) {
			String filename = file.getOriginalFilename();
			try {
				String tmp = new String(filename.getBytes("ISO8859-1"), "UTF-8");
				filename = tmp;
			} catch (UnsupportedEncodingException e1) {
				log.debug("Failed to decode filename {}", filename);

			}

			File copiedFile = new File(webConfig.getUploadPath(), filename);
			if (copiedFile.exists()) {
				mav.setViewName("/videos/uploadform");
				mav.addObject("notifyKey", "notify.file.exist");
				return mav;
			}
			String ext = FilenameUtils.getExtension(filename);
			if ("txt".equalsIgnoreCase(ext)) {
				CharsetDetector detector = new CharsetDetector();
				Charset ch = null;
				try {
					ch = detector.detect(file.getInputStream());
				} catch (IOException e) {
					log.error("Failed to detect charset", e);
					// e.printStackTrace();
				}
				if (ch == null) {
					copyFile(file, copiedFile);
					this.exec("sync");
				} else {
					BufferedReader br = null;
					PrintWriter pw = null;
					try {
						br = new BufferedReader(new InputStreamReader(
								file.getInputStream(), ch));
						pw = new PrintWriter(copiedFile, "UTF-8");
						String line = null;
						boolean isBegin = true;
						while ((line = br.readLine()) != null) {
							if (isBegin) {
								isBegin = false;
								if ((int) line.charAt(0) == 65279) {
									line = line.substring(1); // remove BOM
								}
							}
							System.out.println(line);
							pw.println(line);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
						log.error("write with utf-8", e);
					} catch (IOException e) {
						e.printStackTrace();
						log.error("copy text to utf-8", e);
					} finally {
						IOUtils.closeQuietly(pw);
						IOUtils.closeQuietly(br);
					}
				}
			} else {
				copyFile(file, copiedFile);
				this.exec("sync");
			}
			boolean isNew = false;
			UploadingVideo video = uploadingService.findByFileName(filename);
			if (video == null) {
				isNew = true;
				video = new UploadingVideo();
			}
			video.setCurrentSize(copiedFile.length());
			video.setExpectedSize(copiedFile.length());
			video.setLastModifiedTime(new Date());
			video.setOriginName(filename);

			video.setUploaded(Boolean.TRUE);
			String code = calculateMd5(copiedFile);

			Video uploadedVideo = videoService.getByKey(code);
			if (uploadedVideo != null) {
				FileUtils.deleteQuietly(copiedFile);
				mav.setViewName("/videos/uploadform");
				mav.addObject("notifyKey", "notify.library.code.duplicated");
				return mav;
			}

			List<UploadingVideo> uploadingVideoList = uploadingService
					.listByCode(code);
			if (uploadingVideoList != null && uploadingVideoList.size() > 0) {
				FileUtils.deleteQuietly(copiedFile);
				mav.setViewName("/videos/uploadform");
				mav.addObject("notifyKey", "notify.uploadDir.code.duplicated");
				return mav;
			}

			video.setCode(code);
			if (isNew) {
				uploadingService.insert(video);
			} else {
				uploadingService.update(video);
			}
			mav.setViewName("redirect:/videos/uploadedlist");
		}
		return mav;
	}

	private void copyFile(MultipartFile file, File copiedFile) {
		InputStream in = null;
		OutputStream output = null;
		try {
			in = file.getInputStream();
			output = new FileOutputStream(copiedFile);
			IOUtils.copy(in, output);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to upload file");
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(output);
		}
	}

	@RequestMapping(value = "/upload")
	public ModelAndView upload(@RequestParam("file") MultipartFile file,
			ModelAndView mav) {
		String filename = file.getOriginalFilename();
		File copiedFile = new File(webConfig.getUploadPath(), filename);
		if (copiedFile.exists()) {
			mav.setViewName("/videos/uploadform");
			mav.addObject("notifyKey", "notify.file.exist");
			return mav;
		}
		copyFile(file, copiedFile);
		this.exec("sync");
		boolean isNew = false;
		UploadingVideo video = uploadingService.findByFileName(filename);
		if (video == null) {
			isNew = true;
			video = new UploadingVideo();
		}
		video.setCurrentSize(file.getSize());
		video.setExpectedSize(file.getSize());
		video.setLastModifiedTime(new Date());

		try {
			String tmp = new String(filename.getBytes("ISO8859-1"), "UTF-8");
			video.setOriginName(tmp);
		} catch (UnsupportedEncodingException e) {
			log.error("Failed to set original name ", e);
		}

		video.setUploaded(Boolean.TRUE);
		String code = calculateMd5(copiedFile);

		Video uploadedVideo = videoService.getByKey(code);
		if (uploadedVideo != null) {
			FileUtils.deleteQuietly(copiedFile);
			mav.setViewName("/videos/uploadform");
			mav.addObject("notifyKey", "notify.library.code.duplicated");
			return mav;
		}

		List<UploadingVideo> uploadingVideoList = uploadingService
				.listByCode(code);
		if (uploadingVideoList != null && uploadingVideoList.size() > 0) {
			FileUtils.deleteQuietly(copiedFile);
			mav.setViewName("/videos/uploadform");
			mav.addObject("notifyKey", "notify.uploadDir.code.duplicated");
			return mav;
		}

		video.setCode(code);
		if (isNew) {
			uploadingService.insert(video);
		} else {
			uploadingService.update(video);
		}
		mav.setViewName("redirect:/videos/uploadedlist");
		return mav;
	}

	/**
	 * @Date 9/7/2011
	 * @author LingJun
	 * @return
	 */
	@RequestMapping(value = "/picpreview")
	public @ResponseBody
	String picPreview(@RequestParam("fileName") String fileName,
			HttpServletResponse resp) {
		InputStream image4show = null;
		OutputStream out = null;
		try {
			File picFile = new File(new File(webConfig.getPreviewPath(),
					"/IMAGE/"), fileName);
			if (picFile.exists()) {
				image4show = new FileInputStream(picFile);
				out = resp.getOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int j = -1;
				while ((j = image4show.read(buffer)) != -1) {
					out.write(buffer, 0, j);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (image4show != null) {
					image4show.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Date 9/8/2011
	 * @author LingJun
	 * @param originName
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/textpreview")
	public @ResponseBody
	String textpreview(@RequestParam("fileName") String fileName,
			HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		StringBuffer text = new StringBuffer();
		try {
			File txt = new File(new File(webConfig.getPreviewPath(), "/TXT/"),
					fileName);
			System.out.println(txt.getAbsolutePath());
			if (txt.exists()) {
				InputStream in = new FileInputStream(txt);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF-8"));
				String line = br.readLine();
				while (line != null) {
					text.append(line);
					line = br.readLine();
				}
				System.out.println(text);
				// txtValue = text.toString();
				// mav.addObject("text", text.toString());
				resp.getWriter().write(text.toString());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @date 9/9/2011
	 * @author LingJun
	 * @param originName
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/picpreviewpre")
	public @ResponseBody
	String picPreviewPre(@RequestParam("originName") String originName,
			HttpServletResponse resp) {
		InputStream image4show = null;
		OutputStream out = null;
		try {
			File picFile = new File(webConfig.getUploadPath(), originName);
			if (picFile.exists()) {
				image4show = new FileInputStream(picFile);
				out = resp.getOutputStream();
				byte[] buffer = new byte[1024 * 4];
				int j = -1;
				while ((j = image4show.read(buffer)) != -1) {
					out.write(buffer, 0, j);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (image4show != null) {
					image4show.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @date 9/9/2011
	 * @author LingJun
	 * @param originName
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/textpreviewpre")
	public @ResponseBody
	String textpreviewPre(@RequestParam("originName") String originName,
			HttpServletResponse resp) {
		resp.setContentType("text/html;charset=UTF-8");
		StringBuffer text = new StringBuffer();
		try {
			File txt = new File(webConfig.getUploadPath(), originName);
			System.out.println(txt.getAbsolutePath());
			if (txt.exists()) {
				InputStream in = new FileInputStream(txt);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						in, "UTF-8"));
				String line = br.readLine();
				while (line != null) {
					text.append(line);
					line = br.readLine();
				}
				System.out.println(text);
				// txtValue = text.toString();
				// mav.addObject("text", text.toString());
				resp.getWriter().write(text.toString());
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String calculateMd5(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			byte[] md5Array = DigestUtils.md5(in);
			return Hex.encodeHexString(md5Array);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return null;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}

	/*
	 * private Time getTime(File file) { try { URL mediaURL = new
	 * URL(webConfig.getUploadPath()); Player player =
	 * Manager.createPlayer(mediaURL); return player.getMediaTime(); } catch
	 * (Exception e) { e.printStackTrace(); } return null; }
	 */

	@RequestMapping("/del-video")
	public @ResponseBody
	String deleteUploading(@RequestParam("code") String code) {
		Video video = videoService.getByKey(code);
		File videoFile = new File(new File(webConfig.getPreviewPath()
				+ getDir(video.getMediaType())), video.getFileName());
		if (videoFile.exists()) {
			log.info("File " + videoFile.getName() + " will be deleted!");
			if (!videoFile.delete()) {
				log.debug("Failed to delete file: " + videoFile.getName() + "!");
				return "failed-delete-file";
			}
		}
		try {
			log.info("A record about file " + videoFile.getName()
					+ " will be deleted!");
			videoService.deleteByKey(code);
		} catch (Exception e) {
			log.debug("Failed to delete the record about file: "
					+ videoFile.getName() + "!");
			return "failed-delete-record";
		}
		return "success";
	}

	/**
	 * 
	 * @author LingJun
	 * @date 2012-7-9
	 * 
	 * @param mediaType
	 * @return
	 */
	private String getDir(String mediaType) {
		if (StringUtils.equalsIgnoreCase("video", mediaType)) {
			return "VIDEO";
		} else if ("image".equalsIgnoreCase(mediaType)) {
			return "IMAGE";
		} else if ("text".equalsIgnoreCase(mediaType)) {
			return "TXT";
		}
		return "";
	}

	/**
	 * 
	 * @author LingJun
	 * @date 2012-7-9
	 * 
	 * @param command
	 */
	private void exec(String command) {
		// Process pr = null;
		try {
			Runtime run = Runtime.getRuntime();
			run.exec(command);
		} catch (Exception e) {
			log.error("sync command execute error!");
		}
	}
}
