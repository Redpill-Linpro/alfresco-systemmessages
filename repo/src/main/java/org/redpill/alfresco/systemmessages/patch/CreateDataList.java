package org.redpill.alfresco.systemmessages.patch;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.model.DataListModel;
import org.alfresco.repo.admin.patch.AbstractPatch;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.util.PropertyMap;
import org.redpill.alfresco.systemmessages.model.SystemMessagesModel;
import org.springframework.extensions.surf.util.I18NUtil;

import java.util.List;

/**
 * Created by magnus on 2016-09-22.
 */

public class CreateDataList extends AbstractPatch
{
    private final static String parent = "/app:company_home/app:dictionary";
    public final static String dataListName= "cm_x003a_system-messages-datalist";
    public final static String path =  parent + "/" +  dataListName;
    @Override
    protected String applyInternal() throws Exception {
        String xpath = parent;
        List<NodeRef> refs = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE), xpath,null,namespaceService,false);
        if(refs.size() != 1)
        {
            throw new AlfrescoRuntimeException(I18NUtil.getMessage("patch.updateLyseInviteEmailTemplatesPatch.error"));
        }
        PropertyMap properties = new PropertyMap();
        properties.put(ContentModel.PROP_TITLE, "System Messages");
        properties.put(ContentModel.PROP_NAME, "system-messages-datalist");
        properties.put(ContentModel.PROP_DESCRIPTION, "Datalist containing system messages");
        properties.put(DataListModel.PROP_DATALIST_ITEM_TYPE, SystemMessagesModel.TYPE_SYSTEM_MESSAGE);
        nodeService.createNode(refs.get(0), ContentModel.ASSOC_CONTAINS, QName.createQName(dataListName), DataListModel.TYPE_DATALIST,properties);

        if (refs.size() != 1) {
            throw new AlfrescoRuntimeException(I18NUtil.getMessage("patch.updateLyseInviteEmailTemplatesPatch.error"));
        }
        return I18NUtil.getMessage("patch.updateLyseInviteEmailTemplatesPatch.result");
    }


}
