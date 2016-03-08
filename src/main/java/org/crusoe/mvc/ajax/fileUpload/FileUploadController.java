package org.crusoe.mvc.ajax.fileUpload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/fileUpload")
public class FileUploadController {

	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public void uploadImage(HttpServletRequest request,
			@RequestParam(value = "upload", required = false) MultipartFile file, HttpServletResponse response) {

		// String fileName = file.getOriginalFilename();
		String fileType = file.getContentType();
		String extension = "";
		String callback = request.getParameter("CKEditorFuncNum");
		try {
			// InputStream fileInputStream = file.getInputStream();
			if (fileType.equals("image/pjpeg") || fileType.equals("image/jpeg")) {
				// IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg
				extension = ".jpg";
			} else if (fileType.equals("image/png") || fileType.equals("image/x-png")) {
				// IE6上传的png图片的headimageContentType是"image/x-png"
				extension = ".png";
			} else if (fileType.equals("image/gif")) {
				extension = ".gif";
			} else if (fileType.equals("image/bmp")) {
				extension = ".bmp";
			} else {

				String ret = "<script type=\"text/javascript\">";
				ret += "window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
						+ "'文件格式不正确(必须为.jpg/.gif/.bmp/.png文件');";
				ret += "</script>";

				// throw new
				// ActivitiException("文件格式不正确(必须为.jpg/.gif/.bmp/.png文件)");
			}

			if (file.getSize() > 600 * 1024) {
				String ret = "<script type=\"text/javascript\">";
				ret += "window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'文件大小不得大于600k');";
				ret += "</script>";

				// throw new ActivitiException("文件大小不得大于600k ");

			}

			String newFileName = java.util.UUID.randomUUID().toString();
			String filePath = this.getClass().getResource("/").getPath() + "../../uploadFile/images/" + newFileName
					+ extension;
			File localFile = new File(filePath);
			file.transferTo(localFile);

			String ret = "<script type=\"text/javascript\">";
			ret += "window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + request.getContextPath()
					+ "/uploadFile/images/" + localFile.getName() + "','')";
			ret += "</script>";
			try {
				PrintWriter out = response.getWriter();
				out.print(ret);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			// logger.error("error on deploy process, because of file input
			// stream");
			e.printStackTrace();
		}

	}

}
