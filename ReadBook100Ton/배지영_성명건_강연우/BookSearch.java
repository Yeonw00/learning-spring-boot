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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BookSearch extends JDialog {
	private JLabel lblTitle;
	private JList<Book> list;
	private DefaultListModel<Book> model;
	private JButton btnClose;
	private ReadBook owner;
	private Book[] books;
	
	private String[] names = {"book1.png", "book2.png", "book3.png", "book4.png"};
	private ImageIcon[] icons;
	
	public BookSearch(ReadBook owner, Book...books) {
		super(owner, "Book Search");
		this.owner = owner;
		this.books = books;
		init();
		setDisplay();
		addListeners();
		showDlg();
	}
	
	private void init() {
		lblTitle = new JLabel("책 정보");
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		icons = new ImageIcon[names.length];
		for(int i=0; i<icons.length; i++) {
			Image img = kit.getImage(names[i]);
			Image newImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			icons[i] = new ImageIcon(newImg);
		}
		Arrays.sort(books);
		model = new DefaultListModel<Book>();
		for(Book book : books) {
			model.addElement(book);
		}
		list = new JList<Book>(model);
		list.setPrototypeCellValue(new Book("aa","aa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "aa", "aa", 100));
		list.setVisibleRowCount(3);
		
		btnClose = new JButton("닫기");
	}
	
	private void setDisplay() {
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(lblTitle);
		
		JPanel pnlCenter = new JPanel();
		JScrollPane scroll = new JScrollPane(list);
		pnlCenter.add(scroll);
		
		JPanel pnlSouth = new JPanel();
		JPanel pnlOk = new JPanel();
		JPanel pnlCancel = new JPanel();
		pnlCancel.add(btnClose);
		pnlSouth.add(pnlOk);
		pnlSouth.add(pnlCancel);
		
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
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Object src = ae.getSource();
				if(src == btnClose) {
					dispose();
				} 
			}
		};
		btnClose.addActionListener(aListener);
		
		WindowListener wListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		};
		addWindowListener(wListener);
		
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

	private void showDlg() {
		pack();
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
}
