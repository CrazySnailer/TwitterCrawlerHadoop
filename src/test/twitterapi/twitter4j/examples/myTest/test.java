package twitter4j.examples.myTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
	
	public static void main(String[] args) {
		
		String a = "1365988384000";
		Date aa = new java.util.Date(Long.parseLong(args[0]));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
		String nowtime = dateFormat.format(aa); 
		System.out.println(nowtime);
		Date now = new Date();
		System.out.println(now.getTime());
		int i = 0;
		while(true){
			System.out.println(now.getTime());
			i++;
			if(i%1000 == 0){
				try {
					Thread.sleep(1000*60);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
