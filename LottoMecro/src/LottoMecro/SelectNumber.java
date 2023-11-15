package LottoMecro;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class SelectNumber extends JFrame {
	private JLabel lblInfo;
	private JLabel lblInfo2;
	
	private MainFrame owner;
	private SelectLottoType slType;
	
	// 로또 번호
	private JCheckBox[] rbtnLottoNums = new JCheckBox[45];
	// 로또 용지 번호
	private JLabel[] lblBuyIdx;
	// 선택한 로또 번호
	private JTextField[] tfLottoNum;
	private JButton[] btnConfirms;
	
	private JButton btnBuy;
	private JButton btnCancel;
	
	// 구입 수량
	private int number;
	
	// 선택된 번호 수
	private int selectedNumberCount = 0;
	private Vector<Integer> selectedNumbers;
	// 확정된 복권 용지 수
	private int confirmedLot = 0;
	
	private final Dimension IDX_SIZE = new Dimension(20, 26);
	private final Dimension TF_SIZE = new Dimension(20, 26);
	
	private Font defaultFont = new Font(Font.DIALOG, Font.BOLD, 15);
	
	public SelectNumber(MainFrame owner, SelectLottoType slType, int number) {
		this.number = number;
		this.owner = owner;
		this.slType = slType;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		selectedNumbers = new Vector<Integer>();
		
		lblInfo = new JLabel(" - 번호를 6개 선택해주세요");
		lblInfo.setFont(defaultFont);
		lblInfo2 = new JLabel(" - 밑에 있는 로또 용지 옆에 대입을 눌러주세요");
		lblInfo2.setFont(defaultFont);
		
		for(int i=0; i<rbtnLottoNums.length; i++) {
			rbtnLottoNums[i] = new JCheckBox(String.valueOf(i+1));
		}
		
		lblBuyIdx = new JLabel[number];
		for(int i=0; i<number; i++) {
			lblBuyIdx[i] = new JLabel((char)(65+i) + ". ", JLabel.LEFT);
			lblBuyIdx[i].setPreferredSize(IDX_SIZE);
		}
		
		tfLottoNum = new JTextField[number];
		for(int i=0; i<number; i++) {
			tfLottoNum[i] = new JTextField(20);
			tfLottoNum[i].setPreferredSize(TF_SIZE);
			tfLottoNum[i].setEditable(false);
			tfLottoNum[i].setBorder(new LineBorder(Color.GRAY));
			tfLottoNum[i].setHorizontalAlignment(JTextField.CENTER);
			tfLottoNum[i].setFont(defaultFont);
		}
		
		btnConfirms = new JButton[number];
		for(int i=0; i<number; i++) {
			btnConfirms[i] = new JButton("대입");
		}
		
		btnBuy = new JButton("구입");
		btnCancel = new JButton("취소");
	}
	
	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(new TitledBorder(new LineBorder(Color.ORANGE), /*Lotto.getLottoIdx() + */"회차"));
		
		JPanel pnlNorth = new JPanel(new GridLayout(5, 9));
		for(int i=0; i<rbtnLottoNums.length; i++) {
			pnlNorth.add(rbtnLottoNums[i]);
		}
		pnlNorth.setBorder(new LineBorder(Color.GRAY));
		
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		JPanel pnlInfo = new JPanel(new GridLayout(0, 1));
		pnlInfo.setBorder(new EmptyBorder(5, 0, 0, 0));
		pnlInfo.add(lblInfo);
		pnlInfo.add(lblInfo2);
		pnlCenter.add(pnlInfo);
		for(int i=0; i<number; i++) {
			JPanel pnlCCenter = new JPanel();
			pnlCCenter.add(lblBuyIdx[i]);
			pnlCCenter.add(tfLottoNum[i]);
			pnlCCenter.add(btnConfirms[i]);
			pnlCenter.add(pnlCCenter);
		}
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnBuy);
		pnlSouth.add(btnCancel);
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain);
	}
	
	private void addListeners() {
		ItemListener iListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ie) {
				if(ItemEvent.SELECTED == ie.getStateChange()) {
					JCheckBox cb = (JCheckBox)ie.getSource();
					selectedNumbers.add(Integer.parseInt(cb.getText()));
					selectedNumberCount++;
					if(selectedNumberCount == 7) {
						cb = (JCheckBox)ie.getSource();
						cb.setSelected(false);
					}
				} else if(ItemEvent.DESELECTED == ie.getStateChange()) {
					JCheckBox cb = (JCheckBox)ie.getSource();
					selectedNumbers.remove(selectedNumbers.indexOf(Integer.parseInt(cb.getText())));
					selectedNumberCount--;
				}
			}
		};
		
		for(int i=0; i<rbtnLottoNums.length; i++) {
			rbtnLottoNums[i].addItemListener(iListener);
		}
		
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(selectedNumberCount == 6) {
					JButton btn = (JButton)ae.getSource();
					btn.setEnabled(false);
					confirmedLot++;
					int idx = Arrays.asList(btnConfirms).indexOf(btn);
					Integer[] numbers = selectedNumbers.toArray(new Integer[6]);
					Arrays.sort(numbers);
					tfLottoNum[idx].setText(Arrays.toString(numbers));
				} else {
					JOptionPane.showMessageDialog(SelectNumber.this, 
							"번호를 6개 선택했는지 확인해주세요", 
							"안내", 
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		};
		
		for(int i=0; i<number; i++) {
			btnConfirms[i].addActionListener(aListener);
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				slType.setVisible(true);
			}
		});
		
		ActionListener closingListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dispose();
				slType.setVisible(true);
			}
		};
		
		btnCancel.addActionListener(closingListener);
		
		ActionListener buyListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(confirmedLot == number) {
					int result = JOptionPane.showConfirmDialog(SelectNumber.this, 
							"이대로 구입하시겠습니까?", 
							"구입확인", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.QUESTION_MESSAGE
					);
					if(result == JOptionPane.YES_OPTION) {
						int[][] lottos = new int[number][6];
						for(int i=0; i<number; i++) {
							String[] str = new String[6];
							String temp = tfLottoNum[i].getText().substring(1, tfLottoNum[i].getText().length()-1);
							str = temp.split(", ");
							int[] lotto = new int[6];
							for(int idx=0; idx<6; idx++) {
								lotto[idx] = Integer.parseInt(str[idx]);
							}
							lottos[i] = lotto;
						}
						owner.getCheckMyNum().addLotto(new Lotto(lottos));
						JOptionPane.showMessageDialog(SelectNumber.this, 
								"복권구입이 완료되었습니다\n구입한 복권은 구입한 복권 확인창에서 확인할 수 있습니다", 
								"구입완료", 
								JOptionPane.PLAIN_MESSAGE
						);
						dispose();
						slType.dispose();
						owner.setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(SelectNumber.this, 
							"모든 복권 용지가 기입되지 않았습니다",
							"안내", 
							JOptionPane.INFORMATION_MESSAGE
					);
				}
			}
		};
		
		btnBuy.addActionListener(buyListener);
	}
	
	private void showFrame() {
		setTitle("수동 구입");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
}
