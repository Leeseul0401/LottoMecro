package LottoMecro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
//	private Lotto owner;			// Lotto 객체
	private MainFrame owner;
	private DbConnection loginChk = new DbConnection();	//	Login check 객
	private Join join;
	
	private String[][] id;
	
	private JLabel lblId;			//	ID
	private JLabel lblPw;			//	Password
	
	private JTextField tfId;	
	private JPasswordField tfPw;
	
	private JButton btnLogin;
	private JButton btnJoin;
	
	public Login() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}	
	
	public Login(MainFrame owner) {
		super("Lotto");
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}	
	
	private void init() {
		Dimension dlb = new Dimension(120, 18); 
		lblId = new JLabel("ID");
		lblId.setPreferredSize(dlb);
		lblPw = new JLabel("Password");
		lblPw.setPreferredSize(dlb);
		
		tfId = new JTextField(10);
		tfPw = new JPasswordField(10);
		
		Dimension dbtn = new Dimension(110, 28);
		btnLogin = new JButton("Login");
		btnLogin.setPreferredSize(dbtn);
		btnJoin = new JButton("Join");
		btnJoin.setPreferredSize(dbtn);
	};
	
	private void setDisplay() {
		JPanel pnlCenter = new JPanel(new GridLayout(0,1));
		JPanel pnlSouth = new JPanel();
		
		JPanel pnlId = new JPanel();
		pnlId.add(lblId);
		pnlId.add(tfId);
		
		JPanel pnlPw = new JPanel();
		pnlPw.add(lblPw);
		pnlPw.add(tfPw);
		
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btnLogin);
		pnlBtn.add(btnJoin);
		
		pnlCenter.add(pnlId);
		pnlCenter.add(pnlPw);
		
		pnlSouth.add(pnlBtn);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
	};
	
	private void addListeners() {
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit(); 
			}
		});		
	};
	
	private void showFrame() {
		setSize(300, 150);
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	private void exit() {
		int result = JOptionPane.showConfirmDialog(
				this,
				"종료하시겠습니까?",
				"종료",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
				);
		
		if(result == JOptionPane.YES_OPTION) {
			dispose();
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String login = btnLogin.getActionCommand();
		String join  = btnJoin.getActionCommand();				
		String id	 = tfId.getText();
		String pw	 = new String(tfPw.getPassword());
		
		if(ae.getActionCommand().equals(login)) {
			if(tfId.getText().equals("")) {
				JOptionPane.showMessageDialog(
						Login.this,
						"아이디를 입력해주세요!",
						"경고",
						JOptionPane.INFORMATION_MESSAGE
				);
			} else if(pw.equals("")) {
				JOptionPane.showMessageDialog(
						Login.this, 
						"비밀번호를 입력하세요!",
						"비밀번호 경고", 
						JOptionPane.INFORMATION_MESSAGE
				);			
			} else {
				setVisible(false);
				if(loginChk.selectMember(id, pw)) {
					System.out.println("chk -------------- "+loginChk.selectMember(id, pw));
					loginChk.close();
					new MainFrame(Login.this, id);
					
				} else {
					JOptionPane.showMessageDialog(
							Login.this, 
							"아이디나 비밀번호를 확인하세요!",
							"경고", 
							JOptionPane.INFORMATION_MESSAGE
					);		
				}
			}
			
		} else if(ae.getActionCommand().equals(join)) {
			new Join(Login.this);
		}			
		
	};
	public static void main(String[] args) {
		new Login();
	}
}
