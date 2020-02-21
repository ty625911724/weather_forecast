package com.test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame   ;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUI{
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        MainFrame main_frame = new MainFrame();
        SoundThread soundThread = new SoundThread(main_frame);//设置背景音乐线程
        soundThread.start();//开启背景音乐线程
    }
}

class MainFrame extends JFrame {
    public String city;
    JTextField cityTextField;
    DemoService demoService;
    ConnectProvider connector;
    String data;
    ImagePanel showWeaPanel;
    ChooseCityFrame citysFrame = null;
    LinechartFrame line_chart = new LinechartFrame();

    public int isPlayMusic=0;
    //是否播放音乐，更新信息后，为1，进行语音播报
    public int exit=0;
    //标记窗口是否关闭，0未关闭，1退出

    private void elements_set(){
        this.setTitle("天气预报");

        this.setLayout(new BorderLayout());
        this.setSize(560,500);
        NewPanel jP = new NewPanel();
        this.add("East",jP);
        this.setResizable(false);

        showWeaPanel = new ImagePanel();
        this.add(showWeaPanel);

        //设置按钮样式1
        jP.setPreferredSize(new Dimension(130,40));//设置大小
        jP.setOpaque(false);

        jP.setLayout(new FlowLayout());

        JLabel a = new JLabel("查询的城市:");
        a.setForeground(Color.WHITE);//设置前景色
        a.setFont(new  java.awt.Font("微软楷体",  1,  20));
        a.setLocation(0,0);
        jP.add(a,0);

        cityTextField = new JTextField();
        cityTextField.setColumns(8);
        jP.add(cityTextField,1);

        JButton B1 = new JButton("查询");
        jP.add(B1,2);
        B1.setPreferredSize(new Dimension(90,30));

        JButton B2 = new JButton("常用城市");
        jP.add(B2,3);
        B2.setPreferredSize(new Dimension(90,30));

        JButton B3 = new JButton("周气温曲线");
        jP.add(B3,4);
        B2.setPreferredSize(new Dimension(90,30));

        addActionListener(B1);
        addActionListener(B2);
        addActionListener(B3);
    }
    //设置Frame中的所有元素, 包括按钮、文本框等等

    MainFrame()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:springmvc.xml" });
        context.start();
        demoService = (DemoService) context.getBean("demoService");
        connector = new ConnectProvider();
        elements_set();
        validate();
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent arg0) {
                System.exit(0);
                exit = 1;
            }
        });
    }

    private void addActionListener(JButton saveButton) {
        // 为按钮绑定监听器
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 对话框
                String arg = e.getActionCommand();
                if("查询".equals(arg)){
                    if(citysFrame!=null&&citysFrame.isShowing()){
                        city = citysFrame.city;
                        update();
                    }
                    else{
                        city = cityTextField.getText();
                        System.out.println(city);
                        update();
                    }
                }
                if("常用城市".equals(arg)){
                    if(citysFrame==null||!citysFrame.isShowing()){
                        citysFrame = new ChooseCityFrame();
                    }
                }
                if("周气温曲线".equals(arg)){
                    line_chart.Mark = 1;
                    line_chart.set_visible();
                }
            }
        });
    }

    private void update(){
        data = connector.get_weather_data(city,demoService);
        if(line_chart!=null) {
            line_chart.updateLineChart(data,city);
        }
        showWeaPanel.update_showPanel(data,city);
        cityTextField.setText(city);
        isPlayMusic = 1;
    }
}