package LottoMecro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CheckMyNumResult extends JDialog {
	private MainFrame owner;
	private String id;
	
	private int[][] myLotto;
	private JLabel imgMain;
	
	private JLabel lblMain;
	private JLabel lblTurn;
	
	private JLabel lblIdentifi;
	
	private JPanel pnlCenter;
	private JTextArea taLotto;
	private Font defaultFont = new Font(Font.DIALOG, Font.BOLD, 20);
	private DbConnection lottoChk = new DbConnection();
	//private Vector<Lotto> lottos;

	public CheckMyNumResult(MainFrame owner, String id){
		this.owner = owner;
		this.id = id;
		init();
		setDisplay();
		addListener();
		showFrame();
		addLotto();
	}
	private void init(){
//		lottos = new Vector<Lotto>();
		
		lblMain = new JLabel("구입한 로또");
		Font font = new Font("Dialog",Font.BOLD, 20);
		lblMain.setFont(font);
		lblMain.setHorizontalAlignment(JLabel.CENTER);
		
		lblTurn = new JLabel("제 1회");
		Font turnFont = new Font("Dialog",Font.BOLD, 15);
		lblTurn.setFont(turnFont);
		lblTurn.setHorizontalAlignment(JLabel.CENTER);
		
		String textPNum = "발행 번호 : ";
		lblIdentifi = new JLabel(textPNum);
		Font fontI = new Font("Dialog",Font.BOLD, 13);
		lblIdentifi.setFont(fontI);
		lblIdentifi.setHorizontalAlignment(JLabel.CENTER);

	}
	private void setDisplay(){
		
		imgMain  = new JLabel();
		ImageIcon icon = new ImageIcon("lottoMain.jpg");
		Image originImg = icon.getImage();
		Image resizeImg = originImg.getScaledInstance(310, 100, Image.SCALE_SMOOTH);
		ImageIcon reIcon = new ImageIcon(resizeImg);
		imgMain.setIcon(reIcon);
		imgMain.setHorizontalAlignment(JLabel.CENTER);
		
		//JPanelCenter 로또 정보 및 스크롤바 영역
		pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.setBackground(Color.WHITE);
		taLotto = new JTextArea(10, 40);
		taLotto.setFont(defaultFont);
		taLotto.setEditable(false);
		pnlCenter.add(taLotto);
		
		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(imgMain, BorderLayout.NORTH);
		pnlNorth.add(lblMain, BorderLayout.CENTER);
		pnlNorth.setBackground(Color.WHITE);
	
		// 스크롤바
		JScrollPane scroll = new JScrollPane(pnlCenter);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

	}
	private void addListener(){
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent we) {
				setVisible(false);
				owner.setVisible(true);
			}
		});
	}
	private void showFrame(){
		setTitle("구입한 복권 확인");
		setSize(600, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);;
		setVisible(false);
	}
	
	public void addLotto() {
//		lottos.add(lotto);
//		taLotto.append(lotto.toString());
		myLotto = lottoChk.selectMyLotto(id);
		for(int i=0; i<myLotto.length; i++) {
			for(int j=0; j<myLotto[i].length; j++) {
				if(myLotto[i][j] != 0) {
					if(j == 0) {
						taLotto.append("제 " + myLotto[i][0] + "회 Lotto\n");
					} else {
						taLotto.append(myLotto[i][j] + " ");
					}
				} else {
					break;
				}
			}
			taLotto.append("\n\n");
			System.out.print(taLotto.getText());
		}
	}
	
}
