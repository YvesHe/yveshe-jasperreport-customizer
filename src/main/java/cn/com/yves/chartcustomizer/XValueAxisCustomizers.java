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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

/**
 * 时间自定义
 *
 * @author Yves He
 *
 */
public class XValueAxisCustomizers extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {

        // XYPlot (时序图 是属于XYPlot系列)
        if (chart.getPlot() instanceof XYPlot) {
            // new DateTickUnit(DateTickUnitType.DAY, 1)
            // domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, 10,
            // new java.text.SimpleDateFormat("yyyy-MM-dd")));
            // domainAxis.setAutoTickUnitSelection(false);
            // domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY,
            // 1));

            // x轴设置
            XYPlot xyPlot = chart.getXYPlot();
            DateAxis domainAxis = (DateAxis) xyPlot.getDomainAxis();
            domainAxis.setDateFormatOverride(new java.text.SimpleDateFormat("yyy-MM-dd"));
        }

    }

}
