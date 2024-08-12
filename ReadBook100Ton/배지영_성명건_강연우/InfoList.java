import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

public class InfoList extends JFrame {
	private JButton btnAdd;
	private JButton btnDel;
	private JButton btnClose;
	
	private ListPanel slave;
	private ReadStart king;
	private ReadBook owner;
	
	
	public InfoList(ReadStart king, ReadBook owner, ListPanel slave) {
		this.king = king;
		this.owner = owner;
		this.slave = slave;
		
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		btnAdd = new JButton("추가");
		btnDel = new JButton("삭제");
		btnClose = new JButton("닫기");
	}
	private void setDisplay() {
		JPanel pnlBottom = new JPanel();
		pnlBottom.add(btnAdd);
		pnlBottom.add(btnDel);
		pnlBottom.add(btnClose);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(slave, BorderLayout.CENTER);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlBottom, BorderLayout.SOUTH);
	}
	private void addListeners() {
		ActionListener alistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(ae.getSource() == btnAdd) {
					new InfoInput(king, owner, InfoList.this);
				} else if(ae.getSource() == btnDel) {
					int idx = slave.getList().getSelectedIndex();
					if(idx >= 0) {
						slave.removeModel(idx);
					}
				} else {
					close();
				}
			}
		};
		btnAdd.addActionListener(alistener);
		btnDel.addActionListener(alistener);
		btnClose.addActionListener(alistener);
		
		WindowListener wListener = new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we) {
				close();
			}
		};
		addWindowListener(wListener);
	}
	private void close() {
		int choice = JOptionPane.showConfirmDialog(
			this, "창을 닫으시겠습니까?", "알림", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE
		);
		if(choice == JOptionPane.OK_OPTION) {
			owner.setVisible(true);
			dispose();
		}
	}
	public void addModel(Book book) {
		slave.addModel(book);
		Object[] temp = slave.getModel().toArray();
		Book[] books = new Book[temp.length];
		for(int i=0; i<temp.length; i++) {
			books[i] = (Book)temp[i];
		}
		Arrays.sort(books);
		slave.getModel().removeAllElements();
		for(Book arr : books) {
			slave.addModel(arr);
		}
	}
	public ListPanel setListPanel() {
		return slave;
	}
	private void showFrame() {
		setTitle("InfoList");
		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
