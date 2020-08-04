package com.springbook.board.common;

public class KakaoUserInfo {
	private int id;
	private String connected_at;
	private KakaoProperties properties;
	
	
	
	public KakaoProperties getProperties() {
		return properties;
	}

	public void setProperties(KakaoProperties properties) {
		this.properties = properties;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getConnected_at() {
		return connected_at;
	}
	
	public void setConnected_at(String connected_at) {
		this.connected_at = connected_at;
	}
	
	
}
