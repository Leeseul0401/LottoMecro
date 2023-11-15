package LottoMecro;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SelectLottoType extends JDialog{
	private MainFrame owner;		// MainFrame 객체
	private Lotto lotto;			// 로또생성한 객체
	
	private JButton manual;			// 수동선택버튼
	private JButton auto;			// 자동선택버튼
		
	private JLabel lblCount; 		// 로또구매수량
	
	private JLabel tfInput;			// 수량표시창
	private JButton plus;			// 수량증가버튼
	private JButton minus;		 	// 수량감소버튼
	private int number = 1;		// 수량증가,감소버튼눌렀을 때 파라미터 숫자
	
	//SelectLottoType 생성자
	public SelectLottoType(MainFrame owner){
		super(owner, "수동자동선택", true);
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	//초기화 설정
	private void init(){
		manual = new JButton("수동");
		auto = new JButton("자동");
		plus = new JButton("+");
		minus = new JButton("-");
		
		lblCount = new JLabel("수량");
		
		tfInput = new JLabel(String.valueOf(number));
	}
	//pnlMain GridLayout으로 설정 후 North,Center,South로 나눠서 설정
	private void setDisplay(){
		JPanel pnlMain = new JPanel(new GridLayout(0,1));
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(manual);
		pnlNorth.add(auto);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(lblCount);
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(minus);
		pnlSouth.add(tfInput);
		pnlSouth.add(plus);
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain, BorderLayout.CENTER);
			
	}
	
	private void addListeners(){
	      ActionListener aListener = new ActionListener(){
	         @Override
	         public void actionPerformed(ActionEvent ae){
	            Object src = ae.getSource();
	            JButton btn = (JButton)src;
	            if(btn == manual){			// 수동버튼 누를 시, SelectNumber로 연결
	            	setVisible(false);
	            	SelectNumber selectNumber = new SelectNumber(owner, SelectLottoType.this, number);
	            	selectNumber.setLocationRelativeTo(SelectLottoType.this);
	            } else if(btn == auto){		// 자동버튼 누를 시, CheckMyNum로 연결
	            	int result = JOptionPane.showConfirmDialog(SelectLottoType.this, 
	            			"복권 " + number + "개를 구입하시겠습니까?", 
	            			"구입확인",
	            			JOptionPane.YES_NO_OPTION, 
	            			JOptionPane.QUESTION_MESSAGE
	            	);
	            	if(result == JOptionPane.YES_OPTION) {
	            		lotto = new Lotto(number);
		            	owner.getCheckMyNum().addLotto(lotto);
		            	JOptionPane.showMessageDialog(SelectLottoType.this, 
								"복권구입이 완료되었습니다\n구입한 복권은 구입한 복권 확인창에서 확인할 수 있습니다", 
								"구입완료", 
								JOptionPane.PLAIN_MESSAGE
						);
		            	dispose();
		            	owner.setVisible(true);
	            	}
	            } else if(btn == minus) {	// minus버튼 눌렀을 때 수량 1 감소
	               if(number != 1){
	            	   number--;
	            	   tfInput.setText(String.valueOf(number));
	               }
	            } else if(btn == plus) {	// plus버튼 눌렀을 때 수량 1 증가
	               if(number == 5){
	                  msg();
	               } else {
	            	  number++;
	                  tfInput.setText(String.valueOf(number));
	               }
	            }
	         }
	      };
	      //수동,자동,minus,plus버튼 ActionListener 연결
	      manual.addActionListener(aListener);	
	      auto.addActionListener(aListener);
	      minus.addActionListener(aListener);
	      plus.addActionListener(aListener);
		
	    //창 닫을시 MainFrame이 보이게 설정
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				dispose();
				owner.setVisible(true);
			}
		});
	}
	//프레임 설정
	private void showFrame(){
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	//	0~5 이외 숫자 누를 시, 구매불가 메세지창
	public void msg(){
		JOptionPane.showMessageDialog(
				SelectLottoType.this,
				"5개까지만 구입가능합니다",
				"알림",
				JOptionPane.INFORMATION_MESSAGE
				);
	}
}

