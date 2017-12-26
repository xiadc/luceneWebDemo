package com.cums.demo.pojo;
/**
 * 用于存放上传文件对象
 * @author xuyang.xu
 * @date 2016-9-1 下午3:18:17  
 *
 */
public class FileBean {
	private String fileName;
	private String fileNameId; //该文件名对应的ID值
	private Long fileSize;
	private String sourcePath;
	private String savePath;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getFileNameId() {
		return fileNameId;
	}
	public void setFileNameId(String fileNameId) {
		this.fileNameId = fileNameId;
	}
	
}
