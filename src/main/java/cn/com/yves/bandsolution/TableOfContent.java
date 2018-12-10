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
package cn.com.yves.bandsolution;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.alibaba.fastjson.JSON;

import cn.com.yves.util.ConnectionUtil;
import cn.com.yves.util.JasperUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.EvaluationTimeEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;

/**
 *
 *
 * @author Yves He
 *
 */
public class TableOfContent {
    public static void main(String[] args) {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        // 配置环境
        Properties props = new Properties();
        props.setProperty("com.jaspersoft.studio.data.defaultdataadapter", "true");
        props.setProperty("net.sf.jasperreports.bookmarks.data.source.parameter", "REPORT_DATA_SOURCE");
        JasperUtil.confReportContext(props);

        String sourceJRXML = "band-solution" + File.separator + "ReportMain";
        // 1.获取JasperDesign: 获取JasperDesign对象
        JasperDesign mainDesign = JasperUtil.loadJRXMLFile(sourceJRXML + ".jrxml");
        String jsonString = JSON.toJSONString(mainDesign);
        System.out.println("*************");
        System.out.println(jsonString);

        /****** 设计区域 *****/

        JRDesignSection section = (JRDesignSection) mainDesign.getDetailSection();
        List<JRDesignTextField> anchorList = new ArrayList<JRDesignTextField>();

        // create封面

        // create章节
        for (int i = 0; i < 10; i++) {
            String anchroStr = "Chapter" + i;
            JRDesignTextField chapter1 = createChapter("\"" + anchroStr + "\"", "\"" + anchroStr + "\"", 1);
            JRDesignBand band = new JRDesignBand();
            band.addElement(chapter1);
            band.setHeight(chapter1.getHeight());
            anchorList.add(chapter1);
            section.addBand(band);
        }

        // JRDesignTextField chapter2 = createChapter("\"Chapter2\"",
        // "\"Chapter2\"",
        // 2);
        // JRDesignBand band2 = new JRDesignBand();
        // band2.addElement(chapter2);
        // band2.setHeight(chapter2.getHeight());
        // anchorList.add(chapter2);
        // section.addBand(band2);

        // create目录
        JRDesignSubreport subReport = new JRDesignSubreport(null);
        subReport.setX(0);
        subReport.setY(0);
        subReport.setHeight(70);
        subReport.setWidth(555);
        subReport.setStretchType(StretchTypeEnum.CONTAINER_HEIGHT);
        subReport.setDataSourceExpression(new JRDesignExpression("new net.sf.jasperreports.engine.JREmptyDataSource(1)"));
        createTableOfContentReport("band-solution" + File.separator +
            "ReportTOC.jrxml", anchorList);

        // subReport.setExpression(new JRDesignExpression("\"band-solution" +
        // "/" + "ReportTOC-DEST.jasper\""));
        subReport.setExpression(new JRDesignExpression("\"band-solution" + "/" + "Book_toc.jasper\""));
        JRDesignBand tobBand = new JRDesignBand();
        tobBand.addElement(subReport);
        tobBand.setHeight(subReport.getHeight());
        section.addBand(0, tobBand);

        /****** *************/

        // 2.编译: 产生 .Jasper文件
        JasperReport compileReport = JasperUtil.compileReport(mainDesign);

        // 3.填充数据:
        Map<String, Object> parameters = new HashMap();
        Connection con = ConnectionUtil.getConnection();
        JasperUtil.fillReportToPDFByDataSource(compileReport, null, sourceJRXML + "-DEST.PDF", new JREmptyDataSource(1));

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(compileReport, sourceJRXML + "-DEST.jrxml");
    }

    private static void createTableOfContentReport(String subTemplatePath, List<JRDesignTextField> anchorList) {
        String sourceJRXML = subTemplatePath.substring(0, subTemplatePath.lastIndexOf("."));
        // 1.获取JasperDesign: 获取JasperDesign对象
        JasperDesign design = JasperUtil.loadJRXMLFile(sourceJRXML + ".jrxml");

        // 目录放置在Title中
        JRDesignBand titleBand = new JRDesignBand();
        design.setTitle(titleBand);
        int totalHeight = 0;

        // 添加标题
        JRDesignTextField title = new JRDesignTextField();
        titleBand.addElement(title);
        title.setExpression(new JRDesignExpression("\"TABLE OF CONTENTS\""));
        title.setX(0);
        title.setY(0);
        title.setHeight(80);
        title.setWidth(555);

        int y = title.getHeight();
        // 添加目录
        for (JRDesignTextField textField : anchorList) {

            JRDesignTextField anchor = createAnchor(textField.getAnchorNameExpression().getText(), textField.getAnchorNameExpression().getText(), 0, y);
            titleBand.addElement(anchor);
            y += anchor.getHeight();
            totalHeight += anchor.getHeight();
        }

        titleBand.setHeight(totalHeight + title.getHeight());

        // 2.编译: 产生 .Jasper文件
        JasperReport compileReport = JasperUtil.compileReport(design);
        JasperUtil.compileReportToJasperFile(design, sourceJRXML + "-DEST.jasper");

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(compileReport, sourceJRXML + "-DEST.jrxml");
    }

    public static JRDesignTextField createChapter(String showName, String anchorName, int bkLevel) {
        JRDesignTextField textField = new JRDesignTextField();
        textField.setExpression(new JRDesignExpression(showName));
        textField.setBookmarkLevel(bkLevel);

        // textField.setAnchorNameExpression(new
        // JRDesignExpression(anchorName));
        textField.setAnchorNameExpression(new JRDesignExpression(showName + "-1"));

        textField.setX(0);
        textField.setY(0);
        textField.setHeight(100);
        textField.setWidth(555);
        return textField;
    }

    public static JRDesignTextField createAnchor(String showName, String hyperlinkAnchorName, int x, int y) {
        JRDesignTextField textField = new JRDesignTextField();
        textField.setExpression(new JRDesignExpression(showName));
        textField.setHyperlinkAnchorExpression(new JRDesignExpression(hyperlinkAnchorName));
        textField.setLinkTarget("Self");
        textField.setLinkType("LocalAnchor");
        textField.setEvaluationTime(EvaluationTimeEnum.PAGE);

        textField.setX(x);
        textField.setY(y);
        textField.setHeight(20);
        textField.setWidth(555);
        return textField;
    }

}
