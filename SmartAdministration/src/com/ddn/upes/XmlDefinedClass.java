package com.ddn.upes;

import java.util.ArrayList;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Scanner;

public class XmlDefinedClass {
	public String className = null;
	public String functionName = null;
	public String returnType = null;
	public ArrayList<String> paramType = null;
	public ArrayList<String> paramValue = null;
	public XmlDefinedClass() {
		this.paramType = new ArrayList<String>();
		this.paramValue = new ArrayList<String>();
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public void addParam(String type,String value){
		paramType.add(type);
		paramValue.add(value);
	}
	public void printAll(){
		Debug.out(this.className);
		Debug.out(this.functionName);
		for(int i=0 ;i<paramValue.size();i++) {
			Debug.out(paramType.get(i)+" "+paramValue.get(i));
		}
	}
	public void clearParams() {
		this.paramType.clear();
		this.paramValue.clear();
	}
	public String executeProcedure(String []inputs) {
		try
		{
			ClassLoader classloader= this.getClass().getClassLoader();
			Class<?> ClassName = classloader.loadClass(this.className);
			Object instance=ClassName.newInstance();
			Object objects[] = new Object[this.paramValue.size()];
			Class classes[] = new Class[this.paramType.size()];
			for(int i=0;i<this.paramType.size();i++) {
				if(this.paramType.get(i).equalsIgnoreCase("String")) {
					classes[i] = String.class;
					objects[i] = new String(inputs[i]);
				}
				else if(this.paramType.get(i).equalsIgnoreCase("Integer")) {
					classes[i] = Integer.class;
					objects[i] = new Integer(inputs[i]);
				}
			}
			
			Method method=ClassName.getMethod(this.functionName,classes);
			 String response = (String)method.invoke(instance,objects);
			 return response;
		}catch(Exception ed){Debug.out("executeProcedure"+ed);ed.printStackTrace();}
		return null;
	}
	public String[] getInputs() {
		try{
			Scanner sc = new Scanner(System.in);
			String []inputHandler = new String[this.paramType.size()];
			for(int i=0;i<this.paramType.size();i++) {
				Debug.out(this.paramValue.get(i));
				inputHandler[i] = sc.nextLine();
			}
			return inputHandler;
		}catch(Exception ed){Debug.out(ed);}
		return null;
	}
}