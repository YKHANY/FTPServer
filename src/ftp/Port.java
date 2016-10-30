package ftp;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Port {

	private String ipAddr;
	private int port;
	
	public Port(){
		
	}
	
	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setAddress(String addressMsg){
		try{
			StringTokenizer token = new StringTokenizer(addressMsg, ",");
			int port1, port2;
			ipAddr = token.nextToken() +"." + token.nextToken() + "." + token.nextToken() + "." + token.nextToken();
			
			port1 = Integer.parseInt(token.nextToken());
			port2 = Integer.parseInt(token.nextToken());
			
			port = (port1 << 8) | port2;
		}catch (NoSuchElementException nse){
			
		}
	}
}
