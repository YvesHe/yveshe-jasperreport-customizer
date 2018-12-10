/**
*
* Copyright:   Copyright (c)2016
* Company:     YvesHe
* @version:    1.0
* Create at:   2018年7月24日
* Description:
*
* Author       YvesHe
*/
package cn.com.yves.chartcustomizer.usejrchart;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

public class YvesChartCustomizer implements JRChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        if (chart.getPlot() instanceof CategoryPlot) {

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            if (plot != null) {
                if (plot.getRenderer() instanceof BarRenderer) {
                    BarRenderer renderer = (BarRenderer) plot.getRenderer();
                    renderer.setDrawBarOutline(true);
                    for (int i = 0; i < plot.getDataset().getRowKeys().size(); i++) {
                        renderer.setSeriesOutlinePaint(i, Color.BLACK);
                        renderer.setSeriesOutlineStroke(i, new BasicStroke(3.0f));
                    }
                }
            }

        }
    }

}
