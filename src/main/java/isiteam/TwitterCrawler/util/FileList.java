
/**
* @project Web
* @author Dayong.Shen
* @package org.isiteam.crawler.TwitterCrawler.util
* @file FileList.java
* 
* @date 2013-6-4-下午6:37:34
* @Copyright 2013 ISI Team of NUDT-版权所有
* 
*/
 
package isiteam.TwitterCrawler.util;


import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
/**
 * 文件列表。<br>
 * 可以指定过滤条件。
 * @project Web
 * @author Dayong.Shen
 * @class FileList
 * 
 * @date 2013-6-4-下午6:37:34
 * @Copyright 2013 ISI Team of NUDT-版权所有
 * @Version 1.0.0
 * 
 */
public class FileList {

  /**
   * @param args
   */
  public static void main(String[] args) {
    File dir = new File(".");

    // 所有的文件和目录名
    String[] children = dir.list();
    if (children == null) {
      // 不存在或者不是目录
    } else {
      System.out.println("#### 1 ####");
      for (int i = 0; i < children.length; i++) {
        // 文件名
        System.out.println(children[i]);
      }
    }

    // 可以指定返回文件列表的过滤条件
    // 这个例子不返回那些以.开头的文件名
    FilenameFilter filter = new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return !name.startsWith(".");
      }
    };
    children = dir.list(filter);
    System.out.println("#### 2 ####");
    for (int i = 0; i < children.length; i++) {
      // 文件名
      System.out.println(children[i]);
    }

    // 也可以拿到文件对象的列表
    File[] files = dir.listFiles();
    System.out.println("#### 3 ####");
    for (int i = 0; i < files.length; i++) {
      // 文件名
      System.out.println(files[i].getName());
    }

    // 下面这个过滤条件只返回目录
    FileFilter fileFilter = new FileFilter() {
      public boolean accept(File file) {
        return file.isDirectory();
      }
    };
    files = dir.listFiles(fileFilter);
    System.out.println("#### 4 ####");
    for (int i = 0; i < files.length; i++) {
      // 文件名
      System.out.println(files[i].getName());
    }

  }

}

