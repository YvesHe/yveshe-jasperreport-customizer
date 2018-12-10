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
package cn.com.yves.scriptlet;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class AnchorPageScriptlet extends JRDefaultScriptlet {

    /**
     *
     */
    @Override
    public void beforeReportInit() throws JRScriptletException {
        System.out.println("call beforeReportInit");
    }

    /**
     *
     */
    @Override
    public void afterReportInit() throws JRScriptletException {
    }

    /**
     *
     */
    @Override
    public void beforePageInit() throws JRScriptletException {
        System.out.println("call   beforePageInit : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
    }

    /**
     *
     */
    @Override
    public void afterPageInit() throws JRScriptletException {
        getFieldValue("");

        System.out.println("call   afterPageInit  : PAGE_NUMBER = " + this.getVariableValue("PAGE_NUMBER"));
    }

    /**
     *
     */
    @Override
    public void beforeColumnInit() throws JRScriptletException {
        System.out.println("call     beforeColumnInit");
    }

    /**
     *
     */
    @Override
    public void afterColumnInit() throws JRScriptletException {
        System.out.println("call     afterColumnInit");
    }

    /**
     *
     */
    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {
        if (groupName.equals("CityGroup")) {
            System.out.println("call       beforeGroupInit : City = " + this.getFieldValue("City"));
        }
    }

    /**
     *
     */
    @Override
    public void afterGroupInit(String groupName) throws JRScriptletException {
        if (groupName.equals("CityGroup")) {
            System.out.println("call       afterGroupInit  : City = " + this.getFieldValue("City"));

            String allCities = (String) this.getVariableValue("AllCities");
            String city = (String) this.getFieldValue("City");
            StringBuffer sbuffer = new StringBuffer();

            if (allCities != null) {
                sbuffer.append(allCities);
                sbuffer.append(", ");
            }

            sbuffer.append(city);
            this.setVariableValue("AllCities", sbuffer.toString());
        }
    }

    /**
     *
     */
    @Override
    public void beforeDetailEval() throws JRScriptletException {
        System.out.println("        detail");
    }

    /**
     *
     */
    @Override
    public void afterDetailEval() throws JRScriptletException {
    }

    /**
     *
     */
    public String hello() throws JRScriptletException {
        return "Hello! I'm the report's scriptlet object.";
    }

}
