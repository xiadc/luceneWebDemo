package com.cums.demo.pojo;

import java.util.Date;

import com.cums.demo.common.StringUtil;

public class Record {

	private String title;
	private String titleWithCss;
	private String path;
	
	private Long lastModified;
	private String lastModifiedDesc;

	public String getLastModifiedDesc() {
		return StringUtil.convertDateToString(new Date(lastModified),  StringUtil.DATE_FORMAT_YMDHMS);
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String getTitleWithCss() {
		return titleWithCss;
	}


	public void setTitleWithCss(String titleWithCss) {
		this.titleWithCss = titleWithCss;
	}


	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	
	
	
}
