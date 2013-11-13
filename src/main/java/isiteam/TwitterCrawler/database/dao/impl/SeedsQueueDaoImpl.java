
/**
* @project Twitter
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.database.dao.impl
* @file SeedsQueueDaoImpl.java
* 
* @date 2013-6-8-下午12:20:32
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.database.dao.impl;

import java.sql.SQLException;
import java.util.Date;
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

import isiteam.TwitterCrawler.database.bean.SeedsQueue;
import isiteam.TwitterCrawler.database.dao.SeedsQueueDao;


/**
 * @project Twitter
 * @author Dayong.Shen
 * @class SeedsQueueDaoImpl
 * 
 * @date 2013-6-8-下午12:20:32
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */
@Repository
public class SeedsQueueDaoImpl implements SeedsQueueDao {
	private static final Logger log = LoggerFactory
			.getLogger(SeedsQueueDaoImpl.class);
	
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	

	
    public void batchSaveSeedsQueue(final List<SeedsQueue> seedsQueue,final int batchSize) {
        
         this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
        	@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
			       for (int i = 0; i < seedsQueue.size(); i++) {  
	                    try {
							session.save(seedsQueue.get(i));
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
	public boolean getIsExistSeed(SeedsQueue newSeed) {
		// TODO Auto-generated method stub
		final String hql="from SeedsQueue where userId = '"+newSeed.getUserId() +"'";
		List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
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

	
	 /* (non-Javadoc)
	 * @see isiteam.TwitterCrawler.database.dao.SeedsQueueDao#getSeedsQueueByisUserInfo(int)
	 * 
	 * 采集种子队列中的用户基本信息，进一步判断是否为中文用户
	 * 
	 */
	@Override
	public List<SeedsQueue> getSeedsQueueByisUserInfo(final int count) {
		// TODO Auto-generated method stub
		
		try{
			final String hql="from SeedsQueue where isDeal!=2 and isUserStatus=0 order by isUserInfo ASC, level ASC";//ascending order 升序
			List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query=session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(count);
					List list=query.list();
					return list;
				}
			});
			
			if(list.size()>0){//取到数据
				//将取到的数据的isUserStatus 标志位置为1，表示这次采集（还是放在更新的时候做吧）
				
				return list;
			}else{//没有取到数据
				
				try{
					//获取系统当前时间
					Date   now   =   new   Date();
					now.setHours(14);
					now.setMinutes(0);
					now.setSeconds(0);
					
					long interval=now.getTime()-System.currentTimeMillis()+24*3600*1000;
					log.info("getSeedsQueueByisUserInfo Thread Sleep :"+interval/1000/60/60+" hours");
					
					Thread.sleep(interval);
					
					
				}catch(Exception ex){
					log.error("Thread sleep ERROR！"+ex.getMessage());
				}
				
				
				//需将所有种子节点的isUserStatus重新置0，后取种子节点				
				getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {		        		
		        		  Query query = session.createQuery("update SeedsQueue set isUserStatus = 0");
		        		  return query.executeUpdate();
					}
				});
				
				return getSeedsQueueByisUserInfo(count);
				
			}//end if
			
		
		}catch(Exception e){
			log.error("getSeedsQueueByisUserInfo ERROR!"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<SeedsQueue> getSeedsQueueByisFriendsInfo(final int count) {
		// TODO Auto-generated method stub
		
		try{
			final String hql="from SeedsQueue where isDeal=1 and isFriendsStatus=0 order by isFriendsInfo ASC, level ASC";
			List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query=session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(count);
					List list=query.list();
					return list;
				}
			});

			if(list.size()>0){//取到数据
				//将取到的数据的isFriendsStatus 标志位置为1，表示这次采集（还是放在更新的时候做吧）
				
				return list;
			}else{//没有取到数据
				//需将所有种子节点的isFriendsStatus重新置0，后取种子节点
				
				this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {		        		
		        		  Query query = session.createQuery("update SeedsQueue set isFriendsStatus = 0");
		        		  return query.executeUpdate();
					}
				});
				
