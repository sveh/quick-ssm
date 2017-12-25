package com.yeemin.sxf.framework;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Web初始化配置, 替代web.xml
 */
public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        Dynamic servlet
                = servletContext.addServlet("springMVC", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        // 使用Spring的字符编码过滤器
        servletContext.addFilter("encodingFilter",
                new CharacterEncodingFilter("UTF-8", true));
        servlet.setLoadOnStartup(1);
        ctx.register(SpringMVCConfiguration.class, DaoConfiguration.class);
        ctx.setServletContext(servletContext);
    }

}
