package com.test;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Connector {
	public static void main(String[] args) {
 		test_text();
	}
	static void test_text()
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:springmvc.xml" });

		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService");

		String mark ="y";
		do {
			Scanner input = new Scanner(System.in);
			System.out.println("请输入查询的城市：");
			String length = input.next();//输入中文字符串

			String data = demoService.get_weather(length);

			String[] arr1 = new String[0];
			if (null != data && data.length() > 0) {
				arr1 = data.split("/");
			}

			if((arr1[7].equals("101270101"))&(!length.equals("成都")))
			{
				System.out.println("查无此城市，输出为成都天气信息");
			}

			System.out.println("城市："+length);

			System.out.println("天气："+arr1[0]);

			System.out.println("最高气温："+arr1[1]);
			System.out.println("最低气温："+arr1[2]);
			System.out.println("当前气温："+arr1[3]);

			System.out.println("空气质量："+arr1[4]);
			System.out.println("空气质量等级："+arr1[5]);
			System.out.println("空气质量描述："+arr1[6]);

			System.out.println("风向："+arr1[8]);
			System.out.println("风速："+arr1[9]);
			System.out.println("紫外线指数："+arr1[10]);

			System.out.println("建议："+arr1[11]);
			System.out.println("image："+arr1[12]);

			System.out.println("是否继续，继续请输入y（小写）：");
			mark = input.next();
		}while(mark.equals("y"));

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
//文字测试使用

class ConnectProvider{
	String[] arr1 = new String[0];
    String get_weather_data(String length, DemoService demoService)
	{
		String data = demoService.get_weather(length);

		System.out.println(data);
		if (null != data && data.length() > 0) {
			arr1 = data.split("/");
		}

		if((arr1[7].equals("101270101"))&(!length.equals("成都")))
		{
			System.out.println("查无此城市，输出为成都天气信息");
		}

		System.out.println("城市："+length);

		System.out.println("天气："+arr1[0]);

		System.out.println("最高气温："+arr1[1]);
		System.out.println("最低气温："+arr1[2]);
		System.out.println("当前气温："+arr1[3]);

		System.out.println("空气质量："+arr1[4]);
		System.out.println("空气质量等级："+arr1[5]);
		System.out.println("空气质量描述："+arr1[6]);

		return data;
	}
}
//GUI交互界面使用,查找信息，并在控制台输出
