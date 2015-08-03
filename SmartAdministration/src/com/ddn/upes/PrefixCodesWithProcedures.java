package com.ddn.upes;

import java.util.*;
import java.io.*;

import javax.servlet.ServletContext;

class PrefixCodesWithProcedures {

	public Properties prop;
	public Set<Object> keySet;
	private ArrayList<String> procList = null;
	public PrintWriter out = null;
	public ServletContext context = null;
	PrefixCodesWithProcedures(PrintWriter out,ServletContext context){
		procList = new ArrayList<String>();
		this.out = out;
		this.context = context;
	}
	public boolean comparePrefix(String prefixCode,String prefixCodeWithProc){
		try{
			boolean flag = false;
			String prefixBreak[] = prefixCode.split("#");
			String prefixProcBreak[] = prefixCodeWithProc.split("#");
			for(String str: prefixBreak){
				flag = false;
				for(int i=0;i<prefixProcBreak.length;i++){
					if(prefixProcBreak[i].equals(str)){
						flag = true;
						prefixProcBreak[i] = "-1";
						break;
					}
				}
				if(!flag)
					return false;
			}
			if(prefixCode.length() == prefixCodeWithProc.length()){
				procList.clear();
				procList.add(prefixCodeWithProc);
				return true;
			}
			else
				procList.add(prefixCodeWithProc);
		}catch(Exception ed){}
		return false;
	}
	public ArrayList<String> findProcedureSolution(String prefixCode)throws Exception{
		String key = null;
		for(Object obj:keySet){
			key = (String)obj;
			if(comparePrefix(prefixCode,(String)obj))
				break;
		}
////		for(String strp:procList) {
////				Debug.out(strp);
////				out.println(strp);
////		}
//		if(procList.size() == 1) {
//			new ProcedureCall(out,context).executeProcedure(new String(prop.getProperty(key)));
//		}
		return procList;
	}
	public void loadAllProperties()throws Exception {
		this.prop = new Properties();
		InputStream in = this.getClass().getResourceAsStream("PrefixCodesProcedures.properties");
		prop.load(in);
		keySet = prop.keySet();
	}
	
	public int size() {
		return procList.size();
	}
	
	public String get(int position) {
		return procList.get(position);
	}
	
	public String getProcedureCode(int position) {
		return prop.getProperty(procList.get(position));
	}
	
	public static void main(String []arg)throws Exception{
		//Scanner input = new Scanner(System.in);
		//new PrefixReader(input.nextLine());
		PrefixCodesWithProcedures codes = new PrefixCodesWithProcedures(null,null);
		codes.loadAllProperties();
		codes.findProcedureSolution("3#2#5#");
	}
}