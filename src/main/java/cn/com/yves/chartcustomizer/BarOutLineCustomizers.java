package cn.com.yves.chartcustomizer;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class BarOutLineCustomizers extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        CategoryPlot plot = chart.getCategoryPlot();
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
