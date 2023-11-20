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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Join extends JDialog {
	private DbConnection dbConnector = new DbConnection();
	
	private Login	owner;
	private JLabel	lblId;
	private JLabel	lblPw;
	private JLabel	lblPwChk;
	private JLabel	lblName;
	private JLabel	lblNickname;
	
	private JTextField		tfId;
	private JPasswordField	tfPw;
	private JPasswordField	tfPwChk;
	private JTextField		tfName;
	private JTextField		tfNickname;
	
	private JButton	btnJoin;
	private JButton	btnCancel;
	
	public Join(Login owner) {
		super(owner,"로그인",true);
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	
	
	private void init() {
		Dimension dlb = new Dimension(120, 30);
		lblId = new JLabel("ID");
		lblId.setPreferredSize(dlb);
		lblPw = new JLabel("Password");
		lblPw.setPreferredSize(dlb);
		lblPwChk = new JLabel("Password Check");
		lblPwChk.setPreferredSize(dlb);
		lblName = new JLabel("Name");
		lblName.setPreferredSize(dlb);
		lblNickname = new JLabel("NickName");
		lblNickname.setPreferredSize(dlb);
		
		tfId = new JTextField(20);
		tfPw = new JPasswordField(20);
		tfPwChk = new JPasswordField(20);
		tfName = new JTextField(20);
		tfNickname = new JTextField(20);
		
		Dimension dbtn = new Dimension(110, 28);
		btnJoin = new JButton("Join");
		btnJoin.setPreferredSize(dbtn);
		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(dbtn);
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
		
		JPanel pnlPwChk = new JPanel();
		pnlPwChk.add(lblPwChk);
		pnlPwChk.add(tfPwChk);
		
		JPanel pnlName = new JPanel();
		pnlName.add(lblName);
		pnlName.add(tfName);
		
		JPanel pnlNickname = new JPanel();
		pnlNickname.add(lblNickname);
		pnlNickname.add(tfNickname);
		
		pnlCenter.add(pnlId);
		pnlCenter.add(pnlPw);
		pnlCenter.add(pnlPwChk);
		pnlCenter.add(pnlName);
		pnlCenter.add(pnlNickname);
		
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(btnJoin);
		pnlBtn.add(btnCancel);
		
		pnlSouth.add(pnlBtn);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		
	};
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit(); 
			}
		});
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String cancel	= btnCancel.getActionCommand();
				String join		= btnJoin.getActionCommand();
				String id		= tfId.getText();
				String pw		= new String(tfPw.getPassword());
				String pwChk	= new String(tfPwChk.getPassword());
				String name		= tfName.getText();
				String nickname	= tfNickname.getText();
				
				if(ae.getActionCommand().equals(cancel)) {
					exit();
				} else if(ae.getActionCommand().equals(join)) {
					if(id.equals("")) {
						JOptionPane.showMessageDialog(
								Join.this,
								"아이디를 입력해주세요!",
								"경고",
								JOptionPane.INFORMATION_MESSAGE
						);
					} else if(pw.equals("")) {
						JOptionPane.showMessageDialog(
								Join.this,
								"비밀번호를 입력해주세요!",
								"경고",
								JOptionPane.INFORMATION_MESSAGE
						);
					} else if(pwChk.equals("")) {
						JOptionPane.showMessageDialog(
								Join.this,
								"비밀번호 확인을 입력해주세요!",
								"경고",
								JOptionPane.INFORMATION_MESSAGE
						);
					} else if(name.equals("")) {
						JOptionPane.showMessageDialog(
								Join.this,
								"이름을 입력해주세요!",
								"경고",
								JOptionPane.INFORMATION_MESSAGE
						);
					} else if(nickname.equals("")) {
						JOptionPane.showMessageDialog(
								Join.this,
								"닉네임을 입력해주세요!",
								"경고",
								JOptionPane.INFORMATION_MESSAGE
						);
					} else if(!(pw.equals("")) && !(pwChk.equals(""))) {
						if(!pw.equals(pwChk)) {
							JOptionPane.showMessageDialog(
									Join.this,
									"비밀번호를 확인해주세요!",
									"확인",
									JOptionPane.INFORMATION_MESSAGE
							);
						} else {
							dbConnector.insertMember(id, pw, name, nickname);
							dbConnector.close();
							System.out.println("insert----join");
							JOptionPane.showMessageDialog(
									Join.this,
									"회원가입이 완료되었습니다!",
									"확인",
									JOptionPane.INFORMATION_MESSAGE
							);
							dispose();
						}
					} 
				}
			}
		};
	
	btnJoin.addActionListener(aListener);
	btnCancel.addActionListener(aListener);
		
	};
	private void showFrame() {
		setSize(500, 300);
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

	
}


