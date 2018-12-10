/**
* Filename:    LineOutLineCustomizersTest.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年11月19日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.chartcustomizer;

import java.awt.BasicStroke;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class LineOutLineCustomizersTest extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        if (categoryPlot != null) {
            if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
                LineAndShapeRenderer renderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
                // renderer.setSeriesStroke(0, new BasicStroke(1.0f));// 设置线条粗细
                renderer.setSeriesOutlineStroke(0, new BasicStroke(0.5f));// 设置拐点大小
                renderer.setBaseOutlineStroke(new BasicStroke(10.0f));// 设置所有拐点的大小
            }
        }

    }

}
