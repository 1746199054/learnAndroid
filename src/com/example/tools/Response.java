package com.example.tools;

public class Response {
	private Boolean status;
	private String result;

	public Response(Boolean s, String r) {
		status = s;
		result = r;
	}

	public Boolean getStatus() {
		return status;
	}

	public String getResult() {
		return result;
	}
}