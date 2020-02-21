package com.test;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

class Voice {
    public void strat(String content, int type) {
        ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
        Dispatch sapo = sap.getObject();

        if (type == 0) {
            try {
                // 音量 0-100
                sap.setProperty("Volume", new Variant(100));
                // 语音朗读速度 -10 到 +10
                sap.setProperty("Rate", new Variant(1.3));
                Variant defalutVoice = sap.getProperty("Voice");

                Dispatch dispdefaultVoice = defalutVoice.toDispatch();
                Variant allVoices = Dispatch.call(sapo, "GetVoices");
                Dispatch dispVoices = allVoices.toDispatch();

                Dispatch setvoice = Dispatch.call(dispVoices, "Item",
                        new Variant(1)).toDispatch();
                ActiveXComponent voiceActivex = new ActiveXComponent(
                        dispdefaultVoice);
                ActiveXComponent setvoiceActivex = new ActiveXComponent(
                        setvoice);

                Variant item = Dispatch.call(setvoiceActivex, "GetDescription");
                // 执行朗读
                Dispatch.call(sapo, "Speak", new Variant(content));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sapo.safeRelease();
                sap.safeRelease();
            }
        } else {
            // 停止

            try {
                Dispatch.call(sapo, "Speak", new Variant(content), new Variant(
                        2));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
//语音播报功能类

class MP3Player{
    static Player p;
    static String wea;
    private String imagePath = "../资源文件";
    MP3Player(String wea){
        this.wea = wea;
    }
    public void main() throws Exception {
        BufferedInputStream buffer = new BufferedInputStream(
                new FileInputStream(imagePath + "/music/"+wea+".mp3"));
        p = new Player(buffer);
        p.play();
    }
    public void stop()
    {
        p.close();
    }
}
//MP3播报类

class SoundThread extends Thread {
    MainFrame mainFrame;
    String[] arr1 = new String[0];

    SoundThread(MainFrame b)
    {
        this.mainFrame = b;
    }

    private void setData(String data)
    {
        if (null != data && data.length() > 0) {
            arr1 = data.split("/");
        }
        if(arr1[1].equals(arr1[2])){
            arr1[1]="无";
        }

    }
    public void run(){
        System.out.println("MyThread running");
        Voice voiceReporter = new Voice();
        musicThread t = new musicThread("fate1");
        t.start();
        while(mainFrame.exit == 0)
        {
            if(mainFrame.isPlayMusic == 1)
            {
                setData(mainFrame.data);
                if(t!=null) {
                    t.stop();
                    t = null;
                }

                if((arr1[7].equals("101270101"))&(!mainFrame.city.equals("成都")))
                {
                    System.out.println("查无此城市，输出为成都天气信息");
                    mainFrame.city = "成都";
                }

                voiceReporter.strat(mainFrame.city+","+arr1[0]+",今日当前温度："+arr1[3]+",最高气温："+arr1[1]+
                        ",最低气温："+arr1[2]+"，空气质量："+arr1[4]+",紫外线指数："+arr1[10], 0);
                if(arr1[12].equals("qing")|arr1[12].equals("yu")|arr1[12].equals("yin")) {
                    t = new musicThread(arr1[12]);
                }
                else{
                    t = new musicThread("雾");
                }
                t.start();
                mainFrame.isPlayMusic = 0;
            }
            try {
                sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("MyThread stop");
    }
}
//播放语音天气预报，控制背景音乐的线程
//该类对天气相关的线程做控制

class musicThread extends Thread {
    MP3Player mp3;
    musicThread(String wea)
    {
        mp3 = new MP3Player(wea);
    }
    public void run(){
        try{
            mp3.main();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
//播放与天气音乐相关的线程