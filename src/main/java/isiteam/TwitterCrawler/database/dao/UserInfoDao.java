
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao
* @file UserInfoDao.java
* 
* @date 2013-6-8-下午12:19:30
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao;

import isiteam.TwitterCrawler.database.bean.UserInfo;

import java.util.List;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class UserInfoDao
 * 
 * @date 2013-6-8-下午12:19:30
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public interface UserInfoDao {

	void batchSaveUserInfoList(List<UserInfo> userInfoList, int batchSize);

	boolean getIsExistUserInfo(UserInfo userInfo);

}
