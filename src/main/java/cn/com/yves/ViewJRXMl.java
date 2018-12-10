/**
* Filename:    ViewJRXMl.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2018年5月8日
* Description:
*
* Author       Yves He
*/
package cn.com.yves;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.fastjson.JSON;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ViewJRXMl {
    private static JRPen linePen;

    public static void main(String[] args) {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        InputStream in = null;
        Connection con = null;
        try {
            in = new FileInputStream(new File("test-line.jrxml"));

            // 1.获取JasperDesign: 获取JasperDesign对象
            JasperDesign design = JRXmlLoader.load(in);
            JRBand summary = design.getSummary();
            JRElement[] elements = summary.getElements();
            JRDesignLine jrElement = (JRDesignLine) (elements[0]);
            linePen = jrElement.getLinePen();
            System.out.println(JSON.toJSONString(summary));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
