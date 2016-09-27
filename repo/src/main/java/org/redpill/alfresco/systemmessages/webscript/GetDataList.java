package org.redpill.alfresco.systemmessages.webscript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.json.simple.JSONObject;
import org.redpill.alfresco.systemmessages.patch.CreateDataList;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class GetDataList extends DeclarativeWebScript {
    private NodeService nodeService;
    private SearchService searchService;
    private NamespaceService namespaceService;

    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache) {
        List<NodeRef> dataList = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE),
                CreateDataList.path,null,namespaceService,false);
        HashMap<String,Object> result = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        if(!dataList.isEmpty())
        {
            jsonObject.put("nodeRef",dataList.get(0).toString());
            result.put("json",jsonObject.toJSONString());
            return result;
        }
        else
        {
            throw new AlfrescoRuntimeException("Could not find datalist");
        }
    }

    public void setSearchService(SearchService searchService)
    {
        this.searchService = searchService;
    }

    public void setNamespaceService(NamespaceService namespaceService)
    {
        this.namespaceService = namespaceService;
    }

    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
}
