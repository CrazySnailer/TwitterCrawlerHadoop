
/**
* @project Twitter
* @author Dayong.Shen
* @package daoTest
* @file DaoTest.java
* 
* @date 2013-6-6-下午11:45:19
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package daoTest;




import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * @project Twitter
 * @author Dayong.Shen
 * @class DaoTest
 * 
 * @date 2013-6-6-下午11:45:19
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:*/config/applicationContext.xml")
public class DaoTest  extends AbstractJUnit4SpringContextTests{
	/*@Resource
	private TwitterFansFriendDao twitterFansFriendDao;
	
	@Resource
	private TwitterTweetCommentDao twitterTweetCommentDao;
	
	@Resource
	private TwitterTweetInfoDao twitterTweetInfoDao; */
	
	/*@Resource
	private TwitterUserInfoDao twitterUserInfoDao;

	@Test
	public void save(){
		
		TwitterUserInfo user=new TwitterUserInfo();
		user.setUsername("XXOO");
	
		
		twitterUserInfoDao.save(user);
		
	} */
	
	

}
