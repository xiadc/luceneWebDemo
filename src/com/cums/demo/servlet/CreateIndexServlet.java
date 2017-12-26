package com.cums.demo.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlException;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.cums.demo.common.ProperitiesUtil;

/**
 * Servlet implementation class DemoServlet
 */
public class CreateIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> retMap = new HashMap<String, String>();
	//private static String returnMsg;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateIndexServlet() {
        super();
    }

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
		   // String indexPath = request.getParameter("indexPath");
		   // String docsPath = request.getParameter("docsPath");
		    String createType = request.getParameter("createType");  
		    String fileStr = request.getParameter("fileStr");
			//String docsPath = "d:\\Users\\xiadc\\Desktop\\test";		  
			//boolean ifCreate = true;
		   // String uploadPath = this.getServletContext().getRealPath("/upload");
		    String uploadPath = ProperitiesUtil.getProperities("catalog.properties").get("com.cums.uploadDir");
		   // String indexPath =  this.getServletContext().getRealPath(INDEX_PATH);
		    String indexPath =  ProperitiesUtil.getProperities("catalog.properties").get("com.cums.indexDir");
		    String returnMsg = "";
		    if("0".equals(createType)||"1".equals(createType)){
		    	 returnMsg =indexFiles(indexPath, fileStr, uploadPath, createType);
		    }else{
		    	 returnMsg =indexDocs(indexPath, uploadPath);
		    }
		   
			retMap.put("returnMsg",returnMsg);
			String jsonData = JSONObject.fromObject(retMap).toString();
		
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(jsonData);
	}
	
	/**索引多文件
	 * @param indexPath
	 * @param fileStr
	 * @param uploadPath
	 * @param createType
	 * @return
	 */
	public static String indexFiles(String indexPath,String fileStr,String uploadPath,String createType){
		
		StringBuilder returnMsg= new StringBuilder();
		
		if (fileStr == null) {
		      System.err.println("docsPath can not be null");
		      retMap.put("flag", "fail");
			  retMap.put("msg", "索引文件（夹）不能为空!");
		      return null;
		}
		
		fileStr = fileStr.trim();
		String[] fileNameArray = fileStr.split("\\*");
		if(fileNameArray.length == 1&&"".equals(fileNameArray[0])){
			  System.err.println("docsPath can not be null");
		      retMap.put("flag", "fail");
			  retMap.put("msg", "索引文件（夹）不能为空!");
		      return null;
		}
		
		  try{
			  System.out.println("Indexing to directory '" + indexPath + "'...");
			  returnMsg.append("Indexing to directory '" + indexPath + "'...\r\n");
			  Directory dir = FSDirectory.open(new File(indexPath));
			  //实现最细粒度切分算法
			  Analyzer analyzer = new IKAnalyzer();
		      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);		      
		      if ("1".equals(createType)) {//删除所有索引，并为当前上传文件重新创建索引
		          // Create a new index in the directory, removing any
		          // previously indexed documents:
		    	  config.setOpenMode(OpenMode.CREATE);
		       } else {//为当前上传文件添加索引
		          // Add new documents to an existing index:
		        	config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		        }
		      
		      IndexWriter iwriter = new IndexWriter(dir,config);
		      
		      String fileName = "";
		  	for (int i = 0; i < fileNameArray.length; i++) {
		  		fileName = uploadPath +"\\"+ fileNameArray[i];
		  		File file = new File(fileName);
		  		if (!file.exists() || !file.canRead()) {
				      System.out.println("Document directory '" +file.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
					  retMap.put("msg",file.getAbsolutePath() + "不存在或者不可读取！");
				      continue;
				}
		  		
		  		indexDocs(iwriter,file,returnMsg);
		  	}		           
		  	  iwriter.forceMerge(1); //合并索引文件至一条，达到优化索引的目的
		      iwriter.close();		    
		      retMap.put("flag", "success");
		      
		  }catch (Exception e) {
		    retMap.put("flag", "fail");
		    retMap.put("msg", "建立索引时出现错误！");			  
			e.printStackTrace();
		}
		  
		  return returnMsg.toString();
	}
	
	
	/**
	 * 创建索引文件入口函数
	 * @param indexPath 索引文件存放路径
	 * @param docsPath 需要创建索引的文件夹路径
	 *
	 */
	public static String indexDocs(String indexPath,String docsPath){
		
		StringBuilder returnMsg= new StringBuilder();
		
		if (docsPath == null) {
		      System.err.println("docsPath can not be null");
		      retMap.put("flag", "fail");
			  retMap.put("msg", "索引文件（夹）不能为空!");
		      return null;
		}
		
		File docDir = new File(docsPath);
		if (!docDir.exists() || !docDir.canRead()) {
		      System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
		      retMap.put("flag", "fail");
			  retMap.put("msg", "索引文件（夹）不存在或者不可读取！");
		      return null;
		}
		
		  Date start = new Date();
		  try{
			  System.out.println("Indexing to directory '" + indexPath + "'...");
			  returnMsg.append("Indexing to directory '" + indexPath + "'...\r\n");
			  Directory dir = FSDirectory.open(new File(indexPath));
			  //实现最细粒度切分算法
			  Analyzer analyzer = new IKAnalyzer(false);
		      IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_47, analyzer);		      		  
		      //清空所有已存在索引
		      config.setOpenMode(OpenMode.CREATE);		
		      
		      IndexWriter iwriter = new IndexWriter(dir,config);
		      indexDocs(iwriter,docDir,returnMsg);
		    
		      iwriter.forceMerge(1); //合并索引文件至一条，达到优化索引的目的
		      iwriter.close();
		      
		      Date end = new Date();
		      System.out.println(end.getTime() - start.getTime() + " total milliseconds");
		      returnMsg.append(end.getTime() - start.getTime() + " total milliseconds");
		      retMap.put("flag", "success");
		      
		  }catch (Exception e) {
		    retMap.put("flag", "fail");
		    retMap.put("msg", "建立索引时出现错误！");			  
			e.printStackTrace();
		}
		  
		  return returnMsg.toString();
	}
	
	/**创建索引辅助函数
	 * @param writer 
	 * @param file
	 * @throws Exception 
	 */
	private static void indexDocs(IndexWriter writer,File file,StringBuilder returnMsg) throws Exception {
		// do not try to index files that cannot be read
	    if (file.canRead()) {
	      if (file.isDirectory()) {
	        String[] files = file.list();
	        // an IO error could occur
	        if (files != null) {
	          for (int i = 0; i < files.length; i++) {
	            indexDocs(writer, new File(file, files[i]),returnMsg);
	          }
	        }
	      } else {

	        FileInputStream fis;
	        try {
	          fis = new FileInputStream(file);
	        } catch (FileNotFoundException fnfe) {
	          // at least on windows, some temporary files raise this exception with an "access denied" message
	          // checking if the file can be read doesn't help
	          fnfe.printStackTrace();
	          return;
	        }
	        
	          Document doc = new Document();
	          String fileName = file.getName();
	          String suffix ="";
	          String titleNoSuffix="";
	          Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
	          doc.add(pathField);	       
	          doc.add(new LongField("modified", file.lastModified(), Field.Store.YES));	
	          
	          //添加后缀名索引	         
	          suffix = fileName.substring(fileName.lastIndexOf(".") + 1);//对后缀索引	          
	          doc.add(new TextField("suffix", suffix,Field.Store.NO));  
	          
	          //对文件内容添加索引，只索引，不保存
	          if("doc".equals(suffix)){//如果该文件时doc文档
	        	  doc.add(new TextField("contents",readDoc(file),Field.Store.NO));
	          }else if("docx".equals(suffix)){
	        	  doc.add(new TextField("contents",readDocx(file),Field.Store.NO));
	          }else if("pdf".equals(suffix)){//如果该文件是pdf文件
	        	  doc.add(new TextField("contents",readPdf(file),Field.Store.NO));
	          }else if("xlsx".equals(suffix)){//excel文件
	        	  doc.add(new TextField("contents",readXlsx(file),Field.Store.NO));
	          }else if("xls".equals(suffix)){
	        	  doc.add(new TextField("contents",readXls(file),Field.Store.NO));
	          }else if("ppt".equals(suffix)){//ppt文件
	        	  doc.add(new TextField("contents",readPowerPoint(file),Field.Store.NO));
	          }else if("pptx".equals(suffix)){
	        	  doc.add(new TextField("contents",readPptx(file),Field.Store.NO));
	          }else if("txt".equals(suffix)){//普通txt文本
	        	  doc.add(new TextField("contents",readTxt(file),Field.Store.NO));
	          }else{
	        	  doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis, "utf-8"))));
	          }
	         
	          
	          
	          FieldType fieldType = new FieldType(); 
	          fieldType.setIndexed(false);//不索引
	          fieldType.setTokenized(false)	;//不分词
	          fieldType.setStored(true);//只存储	          	          
	          doc.add(new Field("title",file.getName(),fieldType));	//对文件名只存储不索引
	          
	          
	          //添加不包含后缀的文件名索引
	          int dot = fileName.lastIndexOf(".");
	          if(dot>-1&&dot <fileName.length()){//包含文件后缀名时，去掉后缀名
	        	  titleNoSuffix = fileName.substring(0, dot);
	          }else{//不包含时，返回原文件名
	        	  titleNoSuffix = file.getName();
	          }
	          doc.add(new TextField("titleNoSuffix",titleNoSuffix,Field.Store.NO));//对文件名（不含后缀）索引       	
	       
	          
	          
	          if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
	            // New index, so we just add the document (no old document can be there):
	            System.out.println("添加索引 " + fileName);
	            returnMsg.append("添加索引 " + fileName +"\r\n");
	            writer.addDocument(doc);
	          } else {
	            // Existing index (an old copy of this document may have been indexed) so 
	            // we use updateDocument instead to replace the old one matching the exact 
	            // path, if present:
	            System.out.println("更新或添加索引 " + fileName);
	            returnMsg.append("更新或添加索引 " + fileName +"\r\n");
	            writer.updateDocument(new Term("path", file.getPath()), doc);
	          }
	          fis.close();
	        
	      }
	    }
	}
	
	/**读取doc文件内容
	 * @param path
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static String readDoc(File file) throws FileNotFoundException, IOException{
		 StringBuffer content = new StringBuffer("");// 文档内容
		 FileInputStream fis = new FileInputStream(file);
	            HWPFDocument doc = new HWPFDocument(fis);
	            Range range = doc.getRange();
	            int paragraphCount = range.numParagraphs();// 段落
	            for (int i = 0; i < paragraphCount; i++) {// 遍历段落读取数据
	                Paragraph pp = range.getParagraph(i);
	                content.append(pp.text());
	            }

	        fis.close();
	        System.out.println(content.toString().trim());
	        return content.toString().trim();
	}
	
	/**读取docx文件
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static String readDocx(File file) throws FileNotFoundException, IOException{
		 StringBuffer content = new StringBuffer("");// 文档内容
		 FileInputStream fis = new FileInputStream(file);
	     XWPFDocument doc = new XWPFDocument(fis);
	     XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
	            String docString = extractor.getText();
	            content.append(docString);
	        fis.close();
	        System.out.println(content.toString().trim());
	        return content.toString().trim();
	}
	
	/**读取pdf文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static String readPdf(File file) throws Exception {
        StringBuffer content = new StringBuffer("");// 文档内容
        FileInputStream fis = new FileInputStream(file);
        RandomAccessBuffer rab = new RandomAccessBuffer(fis);
        PDFParser p = new PDFParser(rab);
        p.parse();
        PDFTextStripper ts = new PDFTextStripper();
        content.append(ts.getText(p.getPDDocument()));
        rab.close();
        fis.close();   
        System.out.println(content.toString().trim());
		
		//下面的写法也会出现  Warning: You did not close a PDF Document的警告上面的写法会出现
		/*StringBuffer content = new StringBuffer("");// 文档内容
		FileInputStream is = new FileInputStream(file);		  
		PDFTextStripper stripper = new PDFTextStripper();
		PDDocument  pdfDocument = PDDocument.load(is);		  
		StringWriter writer = new StringWriter();
		stripper.writeText(pdfDocument, writer);  
		content.append(writer.getBuffer().toString());
		is.close();
		System.out.println(content.toString().trim());*/
		
        return content.toString().trim();
    }
	
	/**读取xlsx文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static String readXlsx(File file) throws IOException {
		StringBuffer content = new StringBuffer();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		  //创建工作文档对象   
        FileInputStream fis= new FileInputStream(file);
		XSSFWorkbook xwb = new XSSFWorkbook(fis);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
			XSSFSheet xSheet = xwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				XSSFRow xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}
				// 循环列Cell
				for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
					XSSFCell xCell = xRow.getCell(cellNum);
					if (xCell == null) {
						continue;
					}
					if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						content.append(xCell.getBooleanCellValue());
					} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						content.append(xCell.getNumericCellValue());
					} else {
						content.append(xCell.getStringCellValue());
					}
				}
			}
		}
		System.out.println(content.toString().trim());
		fis.close();
		return content.toString();
	}

	/**读取xls文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static String readXls(File file) throws IOException {
		StringBuffer content = new StringBuffer();
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		  //创建工作文档对象   
        FileInputStream fis= new FileInputStream(file);
      
        HSSFWorkbook hwb = new HSSFWorkbook(fis);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hwb.getNumberOfSheets(); numSheet++) {
			HSSFSheet xSheet = hwb.getSheetAt(numSheet);
			if (xSheet == null) {
				continue;
			}
			// 循环行Row
			for (int rowNum = 0; rowNum <= xSheet.getLastRowNum(); rowNum++) {
				HSSFRow xRow = xSheet.getRow(rowNum);
				if (xRow == null) {
					continue;
				}
				// 循环列Cell
				for (int cellNum = 0; cellNum <= xRow.getLastCellNum(); cellNum++) {
					HSSFCell xCell = xRow.getCell(cellNum);
					if (xCell == null) {
						continue;
					}
					if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
						content.append(xCell.getBooleanCellValue());
					} else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
						content.append(xCell.getNumericCellValue());
					} else {
						content.append(xCell.getStringCellValue());
					}
				}
			}
		}
		System.out.println(content.toString().trim());
		fis.close();
		return content.toString();
	}
	/**
	 * 读取ppt文件
	 * 
	 * @param path
	 * @return
	 * @throws IOException 
	 */
	private static String readPowerPoint(File file) throws IOException {
		StringBuffer content = new StringBuffer("");
		InputStream inputStream = new FileInputStream(file);
		
			SlideShow ss = new SlideShow(new HSLFSlideShow(inputStream));// is
			// 为文件的InputStream，建立SlideShow
			Slide[] slides = ss.getSlides();// 获得每一张幻灯片
			for (int i = 0; i < slides.length; i++) {
				TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
				for (int j = 0; j < t.length; j++) {
					content.append(t[j].getText());// 这里会将文字内容加到content中去
				}
			}
		inputStream.close();
		System.out.println(content.toString().trim());
		return content.toString();
	}  
	
	 /**读取pptx文件
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws OpenXML4JException
	 * @throws XmlException
	 */
	public static String readPptx(File file) throws InvalidFormatException, IOException, OpenXML4JException, XmlException {
		 InputStream inputStream = new FileInputStream(file);
		 POITextExtractor extractor = ExtractorFactory.createExtractor(inputStream);  
		   String reusltString = extractor.getText();
		   inputStream.close();
		   System.out.println(reusltString.toString().trim());
	        return reusltString;
	    }
	/**
	* 读取txt文件，GBK编码
	* @param path
	* @return
	* @throws IOException
	*/
	private static String readTxt(File file) throws IOException {
		StringBuffer sb = new StringBuffer("");
		InputStream is = new FileInputStream(file);
		// 必须设置成GBK，否则将出现乱码
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"GBK"));
		try {
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\r");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		is.close();
		System.out.println(sb.toString().trim());
		return sb.toString().trim();
	}
	
	
}
