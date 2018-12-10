/**
* Filename:    JRParametersTest.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2018年1月30日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.parameter;

import java.util.List;

import cn.com.yves.util.FileUtil;
import cn.com.yves.util.JasperUtil;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JasperDesign;

/**
 * 测试 JRBaseParameter的isForPrompting的属性
 *
 * @author Yves He
 *
 */
public class JRParametersTest {

    public static void main(String[] args) {

        /* 处理xml加载类的使用问题 */
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");

        String sourceJRXML = FileUtil.getFilePath(JRParametersTest.class, "jrxml/jasper-JRParameter.jrxml");

        // 1.获取JasperDesign: 获取JasperDesign对象
        JasperDesign design = JasperUtil.loadJRXMLFile(sourceJRXML);

        List<JRParameter> parametersList = design.getParametersList();
        for (JRParameter p : parametersList) {

            String msg = p.getName();
            if (!p.isForPrompting()) {
                msg += "-->isNotForPrompting";
            }
            String property = p.getPropertiesMap().getProperty("hideInScheduler");
            if (property != null && property.equals("true")) {
                msg += "-->hideInScheduler";
            }
            System.out.println(msg);
        }

    }

}
