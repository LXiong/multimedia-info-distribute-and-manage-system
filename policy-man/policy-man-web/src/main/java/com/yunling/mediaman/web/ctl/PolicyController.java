package com.yunling.mediaman.web.ctl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yunling.mediaman.web.PolicyExporter;
import com.yunling.mediaman.web.WebConfig;
import com.yunling.mediaman.web.ctl.json.JsonResult;
import com.yunling.mediaman.web.utils.UserUtil;
import com.yunling.mediaman.web.vo.JqgridPage;
import com.yunling.mediaman.web.vo.PolicyVo;
import com.yunling.policyman.db.Constant;
import com.yunling.policyman.db.DateUtil;
import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.model.PolicyStatus;
import com.yunling.policyman.db.model.PublishTask;
import com.yunling.policyman.db.model.StbGroup;
import com.yunling.policyman.db.model.StbGroupLevelTwo;
import com.yunling.policyman.db.model.TimedArea;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.model.TimedList;
import com.yunling.policyman.db.service.PolicyMediaService;
import com.yunling.policyman.db.service.PolicyService;
import com.yunling.policyman.db.service.PublishTaskService;
import com.yunling.policyman.db.service.StbGroupLevelTwoService;
import com.yunling.policyman.db.service.StbGroupService;
import com.yunling.policyman.db.service.TimedLayoutService;
import com.yunling.web.PageBean;

@Controller
@RequestMapping("/policy")
public class PolicyController {

	private Logger log = LoggerFactory.getLogger(getClass());
	private static Pattern publishTimePattern = Pattern
			.compile("\\d{4}-\\d{2}-\\d{2}\\s(\\d{2}:){2}\\d{2}");

	private static FastDateFormat fdf = FastDateFormat
			.getInstance(Constant.FULL_DATETIME);

	@Autowired
	private WebConfig webConfig;

	@Autowired
	private PolicyService policyService;

	@Autowired
	private PolicyMediaService policyMediaService;

	@Autowired
	private PublishTaskService publishTaskService;

	@Autowired
	private TimedLayoutService timedLayoutService;

	@Autowired
	private StbGroupService stbGroupService;

	@Autowired
	private StbGroupLevelTwoService stbGroupLevelTwoService;

	@Autowired
	private PolicyExporter exporter;

	@RequestMapping("/list")
	public ModelAndView list(ModelAndView mav) {
		return mav;
	}

	@RequestMapping("/grid")
	public @ResponseBody
	JqgridPage grid(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam("rows") int rows) {
		Policy p = new Policy();
		p.setStatus("normal");
		int total = policyService.countStatus(p);
		p.setStatus("rejected");
		total += policyService.countStatus(p);
		PageBean pb = new PageBean(page, total, rows > 0 ? rows : webConfig
				.getPageSize());

		JqgridPage jpage = new JqgridPage();
		jpage.setPageBean(pb);

		List<Policy> policyList = policyService.listEditablePaged(
				pb.getBegin(), pb.getEnd());
		for (Policy policy : policyList) {
			String rowId = String.valueOf(policy.getId());
			String[] arr = policy.toStringArray();
			jpage.addRow(arr, rowId);
		}

		return jpage;
	}

	@RequestMapping("/listsubmitted")
	public ModelAndView listsubmitted(ModelAndView mav) {
		return mav;
	}

	@RequestMapping("/gridsubmitted")
	public @ResponseBody
	JqgridPage gridsubmitted(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam("rows") int rows) {
		int total = policyService.countAll();
		PageBean pb = new PageBean(page, total, rows > 0 ? rows : webConfig
				.getPageSize());

		JqgridPage jpage = new JqgridPage();
		jpage.setPageBean(pb);

		List<Policy> policyList = policyService.listSubmittedPaged(pb
				.getBegin(), pb.getEnd());
		for (Policy policy : policyList) {
			String rowId = String.valueOf(policy.getId());
			String[] arr = policy.toStringArray();
			jpage.addRow(arr, rowId);
		}

		return jpage;
	}

	@RequestMapping("/listpassed")
	public ModelAndView listpassed(ModelAndView mav) {
		return mav;
	}

