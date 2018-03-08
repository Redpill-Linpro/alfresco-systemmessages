package com.redpill_linpro.alfresco.repo.systemmessages.webscript;

import java.util.HashMap;
import java.util.Map;

import org.alfresco.service.cmr.repository.NodeRef;
import com.redpill_linpro.alfresco.repo.systemmessages.service.SystemMessagesService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class GetDataList extends DeclarativeWebScript {
  private SystemMessagesService systemMessagesService;

  public final static String SYSTEM_MESSAGES_DL_NAME = "cm:system-messages-datalist";
  public final static String DATALIST_PATH = "/app:company_home/app:dictionary/cm:dataLists";

  @Override
  protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
    HashMap<String, Object> result = new HashMap<>();
    NodeRef systemMessagesDatalistNodeRef = systemMessagesService.getSystemMessagesDatalistNodeRef();
    result.put("nodeRef", systemMessagesDatalistNodeRef.toString());
    return result;
  }

  public void setSystemMessagesService(SystemMessagesService systemMessagesService) {
    this.systemMessagesService = systemMessagesService;
  }
}
