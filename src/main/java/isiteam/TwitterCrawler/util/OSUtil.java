package isiteam.TwitterCrawler.util;

import java.io.InputStreamReader; 
import java.io.LineNumberReader; 
import sun.management.ManagementFactory; 
import com.sun.management.OperatingSystemMXBean; 
import java.io.*; 
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.StringTokenizer; 



import org.apache.log4j.Logger;

@SuppressWarnings("restriction")
public class OSUtil {
	private static Logger logger = Logger.getLogger(OSUtil.class);
	private static final int CPUTIME = 30;   
	  
    private static final int PERCENT = 100;   
  
    private static final int FAULTLENGTH = 10;   
     
    private static final File versionFile = new File("/proc/version");   
    private static String linuxVersion = "2.6";  
   /* private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory   
            .getOperatingSystemMXBean(); */
   
    private static int MB = 1024*1024;  
    /**  
     * 获得当前的监控对象.  
     * @return 返回构造好的监控对象  
     * @throws Exception  
     * @author GuoHuang  
     */   
    
    /**
	 * 判断当前操作是否Windows.
	 * 
	 * @return true---是Windows操作系统
	 */
	public static boolean isWindowsOS() {

		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * 获取本机IP地址，并自动区分Windows还是Linux操作系统
	 * 
	 * @return String
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
				logger.info("操作系统是Windows! IP Address : " + ip);
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
						.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces
							.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& ip.getHostAddress().indexOf(":") == -1) {
							bFindIP = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error("getLocalIP ERROR!"+e.getMessage());
		}

		if (null != ip) {
			sIP = ip.getHostAddress();
			logger.info("IP Address : "+sIP);
		}
		return sIP;
	}
    private static String getOsName() {
		// TODO Auto-generated method stub
      	 // 操作系统   
            String osName = System.getProperty("os.name");
		return osName;
	}  
   //cpu占有率   
   public static double getCpuRatio(){
	   
	   double cpuRatio = 0;   
       if (isWindowsOS()) {   
           cpuRatio = getCpuRatioForWindows();   
       }   
       else {   
        cpuRatio = getCpuRateForLinux();   
       }   
       
       return cpuRatio;
   }
    
   public static String getCpuRatioStr(){
	   
	   double cpuRatio = 0;   
       if (isWindowsOS()) {   
           cpuRatio = getCpuRatioForWindows();   
       }   
       else {   
        cpuRatio = getCpuRateForLinux();   
       }          
       return String.valueOf(cpuRatio)+"%";
   }
   
   //可使用内存
   public static long getTotalMemory(){
	   long totalMemory = Runtime.getRuntime().totalMemory() / MB;  
	   return totalMemory;
   }
   //剩余内存
   public static long getFreeMemory(){
	   long freeMemory = Runtime.getRuntime().freeMemory() / MB;
	   return freeMemory;
   }
// 最大可使用内存   
   public static long getMaxMemory(){
	   long maxMemory = Runtime.getRuntime().maxMemory() / MB;  
	   return maxMemory;
   }
