
/**
* @project Web
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.util
* @file AppToken.java
* 
* @date 2013-6-21-下午8:51:52
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.util;

import java.util.Properties;


/**
 * @project Web
 * @author Dayong.Shen
 * @class AppToken
 * 
 * @date 2013-6-21-下午8:51:52
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public class AppToken {
	
	private Properties props = new Properties();
	
	
	 /**
	 * endTime:TODO（appToken 结束时间 / milliseconds）
	 *
	 * @since 1.0.0
	 */
	private long endTime;
	
	/**
	 * endTime:TODO（appToken 重置时间 / milliseconds）
	 *
	 * @since 1.0.0
	 */
	private long ResetTime;
	
	
	

	/**
	 * @function main
	 * 
	 * @param args
	 * @author Dayong.Shen
	 * @date 2013-6-21-下午8:51:52
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}



	public long getResetTime() {
		return ResetTime;
	}

	public void setResetTime(long resetTime) {
		ResetTime = resetTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}





}
