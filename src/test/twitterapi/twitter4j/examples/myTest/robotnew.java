package twitter4j.examples.myTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

public class robotnew {

	private static int frequentLimit = 15;
	private static long interval = 1000 * 60 * 15;
	private static Properties props = new Properties();

	public static void main(String[] args) throws TwitterException {
		String userName="";
		String userPasswd="";
		String dbName="";
		userName="webuser";
		
		//userPasswd="webuser10";
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
		try {
			statement = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(
					"source.txt")));
			BufferedReader br1 = new BufferedReader(new FileReader(new File(
					"username.txt")));
			String str = "";
			Date startTime = new Date();
			int whileCurse = 0;
			props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
			props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
			props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
					"mES6W9DXppL2JxV1LL1qA");
			props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
					"5UcDbxMhCaJ6jNcHoBVGVZHtuo402mI04aIg6WM9xs");
			AccessToken token = new AccessToken(
					"835028173-Jp708qKFZiW2V8UEi0ghCQRmkzbYy6O5avMZruwz",
					"Vqyuf2p2YLBnKVYqv34D8G5TQevXon8xiizM1rvPQU");
			PropertyConfiguration conf = new PropertyConfiguration(props);
			Twitter twitter = new TwitterFactory(conf).getInstance(token);
			int excepNum = 0;
			HashMap <Integer,String> nameSet = new HashMap <Integer,String>();
			int loopCurse = -1;
			while ((str = br1.readLine()) != null) {
				loopCurse ++;
				nameSet.put(loopCurse, str);
			}
			br1.close();
			int kk = 0;
			while ((str = br.readLine()) != null) {
				kk++;
				if(str.equals("")){
					continue;
				}
				if(str.length() > 110){
					str = str.substring(0, 100);
				}			
				double r = Math.random() * nameSet.size();
				String methionName = nameSet.get((int)r);
				if(kk%100 == 0){
					str = "@" + methionName + " " + str;
				}				
				if(excepNum == 5){
					System.out.println("Sleep one hour..............");
					Thread.sleep((long)(Math.random() * 1000 * 60 * 60));
					excepNum = 0;
					props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
					props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
							"mES6W9DXppL2JxV1LL1qA");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
							"5UcDbxMhCaJ6jNcHoBVGVZHtuo402mI04aIg6WM9xs");
					token = new AccessToken(
							"835028173-Jp708qKFZiW2V8UEi0ghCQRmkzbYy6O5avMZruwz",
							"Vqyuf2p2YLBnKVYqv34D8G5TQevXon8xiizM1rvPQU");
					conf = new PropertyConfiguration(props);
					twitter = new TwitterFactory(conf).getInstance(token);
				}
				whileCurse++;
				try {
					twitter.updateStatus(str);
					System.out.println("Update status: " + str);
					String userId = "835028173";
					String text = str;
					java.util.Date date1 = new Date();
					java.sql.Timestamp currentTime = new java.sql.Timestamp(date1.getTime());
					String strSql ="insert into user_tweet (userid,userTweet,statusTime) values('"+userId+"','"+text+"','"+currentTime+"')";
					try {
						statement.execute(strSql);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						try {
							statement.close();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							connection.close();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							connection = DriverManager.getConnection(url);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							statement = connection.createStatement();
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							statement.execute(strSql);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
					System.out.println("insert content success");					
				} catch (Exception e1) {
					excepNum ++;
					props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
					props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
							"mES6W9DXppL2JxV1LL1qA");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
							"5UcDbxMhCaJ6jNcHoBVGVZHtuo402mI04aIg6WM9xs");
					token = new AccessToken(
							"835028173-Jp708qKFZiW2V8UEi0ghCQRmkzbYy6O5avMZruwz",
							"Vqyuf2p2YLBnKVYqv34D8G5TQevXon8xiizM1rvPQU");
					conf = new PropertyConfiguration(props);
					twitter = new TwitterFactory(conf).getInstance(token);
					// TODO Auto-generated catch block					
					e1.printStackTrace();
				}				
				//double r = Math.random() * 1000 * 60;
				//Thread.sleep((long) r);
				Thread.sleep(1000 * 60);
				//System.out.println("Sleep time: " + (long) r);
				System.out.println("Sleep time: one minute");
				if (whileCurse == frequentLimit) {
					Date endTime = new Date();
					long tempInterval = endTime.getTime() - startTime.getTime();
					System.out.println("Sleep......" + "time: "
							+ (interval - tempInterval + 1) / (1000 * 60)
							+ "minutes");
					if (tempInterval < interval) {
						try {
							Thread.sleep(interval - tempInterval + 1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					startTime = new Date();
					whileCurse = 0;
				}
				/*if(dailyLimit == 290){
					System.out.println("Sleep one dya..............");
					Thread.sleep((long)(Math.random() * 1000 * 60 * 60 * 24));
					dailyLimit = 0;
					props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
					props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
							"eEKgNmj5LZM1Y0s9w256A");
					props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
							"IZzKrkFVd2dsJkaKQCUFA5vHFI8cDjthj7hccXnM");
					token = new AccessToken(
							"1346773026-x9FAnE5qFB3xjm47OU2puDSQiduMS7WDc3fdUX7",
							"EOQC5Ejfx8CVy4y1ZBFgQbrSaQltsesULNm0rNHPDQ");
					conf = new PropertyConfiguration(props);
					twitter = new TwitterFactory(conf).getInstance(token);
				}*/
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
