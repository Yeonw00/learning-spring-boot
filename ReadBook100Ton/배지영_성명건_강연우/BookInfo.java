import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class BookInfo extends JFrame {
	private JLabel lblTitle;
	private String[] infos = {"장르", "작가", "제목", "읽은 날짜", "리뷰/메모"};
	private JLabel[] lblInfo;
	private JTextField[]  tfs;
	private JTextArea ta;
	private JButton[] btns;
	private JButton btnClose;
	private Book book;
	private ReadBook owner;
	private Color color = new Color(0x4472C4);
	
	public BookInfo(ReadBook owner, Book book) {
		this.owner = owner;
		this.book = book;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		lblTitle = new JLabel("< " + book.getTitle()+ " > 의 정보", JLabel.CENTER);
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		lblInfo = new JLabel[infos.length];
		for(int i=0; i<lblInfo.length; i++) {
			lblInfo[i] = new JLabel(infos[i]);
			lblInfo[i].setBorder(new EmptyBorder(0,10,0,0));
		}
		
		tfs = new JTextField[4];
		for(int i=0; i<tfs.length; i++) {
			tfs[i] = new JTextField(25);
			tfs[i].setEditable(false);
			tfs[i].setBorder(new LineBorder(Color.GRAY, 1));
			tfs[i].setOpaque(true);
			tfs[i].setForeground(Color.BLACK);
			tfs[i].setBackground(Color.WHITE);
		}
		tfs[0].setText(book.getType());
		tfs[1].setText(book.getAuthor());
		tfs[2].setText(book.getTitle());
		tfs[3].setText(book.getDate());
		
		ta = new JTextArea(5, 25);
		ta.setText(book.getMemo());
		ta.setEditable(false);
		ta.setBackground(Color.WHITE);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		
		btns = new JButton[5];
		for(int i=0; i<btns.length; i++) {
			btns[i] = new JButton();
			btns[i].setBorder(new LineBorder(color, 1));
			btns[i].setBackground(new Color(0xDAE3F3));
			btns[i].setPreferredSize(new Dimension(40,40));
			btns[i].setEnabled(false);
		}
		for(int i=0; i<book.getGrade(); i++) {
			btns[i].setBackground(color);
		}
		
		btnClose = new JButton("닫기");
	}
	private void setDisplay() {
		JPanel pnlTop = new JPanel();
		pnlTop.add(lblTitle);
		
		JPanel pnlType = new JPanel(new BorderLayout());
		JPanel pnlType1 = new JPanel();
		pnlType1.add(tfs[0]);
		pnlType.add(lblInfo[0], BorderLayout.NORTH);
		pnlType.add(pnlType1, BorderLayout.CENTER);
		
		JPanel pnlAuthor = new JPanel(new BorderLayout());
		pnlAuthor.add(lblInfo[1], BorderLayout.NORTH);
		JPanel pnlAuthor1 = new JPanel();
		pnlAuthor1.add(tfs[1]);
		pnlAuthor.add(pnlAuthor1, BorderLayout.CENTER);
		
		JPanel pnlTitle = new JPanel(new BorderLayout());
		pnlTitle.add(lblInfo[2], BorderLayout.NORTH);
		JPanel pnlTitle1 = new JPanel();
		pnlTitle1.add(tfs[2]);
		pnlTitle.add(pnlTitle1, BorderLayout.CENTER);
		
		JPanel pnlDate = new JPanel(new BorderLayout());
		pnlDate.add(lblInfo[3], BorderLayout.NORTH);
		JPanel pnlDate1 = new JPanel();
		pnlDate1.add(tfs[3]);
		pnlDate.add(pnlDate1, BorderLayout.CENTER);

		JPanel pnlMemo = new JPanel(new BorderLayout());
		pnlMemo.add(lblInfo[4], BorderLayout.NORTH);
		JPanel pnlMemo1 = new JPanel();
		JScrollPane scroll = new JScrollPane(ta);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pnlMemo1.add(scroll, BorderLayout.CENTER);
		pnlMemo.add(pnlMemo1, BorderLayout.CENTER);
		
		JPanel pnlGrade = new JPanel();
		for(JButton btn : btns) {
			pnlGrade.add(btn);
		}
		
		JPanel pnlBottom = new JPanel();
		pnlBottom.add(btnClose);
				
		JPanel pnlCenter1 = new JPanel(new BorderLayout());
		pnlCenter1.add(pnlAuthor, BorderLayout.NORTH);
		pnlCenter1.add(pnlTitle, BorderLayout.CENTER);
		pnlCenter1.add(pnlDate, BorderLayout.SOUTH);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnlType, BorderLayout.NORTH);
		pnlCenter.add(pnlCenter1, BorderLayout.CENTER);
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlCenter, BorderLayout.NORTH);
		pnlTotal.add(pnlMemo, BorderLayout.CENTER);
		pnlTotal.add(pnlGrade, BorderLayout.SOUTH);
		pnlTotal.setBorder(new EmptyBorder(10,10,10,10));
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlTotal, BorderLayout.CENTER);
		add(pnlBottom, BorderLayout.SOUTH);
	}
	private void addListeners() {
		ActionListener aListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				close();
			}
		};
		btnClose.addActionListener(aListener);
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
			dispose();
		}
	}
	private void showFrame() {
		setTitle("BookInfo");
		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
