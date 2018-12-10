/**
* Filename:    TimeseriesCustomizers.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年11月22日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.chartcustomizer;

import java.text.SimpleDateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class TimeseriesCustomizers extends JRAbstractChartCustomizer {
    public static final String DATE_FORMAT_PATTERN = "dateFormatPattern";

    public void customize(JFreeChart chart, JRChart jasperChart) {
        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = (XYPlot) chart.getPlot();
            DateAxis axis = (DateAxis) plot.getDomainAxis();
            axis.setDateFormatOverride(new SimpleDateFormat((String) getParameterValue("dateFormatPattern")));
        }
    }

}
