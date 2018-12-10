package cn.com.yves.chartcustomizer.usejrchart;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.jfree.chart.JFreeChart;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

public class JRChartCustomizers2 extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {
        throw new RuntimeCryptoException(chart.getClass().getName());

        // byte chartType = jasperChart.getChartType();
        // switch (chartType) {
        //
        // case JRChart.CHART_TYPE_AREA:
        // throw new RuntimeCryptoException("CHART_TYPE_AREA");
        //
        // case JRChart.CHART_TYPE_BAR:
        // throw new RuntimeCryptoException("CHART_TYPE_BAR");
        //
        // case JRChart.CHART_TYPE_BAR3D:
        // throw new RuntimeCryptoException("CHART_TYPE_BAR3D");
        //
        // case JRChart.CHART_TYPE_BUBBLE:
        // throw new RuntimeCryptoException("CHART_TYPE_BUBBLE");
        //
        // case JRChart.CHART_TYPE_CANDLESTICK:
        // throw new RuntimeCryptoException("CHART_TYPE_CANDLESTICK");
        //
        // case JRChart.CHART_TYPE_GANTT:
        // throw new RuntimeCryptoException("CHART_TYPE_GANTT");
        //
        // case JRChart.CHART_TYPE_HIGHLOW:
        // throw new RuntimeCryptoException("CHART_TYPE_HIGHLOW");
        //
        // case JRChart.CHART_TYPE_LINE:
        // throw new RuntimeCryptoException("CHART_TYPE_LINE");
        //
        // case JRChart.CHART_TYPE_MULTI_AXIS:
        // throw new RuntimeCryptoException("CHART_TYPE_MULTI_AXIS");
        //
        // case JRChart.CHART_TYPE_PIE:
        // throw new RuntimeCryptoException("CHART_TYPE_PIE");
        //
        // case JRChart.CHART_TYPE_PIE3D:
        // throw new RuntimeCryptoException("CHART_TYPE_PIE3D");
        //
        // case JRChart.CHART_TYPE_SCATTER:
        // throw new RuntimeCryptoException("CHART_TYPE_SCATTER");
        //
        // case JRChart.CHART_TYPE_STACKEDAREA:
        // throw new RuntimeCryptoException("CHART_TYPE_STACKEDAREA");
        //
        // case JRChart.CHART_TYPE_STACKEDBAR:
        // throw new RuntimeCryptoException("CHART_TYPE_STACKEDBAR");
        // case JRChart.CHART_TYPE_STACKEDBAR3D:
        // throw new RuntimeCryptoException("CHART_TYPE_STACKEDBAR3D");
        //
        // case JRChart.CHART_TYPE_THERMOMETER:
        // throw new RuntimeCryptoException("CHART_TYPE_THERMOMETER");
        //
        // case JRChart.CHART_TYPE_TIMESERIES:
        // throw new RuntimeCryptoException("CHART_TYPE_TIMESERIES");
        //
        // case JRChart.CHART_TYPE_XYAREA:
        // throw new RuntimeCryptoException("CHART_TYPE_XYAREA");
        //
        // case JRChart.CHART_TYPE_XYBAR:
        // throw new RuntimeCryptoException("CHART_TYPE_XYBAR");
        //
        // case JRChart.CHART_TYPE_XYLINE:
        // throw new RuntimeCryptoException("CHART_TYPE_XYLINE");
        //
        // default:
        // throw new RuntimeCryptoException(jasperChart.getClass().getName());
        // }

    }

}
