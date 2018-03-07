package org.redpill.alfresco.repo.systemmessages.webscript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.redpill.alfresco.repo.systemmessages.bean.SystemNotificationBean;
import org.redpill.alfresco.repo.systemmessages.service.SystemMessagesService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

public class SystemMessagesGet extends DeclarativeWebScript
{
    private SystemMessagesService systemMessagesService;

    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {

        HashMap<String, Object> result = new HashMap<>();
        List<SystemNotificationBean> notifications = null;
        if(req.getParameter("active")!=null)
        {
        	notifications = systemMessagesService.getActiveNotifications();
        }else{
        	notifications = systemMessagesService.getAllNotifications();
        }
        result.put("notifications", notifications);
        return result;
    }

    public void setSystemMessagesService(SystemMessagesService systemMessagesService) {
		this.systemMessagesService = systemMessagesService;
	}
}
 