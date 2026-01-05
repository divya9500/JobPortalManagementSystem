package com.jobportal.controller.admin;

import java.util.List;
import java.util.Set;

public class BulkUpdateResponse {
	private boolean success;
	private  int updatedCount;
	private Set<Long> updatedIds;
	public BulkUpdateResponse(boolean status,int updatedCount,Set<Long> updatedIds) {
		this.success=status;
		this.updatedCount=updatedCount;
		this.updatedIds=updatedIds;
	}
	public boolean isStatus() {
		return success;
	}
	public int getUpdatedCount() {
		return updatedCount;
	}
	public Set<Long> getUpdatedIds() {
		return updatedIds;
	}
	
}
