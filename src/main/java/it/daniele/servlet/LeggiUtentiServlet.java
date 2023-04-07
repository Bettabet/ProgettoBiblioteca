package it.daniele.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/leggi")
public class LeggiUtentiServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private Connection connection;
       
	public void init(ServletConfig config) throws ServletException {
		
		try {
			ServletContext context = config.getServletContext();

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM utente");
			PrintWriter out = response.getWriter();
			out.print("<table>");
			out.print("<tr>");
			out.print("<th>");
			out.println("ID");
			out.print("</th>");
			out.print("<th>");
			out.println("Nome");
			out.print("</th>");
			out.print("<th>");
			out.println("Cognome");
			out.print("</th>");
			out.print("<th>");
			out.println("Email ");
			out.print("</th>");
			out.print("</tr>");
			while (resultSet.next()) {
				out.println("<tr>");
				out.println("<td>");
				out.print(resultSet.getString(1));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(2));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(3));
				out.println("</td>");
				out.println("<td>");
				out.print(resultSet.getString(4));
				out.println("</td>");
				out.println("</tr>");
			}
			out.print("</table>");
			
			out.println();
			request.getRequestDispatcher("link.html").include(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
