package org.example.spring;
import org.eclipse.jetty.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JettySpringRunner{

    public static void main(String[] args) {
        try {
            new JettySpringRunner();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public JettySpringRunner() throws Exception{
        System.out.println("Initializing Spring context.");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
        Server server = (Server)applicationContext.getBean("server.Server");
        server.start();
        server.join();
    }
}