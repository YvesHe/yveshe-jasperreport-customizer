/**
* Filename:    JasperBookViewCatalog.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2018年3月22日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPart;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignPart;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.SectionTypeEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.parts.subreport.StandardSubreportPartComponent;

/**
 * JasperReport 目录设置
 *
 * @author Yves He
 *
 */
public class JasperBookCatalogView {

    public static void main(String[] args) {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        InputStream in = null;
        Connection con = null;
        try {
            in = new FileInputStream(new File("resource/Book.jrxml"));

            // 1.获取JasperDesign: 获取JasperDesign对象
            JasperDesign design = JRXmlLoader.load(in);
            SectionTypeEnum sectionType = design.getSectionType(); // PART /BAND

            showPart(design);

            System.out.println("show success!");

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

    /**
     * 查看结构
     *
     * @param design
     */
    private static void showPart(JasperDesign design) {

        System.out.println(JSON.toJSONString(design));

        // Part相关
        JRDesignSection section = (JRDesignSection) design.getDetailSection();
        List<JRPart> partsList = section.getPartsList();
        for (JRPart part : partsList) {
            JRDesignPart p = (JRDesignPart) part;
            StandardSubreportPartComponent sub = (StandardSubreportPartComponent) p.getComponent();
            JRDesignExpression expression = (JRDesignExpression) sub.getExpression();
            Map<String, JRSubreportParameter> parametersMap = sub.getParametersMap();
            JRExpression parametersMapExpression = sub.getParametersMapExpression();
            System.out.println(parametersMap.toString());
        }

        // 主数据集--获取的主数据集 是同一个实例 mainDataset == mainDesignDataset
        JRDataset mainDataset = design.getMainDataset();
        JRDesignDataset mainDesignDataset = design.getMainDesignDataset();
        mainDataset.getName();

        JRDesignQuery query = (JRDesignQuery) mainDesignDataset.getQuery();
        query.getText();
        query.getLanguage();// sql

        // JRQueryChunk[] chunks = query.getChunks();
        // JRPropertyChangeSupport eventSupport = query.getEventSupport();

        // 子数据集--不同结构的DataSet
        design.getDatasetMap(); // key: datasetName
        List<JRDataset> list = design.getDatasetsList();

        design.getDatasets();

        WhenNoDataTypeEnum whenNoDataTypeValue = design.getWhenNoDataTypeValue();
        design.getNoData();

        // 关于detail部分
        JRDesignSection section2 = (JRDesignSection) design.getDetailSection();
        List<JRBand> bandsList = section2.getBandsList();
        // JRBand (接口)--> JRDesignBand/JRBaseBand/JRFillBand
        JRDesignBand designBand = new JRDesignBand();
        JROrigin origin = designBand.getOrigin();

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
