package com.redpill_linpro.alfresco.repo.systemmessages;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.alfresco.rad.test.AbstractAlfrescoIT;
import org.alfresco.rad.test.AlfrescoTestRunner;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.redpill_linpro.alfresco.repo.systemmessages.webscript.GetDataList;

/**
 * Tests if the systemmessages datalist is bootstrapped correctly.
 * 
 * @author Erik Billerby
 *
 */

@RunWith(value = AlfrescoTestRunner.class)
public class ListExistsComponentIT extends AbstractAlfrescoIT {

  @Test
  public void testCreateDataListPatch() throws Exception {

    List<NodeRef> result = getServiceRegistry().getSearchService().selectNodes(getServiceRegistry().getNodeService().getRootNode(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE), GetDataList.DATALIST_PATH + "/" + GetDataList.SYSTEM_MESSAGES_DL_NAME, null, getServiceRegistry().getNamespaceService(), false);
    assertEquals(1, result.size());

  }
}
