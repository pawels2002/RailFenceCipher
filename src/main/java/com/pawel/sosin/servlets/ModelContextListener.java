/**
 * This package contains servlets, which are responsible for application logic
 */
package com.pawel.sosin.servlets;

import com.pawel.sosin.model.Model;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


/**
 * This class is a context listener that initializes and destroys application-wide resources.
 * Specifically, it sets up the Model instance to be used throughout the application.
 * 
 * @author Pawel Sosin
 * @version 1.0
 */
@WebListener
public class ModelContextListener implements ServletContextListener {
    
    /**
     * Initializes the application context by creating and setting a instance
     * as an attribute in the servlet context.
     * 
     * @param sce contains the servlet context
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("model", new Model());
    }
}