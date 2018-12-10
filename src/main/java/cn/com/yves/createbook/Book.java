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
package cn.com.yves.createbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cn.com.yves.util.JasperUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignPart;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.part.StandardPartEvaluationTime;
import net.sf.jasperreports.parts.subreport.StandardSubreportPartComponent;
import net.sf.jasperreports.parts.subreport.SubreportPartComponent;

/**
 * Book生成处理
 *
 * 1.无封面,无Cover可以正常显示 , 计划将所有的Jasper文件换成加载jrxml的方式.
 *
 * @author Yves He
 *
 */
public class Book {

    public static void main(String[] args) throws JRException {
        genBookAll();
    }

    /**
     * 根据空的Book.jrxml生成PDF正常显示
     *
     * @throws JRException
     */
    public static void genBook() throws JRException {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        String bookPath = Book.class.getResource("Book.jrxml").getPath();
        String bookJRXMLName = bookPath.substring(0, bookPath.lastIndexOf(".")); /// D:/workspace/Jasper-workspace/jasperreport-customizer/target/classes/cn/com/yves/createbook/Book

        // 1.获取JasperDesign对象
        JasperDesign bookDesign = JasperUtil.loadJRXMLFile(bookJRXMLName + ".jrxml");

        // 2.编译: 产生 JasperReport文件(.jasper)
        JasperReport bookJasper = JasperUtil.compileReport(bookDesign);

        // 3.填充数据:
        Map<String, Object> bookParas = new HashMap();
        JasperPrint result = JasperFillManager.fillReport(bookJasper, bookParas, new JREmptyDataSource(1));

        // 4.生成报表
        JasperExportManager.exportReportToPdfFile(result, bookJRXMLName + "-DEST.PDF");

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(bookJasper, bookJRXMLName + "-DEST.jrxml");
        System.out.println("GenPDF: " + bookJRXMLName + "-DEST.PDF");

    }

    /**
     * 生成只有封面的PDF (也就是有Group和Part的)
     *
     * @throws JRException
     */
    public static void genBookCover() throws JRException {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        String path = Book.class.getResource("").getPath();
        String bookJRXMLName = path + "Book"; /// D:/workspace/Jasper-workspace/jasperreport-customizer/target/classes/cn/com/yves/createbook/Book

        // 1.获取JasperDesign对象
        JasperDesign bookDesign = JasperUtil.loadJRXMLFile(bookJRXMLName + ".jrxml");

        JRDesignGroup group = new JRDesignGroup();
        group.setName("cover");
        bookDesign.addGroup(group);

        // 封面首
        JRDesignSection header = (JRDesignSection) group.getGroupHeaderSection();
        header.addPart(createPartCover(path + "Book_cover.jasper"));

        // 封面底
        JRDesignSection footer = (JRDesignSection) group.getGroupFooterSection();
        footer.addPart(createPartCover(path + "Book_backcover.jasper"));

        // 2.编译: 产生 JasperReport文件(.jasper)
        JasperReport bookJasper = JasperUtil.compileReport(bookDesign);

        /******** 设计完后,反写成jrxml ********/
        JasperUtil.writeJrxml(bookJasper, bookJRXMLName + "-DEST.jrxml");
        /*************************************/

        // 3.填充数据:
        Map<String, Object> bookParas = new HashMap();
        bookParas.put("BookTitle", "BES Report Title.");
        bookParas.put("BES_PARAMETER_MAP", bookParas);
        JasperPrint result = JasperFillManager.fillReport(bookJasper, bookParas, new JREmptyDataSource(1));

        // 4.生成报表
        JasperExportManager.exportReportToPdfFile(result, bookJRXMLName + "-DEST.PDF");
        System.out.println("GenPDF: " + bookJRXMLName + "-DEST.PDF");

    }

    public static void genBookMain() throws JRException {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        String path = Book.class.getResource("").getPath();
        String bookJRXMLName = path + "Book"; /// D:/workspace/Jasper-workspace/jasperreport-customizer/target/classes/cn/com/yves/createbook/Book

        // 1.获取JasperDesign对象
        JasperDesign bookDesign = JasperUtil.loadJRXMLFile(bookJRXMLName + ".jrxml");
        JRDesignSection main = (JRDesignSection) bookDesign.getDetailSection();
        main.addPart(createPartMain(path + "Main.jasper"));

        // 2.编译: 产生 JasperReport文件(.jasper)
        JasperReport bookJasper = JasperUtil.compileReport(bookDesign);

        // 3.填充数据:
        Map<String, Object> bookParas = new HashMap();
        bookParas.put("P1", "P1_BES");
        bookParas.put("BES_REPORT_CONNECTION", getConnection());
        bookParas.put("BES_PARAMETER_MAP", bookParas);
        JasperPrint result = JasperFillManager.fillReport(bookJasper, bookParas, new JREmptyDataSource(1));

        // 4.生成报表
        JasperExportManager.exportReportToPdfFile(result, bookJRXMLName + "-DEST.PDF");

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(bookJasper, bookJRXMLName + "-DEST.jrxml");
        System.out.println("GenPDF: " + bookJRXMLName + "-DEST.PDF");

    }

