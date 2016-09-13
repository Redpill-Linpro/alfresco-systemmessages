package org.redpill.alfresco.systemmessages.webscript;

import java.util.Map;

import org.alfresco.service.cmr.repository.NodeService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class SystemMessagesGet extends DeclarativeWebScript {
  private NodeService nodeService;
  
  public void setNodeService(NodeService nodeService) {
    this.nodeService = nodeService;
  }
  
  @Override
  protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
    // TODO Auto-generated method stub 
    return super.executeImpl(req, status, cache);
  }
}
 