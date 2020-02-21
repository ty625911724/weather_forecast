package com.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

class ChooseCityFrame extends JFrame{
    static ChooseCityPanel jp1;
    String[] arr1 = new String[0];

    static JFrame d=null;

    public static String city = "无选中城市";

    ChooseCityFrame(){
        jp1=new ChooseCityPanel();
        jp1.setBounds(0,0,497,467);

        draw_botton("石家庄、沈阳、哈尔滨、杭州、福州、济南、广州、武汉、成都" +
                "、昆明、兰州、台北、南宁、银川、太原、长春、南京、合肥、南昌、郑州、长沙、" +
                "海口、贵阳、西安、西宁、呼和浩特、拉萨、乌鲁木齐、郫县、深圳",jp1);

        this.add(jp1);
        this.setSize(500,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(550,20);
        this.setTitle("选择城市");
        this.setResizable(false);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                if(d!=null){
                    d.dispose();}
            }
        });

        this.setVisible(true);
    }

    void draw_botton(String citys,JPanel jp1){
        if (null != citys && citys.length() > 0) {
            arr1 = citys.split("、");
        }
        jp1.setLayout(new FlowLayout());
        for(int i = 0; i< arr1.length;i++){
            JButton jb = new JButton(arr1[i]);
            jb.setPreferredSize(new Dimension(90,30));
            jp1.add(jb);
            addActionListener(jb);
        }
    }

    private static void addActionListener(JButton saveButton){
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 对话框
                String arg = e.getActionCommand();
                System.out.println(arg);

                city = arg;

                if(d!=null){
                    d.dispose();
                }
                d = new JFrame();
                d.setLayout(new FlowLayout());
                d.add(new JLabel("已选择的城市:"+city));
                d.add(new JLabel("点击查询按钮查询"));
                d.add(new JLabel("（文本框内内容无效）"));

                d.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent arg0) {
                        d.setVisible(false);
                    }
                });
                d.setLocation(600,300);
                d.setSize(150,120);
                d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                d.setVisible(true);
            }
        });
    }
}
//选择城市窗口

class ChooseCityPanel extends JPanel {
    private String imagePath = "../资源文件";
    public ChooseCityPanel() {}
    public void paintComponent(Graphics g) {

        int x = 0, y = 0;
        BufferedImage moon1 = null;
        try{
            moon1 = ImageIO.read(new FileInputStream(imagePath + "/timp.jpg"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        g.drawImage(moon1.getScaledInstance(getSize().width,getSize().height,moon1.SCALE_SMOOTH),x,y,null);
    }
}
//选择城市的图片加载作用
