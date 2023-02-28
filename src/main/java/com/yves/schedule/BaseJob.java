package com.yves.schedule;

public abstract class BaseJob {

	protected String cronExpression = null;

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
