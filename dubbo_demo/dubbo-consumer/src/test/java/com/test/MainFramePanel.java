package com.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

class ImagePanel extends JPanel {
    private float transparency=(float)0.8 ;

    String data;
    String[] arr1 = new String[0];
    String city;
    private int Mark = 0;
    //标志是否需要更新图像
    private String imagePath = "../资源文件";
    //图像的路径

    private void draw_tem(Graphics2D g1)
    {
        //气温
        g1.setColor(new Color(0,0,0));
        Font temp1 = new Font("等线",  1,  50);
        g1.setFont(temp1);
        g1.drawString(arr1[3],220,125);

        temp1 = new Font("等线",  1,  30);
        g1.setFont(temp1);
        if(!arr1[1].equals(arr1[2])) {
            g1.drawString(arr1[1] + "/" + arr1[2], 220, 160);
        }
        else{
            g1.drawString("无" + "/" + arr1[2], 220, 160);
        }

        temp1 = new Font("等线",  1,  30);
        g1.setFont(temp1);
        g1.drawString(arr1[0],220,45);

        if((arr1[7].equals("101270101"))&(!city.equals("成都")))
        {
            System.out.println("查无此城市，输出为成都天气信息");
            city = "成都";
        }
        temp1 = new Font("等线",  1,  30);
        g1.setFont(temp1);
        g1.drawString(":"+ city,250,75);

        ImageIcon icon = new ImageIcon(imagePath + "/image/city.png");
        Image city_i = icon.getImage();
        g1.drawImage(city_i, 210, 45, 40,40,null);

    }

    private void draw_air(Graphics2D g1){
        g1.setStroke(new BasicStroke(3.0f));
        g1.setColor(new Color(255,255,255));
        g1.drawArc(10,195,80,80,15,330);

        if(!arr1[4].equals("0")) {
            g1.setColor(new Color(83, 197, 255));
            int i;
            i = Integer.parseInt(arr1[4]);
            i= i * 330 / 500;
            g1.drawArc(10, 195, 80, 80, 15, i );

            g1.setColor(new Color(0, 0, 0));
            Font temp1 = new Font("等线", 1, 30);
            g1.setFont(temp1);
            g1.drawString(arr1[4], 25, 245);
            temp1 = new Font("等线", 1, 15);
            g1.setFont(temp1);
            g1.drawString("污染：" + arr1[5], 15, 300);

            temp1 = new Font("等线", 1, 13);
            DrawStringChange S = new DrawStringChange();
            S.setRowWidth(100);
            S.draw(g1, arr1[6], 100, 210, temp1);
        }
        else{
            g1.setColor(new Color(0, 0, 0));
            Font temp1 = new Font("等线", 1, 30);
            g1.setFont(temp1);
            g1.drawString("无", 25, 245);
            temp1 = new Font("等线", 1, 15);
            g1.setFont(temp1);
            g1.drawString("污染：" + "无", 15, 300);

            temp1 = new Font("等线", 1, 13);
            DrawStringChange S = new DrawStringChange();
            S.setRowWidth(100);
            S.draw(g1, "查无空气质量信息", 100, 210, temp1);
        }
    }

    private void draw_index(Graphics2D g1){
        ImageIcon icon = new ImageIcon(imagePath + "/image/win.png");
        Image city_i = icon.getImage();
        g1.drawImage(city_i, 190, 195, 80,80,null);

        Font temp1 = new Font("等线",  1,  17);
        g1.setFont(temp1);
        g1.drawString("风向："+arr1[8],270,215);

        temp1 = new Font("等线",  1,  17);
        g1.setFont(temp1);
        if(arr1[9].length()>5)
        {
            int index=arr1[9].indexOf("转");
            g1.drawString("风速："+arr1[9].substring(0,index),270,235);
        }
        else{
            g1.drawString("风速："+arr1[9],270,235);
        }

        temp1 = new Font("等线",  1,  17);
        g1.setFont(temp1);
        g1.drawString("紫外线指数："+arr1[10],270,255);

        temp1 = new Font("等线",  1,  13);
        DrawStringChange S = new DrawStringChange();
        S.setRowWidth(120);
        S.draw(g1,arr1[11],270,275,temp1);
    }

