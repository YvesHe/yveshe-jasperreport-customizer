package cn.com.yves.property;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;

public abstract class AbstractAxisCustomizer extends JRAbstractChartCustomizer {
    public static final String PROPERTY_MIN_VALUE = "minValue";
    public static final String PROPERTY_MAX_VALUE = "maxValue";
    public static final String PROPERTY_TICK_UNIT = "tickUnit";

    protected void configValueAxis(ValueAxis valueAxis, String minProp, String maxProp) {
        if (valueAxis != null) {
            Double rangeMin = getDoubleProperty(minProp);
            Double rangeMax = getDoubleProperty(maxProp);
            if (rangeMin != null || rangeMax != null) {
                valueAxis.setRange(rangeMin == null ? valueAxis.getRange().getLowerBound() : rangeMin, rangeMax == null ? valueAxis.getRange().getUpperBound() : rangeMax);
            }
        }
    }

    protected void configNumberAxis(NumberAxis numberAxis, String tickUnitProp) {
        if (numberAxis != null) {
            Double tickUnit = getDoubleProperty(tickUnitProp);
            if (tickUnit != null) {
                numberAxis.setTickUnit(new NumberTickUnit(tickUnit));
            }
        }
    }
}