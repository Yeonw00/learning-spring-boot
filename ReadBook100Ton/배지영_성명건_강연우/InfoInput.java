import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class InfoInput extends JFrame {
	private JLabel lblTitle;
	private String[] str = {"국내소설", "외국소설", "에세이", "기타"};
	private String[] infos = {"장르", "작가", "제목", "읽은 날짜", "리뷰/메모"};
	private JRadioButton[] rbtns;
	private JLabel[] lblInfo;
	private JTextField[]  tfs;
	private JTextArea ta;
	private JButton[] btns;
	private JButton btnSave;
	private JButton btnClose;
	private JComboBox<String> cYear;
	private JComboBox<String> cMonth;
	private JComboBox<String> cDay;
	private Book book;
	private ReadStart king;
	private ReadBook owner;
	private InfoList norm;
	private Color color = new Color(0x4472C4);
	
	public InfoInput(ReadStart king, ReadBook owner, InfoList norm) {
		this.king = king;
		this.owner = owner;
		this.norm = norm;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		lblTitle = new JLabel("책 정보 입력", JLabel.CENTER);
		ButtonGroup group = new ButtonGroup();
		rbtns = new JRadioButton[str.length];
		for(int i=0; i<rbtns.length; i++) {
			rbtns[i] = new JRadioButton(str[i]);
			group.add(rbtns[i]);
		}

		lblInfo = new JLabel[infos.length];
		for(int i=0; i<lblInfo.length; i++) {
			lblInfo[i] = new JLabel(infos[i]);
			lblInfo[i].setBorder(new EmptyBorder(0,10,0,0));
		}
		
		tfs = new JTextField[2];
		for(int i=0; i<tfs.length; i++) {
			tfs[i] = new JTextField(25);
		}
		
		cYear = new JComboBox<String>();
		cYear.setPrototypeDisplayValue("xxxxxxxxx");
		
		for(int i=2020, j=0; i<=Integer.parseInt(king.getYear()); i++, j++) {
			cYear.addItem(String.valueOf(i + "년"));
			if(String.valueOf(i).equals(owner.getYear())) {
				cYear.setSelectedIndex(j);
			}
		}
		
		cMonth = new JComboBox<String>();
		cMonth.setPrototypeDisplayValue("xxxxxxxxx");
		for(int i=1, j=0; i<=12; i++, j++) {
			cMonth.addItem(String.valueOf(i + "월"));
			if(i>=10) {
				if(String.valueOf(i).equals(owner.setMonth())) {
					cMonth.setSelectedIndex(j);
				}
			} else {
				if(String.valueOf("0" + i).equals(owner.setMonth())) {
					cMonth.setSelectedIndex(j);
				}
			}
		}
		
		cDay = new JComboBox<String>();
		cDay.setPrototypeDisplayValue("xxxxxxxxx");
		String select = cMonth.getSelectedItem().toString();
		int maxNum = 0;
		if(select.equals("2월")) {
			maxNum = 29;
		} else if(select.equals("4월") || select.equals("6월") || select.equals("9월") || select.equals("11월")) {
			maxNum = 30;
		} else {
			maxNum = 31;
		}
		for(int i=1; i<=maxNum; i++) {
			cDay.addItem(String.valueOf(i + "일"));
		}
		
		ta = new JTextArea(5, 25);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		
		btns = new JButton[5];
		for(int i=0; i<btns.length; i++) {
			btns[i] = new JButton();
			btns[i].setBorder(new LineBorder(color, 1));
			btns[i].setBackground(new Color(0xDAE3F3));
			btns[i].setPreferredSize(new Dimension(40,40));
		}
		
		btnSave = new JButton("저장");
		btnClose = new JButton("닫기");
		
	}
	private void setDisplay() {
		JPanel pnlTop = new JPanel();
		pnlTop.add(lblTitle);
		
		JPanel pnlType = new JPanel(new BorderLayout());
		JPanel pnlType1 = new JPanel();
		for(JRadioButton btn : rbtns) {
			pnlType1.add(btn);
		}
		pnlType.add(lblInfo[0], BorderLayout.NORTH);
		pnlType.add(pnlType1, BorderLayout.CENTER);
		
		JPanel pnlAuthor = new JPanel(new BorderLayout());
		pnlAuthor.add(lblInfo[1], BorderLayout.NORTH);
		JPanel pnlAuthor1 = new JPanel();
		pnlAuthor1.add(tfs[0]);
		pnlAuthor.add(pnlAuthor1, BorderLayout.CENTER);
		
		JPanel pnlTitle = new JPanel(new BorderLayout());
		pnlTitle.add(lblInfo[2], BorderLayout.NORTH);
		JPanel pnlTitle1 = new JPanel();
		pnlTitle1.add(tfs[1]);
		pnlTitle.add(pnlTitle1, BorderLayout.CENTER);
		
		JPanel pnlDate = new JPanel(new BorderLayout());
		pnlDate.add(lblInfo[3], BorderLayout.NORTH);
		JPanel pnlDate1 = new JPanel();
		pnlDate1.add(cYear);
		pnlDate1.add(cMonth);
		pnlDate1.add(cDay);
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
		pnlBottom.add(btnSave);
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
		ActionListener alistener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if(src == btnClose) {
					close();
				} else if(src == btnSave){
					save();
				} else {
					JButton temp = (JButton)src;
					for(int i=0; i<btns.length; i++) {
						if(temp == btns[i]) {
							for(JButton btn : btns) {
								btn.setBackground(new Color(0xDAE3F3));
							}
							for(int j=0; j<=i; j++) {
								btns[j].setBackground(color);
							}
						}
					}
				}
			}
		};
		btnSave.addActionListener(alistener);
		btnClose.addActionListener(alistener);
		for(JButton btn : btns) {
			btn.addActionListener(alistener);
		}
		
		cMonth.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					int month = cMonth.getSelectedIndex() + 1;
					int maxNum = 0;
					cDay.removeAllItems();
					if(month == 2) {
						maxNum = 29;
					} else if(month == 4 || month == 6 || month == 9 || month == 11 ) {
						maxNum = 30;
					} else {
						maxNum = 31;
					}
					for(int i=1; i<=maxNum; i++) {
						cDay.addItem(String.valueOf(i + "일"));
					}
				}
			}
		});
		
		WindowListener wListener = new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we) {
				close();
			}
		};
		addWindowListener(wListener);
	}
	private void save() {
		String type = null;
		String author = null;
		String title = null;
		String date = null;
		String memo = null;
		int grade = 0;
		
		boolean flag = true;
		if(flag) {
			int count = 0;
			for(JRadioButton rbtn : rbtns) {
				if(rbtn.isSelected()) {
					count++;
					type = rbtn.getText();
				}
			}
			if(count == 0) {
				flag = false;
			}
		}
		if(flag) {
			if(tfs[0].getText().trim().length() == 0) {
				flag = false;
			} else {
				author = tfs[0].getText().trim();
			}
		}
		if(flag) {
			if(tfs[1].getText().trim().length() == 0) {
				flag = false;
			} else {
				title = tfs[1].getText().trim();
			}
		}
		if(flag) {
			StringBuffer temp = new StringBuffer();
			temp.append(String.valueOf(cYear.getSelectedItem().toString()));
			if(cMonth.getSelectedIndex() >= 9) {
				temp.append(cMonth.getSelectedIndex() + 1 + "월");
			} else {
				temp.append("0" + (cMonth.getSelectedIndex() + 1) + "월");
			}
			if(cDay.getSelectedIndex() <= 9) {
				temp.append("0" + (cDay.getSelectedIndex() + 1) + "일");
			} else {
				temp.append(cDay.getSelectedIndex() + 1 + "일");
			}
			
			date = temp.toString();
		}
		if(flag) {
			if(ta.getText().trim().length() == 0) {
				flag = false;
			} else {
				memo = ta.getText();
			}
		}
		if(flag) {
			int count = 0;
			for(JButton btn : btns) {
				if(	btn.getBackground() == color) {
					count++;
				}
			}
			if(count == 0) {
				flag = false;
			} else {
				grade = count;
			}
		}
		if(flag) {
			book = new Book(type, author, title, date, memo, grade);
			
			int idx = date.indexOf("년");
			String str1 = date.substring(0, idx);
			String str2 = date.substring(idx+1, idx+3);
			int idx2 = date.indexOf("일");
			String str3 = date.substring(idx+4, idx2);
			String msg = king.getYear() + "년 " + king.getMonth() + "월 " + king.getDay() + 
				"일 이후 날짜는 등록할 수 없습니다.";
			if(king.getProp().get(str1) != null) {
				if(Integer.parseInt(str1) < Integer.parseInt(king.getYear())) {
					update(str1, str2);
				} else if(Integer.parseInt(str1) == Integer.parseInt(king.getYear())) {
					if(Integer.parseInt(str2) > Integer.parseInt(king.getMonth())) {
						showDlg(msg);
					} else if(Integer.parseInt(str2) == Integer.parseInt(king.getMonth())) {
						if(Integer.parseInt(str3) > Integer.parseInt(king.getDay())) {
							showDlg(msg);
						} else {
							update(str1, str2);
						}
					} else {
						update(str1, str2);
					}
				} else {
					showDlg(msg);
				}
			} else {
				showDlg(str1 + "년도 목표 독서량이 아직 등록되어 있지 않습니다. \n목표 독서량을 먼저 등록해주세요.");
			}
		} else {
			JOptionPane.showMessageDialog(
				this, 
				"입력되지 않은 정보가 있습니다.", 
				"알림",
				JOptionPane.INFORMATION_MESSAGE
			);
		}
	}
	public Book getBook() {
		return book;
	}
	private void showDlg(String str) {
		JOptionPane.showMessageDialog(
			this,
			str,
			"알림",
			JOptionPane.WARNING_MESSAGE
		);
	}
	private void update(String str1, String str2) {
		king.setData(book);
		try {
			if(Integer.parseInt(owner.getYear()) == Integer.parseInt(str1)) {
				owner.setReadNum(owner.getReadNum()+1);
				owner.colorBar();
				owner.setLbl(String.valueOf(owner.getReadNum()));
				owner.update();
				owner.setListPanel(str1+str2).addModel(book);
				Object[] temp = owner.setListPanel(str1+str2).getModel().toArray();
				Book[] books = new Book[temp.length];
				for(int i=0; i<temp.length; i++) {
					books[i] = (Book)temp[i];
				}
				Arrays.sort(books);
				owner.setListPanel(str1+str2).getModel().removeAllElements();
				for(Book arr : books) {
					owner.setListPanel(str1+str2).addModel(arr);
				}
				owner.setUpdate(str2);
				
				String key = owner.getYear() + owner.getMonth();
				if(key.equals(str1+str2)) {
					norm.addModel(book);
				}
			}
		} catch(NullPointerException e) {
		}
		dispose();
	}
	private void close() {
		int choice = JOptionPane.showConfirmDialog(
			this, "취소할까요?", "알림", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE
		);
		if(choice == JOptionPane.OK_OPTION) {
			dispose();
		}
	}
	private void showFrame() {
		setTitle("Input Information");
		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
