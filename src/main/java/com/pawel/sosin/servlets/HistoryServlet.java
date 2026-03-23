/**
 * This package contains servlets, which are responsible for application logic
 */
package com.pawel.sosin.servlets;

import com.pawel.sosin.model.HistoryRecord;
import com.pawel.sosin.model.Model;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet responsible for showing the history table of operations done with Rail Fence Cipher
 *
 * @version 1.0
 * @author Pawel Sosin
 */
@WebServlet(name = "HistoryServlet", urlPatterns = {"/HistoryServlet"})
public class HistoryServlet extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        out.println("<html><head><title>History table</title></head><body><table border='1'>"
                + "<thead><tr><th>Operation</th><th>Rails</th><th>Input</th><th>Output</th></tr>");
        
        for(HistoryRecord re : model.recordList){
            out.println("<tr><td>" + re.operation() + "</td>"
                    + "<td>"+ re.rails() + "</td>"
                    + "<td>"+ re.input() + "</td>"
                    + "<td>"+ re.output() + "</td></tr>");
        }
        out.println("</table><a href='index.html'>Return</a></body></html>");
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
