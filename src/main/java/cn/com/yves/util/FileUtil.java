/**
* Filename:    FileUtil.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2018年1月30日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.util;

public class FileUtil {

    /**
     * 获取类字节码下的文件
     *
     *
     *
     * @param clazz
     * @param shortFileName
     *            注意shortFileName不能以/开头
     * @return
     */
    public static String getFilePath(Class<?> clazz, String shortFileName) {
        return clazz.getResource(shortFileName).getPath();
    }

}
