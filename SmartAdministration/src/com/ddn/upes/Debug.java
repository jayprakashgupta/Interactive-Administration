package com.ddn.upes;
class Debug{
	public static void out(String message){
		System.out.println("Message: "+message);
	}
	public static void out(Exception message){
		System.out.println("Message: "+message);
	}
	public static void err(String message){
		System.out.println("Error: "+message);
	}
	public int test(Integer a,Integer b,Integer c)
	{
		return a+b+c;
	}
}