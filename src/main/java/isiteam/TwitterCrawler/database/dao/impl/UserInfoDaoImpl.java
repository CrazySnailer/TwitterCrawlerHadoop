
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao.impl
* @file UserInfoDaoImpl.java
* 
* @date 2013-6-8-下午12:22:40
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
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import isiteam.TwitterCrawler.database.bean.UserInfo;
import isiteam.TwitterCrawler.database.dao.UserInfoDao;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class UserInfoDaoImpl
 * 
 * @date 2013-6-8-下午12:22:40
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@Repository
public class UserInfoDaoImpl implements UserInfoDao {
	private static final Logger log = LoggerFactory
			.getLogger(UserInfoDaoImpl.class);
	
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public void batchSaveUserInfoList(final List<UserInfo> userInfoList, final int batchSize) {
		// TODO Auto-generated method stub
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
        	@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
			       for (int i = 0; i < userInfoList.size(); i++) {  
	                    try {
							session.save(userInfoList.get(i));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							log.error(userInfoList.get(i).getAllText());
							log.error(e1.getMessage());
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
	public boolean getIsExistUserInfo(final UserInfo userInfo) {
		// TODO Auto-generated method stub
		final String hql="from UserInfo where userId = :userid and crawledNum= :crawlednum";
		List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setString("userid", userInfo.getUserId());
				query.setInteger("crawlednum", userInfo.getCrawledNum());
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
