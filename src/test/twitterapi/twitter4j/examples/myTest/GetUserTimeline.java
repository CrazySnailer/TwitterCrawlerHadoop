/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package twitter4j.examples.myTest;

import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class GetUserTimeline {
    /**
     * Usage: java twitter4j.examples.timeline.GetUserTimeline
     *
     * @param args String[]
     */
	
	private static Properties props = new Properties();
	
	public static void getuserNetWork(String userScreenName){
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
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
		AccessToken token = new AccessToken(
				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
		PropertyConfiguration conf = new PropertyConfiguration(props);
		Twitter twitter = new TwitterFactory(conf).getInstance(token);
		User user;
		long userIDSource = -1;		
		try {
			user = twitter.showUser(userScreenName);
			if (user.getStatus() != null) {
				userIDSource = user.getId();
	        }
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		System.out.println("Sleep one hour..............");
		try {
			Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
			//statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		long cursor = -1;
        IDs ids = null;
        do {
        	try {
				ids = twitter.getFollowersIDs(userScreenName, cursor);
				for (long id : ids.getIDs()) {
					Date nowTime = new Date();
					java.sql.Timestamp currentTime = new java.sql.Timestamp(nowTime.getTime());
					String strSql ="insert into netWorks (sourceId,targetId,tag,currentTime) values('"+userIDSource+"','"+id+"','"+0+"','"+currentTime+"')";
					try {
						statement.execute(strSql);
						//System.out.println("insert followers success------------------------------------------------");	
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
					}
	            }
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}            
        } while ((cursor = ids.getNextCursor()) != 0);
        System.out.println("Sleep one hour..............");
		try {
			Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
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
			//statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        cursor = -1;
        ids = null;
        do {
        	try {
				ids = twitter.getFriendsIDs(userScreenName, cursor);
				 for (long id : ids.getIDs()) {
					 Date nowTime = new Date();
						java.sql.Timestamp currentTime = new java.sql.Timestamp(nowTime.getTime());
						String strSql ="insert into netWorks (sourceId,targetId,tag,currentTime) values('"+userIDSource+"','"+id+"','"+1+"','"+currentTime+"')";
						try {
							statement.execute(strSql);
							//System.out.println("insert friends success------------------------------------------------");	
						} catch (SQLException e) {
							// TODO Auto-generated catch block
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
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							e.printStackTrace();
						}
		            }
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
        } while ((cursor = ids.getNextCursor()) != 0);
	}
	
	public static void getUserInfo(String [] userScreenName){
		
		props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
		AccessToken token = new AccessToken(
				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
		PropertyConfiguration conf = new PropertyConfiguration(props);
		Twitter twitter = new TwitterFactory(conf).getInstance(token);
		try {
			ResponseList<User> users = twitter.lookupUsers(userScreenName);
			 for (User user : users) {
	                if (user.getStatus() != null) {
	                   // System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
	                    String userNameS = user.getScreenName();
	                    String userNameU = user.getName();
	                    int statusNum = user.getStatusesCount();
	                    int followerNum = user.getFollowersCount();
	                    int friendNum = user.getFriendsCount();
	                    java.sql.Timestamp userDate = new java.sql.Timestamp(user.getCreatedAt().getTime());
	                    Date nowTime = new Date();
						java.sql.Timestamp currentTime = new java.sql.Timestamp(nowTime.getTime());
						long userId = user.getId();
						String timezone = user.getTimeZone();
						String location = user.getLocation();
						String desc = user.getDescription();
						//String strSql ="insert into user_info (userId,userScreenName,userName,statusNum,followerNum,friendNum,createTime,currentTime,timeZone,location,desc) values('"+userId+"','"+userNameS+"','"+userNameU+"','"+statusNum+"','"+followerNum+"','"+friendNum+"','"+userDate+"','"+currentTime+"','"+timezone+"','"+location+"','"+desc+"')";
						String strSql ="insert into user_info (userId,userScreenName,userName,statusNum,followerNum,friendNum,createTime,currentTime,timeZone,location) values('"+userId+"','"+userNameS+"','"+userNameU+"','"+statusNum+"','"+followerNum+"','"+friendNum+"','"+userDate+"','"+currentTime+"','"+timezone+"','"+location+"')";
						
						//System.out.println(strSql);
						//String strSql ="insert into userStatus (statusId,userScreenName,userTweet,statusTime,retweetNum,source,insertTime) values('"+statusId+"','"+screenName+"','"+text+"','"+statusDate+"','"+retweetNum+"','"+source+"','"+currentTime+"')";
						
						
						
	                } else {
	                    // the user is protected
	                    System.out.println("@" + user.getScreenName());
	                }
	            }
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void getTimeLine(String userScreenName){
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
		Statement statementAdd = null;
		try {
			statement = connection.createStatement();
			statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
		AccessToken token = new AccessToken(
				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
		PropertyConfiguration conf = new PropertyConfiguration(props);
		Twitter twitter = new TwitterFactory(conf).getInstance(token);
        try {
            List<Status> statuses = null;
            String user;           
            user = userScreenName;       
            Paging page = new Paging();
            page.setCount(200);
            int captionNum = 0;
            for(int a = 1; a <= 15; a++){
            	System.out.println("Sleep one hour..............");
				try {
					Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
					statementAdd = connection.createStatement();
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
            	page.setPage(a);
            	statuses = twitter.getUserTimeline(user,page);
                for (Status status : statuses) {
                    //System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                    try {
						String screenName = status.getUser().getScreenName();
						long statusId = status.getId();
						Date statusTime = status.getCreatedAt();
						long retweetNum = status.getRetweetCount();
						String text = status.getText();
						String source = status.getSource();
						Date nowTime = new Date();
						java.sql.Timestamp currentTime = new java.sql.Timestamp(nowTime.getTime());
						java.sql.Timestamp statusDate = new java.sql.Timestamp(statusTime.getTime());
						String strSql ="insert into userStatus (statusId,userScreenName,userTweet,statusTime,retweetNum,source,insertTime) values('"+statusId+"','"+screenName+"','"+text+"','"+statusDate+"','"+retweetNum+"','"+source+"','"+currentTime+"')";
						try {
							statement.execute(strSql);
							System.out.println("insert content success------------------------------------------------");	
						} catch (Exception e) {
							// TODO Auto-generated catch block
							String sqlupdate="update userStatus set retweetNum = '"+retweetNum+"' where statusId ='"+statusId+"'";
							try {
								statementAdd.executeUpdate(sqlupdate);
								System.out.println("update retweets success-------------------------------------------");
							} catch (SQLException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							//e.printStackTrace();
						}						
						double r = Math.random() * 1000 * 10;
						try {
							Thread.sleep((long) r);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Sleep time: " + (long) r);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						captionNum ++;
						if (captionNum == 4){
							System.out.println("Sleep one hour..............");
							try {
								Thread.sleep((long)(Math.random() * 1000 * 60 * 30));
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
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
								statementAdd = connection.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
						captionNum = 0;
						e.printStackTrace();
					}
                }
            }
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	 public static void main(String[] args) {
		 String [] userScreenName = new String [1];
		 userScreenName [0] = "opiniontest";
		 while (true){
			 try {
				 System.out.println("��ȡ�����ϵ----------------------");
				getuserNetWork("opiniontest");
				/*try {
					Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}				 
			 try {
				System.out.println("��ȡ������Ϣ----------------------");
				getUserInfo(userScreenName);
				/*try {
					Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		 
			 try {
				 System.out.println("��ȡ������Ϣ----------------------");
				getTimeLine("opiniontest");
				/*try {
					Thread.sleep((long)(Math.random() * 1000 * 60 * 5));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }		
	 }
	
	
    /*public static void main(String[] args) {
        // gets Twitter instance with default credentials
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
		Statement statementAdd = null;
		try {
			statement = connection.createStatement();
			statementAdd = connection.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		props.put(PropertyConfiguration.HTTP_PROXY_HOST, "127.0.0.1");
		props.put(PropertyConfiguration.HTTP_PROXY_PORT, "8087");
		props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY, "WdM6eZhENPYSEbxvCXH8A");
        props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET, "Z3rUV2S9sgsfTKytdpipjugZykQe9F8TQPOyt9ldo");	
		AccessToken token = new AccessToken(
				"835028173-Tc6qHpE14zNWcxpp65aKkIzd3ggXRU2xKtjnKdzh",
				"cGrAh8FDmfDwNlLEr2PCwzMJulzMCu0bPkxz5suMCc");
		PropertyConfiguration conf = new PropertyConfiguration(props);
		Twitter twitter = new TwitterFactory(conf).getInstance(token);
        try {
            List<Status> statuses = null;
            String user;           
            user = "opiniontest";       
            Paging page = new Paging();
            page.setCount(200);
            int captionNum = 0;
            for(int a = 1; a <= 15; a++){
            	page.setPage(a);
            	statuses = twitter.getUserTimeline(user,page);
                for (Status status : statuses) {
                    System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
                    try {
						String screenName = status.getUser().getScreenName();
						long statusId = status.getId();
						Date statusTime = status.getCreatedAt();
						long retweetNum = status.getRetweetCount();
						String text = status.getText();
						String source = status.getSource();
						Date nowTime = new Date();
						java.sql.Timestamp currentTime = new java.sql.Timestamp(nowTime.getTime());
						java.sql.Timestamp statusDate = new java.sql.Timestamp(statusTime.getTime());
						String strSql ="insert into userStatus (statusId,userScreenName,userTweet,statusTime,retweetNum,source,insertTime) values('"+statusId+"','"+screenName+"','"+text+"','"+statusDate+"','"+retweetNum+"','"+source+"','"+currentTime+"')";
						try {
							statement.execute(strSql);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							String sqlupdate="update userStatus set retweetNum = '"+retweetNum+"' where statusId ='"+statusId+"'";
							try {
								statementAdd.executeUpdate(sqlupdate);
								System.out.println("update retweets success-------------------------------------------");
							} catch (SQLException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							if(statement == null){    						
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
									statementAdd = connection.createStatement();
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								try {
									statement.execute(strSql);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									sqlupdate="update userStatus set retweetNum = '"+retweetNum+"' where statusId ='"+statusId+"'";
									try {
										statementAdd.executeUpdate(sqlupdate);
										System.out.println("update retweets success");
									} catch (SQLException e3) {
										// TODO Auto-generated catch block
										e3.printStackTrace();
									}
									e1.printStackTrace();
								}						
							}
							//e.printStackTrace();
						}
						System.out.println("insert content success------------------------------------------------");	
						double r = Math.random() * 1000 * 10;
						try {
							Thread.sleep((long) r);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Sleep time: " + (long) r);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						captionNum ++;
						if (captionNum == 4){
							System.out.println("Sleep one hour..............");
							try {
								Thread.sleep((long)(Math.random() * 1000 * 60 * 60));
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						captionNum = 0;
						e.printStackTrace();
					}
                }
            }
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }*/
}
