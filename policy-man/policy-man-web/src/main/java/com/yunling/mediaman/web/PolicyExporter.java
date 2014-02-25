package com.yunling.mediaman.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.server.remote.WebControl;
import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.model.PublishRecord;
import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.model.TimedArea;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.model.TimedList;
import com.yunling.policyman.db.service.PolicyRecService;
import com.yunling.policyman.db.service.PublishRecordService;
import com.yunling.policyman.db.service.StbGroupService;

public class PolicyExporter {
	private Logger log = LoggerFactory.getLogger(PolicyExporter.class);
	private static FastDateFormat dateTimeFormat = FastDateFormat
			.getInstance("yyyy-MM-dd H:mm");
	private static FastDateFormat compactDateMinuteFormat = FastDateFormat
			.getInstance("yyyyMMddHHmm");

	private String outputPath;
	private String downloadPath;
	private String localVideoPath;
	private String mediaPath;
	private WebControl webControl;
	private String cityCode = "1";
	private String customerName;

	private PolicyRecService policyRecService;
	private PublishRecordService publishRecordService;
	private StbGroupService stbGroupService;

	public void exportFile(Policy policy, List<TimedLayout> layoutList,
			List<PolicyMedia> policyMediaList, List<StbGroupLevelTwo> groups,
			List<Object[]> errors) {
		String datetimeStr = compactDateMinuteFormat.format(new Date());
		Date publishedTime = Calendar.getInstance().getTime();
		Document doc = processPolicy(policy, datetimeStr, layoutList,
				policyMediaList, errors);
		OutputFormat oformat = new OutputFormat("  ", true);
		oformat.setEncoding("utf-8");
		XMLWriter xw = null;

		for (StbGroupLevelTwo group : groups) {
			assert(group != null);
			log.info("+++++++++++++++++++++++++++++++++++++++++++++");
			log.info("group is null " + (group == null));
			log.info("stbGroupService is null " + (stbGroupService == null));
			log.info("+++++++++++++++++++++++++++++++++++++++++++++");
			StbGroup groupOne = stbGroupService.getByKey(group.getTypeId());
			File dir = new File(outputPath, groupOne.getFolderName()+"/"+group.getGroupId());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, datetimeStr + ".xml");

			try {
				xw = new XMLWriter(new FileOutputStream(file), oformat);
				xw.write(doc);
				xw.flush();

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (xw != null) {
					try {
						xw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			if (file.exists() && file.length() > 0) {
				Calendar afterSixMonths = Calendar.getInstance();
				afterSixMonths.add(Calendar.DATE, 180);
				String md5 = md5File(file);
				if (md5 == null) {
					throw new RuntimeException(
							"Failed to generate policy md5 code");
				}

				String downloadFilePath = downloadPath + "/"
						+ groupOne.getFolderName() + "/" + group.getGroupId() + "/" + datetimeStr + ".xml";
				PublishRecord record = new PublishRecord(policy, groupOne, group);
				record.setPolicyNumber(datetimeStr);
				record.setMd5(md5);
				record.setFilePath(downloadFilePath);
				record.setSizeBytes(file.length());
				record.setPublishedAt(publishedTime);
				publishRecordService.insert(record);
				File md5File = new File(dir, datetimeStr + ".xml.md5");
				generateMd5File(file, md5File, afterSixMonths);
				try{
					log.info("PolicyExporter: update policy for group: " + group.getGroupId());
					webControl.updatePolicyForGroup(group.getGroupId(), record.getAsParams());
				}catch(Exception e){
					log.error("PolicyExporter: Can not connect to watch server.");
					//e.printStackTrace();
				}
			}
		}
	}

	private void generateMd5File(File inputFile, File outputFile,
			Calendar expiredTime) {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(outputFile));
			StringBuffer sb = new StringBuffer();
			sb.append(FileUtils.readFileToString(inputFile));
			String expiredStr = compactDateMinuteFormat.format(expiredTime);
			sb.append(" " + expiredStr);
			// sb.append(" " + customerName);
			sb.append(" YUNLING");
			log.debug("expiredStr" + expiredStr);
			//log.debug(FileUtils.readFileToString(inputFile));
			String md5code = DigestUtils.md5Hex(FileUtils
					.readFileToString(inputFile, "utf-8")
					+ " " + expiredStr + " YUNLING");
			log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + md5code);
			bw.write("md5:" + md5code + "\r\n");
			bw.write("expire_at:" + expiredStr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(bw);
		}
	}

	private String md5File(File outputFile) {
		InputStream in = null;
		try {
			in = new FileInputStream(outputFile);
			String md5 = DigestUtils.md5Hex(in);
			return md5;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
		}
		return null;
	}

