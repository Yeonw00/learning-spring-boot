import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ListPanel extends JPanel {
	private String year;
	private String month;
	private JLabel lblTitle;
	
	private JList<Book> list;
	private DefaultListModel<Book> model;
	
	private String[] str = {"날짜", "제목", "작가", "평점"};
	private JRadioButton[] rbtns;
	
	private String[] names = {"book1.png", "book2.png", "book3.png", "book4.png"};
	private ImageIcon[] icons;
	
	private int key;
	
	private Book book;
	
	private ReadStart king;
	private ReadBook owner;
	private Vector<Book> mList;
	
	public ListPanel(ReadStart king, ReadBook owner, String month) {
		this.king = king;
		this.owner = owner;
		this.month = month;
		init();
		setDisplay();
		addListeners();
	}
	private void init() {
		mList = new Vector<Book>();
		year = owner.getYear();
		
		lblTitle = new JLabel(month + "월 독서리스트");
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		icons = new ImageIcon[names.length];
		for(int i=0; i<icons.length; i++) {
			Image img = kit.getImage(names[i]);
			Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icons[i] = new ImageIcon(newImg);
		}
		
		ButtonGroup group = new ButtonGroup();
		rbtns = new JRadioButton[str.length];
		for(int i=0; i<rbtns.length; i++) {
			rbtns[i] = new JRadioButton(str[i]);
			group.add(rbtns[i]);
		}
		rbtns[1].setSelected(true);
		setModel();
	}
	private void setDisplay() {
		JPanel pnlTop = new JPanel();
		pnlTop.add(lblTitle);

		JPanel pnlBut = new JPanel();
		for(JRadioButton btn : rbtns) {
			pnlBut.add(btn);
		}
		
		JPanel pnlCenter1 = new JPanel();
		JScrollPane scroll = new JScrollPane(list);
		pnlCenter1.add(scroll);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(pnlBut, BorderLayout.NORTH);
		pnlCenter.add(pnlCenter1, BorderLayout.CENTER);

		list.setCellRenderer(new DefaultListCellRenderer(){
			@Override
			public Component getListCellRendererComponent(
				JList list, Object value, int i, 
				boolean isSelected, boolean cellHasfocus
			) {
				JPanel pnl = new JPanel(new FlowLayout(FlowLayout.LEFT));
				
				Book b = (Book)value;
				JLabel lblIcon = new JLabel();
				
				if(b.getType().equals("국내소설")) {
					lblIcon.setIcon(icons[0]);
				} else if(b.getType().equals("외국소설")) {
					lblIcon.setIcon(icons[1]);
				} else if(b.getType().equals("에세이")) {
					lblIcon.setIcon(icons[2]);
				} else {
					lblIcon.setIcon(icons[3]);
				}
			
				if(isSelected) {
					pnl.setBackground(new Color(0xDAE3F3));
				} else {
					pnl.setBackground(Color.WHITE);
				}
				JLabel lblInfo = new JLabel(b.getTitle() + "( 작가 : " + b.getAuthor() + ")");
				
				pnl.add(lblIcon);
				pnl.add(lblInfo);
				
			return pnl;
			}
		});
		setLayout(new BorderLayout());
		add(pnlTop, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
	}
	private void addListeners() {
		ItemListener listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					Object[] temp = model.toArray();
					Book[] books = new Book[temp.length];
					for(int i=0; i<temp.length; i++) {
						books[i] = (Book)temp[i];
					}
					if(model.size() != 0) {
						if(e.getSource() == rbtns[0]) {
							Arrays.sort(books, new OrderByDate());
							model.removeAllElements();
							for(Book book : books) {
								model.addElement(book);
							}
						} else if(e.getSource() == rbtns[1]) {
							Arrays.sort(books);
							model.removeAllElements();
							for(Book book : books) {
								model.addElement(book);
							}
						} else if(e.getSource() == rbtns[2]) {
							Arrays.sort(books, new OrderByAuthor());
							model.removeAllElements();
							for(Book book : books) {
								model.addElement(book);
							}
						} else {
							Arrays.sort(books, new OrderByGrade());
							model.removeAllElements();
							for(Book book : books) {
								model.addElement(book);
							}
						}
					} else {
						JOptionPane.showMessageDialog(
							ListPanel.this,
							"정렬할 데이터가 없습니다.",
							"알림",
							JOptionPane.WARNING_MESSAGE
						);
					}
				}
			}
		};
		for(JRadioButton btn : rbtns) {
			btn.addItemListener(listener);
		}
		list.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					Rectangle r = list.getCellBounds(
						list.getLeadSelectionIndex(), list.getLeadSelectionIndex()
					);
					Book temp = list.getSelectedValue();
					int listY = r.y + r.height;
					int mouseY = me.getY();
					
					if(mouseY < listY) {
						new BookInfo(owner, temp);
					}
				}
			}
		});
	}
	public void setModel(){
		key = Integer.parseInt(year+month);
		if(owner.getList(key) != null) {
			mList = owner.getList(key);
			
			Book[] arr = mList.toArray(new Book[0]);
			Arrays.sort(arr);
			
			model = new DefaultListModel<Book>();
			for(Book book : arr) {
				model.addElement(book);
			}
		} else {
			model = new DefaultListModel<Book>();
		}
		list = new JList<Book>(model);
		list.setOpaque(true);
		list.setBackground(Color.WHITE);
		list.setPrototypeCellValue(new Book("aa","aa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aa", "aa", 100));
		list.setVisibleRowCount(4);
	}
	public void addModel(Book book) {
		model.addElement(book);
		rbtns[1].setSelected(true);
	}
	public void remove() {
		model.removeAllElements();
	}
	public void removeModel(int idx) {
		model.remove(idx);
		Object[] temp = model.toArray();
		Book[] books = new Book[temp.length];
		for(int i=0; i<temp.length; i++) {
			books[i] = (Book)temp[i];
		}
		Arrays.sort(books);
		owner.setReadNum(owner.getReadNum()-1);
		owner.colorInit();
		owner.colorBar();
		owner.setLbl(String.valueOf(owner.getReadNum()));
		owner.update();
		
		owner.setListPanel(String.valueOf(key)).remove();
		key = Integer.parseInt(year+month);
		owner.setListPanel(String.valueOf(key));
		for(Book book : books) {
			owner.setListPanel(String.valueOf(key)).addModel(book);
		}
		Vector<Book> tempList = new Vector<Book>(Arrays.asList(books));
		if(owner.setListPanel(year + month).getModel().size() == 0) {
			owner.setCard();
		}
		king.removeData(key, tempList);
		rbtns[1].setSelected(true);
	}
	public JList<Book> getList() {
		return list;
	}
	public DefaultListModel<Book> getModel() {
		return model;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public void showLast() {
		list.ensureIndexIsVisible(model.getSize() - 1);
	}
	public int getKey() {
		return key;
	}
}
