
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao
* @file UserFriendsDao.java
* 
* @date 2013-6-8-下午12:19:10
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao;

import java.util.List;

import isiteam.TwitterCrawler.database.bean.UserFriends;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class UserFriendsDao
 * 
 * @date 2013-6-8-下午12:19:10
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public interface UserFriendsDao {

	void save(UserFriends newFriend);

	void batchSaveUserFriends(List<UserFriends> userFriendsList, int batchSize);

	boolean getIsExistFriends(UserFriends newFriend);

}
