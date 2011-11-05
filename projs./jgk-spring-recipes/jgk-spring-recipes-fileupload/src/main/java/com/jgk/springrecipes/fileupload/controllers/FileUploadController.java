package com.jgk.springrecipes.fileupload.controllers;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jgk.springrecipes.fileupload.service.ImageFileService;

@Controller
public class FileUploadController {

	@Inject ImageFileService imageFileService;
	
	@RequestMapping(value="/gravy")
	public @ResponseBody String gravy() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>Gravy time</body></html>");
		return sb.toString();
	}

	@RequestMapping(value="/upload.form")
	public ModelAndView doiingSomesazeing(
				HttpServletRequest request,
				HttpServletResponse response,
				ModelAndView mov,
				FileUploadBean bean) {
//		System.out.println("HOWDY FRIEND");
//		System.out.println("imageFileService:"+imageFileService.getText());
//		System.out.println(bean);
//		System.out.println(bean.getDescription());
		
		if(bean.getFile()!=null) {
			System.out.println("DOING SOMETHING WITH THE FILE");
			imageFileService.archiveImageFileUpload(bean);
		} else {
			System.out.println("NO FILE PROVIDED");
		}
		mov.addObject("jed","Jed's friend");
		mov.setViewName("gotfile");
		return mov;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView listUploadedFiles(
			HttpServletRequest request,
			HttpServletResponse response,
			ModelAndView mov
			) {
		mov.addObject("stuff","there is stuff here");
		mov.addObject("ii",imageFileService.getImageInfoList());
		mov.setViewName("image.list");
		return mov;
	}

}
