package ftp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class DataSender {

	private BufferedReader reader;
	private PrintWriter writer;
	private DataOutputStream dout;
	private String data;
	
	public DataSender(){
		
	}
	
	public DataSender(PrintWriter writer){
		this.writer =  writer;
		
	}
	public DataSender(DataOutputStream dout){
		this.dout =  dout;
		
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String sendList(){
		System.out.println("DataSender : "+data);
		writer.write(data);
		writer.flush();
		return data;
	}
	
	public void sendData(String currentPath){
		File file = new File(currentPath+data);
		FileInputStream fio = null;
		
		byte[] buf = new byte[1024];
		try {
			fio = new FileInputStream(file);
			//int t = fio.read(buf);
			//System.out.println("!!!!!"+t);
			while((fio.read(buf)!= -1)){
				System.out.println("buf size : "+buf.length);
				dout.write(buf);
				dout.flush();
				buf = new byte[1024];
			}
		}catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				
				dout.close();
				fio.close();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
