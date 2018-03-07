package org.redpill.alfresco.repo.systemmessages.bean;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class SystemNotificationBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2550061773631638481L;
	private String id;
	private String nodeRef;
	private String title;
	private String message;
	private String priority;
	private Date startTime;
	private Date endTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeRef() {
		return nodeRef;
	}

	public void setNodeRef(String nodeRef) {
		this.nodeRef = nodeRef;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Returns true if the current time is in the interval of
	 * the start and end time.
	 * 
	 * @return
	 */
	public boolean isActive() {
		DateTime now = new DateTime();

		if (startTime != null && endTime != null) {
			Interval timeSpan = new Interval(startTime.getTime(), endTime.getTime());
			return timeSpan.contains(now);
		}
		return false;
	}
}
