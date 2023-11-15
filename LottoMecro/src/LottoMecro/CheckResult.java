package LottoMecro;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class CheckResult extends JDialog {
	private MainFrame owner;
	private Lotto inputLotto;
	
	private final Font DEFAULT_FONT = new Font(Font.DIALOG, Font.BOLD, 15);
	
	private static HashMap<Integer, int[]> lots = new HashMap<>();
	
	private JLabel lblTitle;
	
	private JLabel lblRound;
	private JLabel lblLotMsg;
	private JLabel lblLotNum;
	private JLabel lblLotBonusNum;
	private JLabel lblCongMsg;
	private JLabel lblPrizeMsg;
	
	private int prizeCheck;
	
	private JTextArea taLottoList;
	
	private JLabel lbl2ndInfo;
	private JButton btn2ndLot;
	
	public CheckResult(MainFrame owner, Lotto inputLotto){
		this.inputLotto = inputLotto;
		this.owner = owner;
		init();
		setDisplay();
		addListener();
		lot();
		showFrame();
	}
	
	private void init() {
		lblTitle = new JLabel("구매복권 당첨 결과");
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(DEFAULT_FONT);
		
		lblRound = new JLabel("", JLabel.CENTER);
		lblRound.setFont(DEFAULT_FONT);
		lblLotMsg = new JLabel("당첨번호", JLabel.CENTER);
		lblLotMsg.setFont(DEFAULT_FONT);
		lblLotNum = new JLabel("", JLabel.CENTER);
		lblLotNum.setFont(DEFAULT_FONT);
		
		lblLotBonusNum = new JLabel("", JLabel.CENTER);
		lblLotBonusNum.setFont(DEFAULT_FONT);
		lblCongMsg = new JLabel("", JLabel.CENTER);
		lblCongMsg.setFont(DEFAULT_FONT);
		lblPrizeMsg = new JLabel("", JLabel.CENTER);
		lblPrizeMsg.setFont(DEFAULT_FONT);
		
		taLottoList = new JTextArea(10, 20);
		taLottoList.setEditable(false);
		taLottoList.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		
		lbl2ndInfo = new JLabel("2등이상이 나올때까지 당첨확인하기");
		lbl2ndInfo.setFont(DEFAULT_FONT);
		btn2ndLot = new JButton("실행");
		btn2ndLot.setFont(DEFAULT_FONT);
	}
	
	private void setDisplay() {
		JPanel pnlTop = new JPanel();
		pnlTop.setBackground(Color.BLUE);
		pnlTop.add(lblTitle);
		
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlMain.setBackground(Color.WHITE);
		
		JPanel pnlNorth = new JPanel(new GridLayout(0, 1, 0, 5));
		pnlNorth.setBackground(Color.WHITE);
		pnlNorth.add(lblRound);
		pnlNorth.add(lblLotMsg);
		JPanel pnlLottoNum = new JPanel();
		pnlLottoNum.setBackground(Color.WHITE);
		pnlLottoNum.add(lblLotNum);
		pnlLottoNum.add(lblLotBonusNum);
		pnlNorth.add(pnlLottoNum);
		pnlNorth.add(lblCongMsg);
		pnlNorth.add(lblPrizeMsg);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBackground(Color.WHITE);
		pnlCenter.add(taLottoList);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.WHITE);
		pnlSouth.add(lbl2ndInfo);
		pnlSouth.add(btn2ndLot);
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlMain, BorderLayout.CENTER);
	}
	
	private void addListener() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				owner.setVisible(true);
			}
		});
		
		btn2ndLot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String str = "";
				int count = 0;
				while(str != "2등" && str != "1등") {
					Lotto lot = new Lotto();
					count++;
					for(int i=0; i<inputLotto.getLottoNum().length; i++) {
						str = prize(lot.getLotNum(), i);
						if(str == "2등" || str == "1등") {
							i = 10;
						}
					}
				}
				JOptionPane.showMessageDialog(CheckResult.this, 
						str + "이 나오기까지 " + count + "회가 걸렸습니다", 
						"결과", 
						JOptionPane.INFORMATION_MESSAGE
				);
			}
		});
	}
	
	private void showFrame() {
		setTitle("구매복권 당첨결과");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void lot() {
		int[] lotNum;
		if(inputLotto.getLottoRNum() < Lotto.getRound()) {
			lotNum = CheckResult.lots.get(inputLotto.getLottoRNum());
		} else {
			Lotto lot = new Lotto();
			Lotto.nextRound();
			lotNum = lot.getLotNum();
			CheckResult.lots.put(lot.getLottoRNum(), lotNum);
		}
		StringBuffer str = new StringBuffer();
		for(int i=0; i<6; i++) {
			str.append(lotNum[i] + " ");
		}
		lblLotNum.setText(str.toString());
		lblLotBonusNum.setText("보너스 번호: " + lotNum[6]);
			
		str = new StringBuffer();
		int[][] input = inputLotto.getLottoNum();
		for(int i=0; i<input.length; i++) {
			String prizeStr = prize(lotNum, i);
			str.append((char)(65+i) + ". " + Arrays.toString(input[i]) + " - " + prizeStr + "\n");
		}
		taLottoList.setText(str.toString());
		
		lblTitle.setText("제 " + inputLotto.getLottoRNum() + " 회");
		
		if(prizeCheck != 10) {
			lblCongMsg.setText("축하합니다!");
			lblPrizeMsg.setText(prizeCheck + "등입니다");
		} else {
			lblCongMsg.setText("아쉽네요");
			lblPrizeMsg.setText("꽝입니다");
		}
	}
	
	private String prize(int[] lotNum, int idx) {
		prizeCheck = 10;
		int[][] lotto = inputLotto.getLottoNum();
		int correct = 0;
		String str = "꽝";
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				if(lotto[idx][i] == lotNum[j]) {
					correct++;
				}
			}
		}
		int check = 10;
		if(correct == 5) {
			for(int i=0; i<6; i++) {
				if(lotto[idx][i] == lotNum[6]) {
					str = "2등";
					check = 2;
				}
			}
		}
		switch(correct) {
			case 3 :
				str = "5등";
				check = 5;
				break;
			case 4 :
				str = "4등";
				check = 4;
				break;
			case 5 :
				str = "3등";
				check = 3;
				break;
			case 6 :
				str = "1등";
				check = 1;
				break;
			default :
		}
		if(prizeCheck > check) {
			prizeCheck = check;
		}
		return str;
	}
	
}
	