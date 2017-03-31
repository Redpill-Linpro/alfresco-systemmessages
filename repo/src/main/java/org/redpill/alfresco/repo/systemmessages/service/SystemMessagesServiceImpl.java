package org.redpill.alfresco.repo.systemmessages.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.redpill.alfresco.repo.systemmessages.bean.SystemNotificationBean;
import org.redpill.alfresco.repo.systemmessages.model.SystemMessagesModel;
import org.redpill.alfresco.repo.systemmessages.webscript.GetDataList;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SystemMessagesServiceImpl implements SystemMessagesService, InitializingBean {
  private NodeService nodeService;
  private NamespaceService namespaceService;
  private SearchService searchService;

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(nodeService, "You must provide an instance of NodeService");
    Assert.notNull(searchService, "You must provide an instance of SearchService");
    Assert.notNull(namespaceService, "You must provide an instance of NamespaceService");
  }

  @Override
  public List<SystemNotificationBean> getActiveNotifications() {
    return getNotifications(true);
  }

  @Override
  public List<SystemNotificationBean> getAllNotifications() {
    return getNotifications(false);
  }

  @Override
  public NodeRef getSystemMessagesDatalistNodeRef() {
    List<NodeRef> dataList = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE), GetDataList.DATALIST_PATH + "/" + GetDataList.SYSTEM_MESSAGES_DL_NAME, null, namespaceService, false);

    if (!dataList.isEmpty()) {
      return dataList.get(0);
    } else {
      throw new AlfrescoRuntimeException("Could not find System Messages datalist");
    }
  }

  private List<SystemNotificationBean> getNotifications(boolean onlyActive) {
    List<NodeRef> nodeRefList = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE), GetDataList.DATALIST_PATH + "/" + GetDataList.SYSTEM_MESSAGES_DL_NAME + "/*", null, namespaceService, false);
    List<SystemNotificationBean> notifications = new ArrayList<>();

    for (NodeRef nodeRef : nodeRefList) {

      SystemNotificationBean notification = new SystemNotificationBean();
      notification.setId(nodeRef.getId());
      notification.setNodeRef(nodeRef.toString());
      notification.setTitle((String) nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_TITLE));
      notification.setMessage((String) nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_DESCRIPTION));
      notification.setPriority((String) nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_PRIORITY));
      notification.setStartTime((Date) nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_START_TIME));
      notification.setEndTime((Date) nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_END_TIME));

      if (onlyActive) {
        if (notification.isActive()) {
          notifications.add(notification);
        }
      } else {
        notifications.add(notification);
      }

    }
    return notifications;
  }

  public void setNodeService(NodeService nodeService) {
    this.nodeService = nodeService;
  }

  public void setNamespaceService(NamespaceService namespaceService) {
    this.namespaceService = namespaceService;
  }

  public void setSearchService(SearchService searchService) {
    this.searchService = searchService;
  }

}
