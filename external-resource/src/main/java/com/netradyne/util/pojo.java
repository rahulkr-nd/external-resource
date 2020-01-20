package com.netradyne.util;

public class pojo {
	private Integer id;
	private String url;

	public pojo(int id, String url) {
		this.id = id;
		this.url = url;
	}

	public pojo(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "id : " + id + "," + " url" + url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
