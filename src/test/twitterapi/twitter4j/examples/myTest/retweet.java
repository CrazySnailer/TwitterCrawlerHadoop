package twitter4j.examples.myTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

public class retweet {	
	
	private static Properties props = new Properties();
	
	public static void retweetId (String str, String key, String sec, String tk, String ts){
		String userName="";
		String userPasswd="";
		String dbName="";
		userName="webuser";
		userPasswd="webuser10";
		dbName="XuniTwitter";;
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
		//Statement statementAdd = null;
		try {
			statement = connection.createStatement();
			//statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, key);
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, sec);	
		AccessToken token = new AccessToken(tk, ts);
		PropertyConfiguration conf = new PropertyConfiguration(props);
		Twitter twitter = new TwitterFactory(conf).getInstance(token);
		String sql= "select statusId from userStatus where userTweet like '%" + str + "%'" ;
		ResultSet rs;
		try {
			rs = statement.executeQuery(sql);
			long statusId = -1;
			while (rs.next()){
				statusId = rs.getLong(1);
				break;
			}
			rs.close();
			if(statusId != -1){
				try {
					twitter.retweetStatus(statusId);
					System.out.println("Successfully retweeted status [" + statusId + "].");
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String str = "引发脑中风恐要命";
		String [] key = {"WdM6eZhENPYSEbxvCXH8A",
				""};
		String [] sec = {"Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo",
				""};
		String [] tk = {"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				""};
		String [] ts = {"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc",
				""};
		for (int i = 0; i < key.length; i++){
			if(!key[i].equals("")){
				retweetId(str,key[i],sec[i],tk[i],ts[i]);
			}
		}
	}

}
