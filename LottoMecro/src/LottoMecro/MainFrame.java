package LottoMecro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JDialog implements ActionListener {
	public final Dimension BTN_SIZE = new Dimension(150, 50);
	
	private Login owner;
	private String id;
	private JButton btnBuy;						// 구매 버튼
	private JButton btnCheck;					// 확인 버튼
	private JButton btnResult;					// 결과 버튼
	private SelectLottoType selectLttTp;		// 숫자 셀렉 버튼	
	private CheckMyNumResult chkMyNumResult;	// 나의 로또 번호
	private CheckResult chkResult;				// 로또 당첨 결과
	
	///MainFrame 순서
	public MainFrame(Login owner, String id){
		super(owner, "로그인", true);
		this.owner = owner;
		this.id = id;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init(){
		chkMyNumResult = new CheckMyNumResult(this, id);  // CheckMyNum 생성 (기본 안보임)
		
		btnBuy 		= new JButton("구매");
		btnCheck 	= new JButton("확인");
		btnResult	= new JButton("결과");
	}

	//pnlMain GridLayout으로 설정 후 North,Center,South로 나눠서 설정
	private void setDisplay(){
		JPanel pnlMain = new JPanel(new GridLayout(0,1));
		pnlMain.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(btnBuy);
		btnBuy.setPreferredSize(BTN_SIZE);

		JPanel pnlCenter = new JPanel();
		pnlCenter.add(btnCheck);
		btnCheck.setPreferredSize(BTN_SIZE);
			
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnResult);
		btnResult.setPreferredSize(BTN_SIZE);
		
		pnlMain.add(pnlNorth,BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain, BorderLayout.CENTER);
	}

	//구입,구입확인버튼,당첨확인버튼 ActionListener 연결
	private void addListeners(){
		btnBuy.addActionListener(this);
		btnCheck.addActionListener(this);	
		btnResult.addActionListener(this);
		
		//창 종료시 exit메서드의 종료메세지 뜨게끔 설정
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exit();
			}
		});
	}

	//프레임 설정
	private void showFrame(){
		setTitle("Lotto");
		setSize(300,300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae){
		JButton btn = (JButton)ae.getSource();
		if(btn == btnBuy){					// 구입버튼 눌렀을때, SelectLottoType으로 연결
			setVisible(false);
			selectLttTp = new SelectLottoType(this, id);
			selectLttTp.setLocationRelativeTo(MainFrame.this);

		} else if(btn == btnCheck) {		// 구입한 복권확인버튼 눌렀을 때, CheckMyNum으로 연결
			setVisible(false);
			chkMyNumResult = new CheckMyNumResult(this, id);
			chkMyNumResult.setLocationRelativeTo(MainFrame.this);
			chkMyNumResult.setVisible(true);

		} else if(btn == btnResult) {		// 당첨확인버튼 눌렀을 때 
			setVisible(false);
			chkResult = new CheckResult(MainFrame.this);
			chkResult.setLocationRelativeTo(MainFrame.this);
				
		}
	}
	
	// 종료메세지 메서드 
	private void exit(){
		int result = JOptionPane.showConfirmDialog(
				this,
				"구매를 종료하시겠습니까?",
				"종료",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);
		if(result == JOptionPane.YES_OPTION){
			System.exit(0);
		} 
	}

	// CheckMyNum 메서드 (자동, 수동 구입에서 로또객체 생성한 것을 넘기기 위함)
//	public CheckMyNumResult getCheckMyNum(){
//		return chkMyNumResult;
//	}


	
}
