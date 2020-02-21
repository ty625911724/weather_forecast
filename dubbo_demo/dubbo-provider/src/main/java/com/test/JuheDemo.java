package com.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class Days_wea_inform{
    String day_name = null;
    String wea_image = null;
    String high_tem = null;
    String low_tem = null;
    public void change_inform(String name,String image,String high,String low){
        day_name = name;
        wea_image = image;
        high_tem = high;
        low_tem = low;
    }
    public String get_send_inform()
    {
        return "/"+ day_name +"/"+wea_image+"/"+high_tem+"/"+low_tem;
    }
    //该类包含了后续7天的天气信息
}

class Wea_data{
    private Days_wea_inform[] days_wea;
    Wea_data()
    {
        days_wea = new Days_wea_inform[6];
        for(int i=0;i<6;i++)
            days_wea[i] = new Days_wea_inform();
    }

    public String get_wea(String wea_inform_json,String id)
    {
        JSONArray Array_wea_infrom = JSONArray.fromObject(wea_inform_json);
        JSONObject data = Array_wea_infrom.getJSONObject(0);

        System.out.println(data);
        System.out.println(data.getString("wea"));
        String weather = data.getString("wea");

        String tem_low = data.getString("tem1");
        String tem_high = data.getString("tem2");
        String tem_now = data.getString("tem");

        String air = data.getString("air");
        String air_level = data.getString("air_level");
        String air_tips = data.getString("air_tips");

        JSONArray Win = data.getJSONArray("win");
        String win = Win.getString(0);
        String win_speed = data.getString("win_speed");

        JSONArray index = data.getJSONArray("index");
        JSONObject  sun= index.getJSONObject(0);
        String level = sun.getString("level");
        String desc = sun.getString("desc");

        String image = data.getString("wea_img");

        for(int i=1;i<7;i++)
        {
            JSONObject day_wea_data = Array_wea_infrom.getJSONObject(i);

            String day = day_wea_data.getString("day");
            String wea_img = day_wea_data.getString("wea_img");
            String tem1 = day_wea_data.getString("tem1");
            String tem2 = day_wea_data.getString("tem2");
            days_wea[i-1].change_inform(day,wea_img,tem1,tem2);
        }

        String weather_send_data = weather +"/"+tem_low+"/"+tem_high+"/"+tem_now+"/"+air+"/"+air_level+
                "/"+air_tips +"/"+id+"/"+win+"/"+win_speed+"/"+level+"/"+desc+"/"+image;

        for(int i=1;i<7;i++)
        {
            weather_send_data = weather_send_data + days_wea[i-1].get_send_inform();
        }
        return weather_send_data;
    }
}

class JuheConnecter {
    private Wea_data wea_data;
    JuheConnecter()
    {
        wea_data = new Wea_data();
    }
    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public String getWeaRequest(String Id){
        String wea_send_data = null;
        String result = null;
        String url ="https://www.tianqiapi.com/api";
        Map params = new HashMap();//请求参数
        params.put("appid","53624979");
        params.put("appsecret","Cn7hDgbI");
        params.put("version","v1");//要查询的城市，如：温州、上海、北
        params.put("cityid",Id);//返回数据的格式,xml或json，默认jsonJSONObject

        try {
            result =net(url, params, "GET");

            JSONObject result_object = JSONObject.fromObject(result);

            String array_wea_data = result_object.getString("data");
            String cityid = result_object.getString("cityid");

            wea_send_data = wea_data.get_wea(array_wea_data,cityid);

            System.out.println(wea_send_data);
            System.out.println(wea_send_data.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return wea_send_data;
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return  网络请求字符串
     * @throws Exception
     **/
    private String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    private String urlencode(Map<String,String> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
