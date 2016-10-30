package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import ftp.Permission;
import ftp.UserAuth;

public class UserBtnListener implements ActionListener{

	DefaultListModel<String> listModel;
	JList<String> list;
	UserAuth userAuth;
	public UserBtnListener(UserAuth userAuth,JList<String> list,DefaultListModel<String> listModel){
		this.listModel = listModel;
		this.list = list;
		this.userAuth = userAuth;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton)e.getSource();
		if (btn.getText().equals("add")) {
			
		} else if (btn.getText().equals("remove")) {
			if(listModel.getSize() > 0){
				int index = list.getSelectedIndex();
				userAuth.userManage(list.getSelectedValue(), "", "");
				userAuth.rootPathManage(list.getSelectedValue(), "", "");
				Permission perm = new Permission(list.getSelectedValue());
				perm.permissionManage(list.getSelectedValue(), 0, "");
				listModel.remove(index);
			}
		} else if (btn.getText().equals("rename")) {

		}
		
	}

}
