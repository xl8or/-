package com.jgk.springrecipes.fileupload.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.context.WebApplicationContext;

import com.jgk.springrecipes.fileupload.service.ImageFileService;

/**
 * Servlet implementation class StartupServlet
 */
public class StartupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartupServlet() {
        super();
    }

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String archiveLocation=config.getServletContext().getInitParameter("archive.location");
		if(archiveLocation==null) {
			throw new RuntimeException("missing web context param \"archive.location\"");
		}
		String f = config.getServletContext().getRealPath(archiveLocation);
		File archiveDir = new File(f);
//		Integer counter = 0;
//		File counterFile = new File(archiveDir, "counter");
//		if(!archiveDir.exists()) {
//			archiveDir.mkdirs();
//			
//		} 
//		if(!counterFile.exists()) {
//			try {
//				FileUtils.writeStringToFile(counterFile, counter+"");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		String numText = null;
//		try {
//			numText = FileUtils.readFileToString(counterFile).trim();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		counter = Integer.valueOf(numText);
//		config.getServletContext().setAttribute("image.counter.file", counterFile);
//		config.getServletContext().setAttribute("image.counter", counter);
//		WebApplicationContext ac = (WebApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		System.out.println("Setting SYSTEM PROPERTY \'"+ImageFileService.KEY_IMAGE_ARCHIVE_DIRECTORY_NAME+"\' to "+archiveDir.getAbsolutePath());
		System.setProperty(ImageFileService.KEY_IMAGE_ARCHIVE_DIRECTORY_NAME, archiveDir.getAbsolutePath());
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
