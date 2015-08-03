package com.ddn.upes;

import java.io.File;

public class ShowFiles {
	public String showFilesOnServer(String path){
		File file = new File(path);
		File []allfile = file.listFiles();
		String fileNames = "";
		for(File spfile:allfile){
			fileNames+= spfile.getName()+"<br>";
		}
		return fileNames;
	}
}
