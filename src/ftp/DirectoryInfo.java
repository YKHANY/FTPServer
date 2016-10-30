package ftp;

import java.io.File;

public class DirectoryInfo {
	
//	private String path;
	private String currentPath;
	private String names="";
	public DirectoryInfo(){
		
	}
	
	
	public String getCurrentPath() {
		return currentPath;
	}


	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}


	public DirectoryInfo(String currentPath){
		this.currentPath = currentPath;
	
	}
	
	public String pwd(){
		return currentPath;
	}
	
	public String cwd(String rootPath,String path){
		if(path.equals("..")){
			if(currentPath.equals(rootPath)){
				return rootPath;
			}else{
				int length = currentPath.length();
				currentPath = currentPath.substring(0, length-1);
				System.out.println(currentPath);
				int last = currentPath.lastIndexOf("/");
				currentPath = currentPath.substring(0,last)+"/";
				System.out.println(currentPath);
			}
			
		}else{
			File file = new File(currentPath+path);
			if(file.exists()){
				this.currentPath += path+"/";
				return currentPath;
			}
			System.out.println("not exists");
			return null;
		}
		return null;
	}
	
	public String searchDir(String path){
		names ="";
		File file = new File(path);
		File[] files = file.listFiles();
		for(File f : files){
			names += f.getName()+"\n";
		}
		names+= "\r\n";
		return names;
	}
	
	public boolean delete(String path){
		System.out.println(path);
		File file = new File(path);
		if(file.exists()){
			return file.delete();
		}
		return false;
	}
	
	public boolean deleteDirectory(String path){
		System.out.println(path);
		File file = new File(path);
		if(file.exists()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isDirectory()){
					deleteDirectory(f.getAbsolutePath());
				}else{
					f.delete();
				}
			}
			return file.delete();
		}
		return false;
	}

}
