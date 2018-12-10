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

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class YvesScriptlet extends JRDefaultScriptlet {
    private final int pageIndex = 0;
    private final List<Integer> anchroPageIndex = new ArrayList<Integer>();

    @Override
    public void beforeReportInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves beforeReportInit !"));
    }

    public String hello() throws JRScriptletException {
        return "Hello! I'm the report's scriptlet object.";
    }

    public String pageIndex() throws JRScriptletException {
        this.getFieldValue("");
        return Integer.toString((Integer) this.getVariableValue("PAGE_NUMBER") + pageIndex);
    }

    // title pageheader columnheader lastPageHeader
    @Override
    public void afterReportInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves afterReportInit !"));
    }

    @Override
    public void beforePageInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves beforePageInit!"));

    }

    @Override
    public void afterPageInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves afterPageInit!"));
    }

    @Override
    public void beforeColumnInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves beforeColumnInit!"));
    }

    @Override
    public void afterColumnInit() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves afterColumnInit!"));
    }

    @Override
    public void beforeGroupInit(String groupName) throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves beforeGroupInit!"));
    }

    @Override
    public void afterGroupInit(String groupName) throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves afterGroupInit!"));
    }

    @Override
    public void beforeDetailEval() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves beforeDetailEval!"));
    }

    // else
    @Override
    public void afterDetailEval() throws JRScriptletException {
        this.setVariableValue("YvesScriptlet", new String("The is Yves afterDetailEval!"));
    }

}
