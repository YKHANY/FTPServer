package ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class CmdParser extends Thread {

	private PrintWriter writer;
	private BufferedReader reader;
	private Socket socket;
	private boolean loggin = false;
	private Port port;
	private DirectoryInfo dirInfo;

	public CmdParser() {

	}

	public CmdParser(Socket socket) {
		this.socket = socket;
		try {
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			writer = new PrintWriter(new OutputStreamWriter(os));
			reader = new BufferedReader(new InputStreamReader(is));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("filetrans run method");
		ResponseMsg resMsg = new ResponseMsg("", writer);
		StringTokenizer tokenizer;
		String cmd = null;
		String data = null;
		UserAuth userAuth = new UserAuth();
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("while read");
				try {
					cmd = reader.readLine();
					System.out.println(cmd);
					System.out.println("read ok");

					tokenizer = new StringTokenizer(cmd);
					cmd = tokenizer.nextToken();
					System.out.println(cmd);
					while (tokenizer.hasMoreTokens()) {
						data = tokenizer.nextToken();
						System.out.println(data);
					}
					System.out.println("length " + cmd.length());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchElementException nse) {

				}

				if (cmd.equals("USER")) {

					if (userAuth.checkUser(data)) {
						resMsg.setMsg("331 Password required\r\n");
						resMsg.sendStatus();
						loggin = true;
					} else {
						resMsg.setMsg("331 Password required\r\n");
						resMsg.sendStatus();
					}

				}
				if (cmd.equals("PASS")) {

					if (userAuth.checkPwd(data)) {
						dirInfo = new DirectoryInfo(userAuth.getRootPath());
						loggin = true;
						resMsg.setMsg("230 User logged in\r\n");
						resMsg.sendStatus();

					} else {
						loggin = false;
						resMsg.setMsg("530 Login incorrect\r\n");
						resMsg.sendStatus();
					}
				}
				if (cmd.equals("QUIT")) {
					resMsg.setMsg("221 Goodbye\r\n");
					resMsg.sendStatus();
					try {
						socket.close();
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						// e.printStackTrace();
					}

				}
				if (loggin) {
					if (cmd.equals("PORT")) {
						port = new Port();
						port.setAddress(data);

						resMsg.setMsg("200 PORT command succeded\r\n");
						resMsg.sendStatus();

					}
					if (cmd.equals("NLST")) {
						if (userAuth.getPerm().directoryReaderPermission()) {
							resMsg.setMsg("150 Open ASCII data mode connection\r\n");
							resMsg.sendStatus();
							System.out.println("IP : " + port.getIpAddr());
							System.out.println("port : " + port.getPort());

							DataConnection dataConnection = new DataConnection(port);
							dataConnection.connect();
							dataConnection.sendList(dirInfo.searchDir(dirInfo.getCurrentPath()));
							dataConnection.close();

							resMsg.setMsg("226 Closing data connection\r\n");
							resMsg.sendStatus();
						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}
					}

					if (cmd.equals("RETR")) {
						if (userAuth.getPerm().fileReaderPermission()) {
							resMsg.setMsg("150 Open ASCII data mode connection\r\n");
							resMsg.sendStatus();

							DataConnection dataConnection = new DataConnection(port);
							dataConnection.connect();
							dataConnection.sendData(dirInfo.getCurrentPath(), data);
							dataConnection.close();

							resMsg.setMsg("226 Closing data connection\r\n");
							resMsg.sendStatus();
						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}

					}

					if (cmd.equals("STOR")) {
						if (userAuth.getPerm().fileWriterPermission()) {
							resMsg.setMsg("150 Open ASCII data mode connection\r\n");
							resMsg.sendStatus();

							DataConnection dataConnection = new DataConnection(port);
							dataConnection.connect();
							dataConnection.recvData(dirInfo.getCurrentPath(), data);
							dataConnection.close();

							resMsg.setMsg("226 Closing data connection\r\n");
							resMsg.sendStatus();
						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}
					}

					if (cmd.equals("XPWD") || cmd.equals("PWD")) {
						if (userAuth.getPerm().directoryReaderPermission()) {
							resMsg.setMsg("257 Folder" + dirInfo.pwd() + "\r\n");
							resMsg.sendStatus();
						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}
					}

					if (cmd.equals("CWD")) {
						if (userAuth.getPerm().directoryReaderPermission()) {
							dirInfo.cwd(userAuth.getRootPath(), data);
							resMsg.setMsg("250 Change directory success" + dirInfo.pwd() + "\r\n");
							resMsg.sendStatus();
						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}
					}

					if (cmd.equals("DELE")) {
						if (userAuth.getPerm().fileDeletePermission()) {
							if (dirInfo.delete(dirInfo.getCurrentPath() + data)) {
								resMsg.setMsg("250 delete success\r\n");
								resMsg.sendStatus();
							} else {
								resMsg.setMsg("550 file not found\r\n");
								resMsg.sendStatus();
							}

						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}

					}

					if (cmd.equals("XRMD")) {
						if (userAuth.getPerm().directoryDeletePermission()) {
							if (dirInfo.deleteDirectory(dirInfo.getCurrentPath() + data)) {
								resMsg.setMsg("250 delete success\r\n");
								resMsg.sendStatus();
							} else {
								resMsg.setMsg("550 directory not found\r\n");
								resMsg.sendStatus();
							}

						} else {
							resMsg.setMsg("550 Permission denied\r\n");
							resMsg.sendStatus();
						}
					}
				} else {
					resMsg.setMsg("530 Please Login\r\n");
					resMsg.sendStatus();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}