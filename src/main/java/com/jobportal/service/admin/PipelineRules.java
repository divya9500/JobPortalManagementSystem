package com.jobportal.service.admin;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import com.jobportal.model.admin.ApplicationModel.Status;

public class PipelineRules {
	private static final Map<Status, Set<Status>> RULES=new EnumMap<>(Status.class);
	
	static {
		RULES.put(Status.APPLIED, EnumSet.of(Status.SHORTLISTED,Status.REJECTED));
		RULES.put(Status.SHORTLISTED, EnumSet.of(Status.INTERVIEW_SCHEDULED,Status.REJECTED));
		RULES.put(Status.INTERVIEW_SCHEDULED, EnumSet.of(Status.INTERVIEW_PASSED,Status.INTERVIEW_FAILED));
		RULES.put(Status.INTERVIEW_PASSED, EnumSet.of(Status.OFFERED));
		RULES.put(Status.OFFERED, EnumSet.of(Status.OFFERED,Status.REJECTED));
	}
	private PipelineRules() {}
	
	public static boolean isVaild(Status current,Status next) {
		return RULES.containsKey(current) && RULES.get(current).contains(next);
	}


}
