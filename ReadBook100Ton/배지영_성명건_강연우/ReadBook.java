import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class ReadBook extends JFrame{
	private JLabel lblTitle;
	private JLabel lblRead;
	private JLabel lblSlash;
	private JLabel lblGoal;
	private JTextField tfSearch;
	private JButton btnSearch;
	private JButton btnPre;
	private JButton btnNext;
	private JButton btnTotal;
	private JButton btnAdd;
	private JButton btnExit;
	private JLabel lblPer;
	private JLabel[] arrLbl;
	private JComboBox<String> cbKind;
	
	private int readNum;
	private int goalNum;
	private String year;
	private String month;
	private String day;
	private String[] scenes;
	private String[] keys;
	private int lastIdx;
	private String setMonth;
	
	private ReadStart king;
	private ListPanel[] lists;
	private ListPanel list;
	private InfoList norm;
	
	private JPanel pnlMain;
	private CardLayout card;
	
	private Hashtable<Integer, Vector<Book>> data;
	private Vector<Book> bookList;
	private Book[] books;
	
	public ReadBook(ReadStart king, String year, String month, String goal) {
		this.king = king;
		this.year = year;
		this.month = month;
		this.setMonth = month;
		
		this.goalNum = Integer.parseInt(goal);

		update();
		init();
		setDisplay();
		setCard();
		addListeners();
		showFrame();
	}
	public void update() {
		data = king.getData();
		bookList = new Vector<Book>();
		
		Set<Integer> dataKey = king.getData().keySet();
		Integer[] keyArr = dataKey.toArray(new Integer[0]);
		
		for(int i=0; i<keyArr.length; i++) {
			Vector<Book> temp = king.getData().get(keyArr[i]);
			bookList.addAll(temp);
		}
		
		books = bookList.toArray(new Book[0]);
	}
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		keys = new String[12];
		for(int i=0; i<keys.length; i++) {
			if(i < 9) {
				keys[i] = year + 0 + (i + 1);
			} else {
				keys[i] = year + (i + 1);
			}
		}
		
		lblTitle = new JLabel(year + " 독서기록", JLabel.CENTER);
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 30));

		readNum = 0;
		for(int i=0; i<keys.length; i++) {
			if(data.containsKey(Integer.parseInt(keys[i]))) {
				readNum += data.get(Integer.parseInt(keys[i])).size();
			}
		}
		
		lblRead = new JLabel(String.valueOf(readNum),JLabel.CENTER);
		lblSlash = new JLabel("/", JLabel.CENTER);
		lblGoal = new JLabel(String.valueOf(goalNum), JLabel.CENTER);
		tfSearch = new JTextField(15);
		tfSearch.setActionCommand("enter");
		btnSearch = new JButton("검색");
		btnSearch.setActionCommand("enter");
		btnPre = new JButton("<<");
		btnPre.setBorderPainted(false);
		btnPre.setContentAreaFilled(false);
		btnNext = new JButton(">>");
		btnNext.setBorderPainted(false);
		btnNext.setContentAreaFilled(false);
		btnTotal = new JButton("종합");
		btnAdd = new JButton("추가");
		btnExit = new JButton("닫기");

		lblPer = new JLabel();
		
		Dimension d = new Dimension(3, 20);
		
		arrLbl = new JLabel[100];
		for(int idx=0; idx<arrLbl.length; idx++) {
			arrLbl[idx] = new JLabel();
			arrLbl[idx].setPreferredSize(d);
			arrLbl[idx].setOpaque(true);
			arrLbl[idx].setBackground(Color.WHITE);
		}
		
		int num = Integer.parseInt(month);

		scenes = new String[12];
		for(int i=0; i<scenes.length; i++) {
			if(num == 12) {
				scenes[i] = String.valueOf(num);
				num = 1;
			} else {
				if(num >= 10){
				scenes[i] = String.valueOf(num);
				} else {
					scenes[i] = String.valueOf("0" + num);
				}
				num++;
			}
		}

		lists = new ListPanel[scenes.length];
		for(int i=0; i<lists.length; i++) {
			lists[i] = new ListPanel(king, ReadBook.this, scenes[i]);
		}
		
		card = new CardLayout();
		pnlMain = new JPanel(card);
		for(int i=0; i<scenes.length; i++) {
			pnlMain.add(lists[i], scenes[i]);
		}

		cbKind = new JComboBox<String>();
		cbKind.setPrototypeDisplayValue("XXXX");
		cbKind.addItem("제목");
		cbKind.addItem("작가");
		cbKind.addItem("장르");
		
		colorBar();
		lastIdx = Integer.parseInt(month);
	}
	
	private void setDisplay() {
		JPanel pnlNorth = new JPanel(new GridLayout(0,1));
		
		JPanel pnlNorth1 = new JPanel(new BorderLayout());
		JPanel pnlTitle = new JPanel();
		pnlTitle.add(lblTitle);
		JPanel pnlNum = new JPanel();
		pnlNum.add(lblRead);
		pnlNum.add(lblSlash);
		pnlNum.add(lblGoal);
		pnlNorth1.add(pnlTitle, BorderLayout.NORTH);
		pnlNorth1.add(pnlNum, BorderLayout.CENTER);
		
		JPanel pnlBar = new JPanel(new GridLayout(1, 0));
		for(int idx=0; idx<arrLbl.length; idx++) {
			pnlBar.add(arrLbl[idx]);
		}
		JPanel pnlPer = new JPanel();
		pnlPer.add(lblPer);
		
		pnlBar.setBorder(new LineBorder((new Color(0x4472C4)), 1));
		
		JPanel pnlSearch = new JPanel();
		pnlSearch.add(cbKind);
		pnlSearch.add(tfSearch);
		pnlSearch.add(btnSearch);
		
		JPanel pnlNorth2 = new JPanel(new BorderLayout());
		JPanel pnlBarAll = new JPanel();
		pnlBarAll.add(pnlBar);
		pnlBarAll.add(pnlPer);
		pnlNorth2.add(pnlBarAll, BorderLayout.NORTH);
		pnlNorth2.add(pnlSearch, BorderLayout.CENTER);
		
		pnlNorth.add(pnlNorth1);
		pnlNorth.add(pnlNorth2);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		JPanel pnlPre = new JPanel(new BorderLayout());
		pnlPre.add(btnPre, BorderLayout.CENTER);
		JPanel pnlNext = new JPanel(new BorderLayout());
		pnlNext.add(btnNext, BorderLayout.CENTER);
		pnlCenter.add(pnlPre, BorderLayout.WEST);
		
		pnlCenter.add(pnlMain, BorderLayout.CENTER);
		
		pnlCenter.add(pnlNext, BorderLayout.EAST);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlAll = new JPanel();
		pnlAll.add(btnTotal);
		JPanel pnlAdd = new JPanel();
		pnlAdd.add(btnAdd);
		JPanel pnlExit = new JPanel();
		pnlExit.add(btnExit);
		pnlSouth.add(pnlAll);
		pnlSouth.add(pnlAdd);
		pnlSouth.add(pnlExit);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		MouseListener mListener = new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me) {
				list = new ListPanel(king, ReadBook.this, setMonth);
				if((me.getX() >=50 && me.getX() <= 250) &&(me.getY() >= 10 && me.getY() <= 30)) {
					new InfoList(king, ReadBook.this, list);
					setVisible(false);
				}
			}
		};
		for(ListPanel temp : lists) {
			temp.addMouseListener(mListener);
		}
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				String cmd = ae.getActionCommand();
				if(cmd.equals("enter")) {
					String search = tfSearch.getText().trim();
					if(search.length() == 0) {
						showMsg("검색어를 입력하세요.");
						tfSearch.requestFocus();
						tfSearch.selectAll();
					} else if(books.length != 0) {
						String input = tfSearch.getText().trim();
						if(cbKind.getSelectedIndex() == 0) {
							Vector<Book> book1 = new Vector<Book>();
							for(Book book : books) {
								if(book.getTitle().contains(input)){
									book1.add(book);
								}
							}
							if(book1.size() != 0) {
								new BookSearch(ReadBook.this, book1.toArray(new Book[0]));
							} else {
								showMsg("해당하는 책이 아직 등록되지 않았습니다.");
							}
						} else if(cbKind.getSelectedIndex() == 1) {
							Vector<Book> book1 = new Vector<Book>();
							for(Book book : books) {
								if(book.getAuthor().contains(input)){
									book1.add(book);
								}
							}
							if(book1.size() != 0) {
								new BookSearch(ReadBook.this, book1.toArray(new Book[0]));
							} else {
								showMsg("해당하는 책이 아직 등록되지 않았습니다.");
							}
						} else {
							Vector<Book> book1 = new Vector<Book>();
							for(Book book : books) {
								if(book.getType().contains(input)){
									book1.add(book);
								}
							}
							if(book1.size() != 0) {
								new BookSearch(ReadBook.this, book1.toArray(new Book[0]));
							} else {
								showMsg("해당하는 책이 아직 등록되지 않았습니다.");
							}
						}
					} else {
						showMsg("등록된 책이 한권도 없습니다.");
						tfSearch.setText("");
					}
				} else if(src == btnPre) {
					int idx = Integer.parseInt(setMonth);
					if(idx > 1) {
						idx--;
						String name;
						while(idx > 0 && lists[idx].getModel().size() == 0) {
							idx--;
						}
						if(idx > 0) {
							if(idx < 10) {
								name = String.valueOf("0" + idx);
							} else {
								name = String.valueOf(idx);
							}
							setMonth = name;
							card.show(pnlMain, name);
						} else {
							showMsg("처음페이지 입니다.");
						}
					} else {
						showMsg("처음페이지 입니다.");
					}
				} else  if(src == btnNext) {
					int idx = Integer.parseInt(setMonth);
					if(idx < 12) {
						idx++;
						String name;
						while(idx < 12 && lists[idx].getModel().size() == 0) {
							idx++;
						}
						if(idx < lastIdx) {
							if(idx < 10) {
								name = String.valueOf("0" + idx);
							} else {
								name = String.valueOf(idx);
							}
							setMonth = name;
							card.show(pnlMain, name);
						} else if(idx == lastIdx && lists[idx - 1].getModel().size() != 0) {
							if(idx < 10) {
								name = String.valueOf("0" + idx);
							} else {
								name = String.valueOf(idx);
							}
							setMonth = name;
							card.show(pnlMain, name);
						} else {
							showMsg("마지막 페이지입니다.");
						}
					} else {
						showMsg("마지막 페이지입니다.");
					}

				} else if(src == btnTotal) {
					new ReadTotal(king, ReadBook.this);
					setVisible(false);
				} else if(src == btnAdd) {
					new InfoInput(king, ReadBook.this, norm);
				} else {
					exit();
				}
			}
		};
		tfSearch.addActionListener(aListener);
		btnSearch.addActionListener(aListener);
		btnPre.addActionListener(aListener);
		btnNext.addActionListener(aListener);
		btnTotal.addActionListener(aListener);
		btnAdd.addActionListener(aListener);
		btnExit.addActionListener(aListener);
		
		WindowListener wListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exit();
			}
		};
		addWindowListener(wListener);
	}
	private void exit() {
		int choice = JOptionPane.showConfirmDialog(
			this, 
			"창을 닫으시겠습니까?",
			"알림",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.INFORMATION_MESSAGE
		);
		if(choice == JOptionPane.YES_OPTION) {
			king.setVisible(true);
			dispose();
		}
	}

	public ListPanel setListPanel() {
		int idx = -1;
		for(int i=0; i<scenes.length; i++) {
			if(scenes[i].equals(month)){
				idx = i;
			}
		}
		return lists[idx];
	}
	public ListPanel setListPanel(String key) {
		int idx = -1;
		String str = key.substring(4);
		for(int i=0; i<scenes.length; i++) {
			if(scenes[i].equals(str)) {
				idx = i;
			}
		}
		return lists[idx];
	}
	public Vector<Book> getList(int key) {
		data = king.getData();
		return data.get(key);
	}
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public int getGoalNum() {
		return goalNum;
	}
	public void setGoalNum(int goalNum) {
		this.goalNum = goalNum;
	}
	public String getMonth() {
		return month;
	}
	public String setMonth() {
		return setMonth;
	}
	public void setUpdate(String name) {
		this.setMonth = name;
		card.show(pnlMain, name);
	}
	public String getYear() {
		return year;
	}
	public String getDay() {
		return king.getDay();
	}
	public void colorBar() {
		double division = (double)(readNum) / (double)(goalNum);
		double percent = Double.parseDouble(String.format("%.2f", division));
		int range = (int)(percent * 100);
		if(readNum <= goalNum) {
			for(int idx=0; idx<range; idx++) {
				arrLbl[idx].setBackground((new Color(0x4472C4)));
			}
		} else {
			for(int idx=0; idx<arrLbl.length; idx++) {
				arrLbl[idx].setBackground((new Color(0x4472C4)));
			}
		}
		lblPer.setText(String.valueOf(range + "% 달성"));
	}
	public void setLbl(String str) {
		lblRead.setText(str);
	}
	public void colorInit() {
		for(JLabel lbl : arrLbl) {
			lbl.setBackground(Color.WHITE);
		}
	}
	public Book[] getBooks() {
		return books;
	}
	private void showMsg(String msg) {
		JOptionPane.showMessageDialog(
			ReadBook.this,
			msg,
			"알림",
			JOptionPane.WARNING_MESSAGE
		);
	}
	public void setCard() {
		boolean flag = false;
		int idx = -1;
		for(int i=0; i<lists.length; i++) {
			if(i == 0) {
				if(lists[i].getModel().size() != 0) {
					idx = i;
					flag = true;
					break;
				}
			} else {
				if(lists[i].getModel().size() != 0) {
					idx = i;
					flag = true;
				}
			}
		}
		if(!flag) {
			if(!year.equals(king.getYear())) {
				card.show(pnlMain, month);
				setMonth = month;
			} else {
				card.show(pnlMain, king.getMonth());
				setMonth = king.getMonth();
			}
		} else {
			card.show(pnlMain, scenes[idx]);
			setMonth = scenes[idx];
		}
	}
	private void showFrame() {
		setTitle("ReadBook");
		pack();
		setLocationRelativeTo(king);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
