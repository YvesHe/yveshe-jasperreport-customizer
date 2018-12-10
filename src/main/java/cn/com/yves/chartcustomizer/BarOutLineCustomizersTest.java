package cn.com.yves.chartcustomizer;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;

import net.sf.jasperreports.engine.JRAbstractChartCustomizer;
import net.sf.jasperreports.engine.JRChart;

//自定义类 JRAbstractChartCustomizer vs AbstractChartCustomizer
//http://blog.csdn.net/top_code/article/details/39519621
public class BarOutLineCustomizersTest extends JRAbstractChartCustomizer {

    public void customize(JFreeChart chart, JRChart jasperChart) {

        CategoryPlot plot = chart.getCategoryPlot();

        plot.getDataset().getRowKeys().size();// serires 数量
        plot.getDataset().getColumnKeys().size();// category 数量

        if (plot != null) {
            plot.getRendererCount();
            plot.getCategories().size(); // category 的数量

            if (plot.getRenderer() instanceof BarRenderer) {

                // 默认获取第一个Render
                BarRenderer renderer = (BarRenderer) plot.getRenderer();

                renderer.setDrawBarOutline(true);

                /* Base的设置,包括所有的柱子和所有的图例都生效 */
                // renderer.setBaseOutlinePaint(Color.black);
                // renderer.setBaseOutlineStroke(new BasicStroke(2.0f));

                /* 只有一个series的所有柱子显示. */
                // renderer.setSeriesOutlinePaint(0, Color.BLACK);
                // renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));

                BarRenderer renderer2 = (BarRenderer) plot.getRenderer(2);

                renderer2.getColumnCount();
                renderer2.getRowCount();
                renderer2.setSeriesOutlinePaint(4, Color.BLACK);
                renderer2.setSeriesOutlineStroke(4, new BasicStroke(2.0f));

                throw new RuntimeException("dataset:" + plot.getDataset().getRowKeys().size());

                // 对图例不显示边框

                /* 获取数量 */
                // int rendererCount = plot.getRendererCount();
                // throw new RuntimeException("size:" + rendererCount);

                // for (int i = 0; i < plot.getCategories().size(); i++) {
                // // renderer.setBaseOutlinePaint(Color.WHITE);
                // // renderer.setBaseOutlineStroke(new BasicStroke(2.0f));
                // renderer.setSeriesOutlinePaint(i, Color.WHITE, true);
                // renderer.setSeriesOutlineStroke(i, new BasicStroke(2.0f),
                // true);
                // }
            }
        }
    }
}
