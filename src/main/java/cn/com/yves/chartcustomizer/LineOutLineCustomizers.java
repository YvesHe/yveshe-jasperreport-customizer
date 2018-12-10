/**
* Filename:    LineOutLineCustomizers.java
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

public class LineOutLineCustomizers extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        if (categoryPlot != null) {
            if (categoryPlot.getRenderer() instanceof LineAndShapeRenderer) {
                LineAndShapeRenderer renderer = (LineAndShapeRenderer) categoryPlot.getRenderer();
                renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5D, -5D, 10D, 10D));
                renderer.setBaseOutlineStroke(new BasicStroke(1.0f));
            }
        }
    }

}
