package com.ddn.upes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InputHandler
 */
public class InputHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InputHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String unique_proc = (String)request.getAttribute("UNIQUE_PROC_CODE");
		Integer class_sequence = (Integer)request.getAttribute("CLASS_SEQUENCE_NUMBER");
		Integer function_sequence = (Integer)request.getAttribute("FUNCTION_SEQUENCE_NUMBER");
		String responseS = (String)request.getParameter("RESPONSE");
		PrintWriter out = response.getWriter();
		out.print(getServletContext().getRealPath("CreateFile"));
		response.setContentType("text/html");
		out.print("<html><body><form action='excommand' method='post'> ");
		XmlDefinedClass xmlClass = (XmlDefinedClass)request.getAttribute("PROCEDURE_INPUT_CALL");
		for(int i = 0; i< xmlClass.paramType.size();i++){
			out.println(xmlClass.paramValue.get(i)+": <input type='text' name=inputP"+i+" /><br>");
		}
		out.println("<input type='hidden' name='RESPONSE' value='"+responseS+"' />");
		out.println("<input type='hidden' name='INPUT_SIZE' value='"+xmlClass.paramType.size()+"' />");
		out.println("<input type='hidden' name='UNIQUE_PROC_CODE' value='"+unique_proc+"' />");
		out.println("<input type='hidden' name='CLASS_SEQUENCE_NUMBER' value='"+(int)class_sequence+"' />");
		out.println("<input type='hidden' name='FUNCTION_SEQUENCE_NUMBER' value='"+((int)function_sequence)+"' />");
		out.print("<input type='submit' value='Submit' />");
		out.print("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
