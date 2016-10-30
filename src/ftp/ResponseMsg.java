package ftp;

import java.io.PrintWriter;

public class ResponseMsg {

	private String msg;  
	private PrintWriter writer;
	
	public ResponseMsg(){
	}
	
	public ResponseMsg(String msg, PrintWriter writer){
		this.msg = msg;
		this.writer = writer;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}  

	public PrintWriter getWriter() {
		return writer;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public String sendStatus(){
			System.out.println(msg);
			writer.write(msg);
			writer.flush();
		
		return msg;
	}
}
