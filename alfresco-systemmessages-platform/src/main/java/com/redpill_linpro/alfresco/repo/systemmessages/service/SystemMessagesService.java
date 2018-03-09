package com.redpill_linpro.alfresco.repo.systemmessages.service;

import java.util.List;

import org.alfresco.service.cmr.repository.NodeRef;

import com.redpill_linpro.alfresco.repo.systemmessages.bean.SystemNotificationBean;

public interface SystemMessagesService {
	public List<SystemNotificationBean> getActiveNotifications();
	public List<SystemNotificationBean> getAllNotifications();
  public NodeRef getSystemMessagesDatalistNodeRef();
}
