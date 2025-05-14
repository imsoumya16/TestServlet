/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import ConnectionDB.DBPool;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author WINDOWS
 */
public class FetchDataDB extends HttpServlet {

        private final DBPool dbPool = new DBPool();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type to JSON if you want to return JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        CallableStatement stmt = null;

        try {
            // Get the connection from the pool
            conn = dbPool.getConnection(); // Assuming getConnection() returns a valid connection from your pool
            
            // Check if the connection is valid
            if (conn != null && !conn.isClosed()) {
                // Prepare your stored procedure call here
                // For example, calling a stored procedure with no parameters
                String storedProc = "{call Proc_Picky_TemplateDetails_Demo()}"; // Replace with your actual SP name

                // Create a CallableStatement
                stmt = conn.prepareCall(storedProc);

                // Execute the stored procedure
                boolean hasResults = stmt.execute();

                // Assuming your SP returns a result set
                if (hasResults) {
                    ResultSet rs = stmt.getResultSet();
                    // Process the result set here and send the result in JSON format
                    JSONObject jsonResponse = new JSONObject();
                    while (rs.next()) {
                        // Assuming the SP returns a single column, you can adjust as needed
                        jsonResponse.put("data", rs.getString(1)); // Adjust based on your SP output
                    }

                    // Send the JSON response back
                    out.println(jsonResponse.toString());
                } else {
                    out.println("{\"status\":\"success\",\"message\":\"Stored Procedure executed successfully\"}");
                }
            } else {
                out.println("{\"status\":\"error\",\"message\":\"Failed to establish database connection\"}");
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
            out.println("{\"status\":\"error\",\"message\":\"Database error occurred: " + e.getMessage() + "\"}");
        } finally {
            // Close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
