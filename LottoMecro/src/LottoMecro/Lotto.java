package LottoMecro;
import java.util.Arrays;

public class Lotto {
	private int[][] lottoNum;			// 로또 번호
	private int lottoPNum;				// 로또 발행번호
	private int lottoRNum;				// 로또 회차
	private boolean lottoAuto;			// 로또 자동 or 수동
	
	private int[] lotNum;				// 추첨 번호
	
	// 복권 발행 번호
	private static int publishNum = 0001;
	// 복권 회차
	private static int round = 1;
	
	public Lotto(int count) {
		// 자동 복권 구입
		buyRadomLot(count);
		setLottoPNum(Lotto.getPublishNum());
		Lotto.nextPulishNum();
		setLottoRNum(Lotto.getRound());
		setLottoAuto(true);
	}
	public Lotto(int[][] lottoNum) {
		// 수동 복권 구입
		setLottoNum(lottoNum);
		for(int i=0; i<this.lottoNum.length; i++) {
			Arrays.sort(this.lottoNum[i]);
		}
		setLottoPNum(Lotto.getPublishNum());
		Lotto.nextPulishNum();
		setLottoRNum(Lotto.getRound());
		setLottoAuto(true);
	}
	public Lotto() {
		// 추첨
		lot();
		setLottoNum(new int[0][0]);
		setLottoRNum(Lotto.getRound());
	}
	
	public int[][] getLottoNum() {
		return lottoNum;
	}
	public void setLottoNum(int[][] lottoNum) {
		this.lottoNum = lottoNum;
	}
	public int[] getLotNum() {
		return lotNum;
	}
	public void setLotNum(int[] lotNum) {
		this.lotNum = lotNum;
	}
	public int getLottoPNum() {
		return lottoPNum;
	}
	public void setLottoPNum(int lottoPNum) {
		this.lottoPNum = lottoPNum;
	}
	public int getLottoRNum() {
		return lottoRNum;
	}
	public void setLottoRNum(int lottoRNum) {
		this.lottoRNum = lottoRNum;
	}
	public boolean isLottoAuto() {
		return lottoAuto;
	}
	public void setLottoAuto(boolean lottoAuto) {
		this.lottoAuto = lottoAuto;
	}
	
	private void buyRadomLot(int count) {
		lottoNum = new int[count][6];
		for(int i=0; i<count; i++) {
			for(int idx=0; idx<6; idx++) {
				int result = (int)(Math.random() * 45) + 1;
				lottoNum[i][idx] = result;
				for(int j=0; j<idx; j++) {
					if(lottoNum[i][j] == result) {
						idx--;
					}
				}	
			}
			Arrays.sort(lottoNum[i]);
		}
	}
	
	private void lot() {
		lotNum = new int[7];
		for(int i=0; i<7; i++) {
			int result = (int)(Math.random() * 45) + 1;
			lotNum[i] = result;
			for(int j=0; j<i; j++) {
				if(lotNum[j] == result) {
					i--;
				}
			}	
		}
		Arrays.sort(lotNum, 0, 5);
	}
	
	public static int getPublishNum() {
		return Lotto.publishNum;
	}
	
	public static void nextPulishNum() {
		Lotto.publishNum++;
	}
	
	public static int getRound() {
		return Lotto.round;
	}
	
	public static void nextRound() {
		Lotto.round++;
	}
	
	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append("제" + lottoRNum + "회 복권(");
		info.append("발행번호: " + lottoPNum + ")");
		String auto = lottoAuto ? "자동" : "수동";
		info.append(" - " + auto +"\n");
		
		StringBuffer str = new StringBuffer();
		for(int i=0; i<lottoNum.length; i++) {
			str.append((char)(65+i) + ". " + Arrays.toString(lottoNum[i]) + "\n");
		}
		
		info.append(str);
		return info.toString();
	}
}
