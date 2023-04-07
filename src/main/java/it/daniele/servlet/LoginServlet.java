package it.daniele.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet(urlPatterns="/login")

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		
		if(name.equalsIgnoreCase("admin") && password.equals("admin")){
		out.print("Benvenuto admin!");
		request.getRequestDispatcher("link.html").include(request, response);
		HttpSession session=request.getSession();
		session.setAttribute("name",name);
		}
		else{
			out.print("Mi dispiace, ma username o password non sono corretti, riprova");
			request.getRequestDispatcher("index.html").include(request, response);
		}
		out.close();
	}

}