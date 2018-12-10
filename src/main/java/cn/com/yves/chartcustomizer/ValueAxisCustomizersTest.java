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

import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class ValueAxisCustomizersTest extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        Double minValue = 0.0;// x轴的最小值
        Double maxValue = 24.0;// x轴的最大值
        Double tickUnit = 1.0;// 间隔单元

        if (chart.getPlot() instanceof XYPlot) {/* 针对XY的图 */
            XYPlot plot = (XYPlot) chart.getPlot();
            ValueAxis valueAxis = plot.getDomainAxis();
            if (valueAxis != null) {
                if (minValue != null || maxValue != null) {
                    valueAxis.setRange(minValue == null ? valueAxis.getRange().getLowerBound() : minValue, maxValue == null ? valueAxis.getRange().getUpperBound() : maxValue);
                }
                if (valueAxis instanceof NumberAxis) {
                    NumberAxis numAxis = (NumberAxis) valueAxis;
                    numAxis.setTickUnit(new NumberTickUnit(tickUnit));
                }
            }
        } else if (chart
                .getPlot() instanceof CategoryPlot) {/*
                                                      * 针对普通的柱状图,线图,面积图...
                                                      */
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            // 参数解释: value(Y轴), series,category(x轴)
            // dataset.addValue(value, rowKey, columnKey);

            /* 设置X轴 */
            CategoryAxis axis = plot.getDomainAxis();
            DefaultCategoryDataset dataset = (DefaultCategoryDataset) plot.getDataset();
            List columnKeys = dataset.getColumnKeys();
            List rowKeys = dataset.getRowKeys();

            DefaultCategoryDataset result = new DefaultCategoryDataset();
            for (int i = 0; i < rowKeys.size(); i++) {
                Comparable rowKey = (Comparable) rowKeys.get(i);
                setDataSet(result, rowKey);
            }
            // 更新数据
            for (int i = 0; i < columnKeys.size(); i++) {
                for (int j = 0; j < rowKeys.size(); j++) {
                    result.setValue(dataset.getValue((Comparable) rowKeys.get(j), (Comparable) columnKeys.get(i)), (Comparable) rowKeys.get(j), (Comparable) columnKeys.get(i));
                }
            }
            plot.setDataset(result);
        } else if (chart.getPlot() instanceof PiePlot) {
        }
    }

    private static void setDataSet(DefaultCategoryDataset dataset, Comparable rowKey) {
        dataset.setValue(null, rowKey, new Integer(0));
        dataset.setValue(null, rowKey, new Integer(1));
        dataset.setValue(null, rowKey, new Integer(2));
        dataset.setValue(null, rowKey, new Integer(3));
        dataset.setValue(null, rowKey, new Integer(4));
        dataset.setValue(null, rowKey, new Integer(5));
        dataset.setValue(null, rowKey, new Integer(6));
        dataset.setValue(null, rowKey, new Integer(7));
        dataset.setValue(null, rowKey, new Integer(8));
        dataset.setValue(null, rowKey, new Integer(9));
        dataset.setValue(null, rowKey, new Integer(10));
        dataset.setValue(null, rowKey, new Integer(11));
        dataset.setValue(null, rowKey, new Integer(12));
        dataset.setValue(null, rowKey, new Integer(13));
        dataset.setValue(null, rowKey, new Integer(14));
        dataset.setValue(null, rowKey, new Integer(15));
        dataset.setValue(null, rowKey, new Integer(16));
        dataset.setValue(null, rowKey, new Integer(17));
        dataset.setValue(null, rowKey, new Integer(18));
        dataset.setValue(null, rowKey, new Integer(19));
        dataset.setValue(null, rowKey, new Integer(20));
        dataset.setValue(null, rowKey, new Integer(21));
        dataset.setValue(null, rowKey, new Integer(22));
        dataset.setValue(null, rowKey, new Integer(23));
    }
}
