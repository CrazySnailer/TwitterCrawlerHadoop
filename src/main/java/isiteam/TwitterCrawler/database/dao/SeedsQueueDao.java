
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao
* @file SeedsQueueDao.java
* 
* @date 2013-6-8-下午12:18:17
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao;

import isiteam.TwitterCrawler.database.bean.SeedsQueue;

import java.util.List;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class SeedsQueueDao
 * 
 * @date 2013-6-8-下午12:18:17
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public interface SeedsQueueDao {

	List<SeedsQueue> getSeedsQueueByisFriendsInfo(int count);
	
	List<SeedsQueue> getSeedsQueueByisUserInfo(int count);
	
	List<SeedsQueue> getSeedsQueueByisTweetsInfo(int count);
	
    void batchSaveSeedsQueue(final List<SeedsQueue> seedsQueue,final int batchSize);

	boolean getIsExistSeed(SeedsQueue newSeed);

	void updateIsFriendsInfo(SeedsQueue oneSeed);

	void updateisUserInfo(SeedsQueue oneSeed);

	void updateIsTweetsInfo(SeedsQueue oneSeed);

	

	

}
