package com.ddn.upes;
import java.util.ResourceBundle;
import java.util.Scanner;

class Tester {

	public static void main(String []arg)throws Exception{
		Scanner input = new Scanner(System.in);
		String strp ="";
		for(String s:arg)
			strp+=s+" ";
		String str = new PrefixReader().loadPrefixCodes(strp).toString();
		Debug.out(str);
		PrefixCodesWithProcedures codes = new PrefixCodesWithProcedures(null,null);
		codes.loadAllProperties();
		codes.findProcedureSolution(str);
	}
}