	@RequestMapping("/gridpassed")
	public @ResponseBody
	JqgridPage gridpassed(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam("rows") int rows) {
		int total = policyService.countAll();
		PageBean pb = new PageBean(page, total, rows > 0 ? rows : webConfig
				.getPageSize());

		JqgridPage jpage = new JqgridPage();
		jpage.setPageBean(pb);

		List<Policy> policyList = policyService.listPassedPaged(pb.getBegin(),
				pb.getEnd());
		for (Policy policy : policyList) {
			String rowId = String.valueOf(policy.getId());
			String[] arr = policy.toStringArray2();
			jpage.addRow(arr, rowId);
		}

		return jpage;
	}

	@RequestMapping("/new")
	public ModelAndView newpolicy(ModelAndView mav) {
		Policy policy = new Policy();
		policy.setId(-1l);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		policy.setStartTime(new Date());
		cal.add(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		policy.setEndTime(cal.getTime());
		policy.setName(new SimpleDateFormat("yyyyMMddHHmm").format(new Date()));
		mav.addObject("policy", policy);
		mav.addObject("inEdit", Boolean.TRUE);
		mav.setViewName("policy/edit");
		return mav;
	}


	@RequestMapping("/edit")
	public ModelAndView edit(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			ModelAndView mav) {
		if (id == -1l) {
			mav.setViewName("redirect:/policy/new");
			return mav;
		} else {
			mav.addObject("inEdit", Boolean.TRUE);
			fillPolicy(id, mav, true);
		}

		return mav;
	}

	@RequestMapping("/delete")
	public @ResponseBody
	JsonResult delete(@RequestParam(value = "id", required = true) long id) {
		if (id != -1) {
			policyService.deleteWhole(id);
			return new JsonResult("success");
		}
		return new JsonResult("failure");
	}

	@RequestMapping("/view")
	public ModelAndView view(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			ModelAndView mav) {
		if (id == -1l) {
			mav.setViewName("policy/notfound");
		} else {
			fillPolicy(id, mav, false);
		}
		return mav;
	}

	@RequestMapping("/viewsubmitted")
	public ModelAndView viewsubmitted(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			ModelAndView mav) {
		if (id == -1l) {
			mav.setViewName("policy/notfound");
		} else {
			fillPolicy(id, mav, false);
		}
		return mav;
	}

	@RequestMapping("/viewpassed")
	public ModelAndView viewPassed(
			@RequestParam(value = "id", defaultValue = "-1") long id,
			ModelAndView mav) {
		if (id == -1l) {
			mav.setViewName("policy/notfound");
		} else {
			fillPolicy(id, mav, false);
		}
		return mav;
	}

	private void fillPolicy(long id, ModelAndView mav, boolean forEdit) {
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			mav.setViewName("policy/notfound");
			return;
		}
		if (forEdit && !PolicyStatus.isEditable(policy.getStatus())) {
			mav.setViewName("policy/wrongstatus");
			return;
		}
		List<TimedLayout> layoutList = timedLayoutService.listByPolicy(id);
		mav.addObject("layoutList", layoutList);
		List<PolicyMedia> mediaList = policyMediaService.listByPolicy(id);
		mav.addObject("mediaList", mediaList);
		mav.addObject("policy", policy);
		if (forEdit) {
			mav.setViewName("policy/edit");
		} else {
			mav.setViewName("policy/view");
		}
	}

	@RequestMapping("/republish")
	public @ResponseBody
	JsonResult republish(
			@RequestParam(value = "policy_id", defaultValue = "-1") long id,
			@RequestParam("policy_name") String newPolicyName,
			@RequestParam(value = "group_level_two") String[] stbGroupLevelTwoIds,
			//@RequestParam(value = "stb_group", required = false) String[] stbGroupIdArr,
			ModelAndView mav) {
		JsonResult notStbGroup = new JsonResult("not_stb_group");
		if (id == -1l) {
			return JsonResult.NOT_FOUND;
		} else if (stbGroupLevelTwoIds == null || stbGroupLevelTwoIds.length == 0) {
			return notStbGroup;
		}
		// duplicate published policy and publish it .
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		}
		if (!PolicyStatus.isPublished(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}

		List<Long> stbGroupIdList = new ArrayList<Long>();
		List<StbGroupLevelTwo> stbGroupFolderNames = new ArrayList<StbGroupLevelTwo>();
		for (String str : stbGroupLevelTwoIds) {
			try {
				Long tmp = Long.parseLong(str);
				StbGroupLevelTwo group = stbGroupLevelTwoService.getByKey(tmp);
				if (group != null) {
					stbGroupFolderNames.add(group);
					stbGroupIdList.add(tmp);
				}
			} catch (NumberFormatException e) {
			}
		}
		if (stbGroupIdList.size() == 0) {
			return notStbGroup;
		}
		policy.setName(newPolicyName);
		try {
			policyService.copyToNew(policy);
		} catch (Exception e) {
			JsonResult result = new JsonResult("copy_fail");
			result.setMessage(e.getMessage());
			return result;
		}
		List<TimedLayout> layoutList = timedLayoutService.listByPolicy(policy
				.getId());
		List<PolicyMedia> mediaList = policyMediaService
				.listVideoByPolicy(policy.getId());
		List<Object[]> errors = new ArrayList<Object[]>();
		try {
			exporter.exportFile(policy, layoutList, mediaList,
					stbGroupFolderNames, errors);
			if (errors.size() > 0) {
				JsonResult re = new JsonResult("failed");
				return re;
			}
			// sign policy has published.
			policyService.publishTo(id, stbGroupIdList, UserUtil
					.getUserDispName());
		} catch (Exception e) {
			e.printStackTrace();
			JsonResult re = new JsonResult("failed");
			re.setMessage(e.getMessage());
			return re;
		}
		return JsonResult.SUCCESS;
	}

