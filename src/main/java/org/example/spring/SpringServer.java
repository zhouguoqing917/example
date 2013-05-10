package org.example.spring;

import java.util.Collection;

import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;
import java.util.HashMap;
import java.util.Map;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.springframework.js.resource.ResourceServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringServer {
	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	private static final String WEBDEFAULT_PATH = "etc/webdefault.xml";
	private static final String JETTY_FILE = "etc/jetty.xml";
	private static final String JETTY_SPRING_FILE = "src/main/resources/etc/jetty-spring.xml";
	private static final String JETTY_HOME = "target/example-1.0/WEB-INF/lib";

	public static void main(String[] args) throws Exception {
		start();
	}


	public static void start() {
		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(8080);
		server.addConnector(connector);
		ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

		/**启动的时候就会加载这个listener 这里开始建立spring的application content*/
		ContextLoaderListener listener = new ContextLoaderListener();

		/**设置spring配置文件位置*/

		context.setInitParameter("contextConfigLocation", "WEB-INF/applicationContext.xml");
		/**servlet context*/
		context.setResourceBase("src/main/webapp");
		context.addEventListener(listener);

		/**资源文件解析*/
		ServletHolder holder = new ServletHolder(new ResourceServlet());
		context.addServlet(holder, "/static/*");

		/**spring control*/
		holder = new ServletHolder(new DispatcherServlet());
		holder.setInitParameter("dispatcherServletConfig", "WEB-INF/spring-servlet.xml");
		context.addServlet(holder, "/*");

		/**jsp 解析servlet*/
		holder = new ServletHolder(new JspServlet());
		context.addServlet(holder, "*.jsp");

		/**html*/
		holder = new ServletHolder(new DefaultServlet());
		context.addServlet(holder, "*.html");

		/**welcome file 这里没有生效 不知道为什么*/
		context.setWelcomeFiles(new String[] { "index.html" });

		try {

			server.start();
			server.join();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


}