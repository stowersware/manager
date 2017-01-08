package com.mycompany.examplemanager;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class AddSaleTransaction extends HttpServlet {  // JDK 6 and above only
	 
	   // The doGet() runs once per HTTP GET request to this servlet.
	   @Override
	   public void doGet(HttpServletRequest request, HttpServletResponse response)
	               throws ServletException, IOException {
	      // Set the MIME type for the response message
	      response.setContentType("text/html");
	      // Get a output writer to write the response message into the network socket
	      PrintWriter out = response.getWriter();
	 
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      try {
	         // Step 1: Allocate a database Connection object
	         conn = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/advgame?useSSL=false", "myuser", "xxxx"); // <== Check!
	            // database-URL(hostname, port, default database), username, password
	      // INSERT multiple records
	         String sqlInsert = "insert into transaction (employeeid, customerid, saleamount, date) values(?, ?, ?, ?)";
                 
                 // Step 2: Allocate a Statement object within the Connection
	         stmt = conn.prepareStatement(sqlInsert);
                 stmt.setInt(1, Integer.parseInt(request.getParameter("employee_id")));
                 stmt.setInt(2, Integer.parseInt(request.getParameter("customer_id")));
                 stmt.setDouble(3, Double.parseDouble(request.getParameter("sale_amount")));
                 stmt.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
                 
                 stmt.executeUpdate();
                 
                 String employeeid = request.getParameter("employee_id");
                 String customerid = request.getParameter("customer_id");
                 String saleamount = request.getParameter("sale_amount");
                 String date = request.getParameter("date");
                 
	         // Print an HTML page as the output of the query
	         out.println("<html><head><title>Add New Sales Transaction</title>"
                 + "<meta http-equiv=\"refresh\" content=\"3; url=http://localhost:9999/ExampleManager/dashboard.html\" />"
                 + "<link rel=\"stylesheet\" type = \"text/css\" href = \"redcss.css\" media = \"all\" /></head><body>");
	         out.println("<center><h1>New Sales Transaction Added!</h1>");
	         out.println("<table border = \"1\" cellpadding=\"5\" cellspacing=\"5\">"
                 + "<tr>\n" + "<th colspan=\"2\">Transaction Information:</th>\n" + "</tr>"
                 + "<tr><td>Employee ID:</td><td>" + employeeid + "</td></tr>"
                 + "<tr><td>Customer ID:</td><td>" + customerid + "</td></tr>"
                 + "<tr><td>Sale Amount:</td><td>" + saleamount + "</td></tr>"
                 + "<tr><td>Date:</td><td>" + date + "</td></tr>"        
                 + "</table>"
                 + "<p>Please click <a href=\"http://localhost:9999/ExampleManager/dashboard.html\">here</a> if you do not return.</p>");	             
                 
                 out.println("</center></body></html>");
	     } catch (SQLException ex) {
	        ex.printStackTrace();
	     } finally {
	        out.close();  // Close the output writer
	        try {
	           // Step 5: Close the resources
	           if (stmt != null) stmt.close();
	           if (conn != null) conn.close();
	        } catch (SQLException ex) {
	           ex.printStackTrace();
	        }
	     }
	   }
	}