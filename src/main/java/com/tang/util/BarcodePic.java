package com.tang.util;


import com.tang.entity.SysRole;

import org.apache.commons.codec.language.bm.Lang;
import org.jbarcode.JBarcode;
import org.jbarcode.JBarcodeFactory;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.TextPainter;
import org.jbarcode.util.ImageUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;




/**
 *@class:JbarcodeUtil
 *@descript:生成条形码最标准
 *@date:2016年11月25日 下午1:46:07
 *@version:V1.0
 *备注：
 *1.静态代码块的作用：当类被载入时，静态代码块被执行，且只被执行一次，静态块常用来执行类属性的初始化。
 *2.常量条形码的高度和字体大小设置很重要，若是设置小了会看不到设置的文件
 */
public class BarcodePic {

    //设置条形码高度
    private static final int BARCODE_HEIGHT = 35;
    //设置条形码默认分辨率
//    private static final int BARCODE_DPI = ImageUtil.DEFAULT_DPI;
    //设置条形码字体样式
    private static final String FONT_FAMILY = "console";
    //设置条形码字体大小
    private static final int FONT_SIZE = 15;
    //设置条形码文本
    public static String TEXT = "A2-";
    //创建jbarcode
    private static JBarcode jbc = null;
    static JBarcode getJBarcode() throws InvalidAtributeException {
        /**
         * 参考设置样式：
         *barcode.setEncoder(Code128Encoder.getInstance()); //设置编码
         *barcode.setPainter(WidthCodedPainter.getInstance());// 设置Painter
         *barcode.setTextPainter(BaseLineTextPainter.getInstance()); //设置TextPainter
         *barcode.setBarHeight(17); //设置高度
         *barcode.setWideRatio(Double.valueOf(30).doubleValue());// 设置宽度比率
         *barcode.setXDimension(Double.valueOf(2).doubleValue()); // 设置尺寸，大小 密集程度
         *barcode.setShowText(true); //是否显示文本
         *barcode.setCheckDigit(true); //是否检查数字
         *barcode.setShowCheckDigit(false); //是否显示检查数字
         */
        if (jbc == null) {
            //生成code128
            jbc = JBarcodeFactory.getInstance().createCode128();
            jbc.setEncoder(Code128Encoder.getInstance());
            jbc.setTextPainter(CustomTextPainter.getInstance());
            jbc.setBarHeight(BARCODE_HEIGHT);
            jbc.setXDimension(Double.valueOf(0.8).doubleValue());
            jbc.setShowText(true);
        }
        return jbc;
    }

    /**
     * @descript:生成条形码文件
     * @param message  条形码内容
     * @param file   生成文件
     */
    public static void createBarcode(String message, File file,String text) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            createBarcode(message, fos,text);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @descript:生成条形码并写入指定输出流
     * @param message   条形码内容
     * @param os   输出流
     */
    public static void createBarcode(String message, OutputStream os,String text) {
        try {
            //设置条形码文本
            TEXT=text;
            //创建条形码的BufferedImage图像
            BufferedImage image = getJBarcode().createBarcode(message);
            ImageUtil.encodeAndWrite(image, ImageUtil.JPEG, os,150,70);
            os.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 静态内部类
     * 自定义的 TextPainter， 允许定义字体，大小，文本等
     * 参考底层实现：BaseLineTextPainter.getInstance()
     */
    protected static class CustomTextPainter implements TextPainter {
        private static CustomTextPainter instance =new CustomTextPainter();
        public static CustomTextPainter getInstance() {
            return instance;
        }
        public void paintText(BufferedImage barCodeImage, String text, int width) {
            //绘图
            Graphics g2d = barCodeImage.getGraphics();
            //创建字体
            Font font = new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE-4 );
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int height = fm.getHeight();
//            System.out.println(fm.getHeight());
            int center = (barCodeImage.getWidth() - fm.stringWidth(text)) / 2;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, barCodeImage.getWidth(), barCodeImage.getHeight() * 1 / 20);
//            System.out.println(barCodeImage.getWidth()+" "+barCodeImage.getHeight());
            g2d.fillRect(0, barCodeImage.getHeight() - (height * 9 / 10), barCodeImage.getWidth(), (height * 9 / 10));
            g2d.setColor(Color.BLACK);
            //绘制文本
            g2d.drawString(TEXT, 10, 129);
            //绘制条形码
            g2d.drawString(text, center+10, barCodeImage.getHeight() - (height / 10) - 2);
        }
    }



    //测试
    public static void main(String[] args) throws FileNotFoundException, IOException {

//        Bookdetail bt = new Bookdetail();
//        for(int i =0;i<10;i++){
//            Long s = System.currentTimeMillis();
//            for (int j = 1;j<50;j++);
//            String message = s.toString().substring(0,10);
//            BarcodePic.createBarcode(message, new File("F://image/code/"+message+".jpg"),"grid");
//            System.out.println(message);
//        }

        String message = "1554280337";
        BarcodePic.createBarcode(message, new File("F:\\image\\"+message+".jpg"),"A2-");


    }
}
