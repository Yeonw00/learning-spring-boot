import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ReadStart extends JFrame{
	private JLabel lblImg;
	private JLabel lblTitle;
	private JButton btnStart;
	private JButton btnClose;
	private Color color;
	
	private JComboBox<String> cbYear;
	
	private Properties prop;
	
	private String year;
	private String month;
	private String day;
	private String goal;
	
	private String syear;
	
	private Hashtable<Integer, Vector<Book>> data;
	
	public ReadStart() {
		prop = new Properties();
		data = new Hashtable<Integer, Vector<Book>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		long time = System.currentTimeMillis();
		String format = sdf.format(time);
		int idx = format.indexOf(".");
		year = format.substring(0, idx);
		month = format.substring(idx+1, idx+3);
		day = format.substring(idx+4, idx+6);
		
		loadData();
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	private void init() {
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);
		
		color = new Color(0xFEEFDA);
		
		lblImg = new JLabel(new ImageIcon("Books2.png"), JLabel.CENTER);
		lblImg.setOpaque(true);
		lblImg.setBackground(color);
		
		lblTitle = new JLabel("닫혀있기만 한 책은 블록일 뿐이다.", JLabel.CENTER);
		lblTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		lblTitle.setForeground(new Color(0x513114));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(color);
		
		cbYear = new JComboBox<String>();
		cbYear.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxx");
		for(int i=2020; i<=Integer.parseInt(year); i++) {
			cbYear.addItem(String.valueOf(i + "년"));
		}
		int idx = cbYear.getItemCount();
		cbYear.setSelectedIndex(idx - 1);
		
		btnStart = new JButton("시작");
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		btnStart.setForeground(new Color(0x513114));
		btnClose = new JButton("닫기");
		btnClose.setBorderPainted(false);
		btnClose.setContentAreaFilled(false);
		btnClose.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		btnClose.setForeground(new Color(0x513114));
	}
	private void setDisplay() {
		JPanel pnlTop = new JPanel();
		pnlTop.add(lblImg);
		pnlTop.setOpaque(true);
		pnlTop.setBackground(color);
		pnlTop.setBorder(new EmptyBorder(0,50,0,50));
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblTitle);
		pnlCenter.setOpaque(true);
		pnlCenter.setBackground(color);
		
		JPanel pnlYear = new JPanel();
		pnlYear.add(cbYear);
		pnlYear.setOpaque(true);
		pnlYear.setBackground(color);
		
		JPanel pnlBottom1 = new JPanel();
		pnlBottom1.add(btnStart);
		pnlBottom1.add(btnClose);
		pnlBottom1.setOpaque(true);
		pnlBottom1.setBackground(color);
		
		JPanel pnlBottom = new JPanel(new BorderLayout());
		pnlBottom.add(pnlYear, BorderLayout.NORTH);
		pnlBottom.add(pnlBottom1, BorderLayout.CENTER);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlBottom, BorderLayout.SOUTH);
	}
	private void addListener() {
		WindowListener wListener = new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we) {
				close();
			}
		};
		addWindowListener(wListener);
		
		ActionListener listener = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(ae.getSource() == btnClose) {
					close();
				} else{
					int dif = cbYear.getItemCount() - cbYear.getSelectedIndex() -1;
					syear = String.valueOf(Integer.parseInt(year) - dif);
					String input = null;
					if(!prop.containsKey(syear)){
						input = JOptionPane.showInputDialog(
							ReadStart.this, 
							syear + "년 목표 독서량을 입력하세요", 
							"설정", 
							JOptionPane.PLAIN_MESSAGE
						);
						if(input == null) {
							showDlg("입력을 취소합니다.");
						} else if(input.trim().length() == 0) {
							showDlg("입력이 올바르지 않습니다.");
						} else {
							try {
								int num = Integer.parseInt(input);
								goal = String.valueOf(num);
								prop.setProperty(syear, goal);
								new ReadBook(ReadStart.this, syear, "12", goal);
								setVisible(false);
							} catch(NumberFormatException e) {
								showDlg("입력이 올바르지 않습니다.");
							}
						} 
					} else {
						goal = prop.getProperty(syear);
						new ReadBook(ReadStart.this, syear, "12", goal);
						setVisible(false);
					}
				}
			}
		};
		btnStart.addActionListener(listener);
		btnClose.addActionListener(listener);
	}
	private void close() {
		int choice = JOptionPane.showConfirmDialog(
			this, "종료할까요?", "알림", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE
		);
		if(choice == JOptionPane.OK_OPTION) {
			saveData();
			System.exit(0);
		}
	}
	private void showDlg(String str) {
		JOptionPane.showMessageDialog(
			ReadStart.this, 
			str,
			"알림",
			JOptionPane.INFORMATION_MESSAGE
		);
	}
	public void setData(Hashtable<Integer, Vector<Book>> data) {
		this.data = data;
	}
	public Hashtable<Integer, Vector<Book>> getData() {
		return data;
	}
	public void makeKey(String str, String...temp) {
		for(int i=0; i<temp.length; i++) {
			if(i < 9) {
				temp[i] = str + "0" + (i+1);
			} else {
				temp[i] = str + (i+1);
			}
		}
	}
	public int[] getArr() {
		String[] keys = new String[12];
		if(cbYear.getSelectedItem().toString().equals(year+"년")) {
			makeKey(year, keys);
		} else {
			makeKey(syear, keys);
		}
		int[] arr = new int[12];
		for(int i=0; i<keys.length; i++) {
			if(data.containsKey(Integer.parseInt(keys[i]))) {
				arr[i] = data.get(Integer.parseInt(keys[i])).size();
			}
		}
		return arr;
	}
	public void setData(Book book) {
		String str = book.getDate();
		int idx1 = str.indexOf("년");
		int idx2 = str.indexOf("월");
		String str1 = str.substring(0,idx1);
		String str2 = str.substring(idx1+1, idx2);
		int key = Integer.parseInt(str1+str2);
		Vector<Book> list;
		
		if(data.containsKey(key)) {
			list = data.get(key);
		} else {
			list = new Vector<Book>();
			data.put(key, list);
		}
		list.add(book);
		setData(data);
	}
	public void removeData(int key, Vector<Book> list) {
		data.replace(key, list);
	}
	public String getYear() {
		return year;
	}
	public String getDay() {
		return day;
	}
	public String getMonth() {
		return month;
	}
	public Properties getProp() {
		return prop;
	}
	private void loadData() {
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream("yearData.properties");
			prop.load(fis);
			
			goal = prop.getProperty(year);
		} catch(FileNotFoundException e) {
			
		}
		catch(IOException e) {
			JOptionPane.showMessageDialog(
				this, 
				"데이터를 불러오던 도중 문제가 발생했습니다. 프로그램을 종료합니다.",
				"알림",
				JOptionPane.ERROR_MESSAGE
			);
			System.exit(-1);
		} finally {
			IOUtil.closeAll(fis);
		}
		
		ObjectInputStream ois = null;
		
		try{
			fis = new FileInputStream("book.dat");
			ois = new ObjectInputStream(fis);
			
			data = (Hashtable<Integer, Vector<Book>>)ois.readObject();
		} catch(ClassNotFoundException e) {
			
		} catch(FileNotFoundException e) {
			
		} catch(IOException e) {
			JOptionPane.showMessageDialog(
				this, 
				"데이터를 불러오던 도중 문제가 발생했습니다. 프로그램을 종료합니다.",
				"알림",
				JOptionPane.ERROR_MESSAGE
			);
			System.exit(-1);
		} finally {
			IOUtil.closeAll(fis, ois);
		}
	}
	private void saveData() {
		FileOutputStream fos = null;
		
		try{
			fos = new FileOutputStream("yearData.properties");
			prop.store(fos, "yearSetting");
		} catch(IOException e) {
			JOptionPane.showMessageDialog(
				this, 
				"데이터를 저장하던 도중 문제가 발생했습니다. 프로그램을 종료합니다.",
				"알림",
				JOptionPane.ERROR_MESSAGE
			);
			System.exit(-1);
			
		} finally {
			IOUtil.closeAll(fos);
		}
		
		ObjectOutputStream oos = null;
		
		try{
			fos = new FileOutputStream("book.dat");
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(data);
			oos.flush();
			oos.reset();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(
				this, 
				"데이터를 저장하던 도중 문제가 발생했습니다. 프로그램을 종료합니다.",
				"알림",
				JOptionPane.ERROR_MESSAGE
			);
			System.exit(-1);
			
		} finally {
			IOUtil.closeAll(fos, oos);
		}
	}
	private void showFrame() {
		setTitle("ReadBook100Ton");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		new ReadStart();
	}
}
