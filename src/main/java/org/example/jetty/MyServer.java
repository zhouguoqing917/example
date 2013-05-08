package org.example.jetty;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyServer {
	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("classpath:etc/jetty-spring.xml");
	}
}
