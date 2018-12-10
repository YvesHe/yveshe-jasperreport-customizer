/**
* Filename:    AreaRenderer.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2017年11月20日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.chartcustomizer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.PublicCloneable;

public class AreaRendererBES extends AbstractCategoryItemRenderer implements Cloneable, PublicCloneable, Serializable {

    public void drawItem(Graphics2D g2, CategoryItemRendererState state, Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis, ValueAxis rangeAxis, CategoryDataset dataset, int row,
            int column, int pass) {
        // TODO Auto-generated method stub

    }
}