/*  // 总的物理内存   
   public static long getTotalPhysicalMemorySize(){
	   long totalMemorySize = osmxb.getTotalPhysicalMemorySize() / MB; 
	   return totalMemorySize;
   }
   // 剩余的物理内存   
   public static long getFreePhysicalMemorySize(){
	   long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / MB; 
	   return freePhysicalMemorySize;
   }
   // 已使用的物理内存   
   public static long getUsedPhysicalMemorySize(){
   long usedMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb   
           .getFreePhysicalMemorySize())   
           / MB;   
   return usedMemory;
   }
   
   */
   // 获得线程总数   
   public static int getTotalThread(){
	   ThreadGroup parentThread;   
	   for (parentThread = Thread.currentThread().getThreadGroup(); parentThread   
	           .getParent() != null; parentThread = parentThread.getParent())   
	       ;   
	   int totalThread = parentThread.activeCount(); 
	   return totalThread;
   }
   
    
    private static double getCpuRateForLinux(){   
        InputStream is = null;   
        InputStreamReader isr = null;   
        BufferedReader brStat = null;   
        StringTokenizer tokenStat = null;   
        try{   
        	logger.info("Get usage rate of CUP , linux version: "+linuxVersion);   
  
            Process process = Runtime.getRuntime().exec("top -b -n 1");   
            is = process.getInputStream();                     
            isr = new InputStreamReader(is);   
            brStat = new BufferedReader(isr);   
             
            if(linuxVersion.equals("2.4")){   
                brStat.readLine();   
                brStat.readLine();   
                brStat.readLine();   
                brStat.readLine();   
                 
                tokenStat = new StringTokenizer(brStat.readLine());   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                String user = tokenStat.nextToken();   
                tokenStat.nextToken();   
                String system = tokenStat.nextToken();   
                tokenStat.nextToken();   
                String nice = tokenStat.nextToken();   
                 
                logger.info(user+" , "+system+" , "+nice);   
                 
                user = user.substring(0,user.indexOf("%"));   
                system = system.substring(0,system.indexOf("%"));   
                nice = nice.substring(0,nice.indexOf("%"));   
                 
                float userUsage = new Float(user).floatValue();   
                float systemUsage = new Float(system).floatValue();   
                float niceUsage = new Float(nice).floatValue();   
                 
                return (userUsage+systemUsage+niceUsage)/100;   
            }else{   
                brStat.readLine();   
                brStat.readLine();   
                     
                tokenStat = new StringTokenizer(brStat.readLine());   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                tokenStat.nextToken();   
                String cpuUsage = tokenStat.nextToken();   
                     
                 
                System.out.println("CPU idle : "+cpuUsage);   
                Float usage = new Float(cpuUsage.substring(0,cpuUsage.indexOf("%")));   
                 
                return (1-usage.floatValue()/100);   
            }   
  
              
        } catch(IOException ioe){   
            logger.error(ioe.getMessage());   
            freeResource(is, isr, brStat);   
            return 1;   
        } finally{   
            freeResource(is, isr, brStat);   
        }   
  
    }   
    private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br){   
        try{   
            if(is!=null)   
                is.close();   
            if(isr!=null)   
                isr.close();   
            if(br!=null)   
                br.close();   
        }catch(IOException ioe){   
            System.out.println(ioe.getMessage());   
        }   
    }   
  
  
    /**  
     * 获得CPU使用率.  
     * @return 返回cpu使用率  
     * @author GuoHuang  
     */   
    private static double getCpuRatioForWindows() {   
        try {   
            String procCmd = System.getenv("windir")   
                    + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"   
                    + "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";   
            // 取进程信息   
            long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));   
            Thread.sleep(CPUTIME);   
            long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));   
            if (c0 != null&c1 != null) {   
                long idletime = c1[0] - c0[0];   
                long busytime = c1[1] - c0[1];   
                return Double.valueOf(   
                        PERCENT * (busytime) / (busytime + idletime))   
                        .doubleValue();   
            } else {   
                return 0.0;   
            }   
        } catch (Exception ex) {   
            ex.printStackTrace();   
            return 0.0;   
        }   
    }   
  
    /**       
 
* 读取CPU信息.  
     * @param proc  
     * @return  
     * @author GuoHuang  
     */   
    private static long[] readCpu(final Process proc) {   
        long[] retn = new long[2];   
        try {   
            proc.getOutputStream().close();   
            InputStreamReader ir = new InputStreamReader(proc.getInputStream());   
            LineNumberReader input = new LineNumberReader(ir);   
            String line = input.readLine();   
            if (line == null || line.length() < FAULTLENGTH) {   
                return null;   
            }   
            int capidx = line.indexOf("Caption");   
            int cmdidx = line.indexOf("CommandLine");   
            int rocidx = line.indexOf("ReadOperationCount");   
            int umtidx = line.indexOf("UserModeTime");   
            int kmtidx = line.indexOf("KernelModeTime");   
            int wocidx = line.indexOf("WriteOperationCount");   
            long idletime = 0;   
            long kneltime = 0;   
            long usertime = 0;   
            while ((line = input.readLine()) != null) {   
                if (line.length() < wocidx) {   
                    continue;   
                }   
                // 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,   
                // ThreadCount,UserModeTime,WriteOperation   
                String caption =ByteSubstring(line, capidx, cmdidx - 1)   
                        .trim();   
                String cmd =ByteSubstring(line, cmdidx, kmtidx - 1).trim();   
                if (cmd.indexOf("wmic.exe") >= 0) {   
                    continue;   
                }   
                // log.info("line="+line);   
                if (caption.equals("System Idle Process")   
                        || caption.equals("System")) {   
                    idletime += Long.valueOf(   
                           ByteSubstring(line, kmtidx, rocidx - 1).trim())   
                            .longValue();   
                    idletime += Long.valueOf(   
                           ByteSubstring(line, umtidx, wocidx - 1).trim())   
                            .longValue();   
                    continue;   
                }   
  
                kneltime += Long.valueOf(   
                       ByteSubstring(line, kmtidx, rocidx - 1).trim())   
                        .longValue();   
                usertime += Long.valueOf(   
                       ByteSubstring(line, umtidx, wocidx - 1).trim())   
                        .longValue();   
            }   
            retn[0] = idletime;   
            retn[1] = kneltime + usertime;   
            return retn;   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        } finally {   
            try {   
                proc.getInputStream().close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
        return null;   
    }   
     
    /**     测试方法.  
     * @param args  
     * @throws Exception  
     * @author GuoHuang  
       */   
    public static void main(String[] args) throws Exception {   
      
    	System.out.println("操作系统= " + OSUtil.getOsName());
    	System.out.println("系统IP地址= " +OSUtil.getLocalIP());
        System.out.println("cpu占有率= " + OSUtil.getCpuRatio()+"%");            
        System.out.println("可使用内存= " + OSUtil.getTotalMemory() + " MB");   
        System.out.println("剩余内存= " + OSUtil.getFreeMemory() + " MB");   
        System.out.println("最大可使用内存= " + OSUtil.getMaxMemory() + " MB");   
         
       /* System.out.println("总的物理内存= " + OSUtil.getTotalPhysicalMemorySize() + " MB");   
        System.out.println("剩余的物理内存= " + OSUtil.getFreePhysicalMemorySize() + " MB");   
        System.out.println("已使用的物理内存= " + OSUtil.getUsedPhysicalMemorySize()+ " MB");   */
        System.out.println("线程总数= " + OSUtil.getTotalThread()+ " 个");   
    }   
    
       
  
		public static String ByteSubstring(String src, int start_idx, int end_idx){   
            byte[] b = src.getBytes();   
            String tgt = "";   
            for(int i=start_idx; i<=end_idx; i++){   
                tgt +=(char)b[i];   
            }   
            return tgt;   
        }   
  
    
}