    private void draw_day_temp(Graphics2D g1)
    {
        String image_wea = imagePath + "/image/image_wea/";
        ImageIcon icon;

        icon = new ImageIcon(image_wea + arr1[14]+".png");
        Image wea = icon.getImage();
        g1.drawImage(wea, 20, 320, 30,30,null);

        Font temp1 = new Font("等线",  1,  17);
        g1.setFont(temp1);
        g1.drawString(arr1[13],80,340);
        g1.drawString(arr1[15]+"/"+arr1[16],180,340);

        icon = new ImageIcon(image_wea + arr1[18]+".png");
        wea = icon.getImage();
        g1.drawImage(wea, 20, 350, 30,30,null);
        g1.drawString(arr1[17],80,370);
        g1.drawString(arr1[19]+"/"+arr1[20],180,370);

        icon = new ImageIcon(image_wea + arr1[22]+".png");
        wea = icon.getImage();
        g1.drawImage(wea, 20, 380, 30,30,null);
        g1.drawString(arr1[21],80,400);
        g1.drawString(arr1[23]+"/"+arr1[24],180,400);

        icon = new ImageIcon(image_wea + arr1[26]+".png");
        wea = icon.getImage();
        g1.drawImage(wea, 20, 410, 30,30,null);
        g1.drawString(arr1[25],80,430);
        g1.drawString(arr1[27]+"/"+arr1[28],180,430);

        BufferedImage moon1 = null;
        try{
            moon1 = ImageIO.read(new FileInputStream(imagePath + "/image/saber.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        g1.drawImage(moon1.getScaledInstance(100,100,moon1.SCALE_SMOOTH),270,340,null);
    }

    public void paint(Graphics g) {
        super.paint(g);
        Image bg, moon;
        ImageIcon icon;
        if(Mark == 0)
        {
            Graphics2D graphics2d = (Graphics2D) g.create();
            //graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));
            BufferedImage moon1 = null;
            try{
                moon1 = ImageIO.read(new FileInputStream(imagePath + "/背景.jpg"));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            graphics2d.drawImage(moon1.getScaledInstance(550,500,moon1.SCALE_SMOOTH),0,0,null);

            paintComponents(graphics2d);
        }
        else{
            String address = imagePath + "/image/";
            if(arr1[12].equals("ying")||arr1[12].equals("yu")||arr1[12].equals("qing")||arr1[12].equals("yun"))
            {
                icon = new ImageIcon(address +arr1[12]+".jpg");
            }
            else {
                icon = new ImageIcon(imagePath + "/image/2.jpg");
            }
            Graphics2D graphics2d = (Graphics2D) g.create();
            graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));

            bg = icon.getImage();
            graphics2d.drawImage(bg, 0, 0, 550, 500, null);
            paintComponents(graphics2d);
        }

        if(Mark == 1)
        {
            Graphics2D g1 = (Graphics2D) g.create();

            g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            String image_wea = imagePath + "/image/image_wea/";

            BufferedImage moon1 = null;
            try{
                moon1 = ImageIO.read(new FileInputStream(image_wea + arr1[12]+".png"));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            g1.drawImage(moon1.getScaledInstance(200,200,moon1.SCALE_SMOOTH),10,10,null);

            draw_tem(g1);
            g1.drawLine(20,185,380,185);
            draw_air(g1);
            draw_index(g1);

            g1.setStroke(new BasicStroke(1.0f));
            g1.drawLine(20,310,380,310);

            draw_day_temp(g1);

            paintComponents(g1);
        }
    }

    private void setData(String data)
    {
        this.data = data;
        if (null != data && data.length() > 0) {
            arr1 = data.split("/");
        }
    }
    private void setCity(String data)
    {
        city = data;
    }

    public void update_showPanel(String data, String city)
    {
        setData(data);
        Mark = 1;
        setCity(city);
        repaint();
        revalidate();
    }
}
//左侧整体显示窗口绘制

class NewPanel extends JPanel {
    private String imagePath = "../资源文件";
    public NewPanel() {
    }
    public void paintComponent(Graphics g) {
        int x = 0, y = 0;
        BufferedImage moon1 = null;
        try{
            moon1 = ImageIO.read(new FileInputStream(imagePath + "/image/timp1.jpg"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        g.drawImage(moon1.getScaledInstance(getSize().width,getSize().height,moon1.SCALE_SMOOTH),x,y,null);
    }
}
//右侧控制台的图片加载作用

class DrawStringChange
{
    int rowWidth;
    private int  getStringLength(Graphics g,String str) {
        char[]  strcha=str.toCharArray();
        int strWidth = g.getFontMetrics().charsWidth(strcha, 0, str.length());
        return strWidth;
    }
    private int getRowStrNum(int strnum,int rowWidth,int strWidth){
        int rowstrnum=0;
        rowstrnum=(rowWidth*strnum)/strWidth;
        return rowstrnum;
    }
    private  int  getRows(int strWidth,int rowWidth){
        int rows=0;
        if(strWidth%rowWidth>0){
            rows=strWidth/rowWidth+1;
        }else{
            rows=strWidth/rowWidth;
        }
        return rows;
    }
    private int  getStringHeight(Graphics g) {
        int height = g.getFontMetrics().getHeight();
        return height;
    }
    public void setRowWidth(int i)
    {
        rowWidth = i;
    }
    public void  draw(Graphics g, String strContent, int loc_X, int loc_Y, Font font){
        g.setFont(font);
        //获取字符串 字符的总宽度
        int strWidth =getStringLength(g,strContent);
        //每一行字符串宽度
        //获取字符高度
        int strHeight=getStringHeight(g);
        //字符串总个数
        if(strWidth>rowWidth){
            int rowstrnum=getRowStrNum(strContent.length(),rowWidth,strWidth);
            int  rows= getRows(strWidth,rowWidth);
            String temp="";
            for (int i = 0; i < rows; i++) {
                //获取各行的String
                if(i==rows-1){
                    //最后一行
                    temp=strContent.substring(i*rowstrnum,strContent.length());
                }else{
                    temp=strContent.substring(i*rowstrnum,i*rowstrnum+rowstrnum);
                }
                if(i>0){
                    //第一行不需要增加字符高度，以后的每一行在换行的时候都需要增加字符高度
                    loc_Y=loc_Y+strHeight;
                }
                g.drawString(temp, loc_X, loc_Y);
            }
        }else{
            //直接绘制
            g.drawString(strContent, loc_X, loc_Y);
        }
    }
}
//绘制字体所用的功能
