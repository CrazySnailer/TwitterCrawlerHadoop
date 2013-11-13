package twitter4j.examples.myTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class insertDataBase {

	public static Date convertDate(String adateStrteStr, String format) {
		java.util.Date date = null;
		try {
			System.out.println(adateStrteStr);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			date = simpleDateFormat.parse(adateStrteStr);
		} catch (Exception ex) {

		}
		return date;
	}

	public static void main(String[] args) {
		String userName = "";
		String userPasswd = "";
		String dbName = "";
		userName = "root";
		userPasswd = "root";
		dbName = "robot";
		;
		String url = "jdbc:mysql://localhost/" + dbName + "?user=" + userName
				+ "&password=" + userPasswd
				+ "&useUnicode=true&characterEncoding=gbk";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IllegalAccessException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Statement statement1 = null;
		try {
			statement1 = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String sql = "select username,content, minipubtime from twitter_tweet_tmp";
		try {
			ResultSet rs = statement1.executeQuery(sql);
			while (rs.next()) {
				String userId = rs.getString(1);
				String text = rs.getString(2);
				String dateStr = rs.getString(3) + "000";
				Date aa = new java.util.Date(Long.parseLong(dateStr));
				java.sql.Timestamp currentTime = new java.sql.Timestamp(aa.getTime());
				String strSql = "insert into user_tweet values('" + userId
						+ "','" + text + "','" + currentTime + "')";
				try {
					statement.execute(strSql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

}
