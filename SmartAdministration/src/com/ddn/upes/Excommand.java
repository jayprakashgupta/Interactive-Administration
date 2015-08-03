package com.ddn.upes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Excommand
 */
public class Excommand extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Excommand() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		PrefixCodesWithProcedures procList = (PrefixCodesWithProcedures)request.getAttribute("PREFIX_LIST_OBJECT");
		Debug.out("Proc list: "+procList);
		Integer class_sequence = (Integer)request.getAttribute("CLASS_SEQUENCE_NUMBER");
		Integer function_sequence = (Integer)request.getAttribute("FUNCTION_SEQUENCE_NUMBER");
		if(procList!= null && procList.size() > 1)
			showApproximateSolutionUI(response,procList);
		if(procList != null && procList.size() == 1)
			showExactSolution(request,response,class_sequence,function_sequence,procList.getProcedureCode(0),null,"");
		}catch(Exception ed){ Debug.out(ed);	}
	}
	
	private void showApproximateSolutionUI(HttpServletResponse response,PrefixCodesWithProcedures procList)throws Exception {
		PrintWriter out = response.getWriter();
		out.write("Bekaar ho tum");
	}
	
	private void showExactSolution(HttpServletRequest req,HttpServletResponse resp,Integer class_sequence, Integer function_sequence,String code,String []inputs,String response) throws Exception {
		PrintWriter out = resp.getWriter();
		if(inputs == null){
			XmlDefinedClass xmlclass = new ProcedureCall(out, this.getServletContext(), class_sequence, function_sequence).executeProcedure(code);
			req.setAttribute("PROCEDURE_INPUT_CALL", xmlclass);
			req.setAttribute("RESPONSE", "");
			req.setAttribute("UNIQUE_PROC_CODE", code);
			req.setAttribute("CLASS_SEQUENCE_NUMBER", class_sequence);
			req.setAttribute("FUNCTION_SEQUENCE_NUMBER", function_sequence);
			
			getServletContext().getRequestDispatcher("/InputHandler").forward(req, resp);
		}
		else
		{
			XmlDefinedClass xmlclass = new ProcedureCall(out, this.getServletContext(), class_sequence, function_sequence).executeProcedure(code);
			response = response+"<br>"+xmlclass.executeProcedure(inputs);
			function_sequence = function_sequence+1;
			ProcedureCall call = new ProcedureCall(out, this.getServletContext(), class_sequence, function_sequence);
			//Debug.out("CALL with function: "+function_sequence+" "+class_sequence+" "+code);
			xmlclass = call.executeProcedure(code);
			if(xmlclass == null){
				resp.sendRedirect("ShowResponse?MESSAGE=Your command has been executed.&response="+response);
				return;
			}
			req.setAttribute("PROCEDURE_INPUT_CALL", xmlclass);
			req.setAttribute("RESPONSE", response);
			req.setAttribute("RESPONSE", "");
			req.setAttribute("UNIQUE_PROC_CODE", code);
			req.setAttribute("CLASS_SEQUENCE_NUMBER", call.class_seq+1);
			req.setAttribute("FUNCTION_SEQUENCE_NUMBER", call.func_seq+1);
			getServletContext().getRequestDispatcher("/InputHandler").forward(req, resp);
		}
		//Debug.out(xmlclass.className+" "+xmlclass.functionName+" "+procList.getProcedureCode(0));
		//xmlclass.printAll();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
				int input_size = Integer.parseInt(request.getParameter("INPUT_SIZE"));
				String []inputs = new String[input_size];
				for(int i=0;i<input_size;i++){
					inputs[i] = request.getParameter("inputP"+i);
					if(inputs[i].equalsIgnoreCase("/"))
						inputs[i] = getServletContext().getRealPath("");
				}
				String unique_proc_code = request.getParameter("UNIQUE_PROC_CODE");
				Integer class_sequence = new Integer(request.getParameter("CLASS_SEQUENCE_NUMBER"));
				Integer function_sequence = new Integer(request.getParameter("FUNCTION_SEQUENCE_NUMBER"));
				String responseS = (String)request.getParameter("RESPONSE");
				showExactSolution(request, response, class_sequence, function_sequence, unique_proc_code, inputs,responseS);
		} catch (Exception e) {	}
	}

}
