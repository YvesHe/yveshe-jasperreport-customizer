/**
* Filename:    Test.java
* Copyright:   Copyright (c)2016
* Company:     Yves
* @version:    1.0
* Create at:   2018年3月29日
* Description:
*
* Author       Yves He
*/
package cn.com.yves.exportimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleGraphics2DExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class GenImage {
    private static DefaultJasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

    public static void exportToPdfFile(
        JasperPrint jasperPrint,
        String destFileName) throws JRException {
        /*   */
        JRPdfExporter exporter = new JRPdfExporter(jasperReportsContext);

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(destFileName));

        exporter.exportReport();
    }

    public static void exportToImage(
        JasperPrint jasperPrint,
        String destFileName) throws JRException {
        /*   */
        JRGraphics2DExporter exporter = new JRGraphics2DExporter(jasperReportsContext);

        SimpleGraphics2DExporterOutput out = new SimpleGraphics2DExporterOutput();
        out.setGraphics2D(new org.apache.batik.ext.awt.g2d.DefaultGraphics2D(true));

        exporter.setExporterOutput(new SimpleGraphics2DExporterOutput());
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        exporter.exportReport();
    }

    public static void extractPrintImage(String filePath, JasperPrint print) {
        File file = new File(filePath);
        OutputStream ouputStream = null;
        try {
            ouputStream = new FileOutputStream(file);
            DefaultJasperReportsContext.getInstance();
            JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());

            BufferedImage rendered_image = null;
            rendered_image = (BufferedImage) printManager.printPageToImage(print, -1, 1.6f);
            ImageIO.write(rendered_image, "png", ouputStream);
            rendered_image.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch blockd
                    e.printStackTrace();
                }
            }
        }
    }

}
