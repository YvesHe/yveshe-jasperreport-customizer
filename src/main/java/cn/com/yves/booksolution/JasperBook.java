/**
* Filename:    Test.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年12月5日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.booksolution;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.yves.util.ConnectionUtil;
import cn.com.yves.util.JasperUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignPart;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.parts.subreport.StandardSubreportPartComponent;

/**
 * 获取Book,编译成Book : Part的处理
 *
 * @author Yves He
 *
 */
public class JasperBook {

    public static void main(String[] args) {
        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        String bookJRXMLName = "book-solution" + File.separator + "Book";
        // 1.获取JasperDesign: 获取JasperDesign对象
        JasperDesign bookDesign = JasperUtil.loadJRXMLFile(bookJRXMLName + ".jrxml");

        // 获取原来报表的参数
        JasperDesign mainDesign = JasperUtil.loadJRXMLFile("book-solution" + File.separator + "ContentF.jrxml");
        List<JRParameter> mainParas = mainDesign.getParametersList();
        List<String> mainParaNames = new ArrayList<String>();

        // 申明参数
        try {
            for (JRParameter para : mainParas) {
                if (!para.isSystemDefined()) {
                    bookDesign.addParameter(para);
                    mainParaNames.add(para.getName());
                }
            }
        } catch (JRException e) {
            e.printStackTrace();
        }

        // 传递参数
        JRDesignSection bookSection = (JRDesignSection) bookDesign.getDetailSection();
        bookSection.addPart(getPartIncludeSubreport(new JRDesignPart(), "book-solution/ContentF.jasper", mainParaNames));

        // 2.编译: 产生 JasperReport文件(.jasper)
        JasperReport bookJasper = JasperUtil.compileReport(bookDesign);

        // 3.填充数据:
        Map<String, Object> bookParas = new HashMap();

        HashMap<String, Object> mainChild1 = new HashMap<String, Object>();
        mainChild1.put("P1", "p1");
        mainChild1.put("CHILD_REPORT_CONNECTION", ConnectionUtil.getConnection());
        bookParas.put("CHILD_PARAMETER_MAP", mainChild1);
        JasperUtil.fillReportToPDFByDataSource(bookJasper, bookParas, bookJRXMLName + "-DEST.PDF", new JREmptyDataSource(1));

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(bookJasper, bookJRXMLName + "-DEST.jrxml");

        Map<String, JRParameter> parametersMap = bookDesign.getParametersMap();
        System.out.println();

    }

    private static JRDesignPart getPartIncludeSubreport(JRDesignPart part, String subReporJasperName, List<String> subReportParas) {
        StandardSubreportPartComponent subReport = new StandardSubreportPartComponent();
        subReport.setUsingCache(false);
        subReport.setExpression(new JRDesignExpression("\"" + subReporJasperName + "\""));// 子报表达式
        try {
            if (subReportParas != null) {
                for (String para : subReportParas) {
                    subReport.addParameter(getSubreportParameter(para));
                }
            }

            // 空数据源
            JRDesignSubreportParameter dataSourcePara = new JRDesignSubreportParameter();
            dataSourcePara.setName("REPORT_DATA_SOURCE"); // 设置参数名
            dataSourcePara.setExpression(new JRDesignExpression("new JREmptyDataSource(1)")); // 设置参数名的表达式
            subReport.addParameter(dataSourcePara);
        } catch (JRException e) {
            e.printStackTrace();
        }

        part.setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/parts", "p", "subreportPart"));
        part.setComponent(subReport);
        return part;
    }

    private static JRSubreportParameter getSubreportParameter(String name) {
        String expressionName = "$P{" + name + "}";
        JRDesignSubreportParameter parameter = new JRDesignSubreportParameter();
        parameter.setName(name); // 设置参数名
        parameter.setExpression(new JRDesignExpression(expressionName)); // 设置参数名的表达式
        return parameter;
    }

    /**
     * 获取连接
     *
     * @return
     */
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