	@RequestMapping("/copy_new")
	public @ResponseBody
	JsonResult copyNew(
			@RequestParam(value = "policy_id", defaultValue = "-1") long id,
			@RequestParam("policy_name") String newPolicyName, ModelAndView mav) {
		if (id == -1l) {
			return JsonResult.NOT_FOUND;
		}
		// duplicate published policy and publish it .
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		}
		if (!PolicyStatus.isPublished(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}

		policy.setName(newPolicyName);
		policy.setStatus(PolicyStatus.NORMAL.getName());
		try {
			policyService.copyToNew(policy);
		} catch (Exception e) {
			JsonResult result = new JsonResult("copy_fail");
			result.setMessage(e.getMessage());
			return result;
		}
		// return with new policy id
		JsonResult jr = new JsonResult("success");
		jr.setValue(policy.getId().toString());
		return jr;
	}

	@RequestMapping("/publish")
	public @ResponseBody
	JsonResult publish(
			@RequestParam(value = "policy_id", defaultValue = "-1") long id,
			@RequestParam(value = "group_level_two") String[] groupTwoIds,
			//@RequestParam("stb_group") String[] stbGroupIdArr,
			@RequestParam(value = "isTimingPublish", required = false, defaultValue = "false") Boolean isTimingPublish,
			@RequestParam(value = "publishTime", required = false) String publishTimeStr,
			ModelAndView mav) {
		JsonResult notStbGroup = new JsonResult("not_stb_group");
		if (id == -1l) {
			return JsonResult.NOT_FOUND;
		} else if (groupTwoIds == null || groupTwoIds.length == 0) {
			return notStbGroup;
		}
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		} else if (!PolicyStatus.isPassed(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		} else {
			List<Long> stbGroupIdList = new ArrayList<Long>();
			List<StbGroupLevelTwo> stbGroupFolderNames = new ArrayList<StbGroupLevelTwo>();
			for (String str : groupTwoIds) {
				try {
					Long tmp = Long.parseLong(str);
					StbGroupLevelTwo group = stbGroupLevelTwoService
							.getByKey(tmp);
					if (group != null) {
						stbGroupFolderNames.add(group);
						stbGroupIdList.add(tmp);
					}
				} catch (NumberFormatException e) {
				}
			}
			if (stbGroupIdList.size() == 0) {
				return notStbGroup;
			}

			List<TimedLayout> layoutList = timedLayoutService.listByPolicy(id);
			List<PolicyMedia> mediaList = policyMediaService
					.listVideoByPolicy(id);
			List<Object[]> errors = new ArrayList<Object[]>();
			try {
				if (isTimingPublish != null && isTimingPublish) {
					if (publishTimePattern.matcher(publishTimeStr).matches()) {
						Date publishTime = DateUtil.parse(publishTimeStr,
								"yyyy-MM-dd HH:mm:ss");

						PublishTask task = new PublishTask(policy,
								groupTwoIds, publishTime);
						task.setStatus("pending");
						publishTaskService.insert(task);
						policyService.publishTo(policy.getId(), stbGroupIdList,
								UserUtil.getUserDispName());
					} else {
						JsonResult re = new JsonResult("failed");
						re.setMessage("Need publish time.");
					}
				} else {
					log
							.debug("##################publish immediately.##################");
					exporter.exportFile(policy, layoutList, mediaList,
							stbGroupFolderNames, errors);
					if (errors.size() > 0) {
						JsonResult re = new JsonResult("failed");
						return re;
					}
					// sign policy has published.
					policyService.publishTo(id, stbGroupIdList, UserUtil
							.getUserDispName());
					// policyService.setPublished(id,
					// UserUtil.getUserDispName());
				}

			} catch (Exception e) {
				e.printStackTrace();
				JsonResult re = new JsonResult("failed");
				re.setMessage(e.getMessage());
				return re;
			}

			return JsonResult.SUCCESS;
		}
	}

