import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class Ranking extends JDialog {
	private JLabel lblTitle;
	private JLabel lblCount;
	private JLabel lblAuthor;
	private JLabel lblInfo1;
	private JLabel lblType;
	private JLabel lblInfo2;
	private JLabel[] lbls;
	private JLabel[] lbls2;
	private JLabel[] lbls3;
	private JTextField[] tfs;
	private JTextField[] tfs2;
	private JTextField[] tfs3;
	
	private JButton btnClose;
	private ReadStart king;
	private ReadBook owner;
	private ReadTotal total;
	private RankPanel[] aPnls;
	private RankPanel[] tPnls;
	private String[] type = {"국내소설", "외국소설", "에세이", "기타"};
	
	private Hashtable<String, Vector<String>> data;
	private Vector<Book> list;
	private Vector<String> totalCount;
	private Vector<String> totalGoal;
	
	private Book[] books;
	
	private int num;
	private int count;
	private int setCount;

	public Ranking(ReadStart king, ReadBook owner, ReadTotal total) {
		super(total, "Ranking");
		this.king = king;
		this.owner = owner;
		
		books = owner.getBooks();
		data = new Hashtable<String, Vector<String>>();
		
		init();
		setDisplay();
		addListeners();
		showDlg();
	}
	
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		lblTitle = new JLabel("독서리스트 통계", JLabel.CENTER);
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD,25));
		lblCount = new JLabel("통계", JLabel.LEFT);
		lblCount.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		lblAuthor = new JLabel("작가별 통계", JLabel.LEFT);
		lblAuthor.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		lblInfo1 = new JLabel(" - 공동순위가 있는 경우 2명까지만 표시됩니다.", JLabel.LEFT);
		lblType = new JLabel("장르별 통계", JLabel.LEFT);
		lblType.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		lblInfo2 = new JLabel(" - 공동순위가 있는 경우 2가지까지만 표시됩니다.", JLabel.LEFT);

		num = Integer.parseInt(king.getYear());
		count = num - 2020 +1;
		
		setCount = king.getProp().size();
		
		totalCount = new Vector<>();
		totalGoal = new Vector<>();
		totalCount.add(String.valueOf("total/" + books.length));
		
		lbls = new JLabel[count + 1];
		for(int i=0; i<lbls.length; i++) {
			lbls[i] = new JLabel("읽은 책 : ", JLabel.RIGHT);
		}
		
		lbls2 = new JLabel[count + 1];
		for(int i=0; i<lbls2.length; i++) {
			lbls2[i] = new JLabel("목표권수 : ", JLabel.RIGHT);
		}
		
		lbls3 = new JLabel[count + 1];
		for(int i=0; i<lbls3.length; i++) {
			lbls3[i] = new JLabel("달성율 : ", JLabel.RIGHT);
		}
		
		tfs = new JTextField[count + 1];
		for(int i=0; i<tfs.length; i++) {
			tfs[i]= new JTextField(8);
			tfs[i].setEditable(false);
			tfs[i].setBorder(new LineBorder(Color.GRAY, 1));
			tfs[i].setHorizontalAlignment(JTextField.CENTER);
		}
		tfs2 = new JTextField[count + 1];
		for(int i=0; i<tfs2.length; i++) {
			tfs2[i]= new JTextField(8);
			tfs2[i].setEditable(false);
			tfs2[i].setBorder(new LineBorder(Color.GRAY, 1));
			tfs2[i].setHorizontalAlignment(JTextField.CENTER);
		}
		tfs3 = new JTextField[count + 1];
		for(int i=0; i<tfs3.length; i++) {
			tfs3[i]= new JTextField(8);
			tfs3[i].setEditable(false);
			tfs3[i].setBorder(new LineBorder(Color.GRAY, 1));
			tfs3[i].setHorizontalAlignment(JTextField.CENTER);
		}
		
		aPnls = new RankPanel[count + 1];
		aPnls[0] = new RankPanel("전체");
		for(int i=1; i<=count; i++) {
			aPnls[i] = new RankPanel(String.valueOf(num - i +1));
		}
		
		tPnls = new RankPanel[count + 1];
		tPnls[0] = new RankPanel("전체");
		for(int i=1; i<=count; i++) {
			tPnls[i] = new RankPanel(String.valueOf(num - i +1));
		}
		
		btnClose = new JButton("닫기");
		
		for(int i=0; i<count + 1; i++) {
			if(i==0) {
				setAuthorRank(i, books);
			} else {
				Book[] temp = yearBooks(String.valueOf(num - i +1), books);
				
				if(temp != null) {
					setAuthorRank(i, temp);
				} 
			}
		}
		
		setCount();
		String[] goals = totalGoal.toArray(new String[0]);
		int goal = 0;
		for(int i=0; i<goals.length; i++) {
			goal += Integer.parseInt(goals[i]);
		}
		int per = (int)((double)books.length / (double)goal * 100);
		tfs[0].setText(books.length + " 권");
		tfs2[0].setText(goal + " 권");
		tfs3[0].setText(per + " %");
		
		for(int i=0; i<count + 1; i++) {
			if(i==0) {
				setTypeRank(i, books);
			} else {
				Book[] temp = yearBooks(String.valueOf(num - i +1), books);
				if(temp != null) {
					setTypeRank(i, temp);
				} 
			}
		}
	}
	private void setDisplay() {
		JPanel pnlTitle = new JPanel();
		pnlTitle.add(lblTitle);
		pnlTitle.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel[] pnlCounts1 = new JPanel[tfs.length];
		for(int i=0; i<pnlCounts1.length; i++) {
			pnlCounts1[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pnlCounts1[i].add(lbls[i]);
			pnlCounts1[i].add(tfs[i]);
		}
		JPanel[] pnlCounts2 = new JPanel[tfs2.length];
		for(int i=0; i<pnlCounts2.length; i++) {
			pnlCounts2[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pnlCounts2[i].add(lbls2[i]);
			pnlCounts2[i].add(tfs2[i]);
		}
		JPanel[] pnlCounts3 = new JPanel[tfs3.length];
		for(int i=0; i<pnlCounts3.length; i++) {
			pnlCounts3[i] = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			pnlCounts3[i].add(lbls3[i]);
			pnlCounts3[i].add(tfs3[i]);
		}
		
		JPanel pnlCountss[] = new JPanel[tfs.length];
		for(int i=0; i<pnlCountss.length; i++) {
			pnlCountss[i] = new JPanel(new GridLayout(3,0));
			pnlCountss[i].add(pnlCounts1[i]);
			pnlCountss[i].add(pnlCounts2[i]);
			pnlCountss[i].add(pnlCounts3[i]);
		}

		
		JPanel pnlCount1 = new JPanel(new GridLayout(0,4));
		for(JPanel pnl : pnlCountss) {
			pnlCount1.add(pnl);
		}
		pnlCountss[0].setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), "전체"));
		for(int i=1; i<pnlCountss.length; i++) {
			pnlCountss[i].setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), String.valueOf(num - i +1)));
		}
		
		JPanel pnlCount = new JPanel(new BorderLayout());
		pnlCount.add(lblCount, BorderLayout.NORTH);
		pnlCount.add(pnlCount1, BorderLayout.CENTER);
		pnlCount.setBorder(new EmptyBorder(10,20,10,20));
		
		JPanel pnlaPnl = new JPanel(new GridLayout(0,4));
		for(JPanel pnl : aPnls) {
			pnlaPnl.add(pnl);
		}
		
		JPanel pnlAuthor1 = new JPanel(new BorderLayout());
		pnlAuthor1.add(lblAuthor, BorderLayout.WEST);
		pnlAuthor1.add(lblInfo1, BorderLayout.CENTER);
		JPanel pnlAuthor = new JPanel(new BorderLayout());
		pnlAuthor.add(pnlAuthor1, BorderLayout.NORTH);
		pnlAuthor.add(pnlaPnl, BorderLayout.CENTER);
		
		JPanel pnltPnl = new JPanel(new GridLayout(0,4));
		for(JPanel pnl : tPnls) {
			pnltPnl.add(pnl);
		}
		
		JPanel pnlType1 = new JPanel(new BorderLayout());
		pnlType1.add(lblType, BorderLayout.WEST);
		pnlType1.add(lblInfo2, BorderLayout.CENTER);
		
		JPanel pnlType = new JPanel(new BorderLayout());
		pnlType.add(pnlType1, BorderLayout.NORTH);
		pnlType.add(pnltPnl, BorderLayout.CENTER);
		
		JPanel pnlCenter = new JPanel(new GridLayout(2,0));
		pnlCenter.add(pnlAuthor);
		pnlCenter.add(pnlType);
		pnlCenter.setBorder(new EmptyBorder(10,20,10,20));
		
		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlCount, BorderLayout.NORTH);
		pnlTotal.add(pnlCenter, BorderLayout.CENTER);
		
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btnClose);
		
		add(pnlTitle, BorderLayout.NORTH);
		add(pnlTotal, BorderLayout.CENTER);
		add(pnlBtn, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
		
		WindowListener wListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		};
		addWindowListener(wListener);
	}
	private void setHash(String str) {
		Vector<String> lists;
		if(data.containsKey(str)) {
			lists = data.get(str);
		} else {
			lists = new Vector<String>();
			data.put(str, lists);
		}
		lists.add(str);
	}
	private void author(Book...temp) {
		for(Book book : temp) {
			setHash(book.getAuthor());
		}
	}
	private void type(Book...temp) {
		for(Book book : temp) {
			setHash(book.getType());
		}
	}
	private Book[] yearBooks(String num, Book... temp) {
		int a = 0;
		list = new Vector<Book>();
		for(Book book : books) {
			if(book.getDate().substring(0, 4).equals(num)) {
				list.add(book);
				a++;
			}
		}
		totalCount.add(num + "/" + a);

		if(list.size()>0) {
			return list.toArray(new Book[0]);
		} else {
			return null;
		}
	}
	private void setTypeRank(int num, Book...temp) {
		Arrays.sort(temp);
		type(temp);
		if(temp.length != 0) {
			Vector<String> tlist = new Vector<String>();
			for(int i=0; i<type.length; i++) {
				if(data.containsKey(type[i])) {
					tlist.add(data.get(type[i]).size() + "/" + type[i]);
				}
			}
			String[] tarr = tlist.toArray(new String[0]);
			Arrays.sort(tarr, new OrderByDesc());
			if(tarr.length != 0) {
				Hashtable<Integer, Vector<String>> hash = new Hashtable<Integer, Vector<String>>();
				Vector<String> list;
				for(int i=0; i<tarr.length; i++) {
					int idx = tarr[i].indexOf("/");
					int rank = Integer.parseInt(tarr[i].substring(0, idx));
					String value = tarr[i].substring(idx+1);
					if(hash.containsKey(rank)) {
						list = hash.get(rank);
					} else {
						list = new Vector<String>();
						hash.put(rank, list);
					}
					list.add(value);
				}
				Integer[] iii = hash.keySet().toArray(new Integer[0]);
				Arrays.sort(iii);
				String[] str = new String[3];
				if(iii.length >= 3) {
					int co = 0;
					for(int i=iii.length-1, j=0; i>=0; i--, j++) {
						if(co < 3) {
							co++;
							String[] ssss = hash.get(iii[i]).toArray(new String[0]);
							Arrays.sort(ssss);
							StringBuffer sss = new StringBuffer();
							int count = 0;
							for(String aaa : ssss) {
								sss.append(aaa + ", ");
								count++;
							}
							if(count >= 2) {
								int idx = sss.indexOf(",");
								idx = sss.indexOf(",", idx);
								int ex = count - 2;
								if(ex != 0) {
									str[j] = j+1 + ". " + sss.toString().substring(0, idx) + "외 " + ex + "가지";
								} else {
									str[j] = j+1 + ". " + sss.toString().substring(0, idx);
								}
							} else {
								str[j] = j+1 + ". " + sss.toString().substring(0, sss.length()-2);
							}
						}
					}
				} else {
					int co = 0;
					for(int i=iii.length-1, j=0; i>=0; i--, j++) {
						if(co < 3) {
							co++;
							String[] ssss = hash.get(iii[i]).toArray(new String[0]);
							Arrays.sort(ssss);
							StringBuffer sss = new StringBuffer();
							int count = 0;
							for(String aaa : ssss) {
								sss.append(aaa + ", ");
								count++;
							}
							if(count >= 2) {
								int idx = sss.indexOf(",");
								idx = sss.indexOf(",", idx);
								int ex = count - 2;
								if(ex != 0) {
									str[j] = j+1 + ". " + sss.toString().substring(0, idx) + "외 " + ex + "가지";
								} else {
									str[j] = j+1 + ". " + sss.toString().substring(0, idx);
								}
							} else {
								str[j] = j+1 + ". " + sss.toString().substring(0, sss.length()-2);
							}
						}
					}
					for(int i=iii.length; i<str.length; i++) {
						str[i] = "";
					}
				}
				tPnls[num].setRank(str);	
			}
		} 
		data.clear();
	}
	private void setAuthorRank(int num, Book...temp) {
		Arrays.sort(temp);
		author(temp);
		Set<String> key = data.keySet();
		String[] karr = key.toArray(new String[0]);
		String[] aarr = new String[karr.length];
		for(int i=0; i<aarr.length; i++) {
			aarr[i] = data.get(karr[i]).size() + "/" + karr[i];
		}
		Arrays.sort(aarr, new OrderByDesc());
		if(aarr.length != 0) {
			Hashtable<Integer, Vector<String>> hash = new Hashtable<Integer, Vector<String>>();
			Vector<String> list;
			for(int i=0; i<aarr.length; i++) {
				int idx = aarr[i].indexOf("/");
				int rank = Integer.parseInt(aarr[i].substring(0, idx));
				String value = aarr[i].substring(idx+1);
				if(hash.containsKey(rank)) {
					list = hash.get(rank);
				} else {
					list = new Vector<String>();
					hash.put(rank, list);
				}
				list.add(value);
			}
			Integer[] iii = hash.keySet().toArray(new Integer[0]);
			Arrays.sort(iii);
			String[] str = new String[3];
			if(iii.length >= 3) {
				for(int i=iii.length-1, j=0; j<3; i--, j++) {
					String[] ssss = hash.get(iii[i]).toArray(new String[0]);
					Arrays.sort(ssss);
					StringBuffer sss = new StringBuffer();
					int count = 0;
					for(String aaa : ssss) {
						sss.append(aaa + ", ");
						count++;
					}
					if(count >= 2) {
						int idx = sss.indexOf(",");
						idx = sss.indexOf(",", idx+1);
						int ex = count - 2;
						if(ex != 0) {
							str[j] = j+1 + ". " + sss.toString().substring(0, idx) + "외 " + ex + "명";
						} else {
							str[j] = j+1 + ". " + sss.toString().substring(0, idx);
						}
					} else {
						str[j] = j+1 + ". " + sss.toString().substring(0, sss.length()-2);
					}
				}
			} else {
				for(int i=iii.length-1, j=0; i>=0; i--, j++) {
					String[] ssss = hash.get(iii[i]).toArray(new String[0]);
					Arrays.sort(ssss);
					StringBuffer sss = new StringBuffer();
					int count = 0;
					for(String aaa : ssss) {
						sss.append(aaa + ", ");
						count++;
					}
					if(count >= 3) {
						int idx = sss.indexOf(",");
						idx = sss.indexOf(",", idx+1);
						int ex = count - 2;
						if(ex != 0) {
							str[j] = j+1 + ". " + sss.toString().substring(0, idx) + "외 " + ex + "명";
						} else {
							str[j] = j+1 + ". " + sss.toString().substring(0, idx);
						}
					} else {
						str[j] = j+1 + ". " + sss.toString().substring(0, sss.length()-2);
					}
				}
				for(int i=iii.length; i<str.length; i++) {
					str[i] = "";
				}
			}
			aPnls[num].setRank(str);
		} 
		data.clear();
	}
	private void setCount() {
		String[] myArr = totalCount.toArray(new String[0]);
		
		for(int i=1; i<myArr.length ; i++) {
			int idx = myArr[i].indexOf("/");
			String str1 = myArr[i].substring(0, idx);
			String str2 = myArr[i].substring(idx+1);
			 
			if(king.getProp().getProperty(str1) != null) {
				String str3 = king.getProp().getProperty(str1);
				totalGoal.add(str3);
				int per = (int)((Double.parseDouble(str2) / Double.parseDouble(str3)) * 100);
				tfs[i].setText(str2 + " 권");
				tfs2[i].setText(str3 + " 권");
				tfs3[i].setText(per + " %");
			}
		}
	}
	private void showDlg() {
		pack();
		setLocationRelativeTo(total);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}