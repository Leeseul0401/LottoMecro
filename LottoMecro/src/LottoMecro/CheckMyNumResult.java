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
	
	private JLabel imgMain;
	
	private JLabel lblMain;
	private JLabel lblTurn;
	
	private JLabel lblIdentifi;
	
	private JPanel pnlCenter;
	private JTextArea taLotto;
	private Font defaultFont = new Font(Font.DIALOG, Font.BOLD, 20);
	
	private Vector<Lotto> lottos;

	public CheckMyNumResult(MainFrame owner){
		this.owner = owner;
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	private void init(){
		lottos = new Vector<Lotto>();
		
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
	
	public void addLotto(Lotto lotto) {
		lottos.add(lotto);
		taLotto.append(lotto.toString());
	}
	
	public Lotto getLottoInfo(int publishNum) {
		Lotto searchLotto = null;
		try {
			for(int i=0; i<lottos.size(); i++) {
				if(lottos.get(i).getLottoPNum() == publishNum) {
					if(lottos.get(i).getLottoRNum() <= Lotto.getRound()) {
						searchLotto = lottos.get(i);
					} else {
						searchLotto = null;
					}
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(owner, 
					"먼저 로또를 구입해주세요", 
					"알림", 
					JOptionPane.INFORMATION_MESSAGE
			);
		}
		return searchLotto;
	}
}
