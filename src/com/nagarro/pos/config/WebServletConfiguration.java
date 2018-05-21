package com.nagarro.pos.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.nagarro.pos.constant.Constant;
import com.nagarro.pos.constant.MyDoc;

/**
 * java config for dispatcher servlet
 */
@MyDoc(author = Constant.AUTHOR, date = Constant.CREATION_DATE, currentRevision = 1)
public class WebServletConfiguration implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext ctx) throws ServletException {
		final AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.register(SpringConfig.class);
		webCtx.setServletContext(ctx);
		final ServletRegistration.Dynamic servlet = ctx.addServlet("dispatcher", new DispatcherServlet(webCtx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}