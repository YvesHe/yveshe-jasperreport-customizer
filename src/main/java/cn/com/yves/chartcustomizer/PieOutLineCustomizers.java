package cn.com.yves.chartcustomizer;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class PieOutLineCustomizers extends JRAbstractChartCustomizer {
    public void customize(JFreeChart chart, JRChart jasperChart) {
        if (chart.getPlot() instanceof PiePlot) {
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setShadowPaint(null);
            plot.setSectionOutlinesVisible(true);
            plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));
            plot.setBaseSectionOutlinePaint(Color.BLACK);
        }
    }

}