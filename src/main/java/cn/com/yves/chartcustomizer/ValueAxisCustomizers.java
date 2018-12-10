/**
* Filename:    ValueAxisCustomizers.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年11月19日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.chartcustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.Range;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class ValueAxisCustomizers extends JRAbstractChartCustomizer {

    public void test(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setTickMarksVisible(true);
        valueAxis.setAutoRange(false);
        if (valueAxis instanceof NumberAxis) {
            NumberAxis numAxis = (NumberAxis) valueAxis;
            Range range = numAxis.getRange();
            numAxis.setTickUnit(new NumberTickUnit((int) (range.getLength() / 4)));
        }
    }

    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot plot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRange(false);

        rangeAxis.setRange(new Range(0, 400.0));

        double unit = 100.0f;
        rangeAxis.setTickUnit(new NumberTickUnit(unit));
    }

}
