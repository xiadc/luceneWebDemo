package com.cums.demo.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;


import com.cums.demo.common.*;
import com.cums.demo.pojo.Record;

/**
 * Servlet implementation class SearchFileServlet
 */
public class SearchFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final int HITS_PER_PAGE = 10;
	public static final int MAX_PAGE = 10;
    private static String error_msg;  
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		error_msg ="";//清空错误信息
		
		Map<String,Object> params = getParameterMap(request);	
		String indexPath =  ProperitiesUtil.getProperities("catalog.properties").get("com.cums.indexDir");
		response.setCharacterEncoding("UTF-8");
		try {
			Page page = doPagingSearch(indexPath,params);
			
			if(page != null){
				JSONArray arrayStr = JSONArray.fromObject(page);			
				response.getWriter().print(arrayStr.toString());
			}else{
				String json = "{\"error_msg\":\"" +error_msg +"\"}";
				JSONObject jo = JSONObject.fromObject(json);			
				response.getWriter().print(jo.toString());
			}
			
		} catch (Exception e) {	
			error_msg = "索引文件或目录不存在！";
			String json = "{\"error_msg\":\"" +error_msg +"\"}";
			JSONObject jo = JSONObject.fromObject(json);			
			response.getWriter().print(jo.toString());
			e.printStackTrace();			
		}
		
	}


	/**全文搜索函数,分页显示
	 * @param params 传入参数
	 * @param 
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws InvalidTokenOffsetsException 
	 * @throws Exception
	 */
	public static Page doPagingSearch(String indexPath,Map<String,Object> params) throws IOException, ParseException, InvalidTokenOffsetsException {
		
		
		//String indexPath = (String) params.get("indexPath");//索引所在目录
		String titleQueryString = (String) params.get("title");//按标题搜索
		String contentQueryString = (String) params.get("contents");//按内容搜索
		String titleSuffix =  (String) params.get("titleSuffix");//搜索文件类型
		//boolean ifAnd = params.get("ifAnd").equals("0")?true:false; //ifAnd 结果是求交集还是并集，AND还是OR，true代表交集
		
		//判断输入内容是否非空，并去除内容前后的空格
		if (indexPath != null) {
			indexPath = indexPath.trim();
		}
		if (titleQueryString != null) {
			titleQueryString = titleQueryString.trim();
		}
		if (contentQueryString != null) {
			contentQueryString = contentQueryString.trim();
		}
		// 判断查询添加是否为空，true表示非空
		boolean bIndexPath = (!"".equals(indexPath) && null != indexPath);
		boolean bTitleQueryString = (!"".equals(titleQueryString) && null != titleQueryString);
		boolean bContentQueryString = (!"".equals(contentQueryString) && null != contentQueryString);
		if(!bIndexPath){
			System.err.println("索引存储目录不能为空！ ");
			error_msg = "索引存储目录不能为空！";
			return null;
		}		
		if (!bTitleQueryString && !bContentQueryString) {
			System.err.println("至少要有一个查询条件 ");
			error_msg = "至少要有一个查询条件 ";
			return null;
		}

		//实现多字段多关键字查询，多关键字以空白字符分隔
		ArrayList<String> titleQueryList = null;
		ArrayList<String> contentQueryList = null;		
		if(bTitleQueryString){
			//以空白字符分割,必须用ArrayList而不能用List，因为后面用了addAll函数，不支持List
			titleQueryList = new ArrayList<String>(Arrays.asList(titleQueryString.split("\\s+")));
			Iterator<String> ite =  titleQueryList.listIterator();
			while(ite.hasNext()){
				String s =ite.next();
				//查询条件不能以*和?开头
				if(s.startsWith("*")||s.startsWith("?")){
					System.err.println("不支持以*或者?号开头的通配符查询 ");
					error_msg = "不支持以*或者?号开头的通配符查询 ";
					return null;
				}
				if("".equals(s)||null == s){
					ite.remove();
				}
			}		
		}
			
		if(bContentQueryString){				
			//以空白字符分割		
			contentQueryList = new ArrayList<String>(Arrays.asList(contentQueryString.split("\\s+")));
			Iterator<String> ite =  contentQueryList.listIterator();
			while(ite.hasNext()){
				String s =ite.next(); 
				//查询条件不能以*和?开头
				if(s.startsWith("*")||s.startsWith("?")){
					System.err.println("不支持以*或者?号开头的通配符查询 ");
					error_msg = "不支持以*或者?号开头的通配符查询 ";
					return null;
				}
				//过滤掉空字符串
				if("".equals(s)||null == s){
					ite.remove();
				}
			}		
		}
		
		//以空白字符分割	indexpath	
		//建立多索引搜索器
		List<String> indexPathList = Arrays.asList(indexPath.split("\\s+"));
		IndexReader[] readerArray = new IndexReader[indexPathList.size()];
		for (int i = 0; i < readerArray.length; i++) {
			readerArray[i] = ( DirectoryReader.open(FSDirectory.open(new File(indexPathList.get(i)))));
		}
		MultiReader multiReader = new MultiReader(readerArray);
		
	/*	IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(
				indexPath)));*/
		IndexSearcher searcher = new IndexSearcher(multiReader);
		// 创建分析器,默认最细粒度划分方式
		Analyzer analyzer = new IKAnalyzer();
		Query query;
		Occur occur = Occur.MUST;
		
		// 如果titleQueryString为空，则只按照contentQueryString查询
		if (!bTitleQueryString){
			
			 //设置查找条件字符串数组
			 //要查找的字符串数组			
			 String [] contentQueryArray =  contentQueryList.toArray(new String[0]);			 
		     //待查找字符串对应的字段
		     String[] contentFieldsArray= new String[contentQueryArray.length];
		     Occur[] occ = new Occur[contentQueryArray.length];
		     for (int i = 0; i < contentFieldsArray.length; i++) {
		        	contentFieldsArray[i] = "contents";
		        	occ[i] = occur;
			 }	
		     query = MultiFieldQueryParser.parse(Version.LUCENE_47, contentQueryArray,
		    		 contentFieldsArray, occ, analyzer);
		     if(!"all".equals(titleSuffix)){//查询特定类型文档			    
		    	 //这里使用termQuery，termQuery对query结果进行过滤，仅取满足条件的项
		    	 if("pdf".equals(titleSuffix)||"txt".equals(titleSuffix)){//pdf或者txt文档
			    	  Query termQuery = new TermQuery(new Term("suffix",titleSuffix));
			    	  Query query1 = query;
			    	  BooleanQuery boolQuery = new BooleanQuery();		    	  
			    	  boolQuery.add(termQuery,BooleanClause.Occur.MUST);
			    	  boolQuery.add(query1,BooleanClause.Occur.MUST);
			    	  query = boolQuery;
		    	 }else{//word、excel或者ppt文档，有两种类型
		    		 Query termQuery1 = new TermQuery(new Term("suffix",titleSuffix));//查询doc、xls、ppt
		    		 Query termQuery2 = new TermQuery(new Term("suffix",titleSuffix+"x"));//查询docx、xls、pptx
		    		 //doc\docx是或的关系
		    		 BooleanQuery boolQuery = new BooleanQuery();
		    		 boolQuery.add(termQuery1,BooleanClause.Occur.SHOULD);
			    	 boolQuery.add(termQuery2,BooleanClause.Occur.SHOULD);
		    		 //合并boolQuery与query
			    	 BooleanQuery boolQueryTotal = new BooleanQuery();
			    	 Query query1 = query;
			    	 boolQueryTotal.add(boolQuery,BooleanClause.Occur.MUST);
			    	 boolQueryTotal.add(query1,BooleanClause.Occur.MUST);
			    	 query = boolQueryTotal;   	 
		    	 }
		     }
		}else if(!bContentQueryString){//按内容查询为空
			 //设置查找条件字符串数组
			 //要查找的字符串数组			
			 String [] titleQueryArray =  titleQueryList.toArray(new String[0]);			 
		     //待查找字符串对应的字段
		     String[] titleFieldsArray= new String[titleQueryArray.length];
		     Occur[] occ = new Occur[titleQueryArray.length];
		     for (int i = 0; i < titleFieldsArray.length; i++) {
		        	titleFieldsArray[i] = "titleNoSuffix";
		        	occ[i] = occur;
			 }		     
		     query = MultiFieldQueryParser.parse(Version.LUCENE_47, titleQueryArray,
		    		 titleFieldsArray, occ, analyzer);
		     if(!"all".equals(titleSuffix)){//查询特定类型文档			    
		    	 //这里使用termQuery，termQuery对query结果进行过滤，仅取满足条件的项
		    	 if("pdf".equals(titleSuffix)||"txt".equals(titleSuffix)){//pdf或者txt文档
			    	  Query termQuery = new TermQuery(new Term("suffix",titleSuffix));
			    	  Query query1 = query;
			    	  BooleanQuery boolQuery = new BooleanQuery();		    	  
			    	  boolQuery.add(termQuery,BooleanClause.Occur.MUST);
			    	  boolQuery.add(query1,BooleanClause.Occur.MUST);
			    	  query = boolQuery;
		    	 }else{//word、excel或者ppt文档，有两种类型
		    		 Query termQuery1 = new TermQuery(new Term("suffix",titleSuffix));//查询doc、xls、ppt
		    		 Query termQuery2 = new TermQuery(new Term("suffix",titleSuffix+"x"));//查询docx、xls、pptx
		    		 //doc\docx是或的关系
		    		 BooleanQuery boolQuery = new BooleanQuery();
		    		 boolQuery.add(termQuery1,BooleanClause.Occur.SHOULD);
			    	 boolQuery.add(termQuery2,BooleanClause.Occur.SHOULD);
		    		 //合并boolQuery与query
			    	 BooleanQuery boolQueryTotal = new BooleanQuery();
			    	 Query query1 = query;
			    	 boolQueryTotal.add(boolQuery,BooleanClause.Occur.MUST);
			    	 boolQueryTotal.add(query1,BooleanClause.Occur.MUST);
			    	 query = boolQueryTotal;   	 
		    	 }
		     }
		}else{//titleNoSuffix、contents都不为空
			
			 int titleCount = titleQueryList.size();
			 
			 titleQueryList.addAll(contentQueryList);
			 
			 List<String> totalQueryList = titleQueryList;
			 String [] totalQueryArray =  totalQueryList.toArray(new String[0]);
		     //待查找字符串对应的字段			 			 
		     String[] totalFieldsArray= new String[totalQueryArray.length];
		     Occur[] occ = new Occur[totalQueryArray.length];
			 for (int i = 0; i < totalFieldsArray.length; i++) {
				if(i< titleCount){
					totalFieldsArray[i] = "titleNoSuffix";
				}else{
					totalFieldsArray[i] = "contents";
				}
				occ[i] = occur;		   
			}
			 query = MultiFieldQueryParser.parse(Version.LUCENE_47, totalQueryArray,
					 totalFieldsArray, occ, analyzer);
			
			 if(!"all".equals(titleSuffix)){//查询特定类型文档			    
		    	 //这里使用termQuery，termQuery对query结果进行过滤，仅取满足条件的项
		    	 if("pdf".equals(titleSuffix)||"txt".equals(titleSuffix)){//pdf或者txt文档
			    	  Query termQuery = new TermQuery(new Term("suffix",titleSuffix));
			    	  Query query1 = query;
			    	  BooleanQuery boolQuery = new BooleanQuery();		    	  
			    	  boolQuery.add(termQuery,BooleanClause.Occur.MUST);
			    	  boolQuery.add(query1,BooleanClause.Occur.MUST);
			    	  query = boolQuery;
		    	 }else{//word、excel或者ppt文档，有两种类型
		    		 Query termQuery1 = new TermQuery(new Term("suffix",titleSuffix));//查询doc、xls、ppt
		    		 Query termQuery2 = new TermQuery(new Term("suffix",titleSuffix+"x"));//查询docx、xls、pptx
		    		 //doc\docx是或的关系
		    		 BooleanQuery boolQuery = new BooleanQuery();
		    		 boolQuery.add(termQuery1,BooleanClause.Occur.SHOULD);
			    	 boolQuery.add(termQuery2,BooleanClause.Occur.SHOULD);
		    		 //合并boolQuery与query
			    	 BooleanQuery boolQueryTotal = new BooleanQuery();
			    	 Query query1 = query;
			    	 boolQueryTotal.add(boolQuery,BooleanClause.Occur.MUST);
			    	 boolQueryTotal.add(query1,BooleanClause.Occur.MUST);
			    	 query = boolQueryTotal;   	 
		    	 }
		     }
		}
        
		 System.out.println("查询关键字: " + query.toString());	
		 Page page = doPagingSearch(params,searcher, query,analyzer);
		 multiReader.close();
		 return page;
		
		/*if(bTitleQueryString&&(titleQueryString.startsWith("*")||titleQueryString.startsWith("?"))){
			System.err.println("不支持以*或者?号开头的通配符查询 ");
			error_msg = "不支持以*或者?号开头的通配符查询 ";
			return null;
		}
		
		if(bContentQueryString&&(contentQueryString.startsWith("*")||contentQueryString.startsWith("?"))){
			System.err.println("不支持以*或者?号开头的通配符查询 ");
			error_msg = "不支持以*或者?号开头的通配符查询 ";
			return null;
		}*/
		
		
	/*	// 如果titleQueryString为空，则只按照contentQueryString查询
		if (!bTitleQueryString) {

			String field = "contents";
			QueryParser parser = new QueryParser(Version.LUCENE_47, field,
					analyzer);
			query = parser.parse(contentQueryString);
			System.out.println("查询关键字: " +field +": "+ query.toString(field));

		} else if (!bContentQueryString) {
			// 如果contentQueryString为空，则只按照titleQueryString查询

			String field = "title";
			QueryParser parser = new QueryParser(Version.LUCENE_47, field,
					analyzer);
			query = parser.parse(titleQueryString);
			System.out.println("查询关键字: " +field +": "+ query.toString(field));

		} else {// 两个条件组合查询

			// 要查找的字符串数组(标题和内容)
			String[] stringQuery = { titleQueryString, contentQueryString };
			// 待查找字符串对应的字段
			String[] fields = { "title", "contents" };
			// Occur.MUST表示对应字段必须有查询值， Occur.MUST_NOT
			// 表示对应字段必须没有查询值，Occur.SHOULD表示对应字段应该存在查询值（但不是必须）
			// 两个MUST表示求查询条件的交集
			Occur[] occ = new Occur[2];
			if(ifAnd){
				occ[0] = Occur.MUST;
				occ[1] =Occur.MUST ;
			}else{
				occ[0] = Occur.SHOULD;
				occ[1] =Occur.SHOULD ;
			}
			query = MultiFieldQueryParser.parse(Version.LUCENE_47, stringQuery,
					fields, occ, analyzer);
			System.out.println("查询关键字: " + query.toString());
						
		}*/

		
	}
	
	
	/**全文搜索函数辅助函数
	 * @param params
	 * @param searcher
	 * @param query
	 * @return
	 * @throws IOException
	 * @throws InvalidTokenOffsetsException 
	 */
	private static Page doPagingSearch(Map<String, Object> params,
			IndexSearcher searcher, Query query,Analyzer analyzer) throws IOException, InvalidTokenOffsetsException {

		 // 关键字高亮显示的html标签
		 SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
	     Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
		
		// 最多查询出MAX_PAGE页的结果
		TopDocs results = searcher.search(query, MAX_PAGE * HITS_PER_PAGE);
		ScoreDoc[] hits = results.scoreDocs;

		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " 个匹配文档");

		int totalCount = numTotalHits;
		String pageIndex = (String) params.get(Page.PAGE_NUM);
		// 第几页
		int pageNum = Integer.parseInt((pageIndex == null || "0"
				.equals(pageIndex)) ? "0" : pageIndex);
		// 每页显示条数
		int numPerPage = HITS_PER_PAGE;

		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (pageNum) * numPerPage;
		int end = (pageNum + 1) * numPerPage;

		end = Math.min(numTotalHits, end);

		List<Record> list = new ArrayList<Record>();
		for (int i = start; i < end; i++) {
			Record record = new Record();
			Document doc = searcher.doc(hits[i].doc);
					
			String path = doc.get("path");
			if (path != null) {			
				System.out.println((i + 1) + ". " + path);
				record.setPath(path);
			}

			String title = doc.get("title");
			if (title != null) {
				 // 标题增加高亮显示
				record.setTitle(title);
				TokenStream tokenStream2 = analyzer.tokenStream("title", new StringReader(doc.get("title")));
				String titleString = highlighter.getBestFragment(tokenStream2, doc.get("title"));
				System.out.println("   Title: " + doc.get("title"));
				if(null == titleString||"".equals(titleString)){
					record.setTitleWithCss(title);
				}else{
					record.setTitleWithCss(titleString);
				}
				
			}
			String lastMotified = doc.get("modified");
			if (lastMotified != null) {
				record.setLastModified(Long.parseLong(lastMotified));
			}
			list.add(record);

		}
		return new Page(pageNum + 1, numPerPage, totalCount, list);
	}
	
	
	private HashMap<String, Object> getParameterMap(HttpServletRequest request) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		// 得到的MAP
		@SuppressWarnings("unchecked")
		Map<String, String[]> properties = request.getParameterMap();
		// 返回的MAP
		String name = null;
		String value = null;
		Object valueObject = null;
		StringBuffer valueBuilder = null;
		for(Entry<String, String[]> entry : properties.entrySet()) {
			valueObject = entry.getValue();
			name = entry.getKey();
			if(valueObject instanceof String[]) {
				String[] values = (String[]) valueObject;
				valueBuilder = new StringBuffer();
				for(String val : values) {
					valueBuilder.append(val).append(",");
				}
				value = valueBuilder.substring(0, valueBuilder.length() - 1);
				
			} else if(StringUtil.isNullorEmpty(valueObject)) {
				value = "";
			} else {
				value = valueObject.toString();
			}
			paramMap.put(name, value);
		}
		return paramMap;
	}

	
}
