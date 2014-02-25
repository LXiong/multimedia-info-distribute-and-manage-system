package com.yunling.policyman.db.model;

public enum PolicyStatus {
	
	NORMAL("normal"), SUBMITTED("submitted"), PASSED("passed"), REJECTED("rejected"), PUBLISHED("published");
	
	private String name;
	private PolicyStatus(String name) {
		this.name = name;
	}

	public static boolean isNormal(String status ) {
		return PolicyStatus.NORMAL.getName().equals(status);
	}
	
	public static boolean isSubmitted(String status) {
		return PolicyStatus.SUBMITTED.getName().equals(status);
	}
	public static boolean isRejected(String status) {
		return PolicyStatus.REJECTED.getName().equals(status);
	}

	public static boolean isPassed(String status) {
		return PolicyStatus.PASSED.getName().equals(status);
	}
	
	public static boolean isPublished(String status){
		return PolicyStatus.PUBLISHED.getName().equalsIgnoreCase(status);
	}
	public String getName() {
		return name;
	}

	public static boolean isEditable(String status) {
		return isNormal(status) || isRejected(status);
	}


}
