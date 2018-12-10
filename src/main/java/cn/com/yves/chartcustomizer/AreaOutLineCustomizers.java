package cn.com.yves.chartcustomizer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class AreaOutLineCustomizers extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {

        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = chart.getCategoryPlot();

            if (plot.getRenderer() instanceof AreaRenderer) {
                AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
                // renderer.setDrawBarOutline(true);
                for (int i = 0; i < plot.getDataset().getRowKeys().size(); i++) {
                    renderer.setSeriesOutlinePaint(i, Color.BLACK);
                    renderer.setSeriesOutlineStroke(i, new BasicStroke(3.0f));
                    renderer.setBaseSeriesVisible(true);

                    renderer.setAutoPopulateSeriesOutlinePaint(true);
                    renderer.setAutoPopulateSeriesFillPaint(true);
                    renderer.setAutoPopulateSeriesOutlineStroke(true);
                    renderer.setAutoPopulateSeriesPaint(true);
                    renderer.setAutoPopulateSeriesStroke(true);
                    renderer.setBaseCreateEntities(true);
                    renderer.setDataBoundsIncludesVisibleSeriesOnly(true);
                    renderer.setSeriesVisible(true);
                    renderer.setSeriesVisible(i, true);

                    // 0.0f, 0.0f, Color.BLUE, 500.0f, 0.0f, Color.WHITE, true

                    GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.WHITE, 0.0f, 500.0f, Color.RED, true);
                    GradientPaint gp1 = new GradientPaint(50.0f, 0.0f, Color.WHITE, 500.0f, 0.0f, Color.GREEN, true);
                    GradientPaint gp2 = new GradientPaint(50.0f, 0.0f, Color.WHITE, 0.0f, 500.0f, Color.BLUE, false);
                    renderer.setSeriesPaint(0, gp0);
                    renderer.setSeriesPaint(1, gp1);
                    renderer.setSeriesPaint(2, gp2);

                    int defaultEntityRadius = renderer.getDefaultEntityRadius();

                    // renderer.setDrawBarOutline(true);
                    // renderer.setOutline(true);
                }
            }

            // 关于面积
            if (plot.getRenderer() instanceof BarRenderer) {
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setDrawBarOutline(true);

                for (int i = 0; i < plot.getDataset().getRowKeys().size(); i++) {
                    renderer.setSeriesOutlinePaint(i, Color.BLACK);
                    renderer.setSeriesOutlineStroke(i, new BasicStroke(3.0f));
                }

                GradientPaint gp0 = new GradientPaint(50.0f, 0.0f, Color.RED, 500.0f, 0.0f, Color.WHITE, false);
                GradientPaint gp1 = new GradientPaint(50.0f, 0.0f, Color.GREEN, 500.0f, 0.0f, Color.WHITE, false);
                GradientPaint gp2 = new GradientPaint(50.0f, 0.0f, Color.BLUE, 500.0f, 0.0f, Color.WHITE, false);
                renderer.setSeriesPaint(0, gp0);
                renderer.setSeriesPaint(1, gp1);
                renderer.setSeriesPaint(2, gp2);

            }

        }

        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = chart.getXYPlot();
            if (plot.getRenderer() instanceof XYAreaRenderer) {
                XYAreaRenderer renderer = (XYAreaRenderer) plot.getRenderer();
                for (int i = 0; i < plot.getDataset().getSeriesCount(); i++) {

                    renderer.setSeriesOutlinePaint(i, Color.BLACK);
                    renderer.setSeriesOutlineStroke(i, new BasicStroke(3.0f));

                    GradientPaint gp0 = new GradientPaint(50.0f, 0.0f, Color.RED, 500.0f, 0.0f, Color.WHITE, false);
                    GradientPaint gp1 = new GradientPaint(50.0f, 0.0f, Color.GREEN, 500.0f, 0.0f, Color.WHITE, false);
                    GradientPaint gp2 = new GradientPaint(50.0f, 0.0f, Color.BLUE, 500.0f, 0.0f, Color.WHITE, false);
                    renderer.setSeriesPaint(0, gp0);
                    renderer.setSeriesPaint(1, gp1);
                    renderer.setSeriesPaint(2, gp2);

                    renderer.setOutline(true);
                }
            }

        }

    }

}
