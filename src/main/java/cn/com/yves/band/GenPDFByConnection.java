/**
 * Filename:    Test.java
 * Copyright:   Copyright (c)2016
 * Company:     Yves
 * @version:    1.0
 * Create at:   2017-9-18
 * Description:
 *
 * Author       Yves He
 */
package cn.com.yves.band;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 *
 * @author Yves He
 *
 */
public class GenPDFByConnection {

    public static void main(String[] args) throws IOException {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        InputStream in = null;
        Connection con = null;
        try {
            in = new FileInputStream(new File("ExportImage.jrxml"));

            // 1.获取JasperDesign: 获取JasperDesign对象
            JasperDesign jasperDesign = JRXmlLoader.load(in);

            // 2.编译: 产生 .Jasper文件
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            // 3.填充数据:
            Map<String, Object> parameters = new HashMap();
            con = getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);

            // // 4.导出文件:导出为PDF
            String pdfFile = "Hello.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFile);

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

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/jasperreport_test?characterEncoding=utf-8", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
