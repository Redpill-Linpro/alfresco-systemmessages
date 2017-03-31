package org.redpill.alfresco.repo.systemmessages.model;
import org.alfresco.service.namespace.QName;
public interface SystemMessagesModel
{
	String URI =  "http://www.redpill-linpro.com/model/sm/1.0";
	String SHORT_PREFIX = "rlsm";



	//TYPES
	QName TYPE_SYSTEM_MESSAGE = QName.createQName(URI,"systemMessage");



	//Aspects



	//PROPS
	QName PROP_SYSTEM_MESSAGE_TITLE = QName.createQName(URI,"systemMessageTitle");
	QName PROP_SYSTEM_MESSAGE_DESCRIPTION = QName.createQName(URI,"systemMessageDescription");
	QName PROP_SYSTEM_MESSAGE_START_TIME = QName.createQName(URI,"systemMessageStartTime");
	QName PROP_SYSTEM_MESSAGE_END_TIME = QName.createQName(URI,"systemMessageEndTime");
	QName PROP_SYSTEM_MESSAGE_PRIORITY = QName.createQName(URI,"systemMessagePriority");
}