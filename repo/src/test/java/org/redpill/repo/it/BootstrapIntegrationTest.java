package org.redpill.repo.it;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.cmr.search.SearchService;
import org.alfresco.service.namespace.NamespaceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redpill.alfresco.systemmessages.webscript.GetDataList;
import org.redpill.alfresco.test.AbstractRepoIntegrationTest;
import org.redpill.alfresco.test.SpringInstanceTestClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by magnus on 2016-09-26.
 */
@RunWith(SpringInstanceTestClassRunner.class)
//@ContextConfiguration("classpath:alfresco/application-context.xml")
public class BootstrapIntegrationTest extends AbstractRepoIntegrationTest
{
    @Autowired
    @Qualifier("SearchService")
    SearchService searchService;
    @Autowired
    @Qualifier("NodeService")
    NodeService nodeService;
    @Autowired
    @Qualifier("NamespaceService")
    NamespaceService namespaceService;
    @Test
    public void testCreateDataListPatch() throws Exception
    {
        // Setup authentication
        _authenticationComponent.setCurrentUser(AuthenticationUtil.getAdminUserName());

        List<NodeRef> result = searchService.selectNodes(nodeService.getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE),
        		GetDataList.DATALIST_PATH + "/" + GetDataList.SYSTEM_MESSAGES_DL_NAME,null,namespaceService,false);
        assertEquals(1,result.size());

    }
}
