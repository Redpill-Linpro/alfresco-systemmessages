package org.redpill.alfresco.systemmessages.webscript;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import java.util.Map;

/**
 * Created by magnus on 2016-10-26.
 */
public class DeleteNode extends DeclarativeWebScript
{
    private NodeService nodeService;
    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, String> templateArgs = req.getServiceMatch()
                .getTemplateVars();
        String storeType = templateArgs.get("store_type");
        String storeId = templateArgs.get("store_id");
        String nodeId = templateArgs.get("id");
        NodeRef nodeRef = new NodeRef(new StoreRef(storeType+"://"+storeId), nodeId);
        nodeService.deleteNode(nodeRef);
        return null;
    }

    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }

}
