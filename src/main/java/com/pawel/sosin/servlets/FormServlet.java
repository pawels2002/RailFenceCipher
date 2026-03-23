/**
 * This package contains servlets, which are responsible for application logic
 */
package com.pawel.sosin.servlets;

import com.pawel.sosin.exception.InvalidTextException;
import com.pawel.sosin.exception.RailFenceCipherException;
import com.pawel.sosin.model.Model;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import java.util.regex.Pattern;

/**
 * Servlet responsible for handling the form, which encodes and decodes messages using Rail Fence Cipher
 * 
 * @version 1.0
 * @author Pawel Sosin 
 */
@WebServlet(name = "FormServlet", urlPatterns = {"/FormServlet"})
public class FormServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");

    ServletContext context = getServletContext();
    Model model = (Model) context.getAttribute("model");

    PrintWriter out = response.getWriter();

    int errorCount = 0;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if ("errorCount".equals(cookie.getName())) {
                try {
                    errorCount = Integer.parseInt(cookie.getValue());
                } catch (NumberFormatException e) {
                    errorCount = 0; 
                }
                break;
            }
        }
    }

    String message = request.getParameter("message");
    String railsString = request.getParameter("rails");
    String pattern = "^[0-9]+$";

    if (!Pattern.matches(pattern, railsString)) {
        errorCount++;
        Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
        response.addCookie(errorCookie);

        response.sendError(response.SC_BAD_REQUEST, "Number of rails should be greater than 2");
        return;
    }

    int rails = 2;
    try {
        rails = Integer.parseInt(railsString);
    } catch (NumberFormatException e) {
        errorCount++;
        Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
        response.addCookie(errorCookie);

        response.sendError(response.SC_BAD_REQUEST, "Number of rails should be greater than 2");
        return;
    }

    String method = request.getParameter("CipherMethod");

    if (method.equals("encode")) {
        try {
            out.println("<html><head><title>Encoded message</title></head>\n<body>\n<p>Encoded message: "
                    + model.encode(message, rails) + "</p>\n");
        } catch (RailFenceCipherException ex) {
            errorCount++;
            Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
            response.addCookie(errorCookie);

            response.sendError(response.SC_BAD_REQUEST, "Number of rails should be greater than 2");
        } catch (InvalidTextException ex) {
            errorCount++;
            Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
            response.addCookie(errorCookie);

            response.sendError(response.SC_BAD_REQUEST, "The text should consist of only English letters");
        }
    } else {
        try {
            out.println("<html><head><title>Decoded message</title></head>\n<body>\n<p>Decoded message: "
                    + model.decodeBF(message, rails) + "</p>\n");
        } catch (RailFenceCipherException ex) {
            errorCount++;
            Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
            response.addCookie(errorCookie);

            response.sendError(response.SC_BAD_REQUEST, "Number of rails should be greater than 2");
        } catch (InvalidTextException ex) {
            errorCount++;
            Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
            response.addCookie(errorCookie);

            response.sendError(response.SC_BAD_REQUEST, "The text should consist of only English letters");
        }
    }

    Cookie errorCookie = new Cookie("errorCount", String.valueOf(errorCount));
    response.addCookie(errorCookie);
    out.println("<p>Current error count: " + errorCount + "</p>");

    out.println("<a href='index.html'>Return</a>");
}

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
