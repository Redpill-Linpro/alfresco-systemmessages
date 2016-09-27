package org.redpill.alfresco.systemmessages.webscript;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.redpill.alfresco.systemmessages.model.SystemMessagesModel;
import org.redpill.alfresco.systemmessages.patch.CreateDataList;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class SystemMessagesGet extends DeclarativeWebScript
{
    private NodeService nodeService;
    private NamespaceService namespaceService;
    private SearchService searchService;

    private boolean isActive(NodeRef notification)
    {
        Date currentDate = new Date();
        Date startDate = (Date)nodeService.getProperty(notification,SystemMessagesModel.PROP_SYSTEM_MESSAGE_START_TIME);
        Date endDate = (Date)nodeService.getProperty(notification,SystemMessagesModel.PROP_SYSTEM_MESSAGE_END_TIME);
        if(startDate==null)
        {
            return false;
        }
        else if(currentDate.after(startDate) && endDate==null)
        {
            return true;
        }
        else return currentDate.after(startDate) && currentDate.before(endDate);
    }


    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {

        HashMap<String, Object> result = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        List<NodeRef> nodeRefList = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE),
                CreateDataList.path + "/*", null, namespaceService, false);
        JSONArray notifications = new JSONArray();
        for(NodeRef nodeRef:nodeRefList)
        {
            if(req.getParameter("active")!=null)
            {
                if(!isActive(nodeRef))
                {
                    continue;
                }
            }

            JSONObject notification = new JSONObject();
            notification.put("id",nodeRef.getId());
            notification.put("nodeRef",nodeRef.toString());
            notification.put("text",nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_DESCRIPTION));
            notification.put("title",nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_TITLE));
            notification.put("type",nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_PRIORITY));
            notification.put("startTime", iso8601((Date)nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_START_TIME)));
            notification.put("endTime", iso8601((Date)nodeService.getProperty(nodeRef, SystemMessagesModel.PROP_SYSTEM_MESSAGE_END_TIME)));
            notifications.add(notification);
        }
        jsonObject.put("notifications", notifications);
        result.put("json", jsonObject.toJSONString());
        return result;
    }

    private String iso8601(Date date)
    {
        if(date==null)
        {
            return "";
        }
        else
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
            return df.format(date);
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
 