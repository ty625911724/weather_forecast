package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.Date;
import java.text.SimpleDateFormat;

public class database {
    static Connection con;
    database()
    {
        connect();
    }
    private void connect() {
        //Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/city_in?useUnicode=true&characterEncoding=utf8";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "625911724";
        //遍历查询结果集
        try {
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private String get_time(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm");//设置日期格式
        //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return  df.format(new Date());
    }

    private Boolean Is_change(String data){
        String d = get_time();
        String[] d_o = new String[0];
        String[] d_n = new String[0];
        if (null != data && data.length() > 0) {
            d_o = data.split("/");
        }
        if (null != data && d.length() > 0) {
            d_n = d.split("/");
        }
        System.out.println(d);
        System.out.println(data);

        if((d_o[0].equals(d_n[0]))&&(d_o[1].equals(d_n[1]))&&(d_o[2].equals(d_n[2]))){
            int hn = Integer.parseInt(d_n[3]);
            int ho = Integer.parseInt(d_o[3]);
            int mn = Integer.parseInt(d_n[4]);
            int mo = Integer.parseInt(d_o[4]);
            if(d_n[3].equals(d_o[3]))
            {
                if((mn-mo)>20)
                    return true;
                else
                    return false;
            }
            if((hn - ho)==1){
                if((mn - mo + 60)>20)
                    return true;
                else
                    return false;
            }
        }
        else
            return true;
        return true;
    }

    private String getIn(String cityId) {
        try {
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from inform where id="+"\""+cityId+"\"";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            String update_time = null;
            String wea = null;
            while (rs.next()) {
                //获取stuname这列数据
                update_time = rs.getString("update_time");
                //获取stuid这列数据
                wea = rs.getString("wea");

                //输出结果
                //System.out.println(update_time + "\t" + wea);
            }
            rs.close();
            return update_time+";"+wea;
        }
        catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public String getid(String city) {
        try {
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from city where cityZh="+"\""+city+"\"";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            String job = null;
            String id = null;
            while (rs.next()) {
                //获取stuname这列数据
                job = rs.getString("id");
                //获取stuid这列数据
                id = rs.getString("cityZh");

                //输出结果
                //System.out.println(id + "\t" + job);
            }
            rs.close();
            return job;
        }
          catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public void increase(String id,String data){
        PreparedStatement psql;
        ResultSet res;
        try {
            psql = con.prepareStatement("insert into inform(id,update_time,wea) "
                    + "values(?,?,?)");
            psql.setString(1, id);
            psql.setString(2, get_time());
            psql.setString(3, data);
            psql.executeUpdate();
        }
        catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
    }

    public void update(String wea,String cityId){
        PreparedStatement psql;
        try {
            psql = con.prepareStatement("update inform set update_time = ?,wea = ? where id = ?");
            psql.setString(1, get_time());
            psql.setString(2, wea);
            psql.setString(3, cityId);
            psql.executeUpdate();
        }
        catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        }
    }

    public String Is_have(String cityId){
        String a = getIn(cityId);
        String[] arr=null;
        if(a.equals("null;null")){
            return "0";
        }
        else{
            if (null != a && a.length() > 0) {
                arr = a.split(";");
            }
            if(Is_change(arr[0]))
                return "1";
            else
                return arr[1];
        }
    }
}