				return getSeedsQueueByisFriendsInfo(count);
				
			}//end if
		
		}catch(Exception e){
			log.error("getSeedsQueueByisFriendsInfo ERROR!"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<SeedsQueue> getSeedsQueueByisTweetsInfo(final int count) {
		// TODO Auto-generated method stub
		try{
			final String hql="from SeedsQueue where isDeal=1 and isTweetsStatus=0 order by isTweetsInfo ASC, level ASC";
			List list=this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query=session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(count);
					List list=query.list();
					return list;
				}
			});

			if(list.size()>0){//取到数据
				//将取到的数据的isTweetsStatus 标志位置为1，表示这次采集（还是放在更新的时候做吧）
				
				return list;
			}else{//没有取到数据
				//需将所有种子节点的isTweetsStatus重新置0，后取种子节点
				
				getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {		        		
		        		  Query query = session.createQuery("update SeedsQueue set isTweetsStatus = 0");
		        		  return query.executeUpdate();
					}
				});
				
				return getSeedsQueueByisTweetsInfo(count);
				
			}//end if
		
		}catch(Exception e){
			log.error("getSeedsQueueByisTweetsInfo ERROR!"+e.getMessage());
			return null;
		}
	}   
	
	@Override
	public void updateIsFriendsInfo(final SeedsQueue seed) {
		// TODO Auto-generated method stub
		final String hql="update SeedsQueue set isFriendsInfo = :friendsInfoNum, isFriendsStatus= :friendsStatus , insertTime=:inserttime, isDeal=:dealing where userId = :userid";
		try{
			 this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
		        		
		        		  Query query = session.createQuery(hql);
		        		  query.setInteger("friendsInfoNum", seed.getIsFriendsInfo());
		        		  query.setBoolean("friendsStatus", seed.getIsFriendsStatus());
		        		  query.setTimestamp("inserttime", seed.getInsertTime());
		        		  query.setString("userid", seed.getUserId());
		        		  query.setInteger("dealing", seed.getIsDeal());
		        			  
		        		  return query.executeUpdate();
		        		  
		        		 
	
					}
				});
		 
		}catch(Exception e1){
			log.error("updateIsFriendsInfo ERROR!"+e1.getMessage());			
		} 
	}

	@Override
	public void updateisUserInfo(final SeedsQueue seed) {
		// TODO Auto-generated method stub
		final String hql="update SeedsQueue set isUserInfo = :userInfoNum,isUserStatus=:userStatus, insertTime=:inserttime,  isDeal=:dealing where userId = :userid";
		try{                                    
			getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
		        		
		        		  Query query = session.createQuery(hql);
		        		  query.setInteger("userInfoNum", seed.getIsUserInfo());
		        		  query.setBoolean("userStatus", seed.getIsUserStatus());
		        		  query.setTimestamp("inserttime", seed.getInsertTime());
		        		  query.setString("userid", seed.getUserId());
		        		  query.setInteger("dealing", seed.getIsDeal());
		        			  
		        		  return query.executeUpdate();
	
					}
				});
		 
		}catch(Exception e1){
			log.error("updateisUserInfo ERROR!"+e1.getMessage());
			
		}
		
	}

    public void updateIsTweetsInfo(final SeedsQueue seed) {
		// TODO Auto-generated method stub
		final String hql="update SeedsQueue set isTweetsInfo = :tweetsInfoNum, isTweetsStatus=:tweetsStatus ,insertTime=:inserttime,  isDeal=:dealing where userId = :userid";
		try{
			 getHibernateTemplate().execute(new HibernateCallback<Object>() {
		        	@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
		        		
		        		  Query query = session.createQuery(hql);
		        		  query.setInteger("tweetsInfoNum", seed.getIsTweetsInfo());
		        		  query.setBoolean("tweetsStatus", seed.getIsTweetsStatus());
		        		  query.setTimestamp("inserttime", seed.getInsertTime());
		        		  query.setString("userid", seed.getUserId());
		        		  query.setInteger("dealing", seed.getIsDeal());
		        		  
		        		  return  query.executeUpdate();
	
					}
				});
		 
		}catch(Exception e1){
			log.error("updateIsTweetsInfo ERROR!"+e1.getMessage());			
		} 
	}





}
