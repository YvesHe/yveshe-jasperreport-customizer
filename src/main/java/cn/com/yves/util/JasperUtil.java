/**
* Filename:    JasperUtil.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年12月19日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.util;

import java.io.File;
import java.sql.Connection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

public class JasperUtil {
    private static DefaultJasperReportsContext reportContext = DefaultJasperReportsContext.getInstance();

    // jrxml--> JasperDesign
    public static JasperDesign loadJRXMLFile(String jrxmlFilePath) {
        JasperDesign result = null;
        try {
            result = JRXmlLoader.load(new File(jrxmlFilePath));
        } catch (JRException e) {
            e.printStackTrace();
        }
        return result;
    }

    // JasperReportContext(上下文)
    public static JasperReportsContext confReportContext(Properties props) {
        for (Entry<Object, Object> entry : props.entrySet()) {
            reportContext.setProperty((String) entry.getKey(), (String) entry.getValue());
        }
        return reportContext;
    }

    // 编译
    public static JasperReport compileReport(JasperDesign jasperDesign) {
        JasperReport result = null;
        try {
            JasperCompileManager manager = JasperCompileManager.getInstance(reportContext);
            result = manager.compile(jasperDesign);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void compileReportToJasperFile(JasperDesign jasperDesign, String destJasperFileName) {
        try {
            JasperCompileManager manager = JasperCompileManager.getInstance(reportContext);
            manager.compileToFile(jasperDesign, destJasperFileName);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    // .jasper --> jrxml
    public static void writeJrxml(JasperReport jasperReport, String destJrxmlFilePath) {
        try {
            JRXmlWriter.writeReport(jasperReport, destJrxmlFilePath, "UTF-8");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, Connection connection) {
        JasperPrint result = null;
        try {
            result = JasperFillManager.fillReport(jasperReport, parameters, connection);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void fillReportToPDFByDataSource(JasperReport jasperReport, Map<String, Object> parameters, String destPDFName, JRDataSource dataSource) {
        try {
            JasperPrint result = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(result, destPDFName);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void fillReportToPDFByConnection(JasperReport jasperReport, Map<String, Object> parameters, String destPDFName, Connection connection) {
        try {
            JasperPrint result = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperExportManager.exportReportToPdfFile(result, destPDFName);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void printJsonString(Object obj) {
        String jsonString = JSON.toJSONString(obj);
        System.out.println("****jsonStr***");
        System.out.println(jsonString);
        System.out.println("*************");
    }

}
