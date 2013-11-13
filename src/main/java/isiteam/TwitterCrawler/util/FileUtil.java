/**
 * 
 */
package isiteam.TwitterCrawler.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author DaYong.Shen
 *
 */
public class FileUtil {

	private static Log log = LogFactory.getLog(FileUtil.class);
//	 System.getProperty( "line.separator" )
	 /** 
     * 文本文件所在的目录 
     */  
    private static String textPath=Constant.Statics_PATH;  
    /** 
     * 读取文本内容 
     * @param textname 文本名称 
     * @return 
     */  
    public static String readText(String textname){  
        File file=new File(textPath+File.separator+textname);  
        try {  
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));  
            StringBuffer sb = new StringBuffer();  
            String line = br.readLine();  
            while (line != null) {  
                sb.append(line);  
                line = br.readLine();  
            }  
            br.close();  
            return sb.toString();  
        } catch (IOException e) {  
        	log.error("readText ERROR!"+e.getMessage());   
            return null;  
        }  
    }    
    /** 
     * 将内容写到文本中 
     * @param textname　文本名称 
     * @param date 写入的内容 
     * @return 
     */  
    public static boolean writeText(String textname,String date){  
        boolean flag=false;  
        File filePath=new File(textPath);  
        if(!filePath.exists()){  
            filePath.mkdirs();  
        }  
        
        try {  
            FileWriter fw =new FileWriter(textPath+File.separator+textname);  
            fw.write(date+System.getProperty( "line.separator" ));  
            flag=true;  
            if(fw!=null)  
                fw.close();  
        } catch (IOException e) {  
        	log.error("writeText ERROR!"+e.getMessage());  
        }    
        return flag;          
    }  
    
    /** 
     * 在文档后附加内容 
     * @param fileName 
     * @param content 
     * @return 
     */  
    public static boolean appendText(String fileName,String content){  
        boolean flag=false;  
        File filePath=new File(textPath);  
        if(!filePath.exists()){  
            filePath.mkdirs();  
        }  
        
        try {  
            FileWriter fw =new FileWriter(textPath+File.separator+fileName,true);  
            fw.append(content+System.getProperty( "line.separator" ));  
            flag=true;  
            if(fw!=null)  
                fw.close();  
        } catch (IOException e) {  
        	log.error("appendText ERROR!"+e.getMessage());  
        }  
        return flag;      
    }  
    
    public String getTextPath() {  
        return textPath;  
    }  
    
    public void setTextPath(String textPath) {  
        textPath = textPath;  
    }  
	
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileUtil.appendText("aisini.txt", "aisini");
		FileUtil.appendText("aisini.txt", "aisini");
		FileUtil.appendText("aisini.txt", "aisini");
	}
}
