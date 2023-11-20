package LottoMecro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbconnectionClass {
	private static String dburl = "jdbc:mysql://localhost:3306/member";
	private static String dbUser = "root";
	private static String dbpasswd = "tmfdl783";
	
	public DbconnectionClass () {
		Lotto lotto = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			System.out.println("start");
			// 드라이버 로
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 커넥션 객
			conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			
			String sql = "SELECT * FROM member_table";
			ps = conn.prepareStatement(sql);
			
			// 명령어 실행
			rs = ps.executeQuery();
			System.out.println(rs);
			
			while(rs.next()) {
//				String description = rs.getString(1);
//				int id = rs.getInt("role_id");
				
//				lotto = new Lotto(id, description);
				for(int i=0; i<rs.getMetaData().getColumnCount(); i++) {
					System.out.println(rs.getString(rs.getMetaData().getColumnName(i+1)));
				}
			}
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
	public static void main(String[] args) {
		new DbconnectionClass();
	}
}
