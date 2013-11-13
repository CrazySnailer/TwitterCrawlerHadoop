package twitter4j.examples.myTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

public class edgeNormal {
	
	public static void main(String[] args)  {
		String userName="";
		String userPasswd="";
		String dbName="";
		userName="webuser";
		
		//userPasswd="webuser10";
		userPasswd="webuser10";
		dbName="twitter";;
		String url="jdbc:mysql://172.20.201.130/"+dbName+"?user="+userName+"&password="+userPasswd+"&useUnicode=true&characterEncoding=gbk";
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
		Statement statement = null;
		Statement statementAdd = null;
		try {
			statement = connection.createStatement();
			statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		HashSet <String> nameSet = new HashSet <String> ();
		String sql="SELECT  distinct username  FROM tw_twitter_tweet_info where content like '%·ÆÂÉ±ö%' ;";
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				nameSet.add(rs.getString(1));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		sql="select id,username,fansorfriendname from tw_twitter_fans_friend";
		HashMap <Integer,String> keyValue = new HashMap <Integer,String>();
		HashMap <String,Integer> valueKey = new HashMap <String,Integer>();
		HashSet <String> edgeSet = new HashSet <String>();
		int temp = 1;
		int curse = -1;
		try {
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()){
				String sourceId = rs.getString(2);
				String targetId = rs.getString(3);
				if(nameSet.contains(sourceId)){
					String edge = sourceId + " " + targetId;
					int sourceIdInt = -1;
					int targetIdId = -1;
					if(!edgeSet.contains(edge)){
						edgeSet.add(edge);
						if(!valueKey.containsKey(sourceId)){
							keyValue.put(temp, sourceId);
							valueKey.put(sourceId, temp);
							sourceIdInt = temp;
							temp ++;
						}
						else {
							sourceIdInt = valueKey.get(sourceId);
						}
						if(!valueKey.containsKey(targetId)){
							keyValue.put(temp, targetId);
							valueKey.put(targetId, temp);
							targetIdId = temp;
							temp ++;
						}
						else {
							targetIdId = valueKey.get(targetId);
						}
						
						curse ++;
						String sqlInsert="insert into edges (edgeId,sourceId,targeId) values (" + curse + "," +  + sourceIdInt + "," + targetIdId  + ")";
						statementAdd.execute(sqlInsert);
					}
				}
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap <String,String> idToName = new HashMap <String,String>();
		sql="select username,nickname from tw_twitter_user_info";
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				idToName.put(rs.getString(1), rs.getString(2));
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		for(int id : keyValue.keySet()){
			String userId = keyValue.get(id);
			try {
				String nickName = idToName.get(userId);
				String sqlInsert="insert into nodes (nodeId,nodeName) values (" + id + ",'" + userId  + "')";
				try {
					statementAdd.execute(sqlInsert);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			statementAdd.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("³É¹¦====================");
	}

}
