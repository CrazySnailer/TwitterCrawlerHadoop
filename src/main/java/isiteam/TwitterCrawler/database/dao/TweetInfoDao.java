
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao
* @file TweetInfoDao.java
* 
* @date 2013-6-8-下午12:18:52
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao;

import isiteam.TwitterCrawler.database.bean.TweetInfo;

import java.util.List;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class TweetInfoDao
 * 
 * @date 2013-6-8-下午12:18:52
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public interface TweetInfoDao {

	void batchSaveTweetInfoList(List<TweetInfo> tweetInfoList, int batchSize);

	boolean getIsExistTweet(TweetInfo oneTweet);

}
