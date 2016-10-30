package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ftp.Connection;

public class StartBtnListener implements ActionListener{
	private Connection conn;
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button.getText().equals("Server start")){
			button.setText("Server stop");
			conn = new Connection();
			conn.start();
		}else{
			button.setText("Server start");
			conn.stopThread();
		}
		
	}

}
