
/**
* @project Web
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.mainApp
* @file UserInfoDetailsCrawler.java
* 
* @date 2013-6-9-上午8:26:40
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.mainApp;

import isiteam.TwitterCrawler.database.bean.SeedsQueue;
import isiteam.TwitterCrawler.database.bean.UserInfo;
import isiteam.TwitterCrawler.database.dao.SeedsQueueDao;
import isiteam.TwitterCrawler.database.dao.UserInfoDao;
import isiteam.TwitterCrawler.util.AppContext;
import isiteam.TwitterCrawler.util.AppToken;
import isiteam.TwitterCrawler.util.CharUtil;
import isiteam.TwitterCrawler.util.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterResponse;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;


/**
 * @project Web
 * @author Dayong.Shen
 * @class UserInfoDetailsCrawler
 * 
 * @date 2013-6-9-上午8:26:40
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@Controller
public class UserInfoDetailsCrawler {
	private static final Logger log =  LoggerFactory
			.getLogger(UserInfoDetailsCrawler.class);
	
	
	@Resource
	private	SeedsQueueDao seedsQueueDao;
	
	@Resource
	private UserInfoDao userInfoDao;
	
	private static AppToken currentAppToken = new AppToken();
	
	private static Twitter twitter;
	
	private static List<AppToken> appTokenList=new ArrayList<AppToken>();
	
	
	private int myArrayIndexOf(long[] SeedsIdArr,long obj){
		
		for(int i=0;i<SeedsIdArr.length;i++){
			if(SeedsIdArr[i]==obj){
				return i;
			}
		}
		return -1;
	}
	
	@SuppressWarnings("deprecation")
	private void startCrawling(String AppTokenFilePath) {
		// TODO Auto-generated method stub
		
        initialAccessTokenList(Constant.TwitterKey_PATH,AppTokenFilePath);
		
		getOneToken();
		
		int Count=100;
		int batchSize=100;		
		
		List<SeedsQueue> Queue = null;
		long[] SeedsIdArr;
				
		List<UserInfo> UserInfoList=new ArrayList<UserInfo>();
		
		boolean isChinese;
		
		while(true){
			
				if(Count>100){
					log.info("用户队列不能大于100！");
					System.exit(0);
				}
				
				try {
					Queue = seedsQueueDao.getSeedsQueueByisUserInfo(Count);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					log.error("getSeedsQueueByisUserInfo ERROR!"+e2.getMessage());	
				}
				

				if(Queue==null){//已经取不到数据
					continue;
				}
				log.info("采集用户信息队列 Queue Size: "+Queue.size());
				
				
				SeedsIdArr=new long[Queue.size()];
				UserInfoList.clear();
				
				for(int j=0;j<Queue.size();j++){					
					SeedsIdArr[j]=Long.valueOf(Queue.get(j).getUserId());//正序
				}
				
				log.info("采集用户信息队列 Queue Content: "+ Arrays.toString(SeedsIdArr));
				
				try {
					
					ResponseList<User> users = twitter.lookupUsers(SeedsIdArr);
					
				log.info("采集用户信息返回个数: "+ users.size());	
					
					
					
					
					for (int h=0;h<users.size();h++) {
						
						User user = users.get(h);
						
						isChinese=false;
						try {
							
							if(CharUtil.ChinesePercent(user.getStatus().getText())>0.6&&!CharUtil.isChineseByREG(user.getStatus().getText())&&!CharUtil.isKoreaByREG(user.getStatus().getText())){
								isChinese=true;
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();							
						}
						
	                    if((isChinese||user.getLang().startsWith("zh-")||CharUtil.ChinesePercent(user.getDescription())>0.6)&&!user.getLang().startsWith("ja")&&!CharUtil.isChineseByREG(user.getDescription())&&!CharUtil.isKoreaByREG(user.getDescription())){
	                    	//为中文用户
	                    	UserInfo userInfo=new UserInfo();
	    					
							
							userInfo.setAllText(CharUtil.withNonBmpStripped(String.valueOf(user)));
							
							userInfo.setUserId(String.valueOf(user.getId()));
							userInfo.setScreenName(user.getScreenName());
							userInfo.setName(CharUtil.withNonBmpStripped(user.getName()));
							userInfo.setLocation(CharUtil.withNonBmpStripped(user.getLocation()));
							userInfo.setTimeZone(CharUtil.withNonBmpStripped(user.getTimeZone()));
							userInfo.setLang(CharUtil.withNonBmpStripped(user.getLang()));
							
							userInfo.setDescription(CharUtil.withNonBmpStripped(user.getDescription()));
							
							userInfo.setCreatedAt(new Timestamp(user.getCreatedAt().getTime()));
							userInfo.setProfileImageUrl(user.getProfileImageURL());
							userInfo.setWebExtendurl(CharUtil.withNonBmpStripped(user.getURL()));
							userInfo.setIsProtected(user.isProtected()? 1 : 0);
							userInfo.setFollowersCount(user.getFollowersCount());
							userInfo.setFriendsCount(user.getFriendsCount());
							userInfo.setStatusesCount(user.getStatusesCount());
							userInfo.setFavouritesCount(user.getFavouritesCount());
							userInfo.setIsVerified(user.isVerified()? 1 : 0);
							userInfo.setIsGeoEnabled(user.isGeoEnabled()? 1 : 0);
							userInfo.setCrawledNum(Queue.get(myArrayIndexOf(SeedsIdArr,user.getId())).getIsUserInfo());//指定爬行次数
														
							
							try {
								userInfo.setCurrentStatuscreatedAt(new Timestamp(user.getStatus().getCreatedAt().getTime()));
								userInfo.setCurrentStatusId(String.valueOf(user.getStatus().getId()));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
							
							userInfo.setInsertTime(new Timestamp(System.currentTimeMillis()));
						
							   //加入对列之前，看看数据库有没有
							   if (!userInfoDao.getIsExistUserInfo(userInfo)){
								   UserInfoList.add(userInfo);
							   }
							
							   Queue.get(myArrayIndexOf(SeedsIdArr,user.getId())).setIsDeal(1);//设置中文用户
							
						 }else{//为其他语言用户,则下次不予采集
							 
							 
							 Queue.get(myArrayIndexOf(SeedsIdArr,user.getId())).setIsDeal(2);//设置禁止采集
							 
							 
						 }//end if
						
						
						
						
						
					}// end for
					
					 try {
						 userInfoDao.batchSaveUserInfoList(UserInfoList,batchSize);
						    } catch (Exception e1) {
							// TODO Auto-generated catch block
						    	log.error("batchSaveUserInfoList ERROR!"+e1.getMessage());	
						    }
					 
				      //更新Seed中的UserInfo字段 					 
					 for(SeedsQueue oneSeed :Queue){
						//更新
						 oneSeed.setIsUserInfo(oneSeed.getIsUserInfo()+1);
						 oneSeed.setIsUserStatus(true);
						 oneSeed.setInsertTime(new Timestamp(System.currentTimeMillis()));
						 
						  try {
								seedsQueueDao.updateisUserInfo(oneSeed);
								
							   } catch (Exception e1) {
								// TODO Auto-generated catch block
								log.error("updateisUserInfo ERROR!"+e1.getMessage());	
							   }
						 
					 }
					 
						if(users.getRateLimitStatus().getRemaining()==0){
														
							currentAppToken.setEndTime(System.currentTimeMillis());
							currentAppToken.setResetTime(users.getRateLimitStatus().getSecondsUntilReset()*1000);
							
							getOneToken();
							
						}
					
					
				} catch (TwitterException te) {
		            log.error("Failed to lookup Users: " + te.getMessage());	 
		            if(te.getStatusCode()==429){
		            	
		            	currentAppToken.setEndTime(System.currentTimeMillis());
						currentAppToken.setResetTime(te.getRateLimitStatus().getSecondsUntilReset()*1000);
						
						getOneToken();
						
		            }else{//If a requested user is unknown, suspended, or deleted, then that user will not be returned in the results list.
		             		
		            		 //更新Seed中的UserInfo字段 					 
							 for(SeedsQueue oneSeed :Queue){
								//更新
								 oneSeed.setIsUserInfo(oneSeed.getIsUserInfo()+1);
								 oneSeed.setIsUserStatus(true);
								 oneSeed.setInsertTime(new Timestamp(System.currentTimeMillis()));
								 
								  try {
										seedsQueueDao.updateisUserInfo(oneSeed);
										
									   } catch (Exception e1) {
										// TODO Auto-generated catch block
										log.error("updateisUserInfo ERROR!"+e1.getMessage());	
									   }
		            		
		            	}//end for
		            	
		            }//end else
		            
		            
		           }catch(Exception ote){
		        	   
		        	   log.error("UserInfoDetails Crawler Other ERROR!"+ote.getMessage());  
		        	   
		           }//End Catch

				
			}//  End While
			

			
			
			
		}//end startCrawling
		

	
	/**
	 * @function main
	 * 
	 * @param args
	 * @author Dayong.Shen
	 * @date 2013-6-9-上午8:26:40
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PropertyConfigurator.configureAndWatch(Constant.LOG4J_PATH);
		log.info("正在创建数据库连接和缓冲池...");
	    AppContext.initAppCtx();
		log.info("数据库连接已连接！缓冲池已建立");
	
		UserInfoDetailsCrawler crawler=(UserInfoDetailsCrawler) AppContext.appCtx.getBean("userInfoDetailsCrawler");
		
		if (args.length < 1) {
                // consumer key/secret are not set in twitter4j.properties
                System.out.println(
                        "Usage: java isiteam.TwitterCrawler.mainApp.UserInfoDetailsCrawler [twitter4j.properties filePathName]");
                System.exit(-1);
           
        } 
		
		crawler.startCrawling(args[0]);

	}
	
	public void initialAccessTokenList(String filePath,String fileName){
		
		   filePath=filePath+File.separator +fileName;
			
			final String extend_name = ".properties";
			
			File dir = new File(filePath);
			if(!dir.isDirectory()){
				log.info("不是有效的文件夹目录!"+filePath);
				System.exit(-1);
				return;
			}
			// 所有的文件和目录名
		    String[] children=null;
		    
		    // 可以指定返回文件列表的过滤条件,返回那些以extend_name开头的文件名
		    FilenameFilter filter = new FilenameFilter() {
		      public boolean accept(File dir, String name) {
		        return name.endsWith(extend_name);
		      }
		    };
		    children = dir.list(filter);
		    
		    for (int i = 0; i < children.length; i++) {
		      // 文件名
		    	log.info(children[i]);
		      
		      	File file = new File(filePath+File.separator +children[i]);
		      	
		      	AppToken appToken=new AppToken();
		        Properties prop = new Properties();
		        InputStream is = null;
		        OutputStream os = null;
		     
		        try {
		        	
		            if (file.exists()) {
		                is = new FileInputStream(file);
		                prop.load(is);
		            }
		          
		            if (null == prop.getProperty("oauth.consumerKey")
	                        && null == prop.getProperty("oauth.consumerSecret")
	                        &&null==prop.getProperty("http.proxyHost")
	                        &&null==prop.getProperty("http.proxyPort")
	                        &&null==prop.getProperty("oauth.accessToken")
	                        &&null==prop.getProperty("oauth.accessTokenSecret")) {
		                    // consumer key/secret are not set in twitter4j.properties
		                	log.info("Invalid Twitter Properties");
		                    //System.exit(-1);
		                }	      
		            
		            appToken.setProps(prop);
		            appToken.setEndTime(0);
		            appToken.setResetTime(0);
		            
		            appTokenList.add(appToken);
		           
		        } catch (IOException ioe) {
		            ioe.printStackTrace();
		            //System.exit(-1);
		        } finally {
		            if (is != null) {
		                try {
		                    is.close();
		                } catch (IOException ignore) {
		                }
		            }
		            if (os != null) {
		                try {
		                    os.close();
		                } catch (IOException ignore) {
		                }
		            }
		        }
		      
		    }//end for
			
			
		}//end initialAccessTokenList
		
		private void getOneToken() {
			// TODO Auto-generated method stub
			if(appTokenList.size()==0){
				log.info("appTokenList 为空");
				System.exit(-1);
				return;
			}
			
			if(!currentAppToken.getProps().isEmpty()){//非初始状态,将此appToken重新置 endTime ResetTime
				
				//在appTokenList中找到isUse为true的appToken
				for(AppToken myapp:appTokenList){
					
					if(myapp.getProps()==currentAppToken.getProps()){
						myapp=currentAppToken;//新置 endTime ResetTime
					}//end if				
				}//end for			
			}//end if
			
			long currentTime=System.currentTimeMillis()+1000;//milliseconds
			
			//找出差值最大的		
			long maxInterval=Long.MIN_VALUE;
			
			for(AppToken myapp:appTokenList){
				
				long temp=currentTime-myapp.getEndTime()-myapp.getResetTime();
				
				if(temp>maxInterval){
					maxInterval=temp;
					currentAppToken=myapp;
				}
				
			}
			
			//根据maxInterval判断是否Sleep还是直接用
			if(maxInterval<0){
				//Sleep一段时间			
				log.info("Sleep intervalTime: "+Math.abs(maxInterval)/60000+" Minutes");
				
				try {
					Thread.sleep(Math.abs(maxInterval)+6000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					log.error("Sleep Error: " + e1.getMessage());	 
				} 
			}
			
			  AccessToken token = new AccessToken(
					  currentAppToken.getProps().getProperty("oauth.accessToken"),
					  currentAppToken.getProps().getProperty("oauth.accessTokenSecret"));
		        
		        log.info("props's accessToken is: "+currentAppToken.getProps().getProperty("oauth.accessToken"));
		        
				PropertyConfiguration conf = new PropertyConfiguration(currentAppToken.getProps());
				twitter = new TwitterFactory(conf).getInstance(token);	
			
		}//end getOneToken



}