	private Document processPolicy(Policy policy, String datetimeStr,
			List<TimedLayout> layoutList, List<PolicyMedia> policyMediaList,
			List<Object[]> errors) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("policy");
		addTextElement(root, "number", datetimeStr);
		addTextElement(root, "begin_at", dateTimeFormat.format(policy
				.getStartTime()));
		addTextElement(root, "end_at", dateTimeFormat.format(policy
				.getEndTime()));
		appendMedia(root, policyMediaList, errors);
		appendLayout(root, layoutList);
		return doc;
	}

	private void appendLayout(Element root, List<TimedLayout> layoutList) {
		for (TimedLayout layout : layoutList) {
			String suffix = "";
			if(layout.getUsePercent() != null){
				suffix = layout.getUsePercent()? "%":"px";
			}
			
			Element ele = root.addElement("layout");
			addTextElement(ele, "name", layout.getName());
			addTextElement(ele, "begin_at", layout.getStartTime());
			addTextElement(ele, "end_at", layout.getEndTime());
			for (TimedArea ta : layout.getAreas()) {
				Element area = ele.addElement("area");
				addTextElement(area, "name", ta.getName());
				addTextElement(area, "level", ta.getLevel());
				addTextElement(area, "play_type", ta.getPlayType());
				if ("time".equals(ta.getPlayType())) {
					addTextElement(area, "format", ta.getTimeFormat());
				}
				addTextElement(area, "font", ta.getFont());
				addTextElement(area, "char_color", ta.getColor());
				addTextElement(area, "bcolor", ta.getBgcolor());
				addTextElement(area, "left_top_x", ta.getLeft()+suffix);
				addTextElement(area, "left_top_y", ta.getTop()+suffix);
				addTextElement(area, "right_bottom_x", ta.getLeft()
						+ ta.getWidth()+suffix);
				addTextElement(area, "right_bottom_y", ta.getTop()
						+ ta.getHeight()+suffix);

				for (TimedList tl : ta.getPlayLists()) {
					Element list;
					if (tl.getLoop()) {
						list = area.addElement("loop_media_list");
					} else {
						list = area.addElement("set_time_media_list");
						addTextElement(list, "begin_at", tl.getStartTime());
						addTextElement(list, "end_at", tl.getEndTime());
					}
					String[] medias = StringUtils.split(tl.getContent(), "\t");
					if (medias != null) {
						for (String m : medias) {
							addTextElement(list, "media_name", m);
						}
					}
				}
			}
		}
	}

	private void appendMedia(Element root, List<PolicyMedia> policyMediaList,
			List<Object[]> errors) {
		if (policyMediaList == null || policyMediaList.size() == 0) {
			return;
		}
		for (PolicyMedia pm : policyMediaList) {
			Element ele;
			if ("1".equals(pm.getType())) {
				if (pm.getVideo() == null) {
					errors.add(new Object[] { "miss-video", pm.getName() });
					continue;
				}
				ele = root.addElement("video");
				addTextElement(ele, "name", pm.getName());
				addTextElement(ele, "play_time", pm.getVideo().getPlayTime());
				addTextElement(ele, "size", pm.getVideo().getMediaSize());
				addTextElement(ele, "file_path", mediaPath + "VIDEO/"
						+ pm.getVideo().getCode()
						+ "."
						+ FilenameUtils.getExtension(pm.getVideo()
								.getFileName()));
			} else if ("2".equals(pm.getType())) {
				ele = root.addElement("image");
				addTextElement(ele, "name", pm.getName());
				addTextElement(ele, "play_time", String.valueOf(pm
						.getPlayCount()));
				addTextElement(ele, "size", pm.getVideo().getMediaSize());
				addTextElement(ele, "file_path", mediaPath + "IMAGE/"
						+ pm.getVideo().getCode()
						+ "."
						+ FilenameUtils.getExtension(pm.getVideo()
								.getFileName()));
				// addTextElement(ele, "file_path",
				// videoPath+pm.getVideo().getCode() + "." + extentionName);
			} else if ("3".equals(pm.getType())) {
				ele = root.addElement("text");
				addTextElement(ele, "name", pm.getName());
				addTextElement(ele, "play_times", String.valueOf(pm
						.getPlayCount()));
				// addTextElement(ele, "content", pm.getContent());
				String mediaContent = pm.getContent();
				if (mediaContent.endsWith(".txt")) {
//					addTextElement(ele, "file_path",
//							getTextFileContent(mediaContent));
					//addTextElement(ele, "file_path", mediaContent);
					addTextElement(ele, "file_path", mediaPath + "TXT/" + pm.getVideo().getCode() + "." + FilenameUtils.getExtension(pm.getVideo().getFileName()));
				} else {
					addTextElement(ele, "content", mediaContent);
				}
				// addTextElement(ele, "content",
				// getTextFileContent(pm.getContent()));
			}
		}
	}

	String getTextFileContent(String fileName) {
		File file = new File(localVideoPath, fileName);
		if (!file.exists()) {
			return "file not exist";
		}
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "read file error.";
		} finally {
			IOUtils.closeQuietly(reader);
		}
		return sb.toString();
	}

	private void addTextElement(Element ele, String name, Long num) {
		addTextElement(ele, name, String.valueOf(num));
	}

	private void addTextElement(Element root, String name, String text) {
		if (StringUtils.isEmpty(text)) {
			return;
		}
		Element ele = root.addElement(name);
		ele.setText(text);
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}
	
	/**
	 * @return the mediaPath
	 */
	public String getMediaPath() {
		return mediaPath;
	}

	/**
	 * @param mediaPath the mediaPath to set
	 */
	public void setMediaPath(String mediaPath) {
		this.mediaPath = mediaPath;
	}

	public WebControl getWebControl() {
		return webControl;
	}

	public void setWebControl(WebControl webControl) {
		this.webControl = webControl;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public PolicyRecService getPolicyRecService() {
		return policyRecService;
	}

	public void setPolicyRecService(PolicyRecService policyRecService) {
		this.policyRecService = policyRecService;
	}

	public PublishRecordService getPublishRecordService() {
		return publishRecordService;
	}

	public void setPublishRecordService(
			PublishRecordService publishRecordService) {
		this.publishRecordService = publishRecordService;
	}

	public String getLocalVideoPath() {
		return localVideoPath;
	}

	public void setLocalVideoPath(String localVideoPath) {
		this.localVideoPath = localVideoPath;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public StbGroupService getStbGroupService() {
		return stbGroupService;
	}

	public void setStbGroupService(
			StbGroupService stbGroupLevelTwoService) {
		this.stbGroupService = stbGroupLevelTwoService;
	}

}
