
/**
* @project Web
* @author Dayong.Shen
* @package twitter4j.examples.myTest.twitterTest
* @file AccessToken.java
* 
* @date 2013-6-4-下午6:26:28
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package twitter4j.examples.myTest.twitterTest;


import isiteam.TwitterCrawler.util.Constant;

import java.awt.Desktop;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.PropertyConfiguration;



/**
 * @project Web
 * @author Dayong.Shen
 * @class AccessToken
 * 
 * @date 2013-6-4-下午6:26:28
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public class CreatAccessToken {
	private static Log log = LogFactory.getLog(AccessToken.class);
	
	public void GetAccessToken(String filePath){
		
		
		final String extend_name = ".properties";
		
		File dir = new File(filePath);
		if(!dir.isDirectory()){
			log.info("不是有效的文件夹目录!");
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
	      
	      	File file = new File(filePath+"\\"+children[i]);
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
	                        &&null==prop.getProperty("http.proxyPort")
	                        &&null==prop.getProperty("http.proxyHost")) {
	                    // consumer key/secret are not set in twitter4j.properties
	                	log.info("Invalid Twitter Properties");
	                    System.exit(-1);
	                }	            
	           
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	            System.exit(-1);
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
	        
	        PropertyConfiguration conf = new PropertyConfiguration(prop);
	        
	        try {
	            Twitter twitter = new TwitterFactory(conf).getInstance();
	            RequestToken requestToken = twitter.getOAuthRequestToken();
	            log.info("Got request token.");
	            log.info("Request token: " + requestToken.getToken());
	            log.info("Request token secret: " + requestToken.getTokenSecret());
	            AccessToken accessToken = null;

	            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	            while (null == accessToken) {
	            	log.info("Open the following URL and grant access to your account:");
	            	log.info(requestToken.getAuthorizationURL());
	                try {
	                    Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
	                } catch (UnsupportedOperationException ignore) {
	                } catch (IOException ignore) {
	                } catch (URISyntaxException e) {
	                    throw new AssertionError(e);
	                }
	                log.info("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
	                String pin = br.readLine();
	                try {
	                    if (pin.length() > 0) {
	                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
	                    } else {
	                        accessToken = twitter.getOAuthAccessToken(requestToken);
	                    }
	                } catch (TwitterException te) {
	                    if (401 == te.getStatusCode()) {
	                    	log.info("Unable to get the access token.");
	                    } else {
	                        te.printStackTrace();
	                    }
	                }
	            }
	            log.info("Got access token.");
	            log.info("Access token: " + accessToken.getToken());
	            log.info("Access token secret: " + accessToken.getTokenSecret());

	            try {
	                prop.setProperty("oauth.accessToken", accessToken.getToken());
	                prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
	                os = new FileOutputStream(file);
	                prop.store(os, children[i]);
	                os.close();
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	                System.exit(-1);
	            } finally {
	                if (os != null) {
	                    try {
	                        os.close();
	                    } catch (IOException ignore) {
	                    }
	                }
	            }
	            log.info("Successfully stored access token to " + file.getAbsolutePath() + ".");
	           // System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            log.info("Failed to get accessToken: " + te.getMessage());
	            System.exit(-1);
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	            log.info("Failed to read the system input.");
	            System.exit(-1);
	        }
	      
	      
	    }//end for
		
		
	}
	

	/**
	 * @function main
	 * 
	 * @param args
	 * @author Dayong.Shen
	 * @date 2013-6-4-下午6:26:28
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configureAndWatch(Constant.LOG4J_PATH);
		
		CreatAccessToken creatAccessToken=new CreatAccessToken();
		
		creatAccessToken.GetAccessToken(Constant.TwitterKey_PATH);
	}

}
