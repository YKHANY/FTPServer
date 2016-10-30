package ftp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Connection extends Thread {

	private ServerSocket serverSocket;
	private Socket socket;

	private int port;

	public Connection() {
		this.port = 14148;
	}

	public Connection(int port) {
		this.port = port;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// serversocket create
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("server open");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = serverSocket.accept();
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				ResponseMsg resMsg = new ResponseMsg("220 Service ready\r\n", writer);
				resMsg.sendStatus();
				System.out.println("client accept");
				CmdParser cmdParser = new CmdParser(socket);
				cmdParser.start();
			}catch(SocketException se){
				break;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {

		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
