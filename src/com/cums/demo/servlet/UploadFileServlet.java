package com.cums.demo.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.cums.demo.common.ProperitiesUtil;
import com.cums.demo.pojo.FileBean;


/**
 * Servlet implementation class UploadFileServlet
 */
public class UploadFileServlet extends HttpServlet {
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
		Map<String, Object> respMap = new HashMap<String, Object>();
		FileBean[] resultfile=null;
		ArrayList<FileBean> list = new ArrayList<FileBean>();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		//HttpSession session = request.getSession();
		//session.setAttribute("progressBar", 0); // 定义指定上传进度的Session变量
		String error = "";
		int maxSize = 1024 * 1024 * 1024; // 单个上传文件大小的上限
		DiskFileItemFactory factory = new DiskFileItemFactory(); // 基于磁盘文件项目创建一个工厂对象
		ServletFileUpload upload = new ServletFileUpload(factory); // 创建一个新的文件上传对象
		//解决上传名中的中文乱码
		upload.setHeaderEncoding("UTF-8");
		try {
			List items = upload.parseRequest(request);// 解析上传请求
			Iterator itr = items.iterator();// 枚举方法
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next(); // 获取FileItem对象
				if (!item.isFormField()) {// 判断是否为文件域
					if (item.getName() != null && !item.getName().equals("")) {// 判断是否选择了文件
						long upFileSize = item.getSize(); // 上传文件的大小
						String fileName = item.getName(); // 获取文件名
						//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
						//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
						fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
						if (upFileSize > maxSize) {
							error = "您上传的文件太大，请选择不超过600M的文件";
							break;
						}
						// 此时文件暂存在服务器的内存中
						File tempFile = new File(fileName);// 构造临时对象
						//获取上传文件夹路径
						String uploadPath = ProperitiesUtil.getProperities("catalog.properties").get("com.cums.uploadDir");
						File file1 = new File(uploadPath);
						// 判断上传文件的保存目录是否存在
						if (!file1.exists() && !file1.isDirectory()) { // 创建目录
							file1.mkdir();
						}
						File file = new File(uploadPath, tempFile.getName()); // 获取根目录对应的真实物理路径
						InputStream is = item.getInputStream();
						int buffer = 1024; // 定义缓冲区的大小
						int length = 0;
						byte[] b = new byte[buffer];
						double percent = 0;
						FileOutputStream fos = new FileOutputStream(file);
						while ((length = is.read(b)) != -1) {
							percent += length / (double) upFileSize * 100D; // 计算上传文件的百分比
							fos.write(b, 0, length); // 向文件输出流写读取的数据
							//session.setAttribute("progressBar",Math.round(percent)); // 将上传百分比保存到Session中
						}
						FileBean filebean=new FileBean();
						filebean.setFileName(fileName);
						filebean.setSavePath(file.getAbsolutePath());
						list.add(filebean);						
						fos.close();
						
					} else {
						error = "没有选择上传文件！";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			error = "上传文件出现错误：" + e.getMessage();
		}
		if (!"".equals(error)) {
			//request.setAttribute("error", error);
			respMap.put("flag", "fail");
			respMap.put("msg",error);
			/*request.getRequestDispatcher("error.jsp")
					.forward(request, response);*/
		} else {
			/*request.setAttribute("result", "文件上传成功！");
			request.getRequestDispatcher("upFile_deal.jsp").forward(request,
					response);*/
			respMap.put("files", list.toArray(new FileBean[0]));
			respMap.put("flag", "success");
		}
		String jsonData = JSONObject.fromObject(respMap).toString();
		response.getWriter().print(jsonData);
	}

}