	@RequestMapping("/review")
	public @ResponseBody
	JsonResult review(
			@RequestParam(value = "policy_id", defaultValue = "-1") long id,
			@RequestParam(value = "group_level_two") String[] stbGrouupLevelTwoIds,
			//@RequestParam("stb_group") String[] stbGroupIdArr, 
			ModelAndView mav) {
		JsonResult notStbGroup = new JsonResult("not_stb_group");
		if (id == -1l) {
			return JsonResult.NOT_FOUND;
		} else if (stbGrouupLevelTwoIds == null || stbGrouupLevelTwoIds.length == 0) {
			return notStbGroup;
		}
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		} else {
			List<Long> stbGroupIdList = new ArrayList<Long>();
			List<StbGroupLevelTwo> stbGroupFolderNames = new ArrayList<StbGroupLevelTwo>();
			for (String str : stbGrouupLevelTwoIds) {
				try {
					Long tmp = Long.parseLong(str);
					StbGroupLevelTwo group = stbGroupLevelTwoService
							.getByKey(tmp);
					if (group != null) {
						stbGroupFolderNames.add(group);
						stbGroupIdList.add(tmp);
					}
				} catch (NumberFormatException e) {
				}
			}
			if (stbGroupIdList.size() == 0) {
				return notStbGroup;
			}

			List<TimedLayout> layoutList = timedLayoutService.listByPolicy(id);
			List<PolicyMedia> mediaList = policyMediaService
					.listVideoByPolicy(id);
			List<Object[]> errors = new ArrayList<Object[]>();
			try {
				exporter.exportFile(policy, layoutList, mediaList,
						stbGroupFolderNames, errors);
				if (errors.size() > 0) {
					JsonResult re = new JsonResult("failed");
					return re;
				}
				// sign policy has published.
				policyService.reviewPublish(id, stbGroupIdList, UserUtil
						.getUserDispName());
				// policyService.setPublished(id, UserUtil.getUserDispName());
			} catch (Exception e) {
				e.printStackTrace();
				JsonResult re = new JsonResult("failed");
				re.setMessage(e.getMessage());
				return re;
			}

			return JsonResult.SUCCESS;
		}

	}

	@RequestMapping(value = "/submit")
	public @ResponseBody
	JsonResult submitAudit(@RequestParam("id") long id) {
		// TODO verify
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		} else if (!PolicyStatus.isNormal(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}
		policyService.setSubmitted(id, UserUtil.getUserDispName());
		return JsonResult.SUCCESS;
	}

	@RequestMapping(value = "/passaudit")
	public @ResponseBody
	JsonResult passAudit(@RequestParam("id") long id) {
		// TODO verify
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		} else if (!PolicyStatus.isSubmitted(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}
		policyService.setPassed(id, UserUtil.getUserDispName());
		return JsonResult.SUCCESS;
	}

	@RequestMapping(value = "/rejectaudit")
	public @ResponseBody
	JsonResult rejectAudit(@RequestParam("id") long id,
			@RequestParam("reason") String reason) {
		// TODO verify
		Policy policy = policyService.getByKey(id);
		if (policy == null) {
			return JsonResult.NOT_FOUND;
		} else if (!PolicyStatus.isSubmitted(policy.getStatus())
				&& !PolicyStatus.isPassed(policy.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}
		policyService.rejectPolicy(id, UserUtil.getUserDispName(), reason);
		return JsonResult.SUCCESS;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody
	JsonResult save(@RequestBody PolicyVo newPolicy, ModelAndView mav, HttpServletRequest req) {

		Policy policy = convert(newPolicy);
		String validateResult = validatePolicy(policy);
		if (!"success".equalsIgnoreCase(validateResult)) {
			return new JsonResult(validateResult);
		}

		if (policy == null) {
			JsonResult jsonResult = new JsonResult("failed");
			jsonResult.setMessage("no_new_policy");
			return jsonResult;
		}
		if (policy.getId() == null || policy.getId() == -1) {
			policy.setStatus(PolicyStatus.NORMAL.getName());
			policy.setUpdatedBy(UserUtil.getUserDispName());
			policy.setCreatedBy(UserUtil.getUserDispName());
			policyService.insertAll(policy);
		} else {
			Policy old = policyService.getByKey(policy.getId());
			if (old == null) {
				return JsonResult.NOT_FOUND;
			} else {
				if (!PolicyStatus.isEditable(old.getStatus())) {
					return JsonResult.WRONG_STATUS;
				}
				policy.setUpdatedBy(UserUtil.getUserDispName());
				policyService.updateAll(policy);
			}
		}

		return JsonResult.SUCCESS;
	}

	@RequestMapping(value = "/status/{status}", method = RequestMethod.GET)
	public ModelAndView listByStatus(@PathVariable("status") String status) {
		ModelAndView mav = new ModelAndView("/policy/list_by_status");
		String gridUrl = "/policy/status/" + status + "/grid";
		String captionKey = "policy.status." + status;
		String viewUrl = "/policy/" + status;
		mav.addObject("currentStatus", status);
		mav.addObject("gridUrl", gridUrl);
		mav.addObject("captionKey", captionKey);
		mav.addObject("viewUrl", viewUrl);
		mav.addObject("showEditButton", "rejected".equals(status));

		return mav;
	}

	@RequestMapping(value = "/status/{status}/grid", method = RequestMethod.GET)
	public @ResponseBody
	JqgridPage statusGrid(@PathVariable("status") String status, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam("rows") int rows) {
		JqgridPage jpage = new JqgridPage();
		Policy p = new Policy();
		p.setStatus(status);
		int total = policyService.countStatus(p);
		PageBean pb = new PageBean(page, total, rows > 0 ? rows : webConfig
				.getPageSize());
		List<Policy> policies = policyService.listPageByStatus(status, (page-1)*rows, page*rows);
		jpage.setPageBean(pb);
		for (Policy policy : policies) {
			String rowId = String.valueOf(policy.getId());
			String[] arr = {};
			if ("published".equals(status)) {
				arr = new String[] {
						String.valueOf(policy.getId()),
						policy.getName(),
						policy.getStatus(),
						policy.getPublishedAt() != null ? fdf.format(policy
								.getPublishedAt()) : "" };
			} else {
				arr = new String[] { String.valueOf(policy.getId()),
						policy.getName(), policy.getStatus(),
						policy.getFmtStartTime(), policy.getFmtEndTime() };
			}
			jpage.addRow(arr, rowId);
		}
		return jpage;
	}

	@RequestMapping(value = "/{status}", method = RequestMethod.GET)
	public ModelAndView statusView(@PathVariable("status") String status,
			@RequestParam("id") long id, HttpServletRequest req) {
		ModelAndView mav = new ModelAndView();
		if (id == -1l) {
			mav.setViewName("policy/notfound");
		} else {
			fillPolicy(id, mav, false);
			mav.addObject("currentStatus", status);
		}
		return mav;
	}

	private Policy convert(PolicyVo newPolicy) {
		if (newPolicy != null) {
			return newPolicy.toPolicy();
		}
		return null;
	}

	private String validatePolicy(Policy policy) {
		if (policy.getLayoutList().size() == 0) {
			return "empty_policy";
		}
		for (TimedLayout layout : policy.getLayoutList()) {
			if (layout.getAreas().size() == 0) {
				return "empty_layout";
			}
			for (TimedArea area : layout.getAreas()) {
				if ("4".equals(area.getType()) || "5".equals(area.getType())) {
					continue;
				}
				if (area.getPlayLists().size() == 0) {
					return "empty_area";
				}
				for (TimedList list : area.getPlayLists()) {
					if ("".equals(list.getContent())) {
						return "empty_list";
					}
				}
			}
		}
		return "success";
	}

	@RequestMapping("/listPublishedGroup")
	public @ResponseBody
	JsonResult listPublishedGroup(@RequestParam("id") Long id) {
		Policy p = policyService.getByKey(id);
		if (p == null) {
			return JsonResult.NOT_FOUND;
		}
		if (!PolicyStatus.isPublished(p.getStatus())) {
			return JsonResult.WRONG_STATUS;
		}
		List<StbGroup> groups = stbGroupService.listByPublishedPolicy(id);
		JsonResult jr = new JsonResult("success");
		jr.setValue(groups);
		return jr;
	}
}
