package com.ddn.upes;
import javax.servlet.ServletContext;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import java.io.*;
class ProcedureCall {
	public PrintWriter out;
	public ServletContext context;
	public Integer class_seq , func_seq;
	public ProcedureCall(PrintWriter out,ServletContext context,Integer class_seq,Integer func_seq) {
		this.out = out;
		this.context = context;
		this.class_seq = class_seq-1;
		this.func_seq = func_seq-1;
	}
	private XmlDefinedClass xmlDefinedClass = null;
	public XmlDefinedClass executeProcedure(String procedureId)throws Exception {
		InputStream is = this.getClass().getResourceAsStream("abc.xml");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(is);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName("Procedure");
		Element element;
		Node node;
		
		for(int i=0;i<nodeList.getLength();i++) {
			node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)	{
				element = (Element)node;
				if(element.hasAttribute("id") == true && element.getAttribute("id").toString().equals(procedureId)) {
					getProcedureClassNames(element);
				}
			}
		}
		//Debug.out("class func"+class_seq+" "+func_seq);
		return xmlDefinedClass;
	}
	
	public void getProcedureClassNames(Element elements) {
		NodeList nodeList = elements.getElementsByTagName("Class");
		Element element;
		Node node;
		while(class_seq < nodeList.getLength()){
			node = nodeList.item(class_seq);
			if(node.getNodeType() == Node.ELEMENT_NODE)	{
				element = (Element)node;
				//Debug.out(element.getAttribute("name"));
				xmlDefinedClass = new XmlDefinedClass();
				xmlDefinedClass.setClassName(element.getAttribute("name"));
				if(getProcedureFunctionNames(element)){	
					//Debug.out("getProcedureClassNames "+class_seq+" "+func_seq);
					return;
				}
				else {
					class_seq+=1;
					func_seq = 0;
					//Debug.out("getProcedureClassNames "+class_seq+" "+func_seq);
				}
			}
		}
		xmlDefinedClass = null;
	}
	
	public boolean getProcedureFunctionNames(Element elements) {
		NodeList nodeList = elements.getElementsByTagName("Function");
		Element element;
		Node node;
		if(func_seq >= nodeList.getLength())
			return false;
		
		node = nodeList.item(func_seq);
		if(node.getNodeType() == Node.ELEMENT_NODE)	{
			element = (Element)node;
			//Debug.out(element.getAttribute("name"));
			xmlDefinedClass.setFunctionName(element.getAttribute("name"));
			getProcedureParamsNames(element);
			return true;
			//xmlDefinedClass.printAll();
			}
		return false;
	}
	
	public void getProcedureParamsNames(Element elements) {
		NodeList nodeList = elements.getElementsByTagName("Params");
		Element element;
		Node node;
		for(int i=0;i<nodeList.getLength();i++) {
			node = nodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)	{
				element = (Element)node;
				//Debug.out(element.getAttribute("type"));
				xmlDefinedClass.addParam(element.getAttribute("type"),element.getAttribute("value"));
			}
		}
	}
	public static void main(String arg[])throws Exception {
		//new ProcedureCall(null,null).executeProcedure(new String("1001"));
	}
}