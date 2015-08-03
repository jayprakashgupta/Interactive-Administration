package com.ddn.upes;

import java.io.File;

public class DeleteFile {
	public String deleteFile(String name,String destination){
		try{
			File f = new File(destination+"\\"+name);
			if(f.exists()){
				f.delete();
				return "File was deleted";
			}
			else
				throw new Exception("File does not exists");
		}catch(Exception ed){ return "File cannot be deleted due to +"+ed.getMessage();}
	}
}
