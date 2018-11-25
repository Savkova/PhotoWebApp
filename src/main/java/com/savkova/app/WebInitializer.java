package com.savkova.app;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext servletCxt) throws ServletException {
        // Load Spring web application configuration
        AnnotationConfigWebApplicationContext cxt = new AnnotationConfigWebApplicationContext();
        cxt.register(AppConfig.class);
        cxt.setServletContext(servletCxt);

        // Create and register the DispatcherServlet
        DispatcherServlet servlet = new DispatcherServlet(cxt);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("dispatcher", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }

}
