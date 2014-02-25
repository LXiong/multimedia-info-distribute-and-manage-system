package com.yunling.policyman.db.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yunling.policyman.db.mapper.PolicyMapper;
import com.yunling.policyman.db.mapper.PolicyMediaMapper;
import com.yunling.policyman.db.mapper.TimedAreaMapper;
import com.yunling.policyman.db.mapper.TimedLayoutMapper;
import com.yunling.policyman.db.mapper.TimedListMapper;
import com.yunling.policyman.db.model.Policy;
import com.yunling.policyman.db.model.PolicyMedia;
import com.yunling.policyman.db.model.PolicyStatus;
import com.yunling.policyman.db.model.TimedArea;
import com.yunling.policyman.db.model.TimedLayout;
import com.yunling.policyman.db.model.TimedList;
import com.yunling.policyman.db.service.PolicyService;
import com.yunling.policyman.db.service.impl.base.BasePolicyServiceImpl;

public class PolicyServiceImpl
	extends BasePolicyServiceImpl
	implements PolicyService
{

	@Override
	public void insertAll(Policy policy) {
		getMapper().insert(policy);
		insertLayouts(policy.getId(), policy.getLayoutList());
		insertMediaList(policy.getId(), policy.getMediaList());
	}

	private void insertLayouts(Long policyId, List<TimedLayout> layoutList) {
		if (layoutList != null && layoutList.size() >0) {
			for(TimedLayout tl : layoutList) {
				tl.setPolicyId(policyId);
				getMapper(TimedLayoutMapper.class).insert(tl);
				insertAreas(policyId, tl.getId(), tl.getAreas());
			}
		}
	}

	private void insertAreas(Long policyId, Long layoutId, List<TimedArea> areas) {
		if (areas!=null && areas.size()>0) {
			for(TimedArea ta : areas) {
				ta.setPolicyId(policyId);
				ta.setLayoutId(layoutId);
				getMapper(TimedAreaMapper.class).insert(ta);
				insertPlayList(policyId, ta.getId(), ta.getPlayLists());
			}
		}
	}

	private void insertPlayList(Long policyId, Long areaId, List<TimedList> playLists) {
		if (playLists==null || playLists.size()<=0) {
			return;
		}
		for(TimedList tl : playLists) {
			tl.setAreaId(areaId);
			tl.setPolicyId(policyId);
			getMapper(TimedListMapper.class).insert(tl);
		}
	}

	@Override
	public void updateAll(Policy policy) {
		Policy oldOne = getMapper().getByKey(policy.getId());
		oldOne.setStatus(PolicyStatus.NORMAL.getName());
		oldOne.setStartTime(policy.getStartTime());
		oldOne.setEndTime(policy.getEndTime());
		oldOne.setName(policy.getName());
		oldOne.setComments(policy.getComments());
		getMapper().update(oldOne);
		updateLayoutList(oldOne, policy.getLayoutList());
		mergeMediaList(policy.getId(), policy.getMediaList());
	}

	private void updateLayoutList(Policy policy, List<TimedLayout> layoutList) {
		if (policy.getId() ==null) {
			return;
		}
		TimedLayoutMapper layoutMapper = getMapper(TimedLayoutMapper.class);
		if (layoutList == null || layoutList.size() == 0) {
			layoutMapper.deleteByPolicy(policy.getId());
			getMapper(TimedListMapper.class).deleteByPolicy(policy.getId());
			getMapper(TimedAreaMapper.class).deleteByPolicy(policy.getId());
			return;
		}
		List<TimedLayout> oldList = layoutMapper.listByPolicy(policy.getId());
		List<TimedLayout> newList = new ArrayList<TimedLayout>(layoutList.size());
		for(TimedLayout tl : layoutList) {
			if (tl.getId() == null || tl.getId() == -1l) {
				newList.add(tl);
				continue;
			}
			boolean notfound = true;
			for (Iterator<TimedLayout> it = oldList.iterator(); it.hasNext();) {
				TimedLayout old = it.next();
				if (old.getId().equals(tl.getId())) {
					old.setStartTime(tl.getStartTime());
					old.setEndTime(tl.getEndTime());
					old.setLcomment(tl.getLcomment());
					old.setFullscreen(tl.getFullscreen());
					layoutMapper.update(old);
					updateLayoutArea(policy, old, tl);
					it.remove();
					notfound = false;
				}
			}
			if (notfound) {
				newList.add(tl);
			}
		}
		for(TimedLayout tl : oldList) {
			getMapper(TimedListMapper.class).deleteByLayout(tl.getId());
			getMapper(TimedAreaMapper.class).deleteByLayout(tl.getId());
			layoutMapper.deleteByModelKey(tl);
		}
		insertLayouts(policy.getId(), newList);
	}

	private void updateLayoutArea(Policy policy, TimedLayout oldLayout, TimedLayout tl) {
		TimedAreaMapper areaMapper = getMapper(TimedAreaMapper.class);
		List<TimedArea> oldAreas = oldLayout.getAreas();
		List<TimedArea> newAreas = tl.getAreas();
		if (newAreas == null || newAreas.size() == 0) {
			// this statement is wrong
			areaMapper.deleteByLayout(oldLayout.getId());
			return;
		}
		List<TimedArea> newList = new ArrayList<TimedArea>();
		for(TimedArea newArea : newAreas) {
			if (oldAreas == null) continue;
			boolean found = false;
			for (Iterator<TimedArea> it = oldAreas.iterator(); it.hasNext();) {
				TimedArea oldArea =  it.next();
				if (oldArea.getId().equals(newArea.getId())) {
					found = true;
					it.remove();
					updateArea(policy, oldLayout, oldArea, newArea);
				}
			}
			if (!found) {
				newList.add(newArea);
			}
		}
		for(TimedArea ta : oldAreas ) {
			getMapper(TimedListMapper.class).deleteByArea(ta.getId());
			areaMapper.deleteByModelKey(ta);
		}
	}

	private void updateArea(Policy policy, TimedLayout oldLayout,
			TimedArea oldArea, TimedArea newArea) {
		oldArea.merge(newArea);
		getMapper(TimedAreaMapper.class).update(oldArea);
		
		TimedListMapper listMapper = getMapper(TimedListMapper.class);
		List<TimedList> oldList = oldArea.getPlayLists();
		List<TimedList> newPlayLists = newArea.getPlayLists();
		if (newPlayLists == null || newPlayLists.size()==0) {
			listMapper.deleteByArea(oldArea.getId());
			return;
		}
		
		List<TimedList> newList = new ArrayList<TimedList>(newPlayLists.size());
		for(TimedList newOne: newPlayLists) {
			boolean notfound = true;
			for (Iterator<TimedList> it = oldList.iterator(); it.hasNext();) {
				TimedList oldone = it.next();
				if (oldone.getId().equals(newOne.getId())) {
					notfound = false;
					oldone.setStartTime(newOne.getStartTime());
					oldone.setEndTime(newOne.getEndTime());
					oldone.setContent(newOne.getContent());
					listMapper.update(oldone);
					it.remove();
				}
			}
			if (notfound) {
				newList.add(newOne);
			}
		}
		for(TimedList oldone : oldList) {
			listMapper.deleteByKey(oldone.getId());
		}
		insertPlayList(policy.getId(), oldArea.getId(), newList);
	}

	private void mergeMediaList(Long policyId, List<PolicyMedia> mediaList) {
		PolicyMediaMapper policyMediaMapper = getMapper(PolicyMediaMapper.class);
		if (mediaList == null || mediaList.size()==0) {
			// Delete all old policyMedia, if new one is empty
			policyMediaMapper.deleteByPolicy(policyId);
			return;
		}
		List<PolicyMedia> oldList = policyMediaMapper.listByPolicy(policyId);
		List<PolicyMedia> newList = new ArrayList<PolicyMedia>(mediaList.size());
		List<PolicyMedia> updateList = new ArrayList<PolicyMedia>(mediaList.size());
		for(PolicyMedia pm : mediaList) {
			boolean found = false;
			for (Iterator<PolicyMedia> it = oldList.iterator(); it.hasNext();) {
				PolicyMedia policyMedia = it.next();
				if (StringUtils.equals(pm.getName(), policyMedia.getName())) {
					it.remove();
					found = true;
					policyMedia.setContent(pm.getContent());
					policyMedia.setPlayCount(pm.getPlayCount());
					policyMedia.setLength(pm.getLength());
					policyMedia.setType(pm.getType());
					updateList.add(policyMedia);
					continue;
				}
			}
			if (!found) {
				newList.add(pm);
			}
		}
		for(PolicyMedia pm : oldList) {
			policyMediaMapper.deleteByModelKey(pm);
		}
		for(PolicyMedia pm : updateList) {
			policyMediaMapper.update(pm);
		}
		insertMediaList(policyId, newList);
	}

	private void insertMediaList(Long id, List<PolicyMedia> mediaList) {
		if (mediaList == null || mediaList.size()<=0) {
			return;
		}
		PolicyMediaMapper policyMediaMapper = getMapper(PolicyMediaMapper.class);
		for(PolicyMedia media : mediaList) {
			media.setPolicyId(id);
			String type = media.getType();
			if(StringUtils.equalsIgnoreCase(type, "video")) {
				media.setType("1");
			} else if(StringUtils.equalsIgnoreCase(type, "image")) {
				media.setType("2");
			} else if(StringUtils.equalsIgnoreCase(type, "text")) {
				media.setType("3");
			}
			
			policyMediaMapper.insert(media);
		}
	}

	@Override
	public void setSubmitted(long id, String username) {
		getMapper().setSubmitted(id, username);
	}

	@Override
	public void setPassed(long id, String username) {
		getMapper().setPassed(id, username);
	}

	@Override
	public void rejectPolicy(long id, String username, String reason) {
		getMapper().rejectPolicy(id, username, reason);
	}

	@Override
	public void deleteWhole(long id) {
		getMapper().deleteByKey(id);
		getMapper(PolicyMediaMapper.class).deleteByPolicy(id);
		getMapper(TimedLayoutMapper.class).deleteByPolicy(id);
		getMapper(TimedAreaMapper.class).deleteByPolicy(id);
		getMapper(TimedListMapper.class).deleteByPolicy(id);
	}

	public List<Policy> listPassedPaged(int begin, int end) {
		return getMapper(PolicyMapper.class).listPassedPaged(begin,end);
	}
	@Override
	public List<Policy> listSubmittedPaged(int begin, int end) {
		return getMapper(PolicyMapper.class).listSubmittedPaged(begin,end);
	}

	@Override
	public List<Policy> listEditablePaged(int begin, int end) {
		return getMapper(PolicyMapper.class).listEditablePaged(begin, end);
	}

	@Override
	public List<Policy> listByStatus(String status) {
		return getMapper().listByStatus(status);
	}

	@Override
	public void setPublished(long id, String username) {
		getMapper().setPublished(id, username);
	}

	@Override
	public void publishTo(long id, List<Long> stbGroupIdList,
			String userDispName) {
		getMapper().setPublished(id, userDispName);
//		PublishGroupMapper publishGroupMapper =  getMapper(PublishGroupMapper.class);
//		for(Long groupId : stbGroupIdList) {
//			publishGroupMapper.insert(new PublishGroup(id, groupId));
//		}
	}

	@Override
	public void copyToNew(Policy policy) {
		Long oldId = policy.getId();
		policy.setId(null);
		getMapper().insert(policy);
		List<TimedLayout> layoutList = getMapper(TimedLayoutMapper.class).listByPolicy(oldId);
		for(TimedLayout tlayout : layoutList) {
			tlayout.setId(null);
			for(TimedArea ta : tlayout.getAreas()) {
				ta.setId(null);
			}
		}
		insertLayouts(policy.getId(), layoutList);
		
		List<PolicyMedia> mediaList = getMapper(PolicyMediaMapper.class).listByPolicy(oldId);
		for(PolicyMedia pm : mediaList) {
			pm.setPolicyId(policy.getId());
		}
		insertMediaList(policy.getId(), mediaList);
	}
	
	/* (non-Javadoc)
	 * @see com.yunling.policyman.db.service.PolicyService#reviewPublish(long, java.util.List, java.lang.String)
	 */
	@Override
	public void reviewPublish(long id, List<Long> stbGroupIdList,
			String userDispName) {
		//getMapper().reviewPublish(id, userDispName);
	}

	@Override
	public int countStatus(Policy policy) {
		return getMapper().countStatus(policy);
	}

	@Override
	public List<Policy> listPageByStatus(String status, int begin, int end) {
		List<Policy> all = this.listByStatus(status);
		// eidt by LJ on 2011.11.29
		if(end > all.size()) {
			end = all.size();
		}
		// eidt by LJ on 2011.11.29
		return all.subList(begin, end);
	}
}