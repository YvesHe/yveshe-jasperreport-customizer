package cn.com.yves.chartcustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class BarPeakValueCustomizers extends JRAbstractChartCustomizer {

    // rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。
    // isItemLabelVisible
    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot plot = chart.getCategoryPlot();
        if (plot != null) {
            if (plot.getRenderer() instanceof BarRenderer) {
                CategoryItemRenderer renderer = plot.getRenderer();
                // series count
                for (int i = 0; i < plot.getDataset().getRowKeys().size(); i++) {
                    // renderer.isItemLabelVisible(row, column);

                    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
                    renderer.setSeriesItemLabelsVisible(1, true);// 一個系列的设置

                    // renderer.setItemLabelGenerator(new
                    // StandardCategoryItemLabelGenerator());

                }
            }
        }
    }

}