    // 整个报表
    public static void genBookAll() throws JRException {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        String path = Book.class.getResource("").getPath();
        String bookJRXMLName = path + "Book"; /// D:/workspace/Jasper-workspace/jasperreport-customizer/target/classes/cn/com/yves/createbook/Book

        // 1.获取JasperDesign对象
        JasperDesign bookDesign = JasperUtil.loadJRXMLFile(bookJRXMLName + ".jrxml");

        // ---->设置封面
        JRDesignGroup group = new JRDesignGroup();
        group.setName("cover");
        bookDesign.addGroup(group);

        // 封面首
        JRDesignSection header = (JRDesignSection) group.getGroupHeaderSection();
        header.addPart(createPartCover(path + "Book_cover.jasper"));

        // 封面底
        JRDesignSection footer = (JRDesignSection) group.getGroupFooterSection();
        footer.addPart(createPartCover(path + "Book_backcover.jasper"));

        // ---->设置Catalog
        header.addPart(createPartCatalog(path + "Book_toc.jasper"));

        // ---->设置Main
        JRDesignSection main = (JRDesignSection) bookDesign.getDetailSection();
        main.addPart(createPartMain(path + "Main.jasper"));

        // 2.编译: 产生 JasperReport文件(.jasper)
        JasperReport bookJasper = JasperUtil.compileReport(bookDesign);

        // 3.填充数据:
        Map<String, Object> bookParas = new HashMap();

        bookParas.put("BookTitle", "BES Report Title.");

        bookParas.put("P1", "P1_BES");
        bookParas.put("BES_REPORT_CONNECTION", getConnection());
        bookParas.put("BES_PARAMETER_MAP", bookParas);
        JasperPrint result = JasperFillManager.fillReport(bookJasper, bookParas, new JREmptyDataSource(1));

        // 4.生成报表
        JasperExportManager.exportReportToPdfFile(result, bookJRXMLName + "-DEST.PDF");

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(bookJasper, bookJRXMLName + "-DEST.jrxml");
        System.out.println("GenPDF: " + bookJRXMLName + "-DEST.PDF");

    }

    /**
     * 封面,目录,正文 都是属于Part ,共性是 Subreport 和property
     *
     * @param subreport
     * @param properties
     *            Part下配置的属性,只有目录一定要配置该属性
     * @return
     */
    private static JRDesignPart createPart(SubreportPartComponent subreport, Map<String, String> properties) {
        JRDesignPart result = new JRDesignPart();

        if (properties != null && !properties.isEmpty()) {
            JRPropertiesMap propertiesMap = result.getPropertiesMap();
            for (Entry<String, String> entry : properties.entrySet()) {
                propertiesMap.setProperty(entry.getKey(), entry.getValue());
            }
        }

        result.setComponentKey(new ComponentKey("http://jasperreports.sourceforge.net/jasperreports/parts", "p", "subreportPart"));
        result.setComponent(subreport);
        return result;
    }

    // subReporJasperName= "Book_cover.jasper"
    public static JRDesignPart createPartCover(String subReporJasperName) throws JRException {
        StandardSubreportPartComponent subReport = new StandardSubreportPartComponent();
        subReport.setUsingCache(false);
        subReport.setExpression(new JRDesignExpression("\"" + subReporJasperName + "\""));// 子报表达式
                                                                                          // //Book_cover.jasper

        // 封面中使用的参数来源于Map,
        subReport.setParametersMapExpression(new JRDesignExpression("$P{BES_PARAMETER_MAP}"));// 封面可以使用的Map
        // $P{CHILD_PARAMETER_MAP}

        // Connection和DataSoure在Cover中无需求

        return createPart(subReport, null);
    }

    // subReporJasperName= "Book_toc.jasper"
    public static JRDesignPart createPartCatalog(String subReporJasperName) throws JRException {
        StandardSubreportPartComponent subReport = new StandardSubreportPartComponent();
        subReport.setUsingCache(false);
        subReport.setExpression(new JRDesignExpression("\"" + subReporJasperName + "\""));// 子报表达式
                                                                                          // //Book_cover.jasper

        // 封面中使用的参数来源于Map,
        subReport.setParametersMapExpression(new JRDesignExpression("$P{BES_PARAMETER_MAP}"));// 封面可以使用的Map
        // $P{CHILD_PARAMETER_MAP}

        // Connection和DataSoure在Cover中无需求
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("net.sf.jasperreports.bookmarks.data.source.parameter", "REPORT_DATA_SOURCE");
        JRDesignPart createPart = createPart(subReport, properties);

        createPart.setEvaluationTime(StandardPartEvaluationTime.EVALUATION_REPORT);// 设置执行时间
        return createPart;
    }

    // subReporJasperName= "ContentF.jasper"
    private static JRDesignPart createPartMain(String subReporJasperName) throws JRException {

        StandardSubreportPartComponent subReport = new StandardSubreportPartComponent();

        // 数据源设置
        JRDesignSubreportParameter dataSourcePara = new JRDesignSubreportParameter();
        dataSourcePara.setName("REPORT_DATA_SOURCE"); // 设置参数名
        dataSourcePara.setExpression(new JRDesignExpression("new JREmptyDataSource(1)"));
        subReport.addParameter(dataSourcePara);

        subReport.setParametersMapExpression(new JRDesignExpression("$P{BES_PARAMETER_MAP}"));// MainReport
                                                                                              // 中需要数据库连接也放在Map里面.

        subReport.setUsingCache(false);
        subReport.setExpression(new JRDesignExpression("\"" + subReporJasperName + "\""));

        return createPart(subReport, null);
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
