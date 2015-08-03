package com.ddn.upes;
import java.util.ResourceBundle;
import java.util.Scanner;

class PrefixReader {

	PrefixReader(){
		//Debug.out(loadPrefixCodes(command).toString()); 
	}
	PrefixReader(String command){
		Debug.out(loadPrefixCodes(command).toString()); 
	}
	public StringBuffer loadPrefixCodes(String command){
		try
		{	
			String response = "";
			String commandSplit[] = command.split(" ");
			ResourceBundle bundle=ResourceBundle.getBundle("com.ddn.upes.PrefixCodes");
			for(String s:commandSplit){
				try{
					response+=bundle.getString(s)+"#";
				}catch(Exception ed){}
			}
			return new StringBuffer(response);
		}catch(Exception e) { Debug.out(e); }
		return null;
	}
	public static void main(String []arg)throws Exception{
		Scanner input = new Scanner(System.in);
		new PrefixReader(input.nextLine());
	}
}