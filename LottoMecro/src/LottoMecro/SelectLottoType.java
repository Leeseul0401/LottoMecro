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
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SelectLottoType extends JDialog {
	public final Dimension BTN_SIZE = new Dimension(150, 50);
	private MainFrame owner;		// MainFrame 
	private JButton manual;			// 수동선택버튼
	private JButton auto;			// 자동선택버튼
	private String id;
	
	//SelectLottoType 생성자
	public SelectLottoType(MainFrame owner, String id){
		super(owner, "Lotto 자동/수동 선택", true);
		this.id = id;
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	public SelectLottoType(){
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	//초기화 설정
	private void init(){
		manual = new JButton("수동");
		auto = new JButton("자동");
	}
	//pnlMain GridLayout으로 설정 후 North,Center,South로 나눠서 설정
	private void setDisplay(){
		JPanel pnlMain = new JPanel(new GridLayout(1,1));
		pnlMain.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(manual);
		manual.setPreferredSize(BTN_SIZE);
		pnlCenter.add(auto);
		auto.setPreferredSize(BTN_SIZE);
		
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
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
	            	new AutoLottoNumber(owner, id, 0);
	            } else if(btn == auto){		// 자동버튼 누를 시, CheckMyNum로 연결
	            	setVisible(false);
	            	new AutoLottoNumber(owner, id, 1);
	            }
	         }
	         
	      };
	      //수동,자동,minus,plus버튼 ActionListener 연결
	      manual.addActionListener(aListener);	
	      auto.addActionListener(aListener);
	      
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
	private void showFrame() {
		pack();
		setSize(400,110);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}


