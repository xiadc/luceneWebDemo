package com.cums.demo.common;

public class Page {
	
	/** 页号宏定义 */
	public static final String PAGE_NUM = "pageNum";
	
	/** 每页记录数 */
	public static final String NUM_PER_PAGE = "numPerPage";
	
	/** 当前页号 */
	private int pageNum;
	
	/** 每页记录数 */
	private int numPerPage;
	
	/** 显示页号数量 */
	private int pageNumShown;
	
	/** 查询结果 */
	private Object result;
	
	/** 总记录数 */
	private int totalCount;

	/**
	 * 构造方法
	 * @param pageNum 当前页号
	 * @param numPerPage 每页记录数
	 * @param totalCount 查询总记录数
	 * @param result 查询结构数据
	 */
	public Page(int pageNum, int numPerPage, 
			int totalCount, Object result) {
		this.pageNum = pageNum;	
		this.numPerPage = numPerPage;
		this.totalCount = totalCount;
		this.result = result;
		
//		if (totalCount % numPerPage == 0)
//			pageNumShown = totalCount / numPerPage;
//		else
//			pageNumShown = totalCount / numPerPage + 1;
		pageNumShown =10;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return this.pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the numPerPage
	 */
	public int getNumPerPage() {
		return this.numPerPage;
	}

	/**
	 * @param numPerPage the numPerPage to set
	 */
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	/**
	 * @return the pageNumShown
	 */
	public int getPageNumShown() {
		return this.pageNumShown;
	}

	/**
	 * @param pageNumShown the pageNumShown to set
	 */
	public void setPageNumShown(int pageNumShown) {
		this.pageNumShown = pageNumShown;
	}

	/**
	 * @return the result
	 */
	public Object getResult() {
		return this.result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
