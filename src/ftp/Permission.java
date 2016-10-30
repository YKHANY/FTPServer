package ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Permission {
	private int filePermission,directoryPermission;
	private FileReader fr;
	private FileWriter fw;
	private BufferedReader br;
	private BufferedWriter bw;
	private StringTokenizer strToken;
	private String user;
	/*int permission bit 00000000 00000000 00000000 00"rwdrwd" (00"File,Directory") 
	 * 00 = 000
	 * 01 = 001
	 * 02 = 010
	 * 03 = 011
	 * 04 = 100
	 * 05 = 101
	 * 06 = 110
	 * 07 = 111
	 */
	public Permission(String user){
		this.user=user;
	}
	public boolean permissionManage(String name,int permission,String type){
		String data;
		String indata;
		String dummy="";
		try{
			fr = new FileReader(UserAuth.defaultPath+"permission.dat");
			br = new BufferedReader(fr);
			while((data = br.readLine())!= null){
				strToken = new StringTokenizer(data,":");
				indata =  strToken.nextToken();
				if(name.equals(indata)){
					if(type.equals("add"))
						return false;
					if(type.equals("change"))
						dummy+=(name+":"+permission+"\r\n");
				}
				else{
					dummy+=(data+"\r\n");
				}
			}
		}catch (NoSuchElementException nse) {
			
		}catch (FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch (IOException ioe){
			ioe.printStackTrace();
		}finally{
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			fw = new FileWriter(UserAuth.defaultPath+"permission.dat");
			bw = new BufferedWriter(fw);
			if(type.equals("add"))
				dummy+=(name+":"+permission+"\r\n");
			bw.write(dummy);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				fw.close();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	public void openPermission(){
		String data;
		String indata;
		try {
			fr = new FileReader(UserAuth.defaultPath+"permission.dat");
			br = new BufferedReader(fr);
			while((data = br.readLine())!=null){
				strToken = new StringTokenizer(data,":");
				indata=strToken.nextToken();
				if(user!= null &&this.user.equals(indata)){
					indata=strToken.nextToken();
					//userPermission = Integer.parseByte(indata);
					directoryPermission = Integer.parseInt(indata) & 07;
					filePermission = (Integer.parseInt(indata) >> 3) & 07;
					System.out.printf("dir perm : %o\n",directoryPermission);
					System.out.printf("file perm : %o\n",filePermission);
					break; 
				}
			}
		}catch (NoSuchElementException nse) {
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	public boolean fileReaderPermission(){
		if((filePermission & 04)==04)
			return true;
		return false;
	}
	public boolean fileWriterPermission(){
		if((filePermission & 02)==02)
			return true;
		return false;
	}
	public boolean fileDeletePermission(){
		if((filePermission & 01)==01)
			return true;
		return false;
	}
	public boolean directoryReaderPermission(){
		if((directoryPermission & 04)==04)
			return true;
		return false;
	}
	public boolean directoryWriterPermission(){
		if((directoryPermission & 02)==02)
			return true;
		return false;
	}
	public boolean directoryDeletePermission(){
		if((directoryPermission & 01)==01)
			return true;
		return false;
	}
}
