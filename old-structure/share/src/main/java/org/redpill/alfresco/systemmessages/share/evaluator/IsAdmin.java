package org.redpill.alfresco.systemmessages.share.evaluator;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.web.evaluator.BaseEvaluator;
import org.json.simple.JSONObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.connector.User;

/**
 *
 * @author Marcus Svartmark
 */
public class IsAdmin extends BaseEvaluator {

  @Override
  public boolean evaluate(JSONObject jsono) {
    try {
      RequestContext rc = ThreadLocalRequestContext.getRequestContext();
      User user = rc.getUser();

      return (user != null && user.isAdmin());
    } catch (Exception err) {
      throw new AlfrescoRuntimeException(
              "Exception while running action evaluator: "
              + err.getMessage());
    }
  }

}
