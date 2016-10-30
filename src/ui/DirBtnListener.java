package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;

public class DirBtnListener implements ActionListener {

	private DefaultListModel<String> listModel;
	private JFrame frame;
	private JList list;
	public DirBtnListener(JFrame frame, DefaultListModel<String> listModel,JList list) {
		this.frame = frame;
		this.list = list;
		this.listModel = listModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JButton btn = (JButton) e.getSource();

		if (btn.getText().equals("add")) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showOpenDialog(frame);
			listModel.addElement(fileChooser.getSelectedFile().getAbsolutePath());
		} else if (btn.getText().equals("remove")) {
			if(listModel.getSize() > 0){
				int index = list.getSelectedIndex();
				listModel.remove(index);
			}
		} else if (btn.getText().equals("rename")) {

		}

	}

}
