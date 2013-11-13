
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao.impl
* @file UserFriendsDaoImpl.java
* 
* @date 2013-6-8-下午12:22:07
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import isiteam.TwitterCrawler.database.bean.UserFriends;
import isiteam.TwitterCrawler.database.dao.UserFriendsDao;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class UserFriendsDaoImpl
 * 
 * @date 2013-6-8-下午12:22:07
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@Repository
public class UserFriendsDaoImpl implements UserFriendsDao {
	private static final Logger log = LoggerFactory
			.getLogger(UserFriendsDaoImpl.class);
	
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public void save(UserFriends newFriend) {
		// TODO Auto-generated method stub
		try {
			hibernateTemplate.save(newFriend);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			log.error("UserFriends save ERROR!"+e.getMessage());	
		}
	}

	@Override
	public void batchSaveUserFriends(final List<UserFriends> userFriendsList,
			final int batchSize) {
		// TODO Auto-generated method stub
		
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
        	@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
			       for (int i = 0; i < userFriendsList.size(); i++) {  
	                    try {
							session.save(userFriendsList.get(i));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}         
	                    if (i % batchSize == 0) {  
	                        try {
								session.flush();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
							}  
	                        session.clear();  
	                    }  
	                }			       
			       try {
					session.flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
                   session.clear();
			       return null; 
			}
		});
		
	}

	@Override
	public boolean getIsExistFriends(final UserFriends newFriend) {
		// TODO Auto-generated method stub
			
		final String hql="from UserFriends where userId = :userid and friendsId= :friendsid and crawledNum=:crawlednum";
		
		List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setString("userid", newFriend.getUserId());
				query.setString("friendsid", newFriend.getFriendsId());
				query.setInteger("crawlednum", newFriend.getCrawledNum());
				List list=query.list();
				return list;
			}
		});
		
		if(list.size()==0){			
		    return false;
	    }else {
	    	return true;
	    }
	}
}
