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

public class YValueAxisCustomizers extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot plot = chart.getCategoryPlot();
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setTickMarksVisible(true);
        if (valueAxis instanceof NumberAxis) {
            NumberAxis numAxis = (NumberAxis) valueAxis;
            Range range = numAxis.getRange();
            numAxis.setTickUnit(new NumberTickUnit((int) (range.getLength() / 4)));
        }
    }

}
