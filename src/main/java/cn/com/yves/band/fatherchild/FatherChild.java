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
package cn.com.yves.band.fatherchild;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import cn.com.yves.util.ConnectionUtil;
import cn.com.yves.util.JasperUtil;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 *
 *
 * @author Yves He
 *
 */
public class FatherChild {

    public static void main(String[] args) {
        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        String sourceJRXML = "Wave_Book";

        // 1.获取JasperDesign: 获取JasperDesign对象
        JasperDesign design = JasperUtil.loadJRXMLFile(sourceJRXML + ".jrxml");

        /****** 设计区域 *****/
        JRDesignSubreport subReport = new JRDesignSubreport(null);
        JRDesignBand subReportBand = new JRDesignBand();
        subReportBand.addElement(subReport);

        JRDesignSection detailSection = (JRDesignSection) design.getDetailSection();
        detailSection.addBand(subReportBand);

        /****** *************/

        // 2.编译: 产生 .Jasper文件
        JasperReport compileReport = JasperUtil.compileReport(design);

        // 设计完后,反写成jrxml
        JasperUtil.writeJrxml(compileReport, sourceJRXML + "-DEST.jrxml");

        // 3.填充数据:
        Map<String, Object> parameters = new HashMap();
        Connection con = ConnectionUtil.getConnection();
        JasperUtil.fillReportToPDFByDataSource(compileReport, parameters, sourceJRXML + "-DEST.PDF", new JREmptyDataSource(1));

    }

}
