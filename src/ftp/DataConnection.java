package ftp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataConnection {
	
	private Port clientPort; 
	private Socket socket; 
	private PrintWriter writer;
	private DataOutputStream dout;
	private DataInputStream din;
	private DataSender dataSender;
	private DataReceiver dataReceiver;
	//private String cmd;
	
	public DataConnection()	{
		
	}
	
	public DataConnection(Port port){
		clientPort = port;
	}
	
	public Port getClientPort() {
		return clientPort;
	}

	public void setClientPort(Port clientPort) {
		this.clientPort = clientPort;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void connect(){
		try {
			System.out.println("connection start");
			//socket = new Socket(clientPort.getIpAddr(),clientPort.getPort(),InetAddress.getByName(null),20);
			socket = new Socket(clientPort.getIpAddr(),clientPort.getPort());
			
		//	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(socket.getLocalPort());
			System.out.println("data connection ok");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String sendList(String data){
		try {
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			dataSender = new DataSender(writer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataSender.setData(data);
		dataSender.sendList();
		return data;
	}
	
	public void sendData(String currentPath,String filename){
		try {
			dout = new DataOutputStream(socket.getOutputStream());
			dataSender = new DataSender(dout);
			dataSender.setData(filename);
			dataSender.sendData(currentPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void recvData(String currentPath,String filename){
		try {
			//bin = new BufferedInputStream(socket.getInputStream(), 1024);
			din = new DataInputStream(socket.getInputStream());
			dataReceiver = new DataReceiver(din);
			dataReceiver.recvData(currentPath,filename);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
