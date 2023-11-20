package LottoMecro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public void insertMember(String id, String pw, String name, String nickname) {

		System.out.println("insert----------db");
		try {
			String sql = "INSERT INTO member_table (member_id, member_pw, member_name, member_nickname) VALUES ('" + id + "' , '" + pw + "' , '" + name + "' , '" + nickname +"')";
			ps = conn.prepareStatement(sql);
			
			// 명령어 실행
			int rsId = ps.executeUpdate();
			System.out.println(rsId);
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
