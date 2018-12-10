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
package cn.com.yves.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRPart;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.component.ComponentKey;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignExpressionChunk;
import net.sf.jasperreports.engine.design.JRDesignPart;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.query.JRJdbcQueryExecuterFactory;
import net.sf.jasperreports.engine.type.ExpressionTypeEnum;
import net.sf.jasperreports.engine.type.SectionTypeEnum;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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

        InputStream in = null;
        Connection con = null;
        try {
            in = new FileInputStream(new File("FirstBook-Empty.jrxml"));

            // 1.获取JasperDesign: 获取JasperDesign对象
            JasperDesign design = JRXmlLoader.load(in);
            SectionTypeEnum sectionType = design.getSectionType(); // PART /BAND

            /* 导入包或者类 */
            // design.addImport("java.util.*");
            // design.addImport("java.io.InputStreamReader");

            /* 添加DateSet */
            design.addDataset(getJRDesignDataset(true));

            // 添加部分--detail-part
            // 部分分类: design.getSectionType(); PART /BAND
            JRDesignSection section = (JRDesignSection) design.getDetailSection();
            section.addPart(getPart());// 设置一个部分 与 section.addBand(band)类似

            // 添加组--group
            // design.addGroup(null);

            // 2.编译: 产生 .Jasper文件
            JasperCompileManager.compileReport(design);
            JasperCompileManager.compileReportToFile(design, "FirstBook-Result.jasper");
            System.out.println("success!");

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
     * 设置数据集--DataSet
     *
     * @param isMain
     * @return
     */
    private static JRDesignDataset getJRDesignDataset(boolean isMain) {
        // JasperDesign 只支持JRDesignDataset实例
        JRDesignDataset dataSet = new JRDesignDataset(true);

        // TODO
        // dataSet.setName(name);
        dataSet.setQuery(getJRDesignQuery());// 设置SQL查询语句

        return dataSet;
    }

    /**
     * DataSet 查询语句设置
     *
     * @return
     */
    private static JRDesignQuery getJRDesignQuery() {
        // JRDesignDataset 只支持JRDesignQuery

        JRDesignQuery result = new JRDesignQuery();
        result.setText("select * from info");
        result.setLanguage(JRJdbcQueryExecuterFactory.QUERY_LANGUAGE_SQL); // sql

        // result.setChunks(chunks);

        return result;
    }

    /**
     * 设计的部分Part --类似Band
     *
     * 思考: 主数据源怎么传递给Part部分使用
     *
     * @param design
     */
    private static JRPart getPart() {
        // JRPart(接口) --> JRBasePart -->JRDesignPart
        JRDesignPart part = new JRDesignPart();

        /* 设置Part部分 */
        // 在Part中添加子报表
        setPartComponent(part);
        // part.setEvaluationTime(evaluationTime);
        // part.setPartNameExpression(expression);
        // part.setUUID(uuid);
        // part.setPrintWhenExpression(new JRDesignExpression(""));

        return part;
    }

    /**
     * 在Detail中添加Part
     *
     * @param part
     */
    private static void setPartComponent(JRPart part) {
        // PartComponent(接口)-->SubreportPartComponent(接口)-->StandardSubreportPartComponent
        // 注意: PartComponent只有子报表的实现类
        JRDesignPart designPart = (JRDesignPart) part;

        StandardSubreportPartComponent subReport = new StandardSubreportPartComponent();
        subReport.setUsingCache(false); // 是否启用缓存
        subReport.setExpression(getJRExpression("\"one.jasper\"", ExpressionTypeEnum.SIMPLE_TEXT));// 子报表达式
        // subReport.setParametersMapExpression(null);// 参数表达式设置

        try {
            String paraName = "REPORT_CONNECTION";
            String expressionName = "$P{REPORT_CONNECTION}";
            subReport.addParameter(getSubreportParameter(paraName, expressionName));
        } catch (JRException e) {
            e.printStackTrace();
        } // 添加子报表参数

        String namespace = "http://jasperreports.sourceforge.net/jasperreports/parts";
        String namespacePrefix = "p";
        String name = "subreportPart";

        designPart.setComponentKey(new ComponentKey(namespace, namespacePrefix, name));
        designPart.setComponent(subReport); // 必须设置
    }

    /**
     *
     * @param name
     * @param expressionName
     * @return
     */
    private static JRSubreportParameter getSubreportParameter(String name, String expressionName) {
        // JRSubreportParameter(接口) ↓
        // JRBaseSubreportParameter/JRDesignSubreportParameter/TableSubreportParameter

        JRDesignSubreportParameter parameter = new JRDesignSubreportParameter();

        // 设置参数
        parameter.setName(name); // 设置参数名
        parameter.setExpression(getJRExpression(expressionName, ExpressionTypeEnum.SIMPLE_TEXT)); // 设置参数名的表达式

        return parameter;
    }

    /**
     * 报表表达式 --JRExpression--CDATA
     *
     * @param text
     * @param type
     *            表达式的类型:
     *
     *            ExpressionTypeEnum.DEFAULT 对应变量
     *
     *            ExpressionTypeEnum.SIMPLE_TEXT
     * @return
     */
    private static JRExpression getJRExpression(String text, ExpressionTypeEnum type) {
        // JRExpression (接口)--> JRBaseExpression -- JRDesignExpression
        JRDesignExpression expression = new JRDesignExpression(text);

        /* 表达式设置 */
        expression.setType(type);
        // expression.setId(1);
        // expression.setText(text);
        // expression.setValueClass(null);
        // expression.setValueClassName(null);

        // 不单独设置表达式块时,可以通过构造方法JRDesignExpression 直接传递值既可.
        // List<JRExpressionChunk> chunks = new ArrayList<JRExpressionChunk>();
        // chunks.add(getJRExpressionChunk("\"" + text + "\"",
        // JRExpressionChunk.TYPE_TEXT));
        // expression.setChunks(chunks);

        return expression;
    }

    /**
     * 表达式块
     *
     * @param text
     * @param type
     * @return
     */
    private static JRExpressionChunk getJRExpressionChunk(String text, byte type) {
        // JRExpressionChunk(接口)-->JRBaseExpressionChunk->JRDesignExpressionChunk

        JRDesignExpressionChunk chunk = new JRDesignExpressionChunk();

        // 设置chunk
        chunk.setText(text);
        chunk.setType(type);

        // type支持数据集:
        // JRExpressionChunk.TYPE_TEXT = 1;
        // TYPE_PARAMETER = 2;
        // TYPE_FIELD = 3;
        // TYPE_VARIABLE = 4;
        // TYPE_RESOURCE = 5;

        return chunk;
    }

    /**
     * 添加报表组
     */
    private static void addGroup() {
        // 添加组
        // JRDesignGroup group = new JRDesignGroup();
        // group.setName("YvesGroup");
        // group.getGroupFooterSection();
        // jasperDesign.addGroup(group);
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
