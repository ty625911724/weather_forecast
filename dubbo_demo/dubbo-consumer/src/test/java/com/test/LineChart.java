package com.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

class LinechartFrame extends JFrame
{
    static NewPanel2 jp1;
    static int Mark = 0;
    public LinechartFrame(){
        jp1 = new NewPanel2();
        jp1.setBounds(0,0,100,50);
        this.add(jp1);
        this.setSize(400,270);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLocation(550,20);
        this.setTitle("气温折线图");
        this.setResizable(false);
        if(Mark == 1)
            this.setVisible(true);
    }
    public void updateLineChart(String data, String city){
        jp1.Mark = 1;
        jp1.repaint();
        jp1.revalidate();
        jp1.setData(data);
        jp1.setCity(city);
    }
    void set_visible(){
        this.setVisible(true);
    }
}

class NewPanel2 extends JPanel
{
    private String imagePath = "../资源文件";
    public int Mark = 0;
    String data;
    String[] arr1 = new String[0];
    String city;
    int[] temp_high = new int[6];
    int[] temp_low = new int[6];
    public NewPanel2() {
    }
    public void paintComponent(Graphics g)
    {
        int x = 0, y = 0;
        BufferedImage moon1 = null;
        try{
            moon1 = ImageIO.read(new FileInputStream(imagePath + "/折线图.jpg"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        g.drawImage(moon1.getScaledInstance(getSize().width,getSize().height,moon1.SCALE_SMOOTH),x,y,null);
        if(Mark == 1){
            Graphics2D g1 = (Graphics2D) g.create();
            g1.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g1.setColor(new Color(255,255,255));

            for(int i=0;i<6;i++){
                temp_high[i] = Integer.parseInt(arr1[15+4 * i].substring(0,arr1[15+4*i].indexOf("℃")));
                temp_low[i] = Integer.parseInt(arr1[15+4 * i + 1].substring(0,arr1[15+4*i+1].indexOf("℃")));
            }

            if((arr1[7].equals("101270101"))&(!city.equals("成都")))
            {
                System.out.println("查无此城市，输出为成都天气信息");
                city = "成都";
            }
            Font temp1 = new Font("等线", 1, 15);
            g1.setFont(temp1);
            g1.drawString("城市：" + city, 20, 20);

            for(int i=0;i<6;i++){
                g1.drawString(arr1[13 + i*4].substring(0,arr1[13 + i*4].indexOf("（")),30+55*i,200);
                g1.drawString(arr1[13 + i*4].substring(arr1[13 + i*4].indexOf("（")+1,arr1[13 + i*4].indexOf("）")),30+55*i,220);
            }
            int high = 45;
            int low = 160;
            int H = max(temp_high);
            int L = min(temp_low);
            int d = (low - high)/(H-L);

            for(int i=0;i<5;i++){
                g1.drawLine(40+57*i,high+(H-temp_high[i])*d,40+57*(i+1),high+(H-temp_high[i+1])*d);
                g1.drawLine(40+57*i,low - (temp_low[i]-L)*d,40+57*(i+1),low - (temp_low[i+1]-L)*d);
            }

            for(int i =0;i<6;i++){
                g1.drawString(arr1[15+4 * i],38+57*i,high+(H-temp_high[i])*d-10);
                g1.drawString(arr1[15+4 * i + 1],38+57*i,low - (temp_low[i]-L)*d+20);
                g1.fillOval(40+57*i-2,high+(H-temp_high[i])*d-2,5,5);
                g1.fillOval(40+57*i-2,low - (temp_low[i]-L)*d-2,5,5);
            }
        }
    }

    public void setData(String data)
    {
        this.data = data;
        if (null != data && data.length() > 0) {
            arr1 = data.split("/");
        }
    }
    public void setCity(String data)
    {
        city = data;
    }
    private int max(int[] a){
        int max=a[0];
        for (int i =1;i < a.length;i++){
            if(a[i]>max)
                max = a[i];
        }
        return max;
    }
    private int min(int[] a){
        int min=a[0];
        for (int i =1;i<a.length;i++){
            if(a[i]<min)
                min = a[i];
        }
        return min;
    }
}
