package ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

// file IO , user data pwd
public class UserAuth {

	private Permission perm;
	private BufferedReader br;
	private BufferedWriter bw;
	private FileReader fr;
	private FileWriter fw;
	public final static String defaultPath = "/Users/younghan/Documents/";
	private String rootPath = null;
	private StringTokenizer strToken;
	private String user;
	// private String pwd;

	public UserAuth() {

	}

	public boolean userManage(String name, String password, String type) {
		String data;
		String indata;
		String dummy = "";
		try {
			fr = new FileReader(defaultPath + "config.dat");
			br = new BufferedReader(fr);
			while ((data = br.readLine()) != null) {
				strToken = new StringTokenizer(data, ":");
				indata = strToken.nextToken();
				if (name.equals(indata)) {
					if (type.equals("add"))
						return false;
					if (type.equals("change"))
						dummy += (name + ":" + password + "\r\n");
				} else {
					dummy += (data + "\r\n");
				}
			}
		}catch (NoSuchElementException nse) {
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fw = new FileWriter(defaultPath + "config.dat");
			bw = new BufferedWriter(fw);
			if (type.equals("add"))
				dummy += (name + ":" + password + "\r\n");
			bw.write(dummy);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

	public boolean checkUser(String user) {
		String data;
		String indata;
		try {

			fr = new FileReader(defaultPath + "config.dat");
			br = new BufferedReader(fr);

			while ((data = br.readLine()) != null) {
				System.out.println("data : " + data);
				strToken = new StringTokenizer(data, ":");
				indata = strToken.nextToken();

				if (user.equals(indata)) {
					this.user = user;
					return true;
				}
			}
		} catch (NoSuchElementException nse) {

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean checkPwd(String pwd) {
		String data;
		String indata;
		try {
			fr = new FileReader(defaultPath + "config.dat");
			br = new BufferedReader(fr);

			while ((data = br.readLine()) != null) {
				strToken = new StringTokenizer(data, ":");
				indata = strToken.nextToken();
				System.out.println("indata1 : " + indata);
				if (user != null && this.user.equals(indata)) {
					indata = strToken.nextToken();
					System.out.println("indata2 : " + indata);
					if (pwd.equals(indata)) {
						setOpenPath();
						perm = new Permission(this.user);
						perm.openPermission();
						return true;
					} else
						return false;
				}
			}

			System.out.println("loop break");
		}catch (NoSuchElementException nse) {
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean rootPathManage(String name, String path, String type) {
		String data;
		String indata;
		String dummy = "";
		try {

			fr = new FileReader(defaultPath + "userpath.dat");
			br = new BufferedReader(fr);
			while ((data = br.readLine()) != null) {
				strToken = new StringTokenizer(data, " ");
				indata = strToken.nextToken();
				if (name.equals(indata)) {
					if (type.equals("add"))
						return false;
					if (type.equals("change"))
						dummy += (name + " " + path + "\r\n");
				} else {
					dummy += (data + "\r\n");
				}
			}
		} catch (NoSuchElementException nse) {
			
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(defaultPath + "userpath.dat");
			bw = new BufferedWriter(fw);
			if (type.equals("add"))
				dummy += (name + " " + path + "\r\n");
			bw.write(dummy);
			bw.flush();
			System.out.println("path" + dummy);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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

	public void setOpenPath() {
		System.out.println("openPath in");
		String data;
		String indata;
		try {
			fr = new FileReader(defaultPath + "userpath.dat");
			br = new BufferedReader(fr);
			while ((data = br.readLine()) != null) {
				strToken = new StringTokenizer(data);
				indata = strToken.nextToken();
				System.out.println("user size : " + user.length());
				System.out.println("indata size : " + indata.length());
				if (user != null && this.user.equals(indata)) {
					rootPath = indata = strToken.nextToken();
					return;
				}
			}
		} catch (NoSuchElementException nse) {

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("open path out");
	}

	public String getRootPath() {
		return rootPath;
	}

	public Permission getPerm() {
		return perm;
	}

	public void setPerm(Permission perm) {
		this.perm = perm;
	}

	public String[] getUsers() {
		ArrayList<String> list = new ArrayList<String>();
		String data;
		String indata;
		try {

			fr = new FileReader(defaultPath + "config.dat");
			br = new BufferedReader(fr);

			while ((data = br.readLine()) != null) {
				strToken = new StringTokenizer(data, ":");
				indata = strToken.nextToken();
				list.add(indata);
			}
		} catch (NoSuchElementException nse) {

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String[] res = new String[list.size()];
		System.out.println("read !!!!");
		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
}

/*
 * package ftp;
 * 
 * import java.io.BufferedReader; import java.io.FileNotFoundException; import
 * java.io.FileReader; import java.io.IOException; import
 * java.util.StringTokenizer;
 * 
 * // file IO , user data pwd public class UserAuth {
 * 
 * private BufferedReader br; private FileReader fr; private final static String
 * defaultPath="/Users/younghan/Documents/config.dat"; private StringTokenizer
 * strToken; private String user; private String rootPath=null; // private
 * String pwd;
 * 
 * public UserAuth(){
 * 
 * }
 * 
 * 
 * public boolean checkUser(String user){ String data; String indata; try{
 * 
 * fr = new FileReader(defaultPath); br = new BufferedReader(fr);
 * 
 * while((data = br.readLine())!= null){ System.out.println("data : "+data);
 * strToken = new StringTokenizer(data,":"); indata = strToken.nextToken();
 * 
 * if(user.equals(indata)){ this.user = user; return true; } } }catch
 * (FileNotFoundException fnfe){ fnfe.printStackTrace(); }catch (IOException
 * ioe){ ioe.printStackTrace(); }finally{ try { fr.close(); br.close(); } catch
 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
 * return false; }
 * 
 * public boolean checkPwd(String pwd){ String data; String indata; try{ fr =
 * new FileReader(defaultPath); br = new BufferedReader(fr);
 * 
 * while((data = br.readLine())!= null){ strToken = new
 * StringTokenizer(data,":"); indata = strToken.nextToken(); System.out.println(
 * "indata1 : "+indata); if(user!= null && this.user.equals(indata)){ indata =
 * strToken.nextToken(); System.out.println("indata2 : "+indata);
 * if(pwd.equals(indata)){ return true; } } }
 * 
 * System.out.println("loop break"); }catch(IOException ioe){
 * ioe.printStackTrace(); }finally{ try { fr.close(); br.close(); } catch
 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } }
 * 
 * return false; } }
 */