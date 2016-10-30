package ui;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ftp.UserAuth;

public class FtpUI extends JFrame {
	int FileCheckNum=3;
	int DirCheckNum=3;
	int DirBtnNum=3;
	int UserBtnNum=3;
	int DirListNum=2;	
	int UserListNum=2;	
	
	JButton btn = new JButton("Server start");
	
	JPanel FileCheckPanel=new JPanel();
	JPanel DirCheckPanel=new JPanel();
	JPanel DirListPanel=new JPanel();
	JPanel UserListPanel=new JPanel();
	
	JLabel label1= new JLabel("<Files>");
	JLabel label2= new JLabel("<Directories>");
	JLabel label3= new JLabel("<Directories>");
	JLabel userlabel= new JLabel("<Users>");
	
	JCheckBox[] FilesCheck = new JCheckBox[FileCheckNum];
	JCheckBox[] DirCheck = new JCheckBox[DirCheckNum];
	
	JButton[] DirBtn = new JButton[DirBtnNum];
	JButton[] UserBtn = new JButton[UserBtnNum];
	
	String[] fileBtnMethod={"Read","Write","Delete"};	
	String[] DirBtnMethod={"add","remove","rename"};
	String[] UserBtnMethod={"add","remove","rename"};
	String[] directoryMethod={"Creat","Delete","List"};
	ArrayList<String> DirListAdd;
	ArrayList<String> UserListAdd;
	JList dirList;
	JList userList;
	
	
	public FtpUI() {
		setTitle("FTP Server");
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);

		Container contentPane=this.getContentPane();
		contentPane.setLayout(null);
	
		// init check box
		
		FileCheckPanel.setSize(90,120);
		FileCheckPanel.setLocation(210,5);	
		FileCheckPanel.setLayout(null);
		
		label1.setSize(50,15);
		FileCheckPanel.add(label1);
	
 
		for(int i=0; i<FileCheckNum; i++){
			FilesCheck[i]=new JCheckBox(fileBtnMethod[i]);
			FilesCheck[i].setBounds(5,(i+1)*20,80,20);
			FilesCheck[i].setBorderPainted(false);
			FileCheckPanel.add(FilesCheck[i]);
			FilesCheck[i].addItemListener(new FileCheckListener());
		}
		
		DirCheckPanel.setSize(90,120);
		DirCheckPanel.setLocation(210,125);	
		DirCheckPanel.setLayout(null);
		
		label2.setSize(80,15);
		DirCheckPanel.add(label2);
		
		for(int i=0; i<DirCheckNum; i++){
			DirCheck[i]=new JCheckBox(directoryMethod[i]);
			DirCheck[i].setBounds(5,(i+1)*20,80,20);	
			DirCheck[i].setBorderPainted(false);
			DirCheckPanel.add(DirCheck[i]);
			DirCheck[i].addItemListener(new DirCheckListener());
		}
	
	
		//
		DirListPanel.setSize(200,240);
		DirListPanel.setLocation(5,5);	
		DirListPanel.setLayout(null);
		DirListPanel.setBackground(Color.WHITE);
		label3.setSize(80, 15);
		DirListPanel.add(label3);
		
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		dirList = new JList<String>(listModel);
		
		dirList.setSelectedIndex(1);
		dirList.setSize(90, 90);
		dirList.setLocation(5 ,25);
		DirListPanel.add(dirList);
		
		JFrame frame = (JFrame) SwingUtilities.getRoot(this);		
		for(int i=0; i<DirBtnNum; i++){
			DirBtn[i]=new JButton(DirBtnMethod[i]);
			DirBtn[i].addActionListener(new DirBtnListener(frame,listModel,dirList));
			DirBtn[i].setLocation((i)*(60), 200);
			DirBtn[i].setSize(80, 30);
			DirListPanel.add(DirBtn[i]);
		}		
		
		
		
		UserListPanel.setSize(200,240);
		UserListPanel.setLocation(320,5);	
		UserListPanel.setLayout(null);
		UserListPanel.setBackground(Color.WHITE);
		userlabel.setSize(80, 15);
		UserListPanel.add(userlabel);
		
		DefaultListModel<String> usrlistModel = new DefaultListModel<String>();
		//
		UserAuth userAuth = new UserAuth();
		String[] users = userAuth.getUsers();
		for(String str : users){
			usrlistModel.addElement(str);
		}
		//
		
		userList = new JList<String>(usrlistModel);
		userList.setSelectedIndex(1);
		userList.setSize(90, 90);
		userList.setLocation(5 ,25);
		UserListPanel.add(userList);
		
		for(int i=0; i<UserBtnNum; i++){
			UserBtn[i]=new JButton(UserBtnMethod[i]);
			UserBtn[i].addActionListener(new UserBtnListener(userAuth,userList,usrlistModel));
			UserBtn[i].setLocation((i)*(60), 200);
			UserBtn[i].setSize(80, 30);
			UserListPanel.add(UserBtn[i]);
		}
		
		btn.setSize(120,30);
		btn.setLocation(200, 275);
		btn.addActionListener(new StartBtnListener());
		
		contentPane.add(FileCheckPanel);
		contentPane.add(DirCheckPanel);
		contentPane.add(DirListPanel);
		contentPane.add(UserListPanel);
		contentPane.add(btn);
		
		setSize(550,350);
		setVisible(true);
	}
}