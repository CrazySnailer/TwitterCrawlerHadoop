package isiteam.TwitterCrawler.util;

import java.io.File;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/**
 * Digger application context
 */
public class AppContext {

	public static  ApplicationContext appCtx;

	protected static Logger logger;

	// init internal application context
	public static  void initAppCtx() {
		if(OSUtil.isWindowsOS()){
			appCtx = new FileSystemXmlApplicationContext(new String[] {
					Constant.applicationContext_PATH});
		}else{
			appCtx = new FileSystemXmlApplicationContext("/"+Constant.applicationContext_PATH);
		}
		
	}

	public static void main(String[] args) 
	{
		try {
			System.out.println( "正在创建数据库连接和缓冲池...");
			AppContext.initAppCtx();
			System.out.println( "数据库连接和缓冲池建立完毕");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * get system object for given key
	 * 
	 * @param key
	 *            the key
	 * @return the corresponding object, or null if not found in the application
	 *         context
	 */
	public static Object getObject(String key) {
		try {
			if (key != null)
				return appCtx.getBean(key);
		} catch (BeansException ex) {
			logger.warn(ex.getMessage());
			return null;
		}
            return null;
	}

	// create log folder
	public static void initLogger() {
		File logFolder = new File("log");
		if (!logFolder.exists())
			logFolder.mkdirs();
		logger = Logger.getLogger(AppContext.class);
	}

	public static Logger getLogger() {
		return logger;
	}

}