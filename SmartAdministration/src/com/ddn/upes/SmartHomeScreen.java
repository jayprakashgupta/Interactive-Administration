package com.ddn.upes;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SmartHomeScreen
 */
public class SmartHomeScreen extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SmartHomeScreen() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out= response.getWriter();
		String strp= request.getParameter("query");
		String str = new PrefixReader().loadPrefixCodes(strp).toString();
		out.println(str);
		PrefixCodesWithProcedures codes = new PrefixCodesWithProcedures(out,getServletContext());
		try {
			codes.loadAllProperties();
			codes.findProcedureSolution(str);
			request.setAttribute("PREFIX_LIST_OBJECT", codes);
			request.setAttribute("CLASS_SEQUENCE_NUMBER", new Integer(1));
			request.setAttribute("FUNCTION_SEQUENCE_NUMBER", new Integer(1));
			
			getServletContext().getRequestDispatcher("/excommand").forward(request, response);
		} catch (Exception e) {
			Debug.out(e);
		}
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
