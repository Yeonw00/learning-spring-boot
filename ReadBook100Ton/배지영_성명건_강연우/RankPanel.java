import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class RankPanel extends JPanel {
	private JTextField[] tf;
	private String str;
	
	public RankPanel(String str) {
		this.str = str;
		init();
		setDisplay();
	}
	private void init() {
		tf = new JTextField[3];
		for(int i=0; i<tf.length; i++) {
			tf[i] = new JTextField(13);
			tf[i].setEditable(false);
			tf[i].setBorder(new LineBorder(Color.GRAY, 1));
		}
	}
	private void setDisplay() {
		JPanel[] pnl = new JPanel[tf.length];
		for(int i=0; i<pnl.length; i++) {
			pnl[i] = new JPanel();
			pnl[i].add(tf[i]);
		}
		
		JPanel pnlCenter = new JPanel(new GridLayout(0,1));
		for(int i=0; i<pnl.length; i++) {
			pnlCenter.add(pnl[i]);
		}
		pnlCenter.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), str));
		
		setLayout(new BorderLayout());
		
		add(pnlCenter, BorderLayout.CENTER);
	}
	public void setRank(String...str) {
		for(int i=0; i<tf.length; i++) {
			tf[i].setText(str[i]);
		}
	}
}
