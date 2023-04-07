package it.daniele.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = "/creaUtente")
public class CreateUserServlet extends HttpServlet {
	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("INSERT INTO utente(nome,cognome,email) VALUES('" + firstName + "','" + lastName + "','"
					+ email + "')");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.print("<H1>HAI CREATO UN NUOVO UTENTE</H1>"); 
				request.getRequestDispatcher("link.html").include(request, response);
			} else {
				out.print("<H1>ERRORE NELLA CREAZIONE</H1>");
				request.getRequestDispatcher("link.html").include(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
