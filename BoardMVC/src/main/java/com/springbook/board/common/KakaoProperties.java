package com.springbook.board.common;

public class KakaoProperties {
	
	private String nickname;
	private String profile_image;
	private String thumbnail_image;
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getProfile_image() {
		return profile_image;
	}
	
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	
	public String getThumbnail_image() {
		return thumbnail_image;
	}
	
	public void setThumbnail_image(String thumbnail_image) {
		this.thumbnail_image = thumbnail_image;
	}
}
