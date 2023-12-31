package LottoMecro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DbConnection {
	private static String dburl = "jdbc:mysql://localhost:3306/member";
	private static String dbUser = "root";
	private static String dbpasswd = "tmfdl783";
	
	Lotto lotto = null;
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private String id;
	private String pw;
	private String name;
	private String nickname;
	
	public DbConnection() {
		DbConnection();
	}
	
	public void DbConnection() {
		
		try {
			System.out.println("start1111111111");
			// 드라이버 로
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 커넥션 객
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			
//			String sql = "SELECT * FROM member_table";
//			ps = conn.prepareStatement(sql);
//			
//			// 명령어 실행
//			rs = ps.executeQuery();
//			System.out.println(rs);
			
////			while(rs.next()) {
//////				String description = rs.getString(1);
//////				int id = rs.getInt("role_id");
////				
//////				lotto = new Lotto(id, description);
////				for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
////					System.out.println(rs.getString(rs.getMetaData().getColumnName(i+1)));
////				}
//			}
			System.out.println("end222222222222222");
		} catch(Exception e) {
			e.printStackTrace();
		
		}
		
	}

	public int insertMember(String id, String pw, String name, String nickname) {
		int result = 0;
		System.out.println("insert----------db");
		try {
			String sql = "INSERT INTO member_table (member_id, member_pw, member_name, member_nickname) VALUES ('" + id + "' , '" + pw + "' , '" + name + "' , '" + nickname +"')";
			ps = conn.prepareStatement(sql);
			
			// 명령어 실행
			result = ps.executeUpdate();
//			while(rs.next()) {
////				String description = rs.getString(1);
//				int id = rs.getInt("role_id");
//				
////				lotto = new Lotto(id, description);
//				for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
//					System.out.println(rs.getString(rs.getMetaData().getColumnName(i+1)));
//				}
//			}
		} catch(Exception e) {
			e.printStackTrace();
		
		} finally {
			close();
		}
		return result;
	}
		
		
	public Boolean selectMember(String id, String pw) {
			String sql = "SELECT member_id FROM member_table where member_id = '" + id + "' and member_pw = '" + pw + "'";
			try {
				ps = conn.prepareStatement(sql);
				
				rs = ps.executeQuery(sql);
//				System.out.println("rs.getMetaData().getColumnCount() === " + rs.getMetaData().getColumnCount());
//				System.out.println(rs);
					
				if(!rs.next()) {
//					String description = rs.getString(1);
//					int id = rs.getInt("member_id");
//					
//					lotto = new Lotto(id, description);
					
					return false; 
				}  
			} catch (SQLException e) {
				e.printStackTrace();
			} 
			return true;
			
		
	}
	
	public int[][] selectMyLotto(String id) {
		int[][] lottoMyNumbers = new int[45][7];
		int count = 0;
		String sql = "SELECT * FROM lotto_table where id = '" + id + "'";
		try {
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery(sql);
			int idx = rs.getMetaData().getColumnCount() - 1;	
			
			while(rs.next()) {
				System.out.println(rs);
				
				for(int i=0; i<idx; i++) {
					lottoMyNumbers[count][i] = Integer.valueOf(rs.getString(rs.getMetaData().getColumnName(i+1)));
				}
				count++;
			}  
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return lottoMyNumbers;
	
}
	
	public int insertLottoNumber(int[] lottoNumber, String id) {
		int result = 0;
		String sql = "INSERT INTO lotto_table (num1, num2, num3, num4, num5, num6, id) VALUES (";
		for(int i=0; i<lottoNumber.length; i++) {
			int j = lottoNumber[i];
			sql += j;
			sql += ",";	
		}
		try {			
			sql +=  "'" + id + "')";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			
			// 명령어 실행
			result = ps.executeUpdate();
//			while(rs.next()) {
////				String description = rs.getString(1);
//				int id = rs.getInt("role_id");
//				
////				lotto = new Lotto(id, description);
//				for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
//					System.out.println(rs.getString(rs.getMetaData().getColumnName(i+1)));
//				}
//			}
		} catch(Exception e) {
			e.printStackTrace();
		
		} 
		return result;
	}
	
	public void close() {
		try {
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
