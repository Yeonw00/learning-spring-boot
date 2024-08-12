import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class ReadTotal extends JFrame {
	private JLabel lblRecord;		

	private JLabel lblRead;
	private JLabel lblSlash;
	private JLabel lblGoal;

	private JLabel lblPer;
	private JLabel[] arrLbl;

	private JLabel[] lblBookstack;	
	private JLabel[] lblBookstack1;
	private JLabel[] lblBookstack2;	
	private JLabel[] lblBookstack3;	
	private JLabel[] lblBookstack4;	
	private JLabel[] lblBookstack5;	
	private JLabel[] lblBookstack6;	
	private JLabel[] lblBookstack7;	
	private JLabel[] lblBookstack8;	
	private JLabel[] lblBookstack9;	
	private JLabel[] lblBookstack10;	
	private JLabel[] lblBookstack11;

	private JLabel[] lblMonth;	
	
	private Image image1;
	private Image image2;
	private Image image3;
	private Image image4;

	private JButton btnAll;			
	private JButton btnClose;		

	private ReadStart king;
	private ReadBook owner;


	public ReadTotal(ReadStart king, ReadBook owner){
		this.king = king;
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}


	private void init(){
		ImageIcon icon = new ImageIcon("book.png");
		Image newIcon = icon.getImage();
		setIconImage(newIcon);

		lblRecord = new JLabel(owner.getYear() + " 독서기록");
		lblRecord.setFont(new Font(Font.DIALOG, Font.BOLD, 30));

		lblRead = new JLabel(String.valueOf(owner.getReadNum()),JLabel.CENTER);
		lblSlash = new JLabel("/", JLabel.CENTER);
		lblGoal = new JLabel(String.valueOf(owner.getGoalNum()), JLabel.CENTER);

		lblPer = new JLabel();

		Dimension d = new Dimension(3, 20);

		arrLbl = new JLabel[100];
		for(int idx=0; idx<arrLbl.length; idx++) {
			arrLbl[idx] = new JLabel();
			arrLbl[idx].setPreferredSize(d);
			arrLbl[idx].setOpaque(true);
			arrLbl[idx].setBackground(Color.WHITE);
		}

		colorBar();

		//=========================================================
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image icon1 = kit.getImage("bookIcon.png");
		image1 = icon1.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

		Image icon2 = kit.getImage("openbook.png");
		image2 = icon2.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		Image icon3 = kit.getImage("fullBook.png");
		image3 = icon3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		Image icon4 = kit.getImage("bookbook.png");
		image4 = icon4.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

		
		lblBookstack = new JLabel[10];
		if(king.getArr()[0] <= 30) {
			setIcon(king.getArr()[0], lblBookstack);
		} else {
			setIcon(30, lblBookstack);
		}

		lblBookstack1 = new JLabel[10];
		if(king.getArr()[1] <= 30) {
			setIcon(king.getArr()[1], lblBookstack1);
		} else {
			setIcon(30, lblBookstack1);
		}

		lblBookstack2 = new JLabel[10];
		if(king.getArr()[2] <= 30) {
			setIcon(king.getArr()[2], lblBookstack2);
		} else {
			setIcon(30, lblBookstack2);
		}

		lblBookstack3 = new JLabel[10];
		if(king.getArr()[3] <= 30) {
			setIcon(king.getArr()[3], lblBookstack3);
		} else {
			setIcon(30, lblBookstack3);
		}

		lblBookstack4 = new JLabel[10];
		if(king.getArr()[4] <= 30) {
			setIcon(king.getArr()[4], lblBookstack4);
		} else {
			setIcon(30, lblBookstack4);
		}

		lblBookstack5 = new JLabel[10];
		if(king.getArr()[5] <= 30) {
			setIcon(king.getArr()[5], lblBookstack5);
		} else {
			setIcon(30, lblBookstack5);
		}

		lblBookstack6 = new JLabel[10];
		if(king.getArr()[6] <= 30) {
			setIcon(king.getArr()[6], lblBookstack6);
		} else {
			setIcon(30, lblBookstack6);
		}

		lblBookstack7 = new JLabel[10];
		if(king.getArr()[7] <= 30) {
			setIcon(king.getArr()[7], lblBookstack7);
		} else {
			setIcon(30, lblBookstack7);
		}

		lblBookstack8 = new JLabel[10];
		if(king.getArr()[8] <= 30) {
			setIcon(king.getArr()[8], lblBookstack8);
		} else {
			setIcon(30, lblBookstack8);
		}

		lblBookstack9 = new JLabel[10];
		if(king.getArr()[9] <= 30) {
			setIcon(king.getArr()[9], lblBookstack9);
		} else {
			setIcon(30, lblBookstack9);
		}

		lblBookstack10 = new JLabel[10];
		if(king.getArr()[10] <= 30) {
			setIcon(king.getArr()[10], lblBookstack10);
		} else {
			setIcon(30, lblBookstack10);
		}

		lblBookstack11 = new JLabel[10];
		if(king.getArr()[11] <= 30) {
			setIcon(king.getArr()[11], lblBookstack11);
		} else {
			setIcon(30, lblBookstack11);
		}

		lblMonth = new JLabel[12];
		for(int i = 0; i < lblMonth.length; i++){
			lblMonth[i] = new JLabel(i + 1 + "월");
			lblMonth[i].setHorizontalAlignment(JLabel.CENTER);
			lblMonth[i].setBorder(new LineBorder(Color.GRAY));
		}
		btnAll = new JButton("통계");
		btnClose = new JButton("닫기");
	}
	private void setDisplay(){

		JPanel pnlRecord = new JPanel();
		pnlRecord.add(lblRecord);

		//상단 목표치
		JPanel pnlGoalCenter = new JPanel();
		pnlGoalCenter.add(lblRead);
		pnlGoalCenter.add(lblSlash);
		pnlGoalCenter.add(lblGoal);

		JPanel pnlgoal = new JPanel(new GridLayout(1,0));
		for(JLabel lbl : arrLbl) {
			pnlgoal.add(lbl);
		}
		pnlgoal.setBorder(new LineBorder((new Color(0x4472C4)), 1));

		JPanel pnlPer = new JPanel();
		pnlPer.add(lblPer);

		JPanel pnlGoal = new JPanel();
		pnlGoal.add(pnlgoal);
		pnlGoal.add(pnlPer);

		JPanel pnlTotal = new JPanel(new BorderLayout());
		pnlTotal.add(pnlGoalCenter, BorderLayout.NORTH);
		pnlTotal.add(pnlGoal, BorderLayout.CENTER);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlRecord, BorderLayout.NORTH);
		pnlNorth.add(pnlTotal, BorderLayout.CENTER);

		//하단 넣을 예정
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnAll);
		pnlSouth.add(btnClose);

		JPanel pnlBookstack = new JPanel(new GridLayout(0, 1));
		for(int i=lblBookstack.length-1; i>=0; i-- ){
			pnlBookstack.add(lblBookstack[i]);
		}

		JPanel pnlBookstack1 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack1.length-1; i>=0; i-- ){
			pnlBookstack1.add(lblBookstack1[i]);
		}

		JPanel pnlBookstack2 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack2.length-1; i>=0; i-- ){
			pnlBookstack2.add(lblBookstack2[i]);
		}

		JPanel pnlBookstack3 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack3.length-1; i>=0; i-- ){
			pnlBookstack3.add(lblBookstack3[i]);
		}

		JPanel pnlBookstack4 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack4.length-1; i>=0; i-- ){
			pnlBookstack4.add(lblBookstack4[i]);
		}

		JPanel pnlBookstack5 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack5.length-1; i>=0; i-- ){
			pnlBookstack5.add(lblBookstack5[i]);
		}

		JPanel pnlBookstack6 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack6.length-1; i>=0; i-- ){
			pnlBookstack6.add(lblBookstack6[i]);
		}

		JPanel pnlBookstack7 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack7.length-1; i>=0; i-- ){
			pnlBookstack7.add(lblBookstack7[i]);
		}

		JPanel pnlBookstack8 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack8.length-1; i>=0; i-- ){
			pnlBookstack8.add(lblBookstack8[i]);
		}

		JPanel pnlBookstack9 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack9.length-1; i>=0; i-- ){
			pnlBookstack9.add(lblBookstack9[i]);
		}

		JPanel pnlBookstack10 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack10.length-1; i>=0; i-- ){
			pnlBookstack10.add(lblBookstack10[i]);
		}

		JPanel pnlBookstack11 = new JPanel(new GridLayout(0,1));
		for(int i=lblBookstack11.length-1; i>=0; i-- ){
			pnlBookstack11.add(lblBookstack11[i]);
		}

		JPanel pnlMonth = new JPanel(new GridLayout(1, 0));
		for(JLabel temp : lblMonth){
			pnlMonth.add(temp);
		}

		JPanel pnlCenter = new JPanel(new GridLayout(1, 0));
		pnlCenter.add(pnlBookstack);
		pnlCenter.add(pnlBookstack1);
		pnlCenter.add(pnlBookstack2);
		pnlCenter.add(pnlBookstack3);
		pnlCenter.add(pnlBookstack4);
		pnlCenter.add(pnlBookstack5);
		pnlCenter.add(pnlBookstack6);
		pnlCenter.add(pnlBookstack7);
		pnlCenter.add(pnlBookstack8);
		pnlCenter.add(pnlBookstack9);
		pnlCenter.add(pnlBookstack10);
		pnlCenter.add(pnlBookstack11);

		JPanel  pnlCenter1 = new JPanel(new BorderLayout());
		pnlCenter1.add(pnlCenter, BorderLayout.CENTER);
		pnlCenter1.add(pnlMonth, BorderLayout.SOUTH);
		pnlCenter1.setBorder(new EmptyBorder(0,20,0,20));

		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter1, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	private void addListeners(){
		ItemListener listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

			}
		};
		ActionListener alistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(ae.getSource() == btnClose) {
					close();
				} else {
					new Ranking(king, owner, ReadTotal.this);
				}
			}
		};
		btnClose.addActionListener(alistener);
		btnAll.addActionListener(alistener);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we){
				close();
			}
		});
	}
	private void close() {
		int choice = JOptionPane.showConfirmDialog(
				ReadTotal.this,
				"창을 닫으시겠습니까?",
				"알림",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);
		if(choice == JOptionPane.YES_OPTION){
			owner.setVisible(true);
			dispose();
		}
	}
	private void colorBar() {
		double division = (double)(owner.getReadNum()) / (double)(owner.getGoalNum());
		double percent = Double.parseDouble(String.format("%.2f", division));
		int range = (int)(percent * 100);

		if(owner.getReadNum() <= owner.getGoalNum()) {
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
	private void setIcon(int num, JLabel... lbl) {
		for(int i = 0;i < lbl.length; i++){
			if(num <= 10){
				if(i< num) {
					lbl[i] = new JLabel(new ImageIcon(image2));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				} else {
					lbl[i] = new JLabel(new ImageIcon(image1));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				}
			}else if(num <= 20){
				if(i < num - 10){
					lbl[i] = new JLabel(new ImageIcon(image3));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				}else{
					lbl[i] = new JLabel(new ImageIcon(image2));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				}
			} else {
				if(i < num - 20){
					lbl[i] = new JLabel(new ImageIcon(image4));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				}else{
					lbl[i] = new JLabel(new ImageIcon(image3));
					lbl[i].setBorder(new LineBorder(Color.GRAY));
				}
			}
		}
	}

	private void showFrame(){
		setTitle("ReadTotal");
		setSize(700, 600);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
