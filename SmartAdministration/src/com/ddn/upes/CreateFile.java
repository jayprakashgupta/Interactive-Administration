package com.ddn.upes;


import java.io.*;
public class CreateFile{
	public void CreateFileWithName(String name,String destination){
		try{
			File file = new File(destination+"\\"+name);
			file.createNewFile();
		}catch(Exception e){Debug.out(e);}
	}
}