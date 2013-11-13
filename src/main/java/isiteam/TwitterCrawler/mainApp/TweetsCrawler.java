
/**
* @project Web
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.mainApp
* @file TweetsCrawler.java
* 
* @date 2013-6-9-下午3:12:36
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.mainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import isiteam.TwitterCrawler.database.bean.SeedsQueue;
import isiteam.TwitterCrawler.database.bean.TweetInfo;
import isiteam.TwitterCrawler.database.bean.UserInfo;
import isiteam.TwitterCrawler.database.dao.SeedsQueueDao;
import isiteam.TwitterCrawler.database.dao.TweetInfoDao;
import isiteam.TwitterCrawler.util.AppContext;
import isiteam.TwitterCrawler.util.AppToken;
import isiteam.TwitterCrawler.util.CharUtil;
import isiteam.TwitterCrawler.util.Constant;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterResponse;
import twitter4j.auth.AccessToken;
import twitter4j.conf.PropertyConfiguration;


/**
 * @project Web
 * @author Dayong.Shen
 * @class TweetsCrawler
 * 
 * @date 2013-6-9-下午3:12:36
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@Controller
public class TweetsCrawler {
	private static final Logger log =  LoggerFactory
			.getLogger(TweetsCrawler.class);
	
	private static Properties props = new Properties();
	
	@Resource
	private	SeedsQueueDao seedsQueueDao;
	
	@Resource
	private	TweetInfoDao tweetInfoDao;
	
	private static AppToken currentAppToken = new AppToken();
	
	private static Twitter twitter;
	
	private static List<AppToken> appTokenList=new ArrayList<AppToken>();
	
	
	public void startCrawling(String AppTokenFilePath){
			
		initialAccessTokenList(Constant.TwitterKey_PATH,AppTokenFilePath);
		
		getOneToken();
			
			
			int Count=180;
			int batchSize=180;
			
			List<SeedsQueue> Queue = null;
			List<TweetInfo> TweetInfoList=new ArrayList<TweetInfo>();
			
			Paging page = new Paging();
			while(true){
				
				if(Count>180){
					log.info("用户队列不能大于100！");
					System.exit(0);
				}
				
				try {
					Queue = seedsQueueDao.getSeedsQueueByisTweetsInfo(Count);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					log.error("getSeedsQueueByisTweetsInfo ERROR!"+e2.getMessage());	
				}
				
				if(Queue==null){//已经取不到数据
					continue;
				}
				log.info("采集用户博文队列 Queue Size: "+Queue.size());
				
				for(SeedsQueue oneSeed:Queue){
					
					log.info("采集用户博文: "+oneSeed.getUserId());
					
					TweetInfoList.clear();
					try {
						
						
				         page.setCount(200);
						 List<Status> statuses=twitter.getUserTimeline(Long.valueOf(oneSeed.getUserId()),page);
						
						 log.info("采集用户 "+oneSeed.getUserId()+" 博文返回个数: "+ statuses.size());	
						 
						 for(Status se:statuses){
							 TweetInfo oneTweet=new TweetInfo();
							 
							 try {
								oneTweet.setAllText(CharUtil.withNonBmpStripped(String.valueOf(se)));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								//e1.printStackTrace();
							}
							 							 
							 oneTweet.setTweetId(String.valueOf(se.getId()));
							 oneTweet.setUserId(oneSeed.getUserId());
							 oneTweet.setScreenName(se.getUser().getScreenName());
							 oneTweet.setCreatedAt(new Timestamp(se.getCreatedAt().getTime()));
							 oneTweet.setText(CharUtil.withNonBmpStripped(se.getText()));
							 oneTweet.setSource(CharUtil.withNonBmpStripped(se.getSource()));
							 oneTweet.setIsFavorited(se.isFavorited() ? 1 : 0);
							 oneTweet.setGeoLocation(String.valueOf(se.getGeoLocation()));
							 oneTweet.setPlace(String.valueOf(se.getPlace()));
							 oneTweet.setRetweetCount((int) se.getRetweetCount());
							 
							 
							 oneTweet.setInReplyToStatusId(String.valueOf(se.getInReplyToStatusId()));
							 oneTweet.setInReplyToUserId(String.valueOf(se.getInReplyToUserId()));
							 oneTweet.setInReplyToScreenName(se.getInReplyToScreenName());
							 
							 
							 try {
								 oneTweet.setInRetweetToStatusId(String.valueOf(se.getRetweetedStatus().getId()));
								 oneTweet.setInRetweetToUserId(String.valueOf(se.getRetweetedStatus().getUser().getId()));
								 oneTweet.setInRetweetToScreenName(se.getRetweetedStatus().getUser().getScreenName());
								 oneTweet.setInRetweetCreatedAt(new Timestamp(se.getRetweetedStatus().getCreatedAt().getTime()));
								 oneTweet.setInRetweetCount((int) se.getRetweetedStatus().getRetweetCount());
								 //或者 oneTweet.setInRetweetCount(se.getInReplyToScreenName());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}
								 
							 oneTweet.setInsertTime(new Timestamp(System.currentTimeMillis()));
							
							 //加入对列之前，看看数据库有没有
							   if (!tweetInfoDao.getIsExistTweet(oneTweet)){
								   TweetInfoList.add(oneTweet);
							   }
							
							 
						 }//end for
						 
						  try {
							 tweetInfoDao.batchSaveTweetInfoList(TweetInfoList,batchSize);
							    } catch (Exception e1) {
								// TODO Auto-generated catch block
							    	log.error("batchSaveTweetInfoList ERROR!"+e1.getMessage());	
							    }
						  
						     //更新
						  oneSeed.setIsTweetsInfo(oneSeed.getIsTweetsInfo()+1);
						  oneSeed.setIsTweetsStatus(true);
						  oneSeed.setInsertTime(new Timestamp(System.currentTimeMillis()));
						  
						   try {
							seedsQueueDao.updateIsTweetsInfo(oneSeed);
						   } catch (Exception e1) {
							// TODO Auto-generated catch block
							log.error("updateIsTweetsInfo ERROR!"+e1.getMessage());	
						   }
						
						 
						 if(((TwitterResponse) statuses).getRateLimitStatus().getRemaining()==0){								
								
								currentAppToken.setEndTime(System.currentTimeMillis());
								currentAppToken.setResetTime(((TwitterResponse) statuses).getRateLimitStatus().getSecondsUntilReset()*1000);
								
								getOneToken();
								
							}
						
					}catch (TwitterException te) {
			            log.error("Failed to get user Tweets: " + te.getMessage());	 
			            if(te.getStatusCode()==429){
			            	
			            	currentAppToken.setEndTime(System.currentTimeMillis());
							currentAppToken.setResetTime(te.getRateLimitStatus().getSecondsUntilReset()*1000);
							
							getOneToken();
			            }else{
			            	
			            	 //更新
							  oneSeed.setIsTweetsInfo(oneSeed.getIsTweetsInfo()+1);
							  oneSeed.setIsTweetsStatus(true);
							  oneSeed.setInsertTime(new Timestamp(System.currentTimeMillis()));
							  
							   try {
								seedsQueueDao.updateIsTweetsInfo(oneSeed);
							   } catch (Exception e1) {
								// TODO Auto-generated catch block
								log.error("updateIsTweetsInfo ERROR!"+e1.getMessage());	
							   }
			            	
			            }
			           }catch(Exception ote){
			        	   
			        	   log.error("Tweets Crawler Other ERROR!"+ote.getMessage());  
			        	   
			           }//End Catch
					
					
				}//end for
				
				
				
			}//end while
			
	}// end startCrawling
	
	
	/**
	 * @function main
	 * 
	 * @param args
	 * @author Dayong.Shen
	 * @date 2013-6-9-下午3:12:36
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PropertyConfigurator.configureAndWatch(Constant.LOG4J_PATH);
		log.info("正在创建数据库连接和缓冲池...");
	    AppContext.initAppCtx();
		log.info("数据库连接已连接！缓冲池已建立");
	
		TweetsCrawler crawler=(TweetsCrawler) AppContext.appCtx.getBean("tweetsCrawler");
		
		if (args.length < 1) {
                // consumer key/secret are not set in twitter4j.properties
                System.out.println(
                        "Usage: java isiteam.TwitterCrawler.mainApp.TweetsCrawler [twitter4j.properties filePathName]");
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
