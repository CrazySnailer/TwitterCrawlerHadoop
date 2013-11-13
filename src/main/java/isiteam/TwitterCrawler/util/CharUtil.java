
/**
* @project Web
* @author Dayong.Shen
* @package isiteam.TwitterCrawler.util
* @file CharUtil.java
* 
* @date 2013-6-9-下午1:44:35
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.util;

import java.util.regex.Pattern;


/**
 * @project Web
 * @author Dayong.Shen
 * @class CharUtil 字符通用工具类
 * 
 * @date 2013-6-9-下午1:44:35
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 */

public class CharUtil {
	
	/**
	 * @function withNonBmpStripped
	 * 
	 * 过滤掉4个字节以上的utf-8编码，只保留3个字节一下的编码
	 * 
	 * @param inString
	 * @author Dayong.Shen
	 * @date 2013-6-9-上午8:26:40
	 */	
	public static String withNonBmpStripped( String inString ) {
		 if (inString == null) return null;
	    return inString.replaceAll( "[\\ud800-\\udfff]", "");
	}
	
	
	public static float ChinesePercent(String str){
		
		if(str==null){
			return 0;
		}
		
//		System.out.println(str+"  "+str.length());
		
		char[] charArray=str.toCharArray();
		float percent=0;
		for(int i=0;i<charArray.length;i++){
			char c = charArray[i];
			if (isChinese(c)) {
				percent++;
			}			
		}
		
		return percent/charArray.length;
	}
	
	// 根据Unicode编码完美的判断中文汉字和符号
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}
	
	public static boolean isKoreaByREG(String str) {
		
		
		if (str == null) {
			return false;
		}
		
	/*	Pattern pattern1 = Pattern.compile("[\\x3130-\\x318F]+");
		Pattern pattern2 = Pattern.compile("[\\xAC00-\\xD7A3]+");
	//	Pattern pattern3 = Pattern.compile("[\\u9fa5-\\xD7A3]+");
		
		
		return (pattern1.matcher(str.trim()).find())||(pattern2.matcher(str.trim()).find());
		
		*/
		
		Pattern pattern =  Pattern.compile("[가-힣]+");
		
		return pattern.matcher(str.trim()).find();
	}
	
	//
	public static boolean isJapaneseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u0800-\\u4e00]+");
		return pattern.matcher(str.trim()).find();
	}

   public static float JapanesePercent(String str){
			
			if(str==null){
				return 0;
			}
			
//			System.out.println(str+"  "+str.length());
			
			char[] charArray=str.toCharArray();
			float percent=0;
			for(int i=0;i<charArray.length;i++){
				char c = charArray[i];
				
				if (isJapaneseByREG(String.valueOf(c))) {
					percent++;
				}			
			}
			
			return percent/charArray.length;
		}
	

	/**
	 * @function main
	 * 
	 * @param args
	 * @author Dayong.Shen
	 * @date 2013-6-9-下午1:44:35
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String[] strArr = new String[] { "www.micmiu.com",
				"!@#$%^&*()_+{}[]|\"'?/:;<>,.", "！￥……（）——：；“”‘’《》，。？、", "不要啊",
				"やめて", "韩佳人", "한가인" };
		for (String str : strArr) {
			System.out.println("===========> 测试字符串：" + str);
			System.out.println("正则判断：" + isChineseByREG(str) + " -- "
					+ isChineseByName(str));
			System.out.println("Unicode判断结果 ：" + isChinese(str));
			System.out.println("详细判断列表：");
			char[] ch = str.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				System.out.println(c + " --> " + (isChinese(c) ? "是" : "否"));
			}
		} */
		
		
		String str="Writer, Freelancer, Planning officer";
		String Text = "ニュースに関して「何か言わなくちゃいけない」という強迫観念が人をゲスな言葉へと走らせるのだと思う。事実は事実として何も言わずにただ受け止めるべきである。@daofeichang @aiww @fakecase 你的邮票怎么比我多不少？";
		
		String Text1 = "バリンーホウ御宅，モンスターハンターポータブルシリーズ死忠，爱科学，旁观者~살 ︴96라인 ︴카톡 ︴ 말레이시아 사람 ︴INSTAGRAM/카톡: akiyoseob";
		String Text2 = "영원히 정민도련님만 따를거에요";
		
		//17살 ︴96라인 ︴카톡 ︴ 말레이시아 사람 ︴INSTAGRAM/카톡: akiyoseob
		
		//日文平假名：3040-309F
		//日文片假名：30A0-30FF
		//日文片假名拼音扩展：31F0-31FF
		//[\\u0800-\\u4e00]
		
		System.out.println(JapanesePercent(Text));
		
		System.out.println(isJapaneseByREG(Text1));
		
		System.out.println(isKoreaByREG(Text1));
		
		System.out.println(isKoreaByREG(Text2));
		
	}//end main

}





