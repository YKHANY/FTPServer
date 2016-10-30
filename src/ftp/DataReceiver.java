package ftp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DataReceiver {

	private BufferedInputStream bin;
	private DataInputStream din;
	private DataOutputStream dout;
	
	public DataReceiver(){
		
	}
	
	public DataReceiver(BufferedInputStream bin){
		this.bin = bin;
	}
	
	public DataReceiver(DataInputStream din){
		this.din = din;
	}

	public BufferedInputStream getDin() {
		return bin;
	}

	public void setDin(BufferedInputStream bin) {
		this.bin = bin;
	}
	
	public void recvData(String currentPath,String filename){
		FileOutputStream fio = null;
		File file = new File(currentPath+filename);
		try {
			fio = new FileOutputStream(file);
			dout = new DataOutputStream(fio);
			int num;
			byte[] buf = new byte[2048];
			while((num = din.read(buf))>0){				
				dout.write(buf, 0, num);
				dout.flush();
			}   
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}finally{
			try {
				fio.close();
				dout.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
