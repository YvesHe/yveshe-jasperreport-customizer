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
package cn.com.yves.band.fatherchild;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrintManager;

public class Test {

    public static void main(String[] args) throws JRException, IOException {
        InputStream input = new FileInputStream(new File("职业规划理论.pdf"));
        OutputStream output = new FileOutputStream(new File("hello.jpg"));

        JasperPrintManager printManager = JasperPrintManager.getInstance(DefaultJasperReportsContext.getInstance());

        BufferedImage rendered_image = (BufferedImage) printManager.printToImage(input, 0, 1.0f);
        ImageIO.write(rendered_image, ".jpg", output);

        rendered_image.flush();
        try {
            input.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
