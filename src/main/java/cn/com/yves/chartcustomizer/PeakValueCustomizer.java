package cn.com.yves.chartcustomizer;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class PeakValueCustomizer extends JRAbstractChartCustomizer {
    private static final String BES_PARAM_ITEM_LABEL_FONT_NAME = "BES_PARAM_ITEM_LABEL_FONT_NAME";
    private static final String BES_PARAM_ITEM_LABEL_FONT_SIZE = "BES_PARAM_ITEM_LABEL_FONT_SIZE";
    private static final String BES_PARAM_ITEM_LABEL_FONT_COLOR = "BES_PARAM_ITEM_LABEL_FONT_COLOR";
    private static final int DEFAULT_FONT_STYLE = Font.PLAIN;

    public void customize(JFreeChart chart, JRChart jasperChart) {
        if (chart.getPlot() instanceof CategoryPlot) {
            CategoryPlot plot = chart.getCategoryPlot();
            CategoryItemRenderer renderer = plot.getRenderer();

            // 设置ItemLabel生成器
            renderer.setBaseItemLabelGenerator(new BESCategoryItemLabelGenerator(plot.getDataset()));

            // 设置显示ItemLabel
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelFont(new Font((String) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_NAME), DEFAULT_FONT_STYLE, (Integer) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_SIZE)));
            renderer.setBaseItemLabelPaint(hexStrToColor((String) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_COLOR)));

            // NumberTickUnitSource

            ValueAxis rangeAxis = plot.getRangeAxis();
            NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
            NumberTickUnitSource unitSource = new NumberTickUnitSource(true);
            numberaxis.setStandardTickUnits(unitSource);

            numberaxis.setUpperMargin(0.2D);
        }

        if (chart.getPlot() instanceof XYPlot) {
            XYPlot plot = chart.getXYPlot();
            XYItemRenderer renderer = plot.getRenderer();

            // 设置ItemLabel生成器
            renderer.setBaseItemLabelGenerator(new BESXYItemLabelGenerator(plot.getDataset()));

            // 设置显示ItemLabel
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelFont(new Font((String) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_NAME), DEFAULT_FONT_STYLE, (Integer) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_SIZE)));
            renderer.setBaseItemLabelPaint(hexStrToColor((String) getParameterValue(BES_PARAM_ITEM_LABEL_FONT_COLOR)));

            NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
            numberaxis.setUpperMargin(0.2D);

        }
    }

    private Color hexStrToColor(String hexStr) {
        if (hexStr.trim().length() != 7) {
            throw new RuntimeException("error in set the item label font color,please check the font color!");
        }
        int r = Integer.parseInt(hexStr.trim().substring(1, 3), 16);
        int g = Integer.parseInt(hexStr.trim().substring(3, 5), 16);
        int b = Integer.parseInt(hexStr.trim().substring(5, 7), 16);
        return new Color(r, g, b);
    }

    class BESCategoryItemLabelGenerator extends AbstractCategoryItemLabelGenerator implements CategoryItemLabelGenerator {
        private static final long serialVersionUID = -6630316709996552660L;
        private double peakValue = 0.0D;

        public BESCategoryItemLabelGenerator(CategoryDataset dataSet) {
            super("", NumberFormat.getInstance());
            this.peakValue(dataSet);
        }

        private void peakValue(CategoryDataset dataSet) {
            for (int i = 0; i < dataSet.getRowKeys().size(); i++) {
                for (int j = 0; j < dataSet.getColumnKeys().size(); j++) {
                    peakValue = dataSet.getValue(i, j).doubleValue() > peakValue ? dataSet.getValue(i, j).doubleValue() : peakValue;
                }
            }
        }

        public String generateLabel(CategoryDataset dataset, int row, int column) {
            String itemLabelStr = null;
            Number num = dataset.getValue(row, column);
            if (num != null) {
                double result = num.doubleValue();
                if (result == this.peakValue)
                    itemLabelStr = num.toString();
            }
            return itemLabelStr;
        }

    }

    class BESXYItemLabelGenerator extends AbstractCategoryItemLabelGenerator implements XYItemLabelGenerator {
        private static final long serialVersionUID = 1911176006748528813L;
        private double peakValue = 0.0D;

        public BESXYItemLabelGenerator(XYDataset dataSet) {
            super("", NumberFormat.getInstance());
            this.peakValue(dataSet);
        }

        private void peakValue(XYDataset dataSet) {
            for (int i = 0; i < dataSet.getSeriesCount(); i++) {
                for (int j = 0; j < dataSet.getItemCount(i); j++) {
                    peakValue = dataSet.getYValue(i, j) > peakValue ? dataSet.getYValue(i, j) : peakValue;
                }
            }
        }

        public String generateLabel(XYDataset dataset, int series, int item) {
            String itemLabelStr = null;
            if (peakValue == dataset.getYValue(series, item)) {
                itemLabelStr = String.valueOf(peakValue);
            }
            return itemLabelStr;
        }
    }

}
