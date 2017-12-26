package com.cums.demo.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.cums.demo.common.ProperitiesUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class DownloadFileServlet
 */
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		 //获得请求文件名  
        String filename = request.getParameter("filename");
        filename = new String(filename .getBytes("iso8859-1"),"utf-8"); 
               
        //读取目标文件，通过response将目标文件写到客户端  
        //获取目标文件的绝对路径  
      //  String fullFileName = getServletContext().getRealPath("/upload/" + filename);  
         String fullFileName = ProperitiesUtil.getProperities("catalog.properties").get("com.cums.uploadDir")+"\\"+filename;  
        System.out.println(fullFileName);
        File file = new File(fullFileName);
        if(!file.exists()){
        	request.setAttribute("errorMsg", "您要下载的资源已被删除！！");
        	request.getRequestDispatcher("/error.jsp").forward(request, response); 
			return;
        }
        //设置文件MIME类型  
        response.setContentType(getServletContext().getMimeType(filename));  
        //设置Content-Disposition  
        //文件名加双引号，防止firefox下载文件时文件名在有空格时显示不全
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes("utf-8"),"ISO8859-1")+"\"" ); 
        
        //读取文件  
        InputStream in = new FileInputStream(fullFileName);
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();            
      //创建缓冲区
        byte buffer[] = new byte[2048];
        int len = 0; //循环将输入流中的内容读取到缓冲区当中
        while((len=in.read(buffer))>0){
        	//输出缓冲区的内容到浏览器，实现文件下载 
        	out.write(buffer, 0, len); 
        	}
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close(); 
       /* retMap.put("flag", "success");
        String jsonData = JSONObject.fromObject(retMap).toString();		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(jsonData);*/
	} 

}
