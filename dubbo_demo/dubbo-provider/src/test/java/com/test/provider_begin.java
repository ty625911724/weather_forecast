package com.test;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class provider_begin {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:springmvc.xml");
		context.start();
		
		System.out.println("Dubbo provider start...");
		
		try {
			System.in.read();	// 按任意键退出
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
