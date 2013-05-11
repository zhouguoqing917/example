package org.example.jetty;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.cglib.proxy.Factory;

import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler; 
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.js.resource.ResourceServlet;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

/**
 * http://download.eclipse.org/jetty/stable-8/dist/
 * http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
 * http://nbaertuo.iteye.com/blog/366847
 * http://zhangwei2012.iteye.com/blog/1485344
 * @author guoqing
 *
 */
public class JettyWebServer {

	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	private static final String WEBDEFAULT_PATH = "etc/webdefault.xml";
	private static final String JETTY_FILE = "etc/jetty.xml";
	private static final String JETTY_SPRING_FILE = "etc/jetty-spring.xml";
	private static final String JETTY_HOME = "target/example-1.0/WEB-INF/lib";

	/**
	 * server WebAppContext
	 * 创建用于开发运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server createServerByConfigure() {

		Resource config = null;
		Server server = null;
		try {
			config = Resource.newSystemResource(JETTY_FILE);
			System.out.println(config.getFile().getAbsolutePath());
			XmlConfiguration configuration = new XmlConfiguration(config.getInputStream());
			server = (Server) configuration.configure();

		 
			// webapp
			WebAppContext wac = new WebAppContext();
			wac.setDescriptor("WEB-INF/web.xml");
			wac.setResourceBase(DEFAULT_WEBAPP_PATH);
			wac.setWar(DEFAULT_WEBAPP_PATH);
			wac.setDefaultsDescriptor(WEBDEFAULT_PATH);
			wac.setContextPath("/");
			wac.setTempDirectory(new File("target/tmp"));
			wac.setParentLoaderPriority(true);
			// spring
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
			context.setDecorators(wac.getDecorators());

			ClassPathXmlApplicationContext res = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
			DefaultListableBeanFactory beans = (DefaultListableBeanFactory) res.getBeanFactory();
			for (String s : res.getBeanDefinitionNames()) {
				System.out.println(" " + s + "  " + res.getBeanDefinitionCount());
			}

			ContextLoaderListener listener = new ContextLoaderListener();
			context.setInitParameter("contextConfigLocation", "classpath*:applicationContext.xml");
			context.addEventListener(listener);


			// /**spring control*/

			ServletHolder holder = new ServletHolder(new DispatcherServlet());
			holder.setInitParameter("contextConfigLocation", "classpath*:spring-servlet.xml");
			context.addServlet(holder, "/*");

			// /**资源文件解析*/
			// holder = new ServletHolder(new ResourceServlet());
			// context.addServlet(holder, "/*");

			// /**jsp 解析servlet*/
			holder = new ServletHolder(new JspServlet());
			context.addServlet(holder, "*.jsp");

			/**html*/
			//holder = new ServletHolder(new DefaultServlet());
			//context.addServlet(holder, "*.html");

			ContextHandlerCollection contexts = new ContextHandlerCollection();
			contexts.setHandlers(new Handler[] { wac, context });
			server.setHandler(contexts);

		} catch (IOException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return server;
	}

	/**
	 * 设置除jstl-*.jar外其他含tld文件的jar包的名称.
	 * jar名称不需要版本号，如sitemesh, shiro-web
	 */
	public static void setTldJarNames(Server server, String... jarNames) {
		WebAppContext context = (WebAppContext) server.getHandler();
		List<String> jarNameExprssions = Lists.newArrayList(".*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
		for (String jarName : jarNames) {
			jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
		}
		context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", StringUtils.join(jarNameExprssions, '|'));

	}

	/**
	 * 快速重新启动application，重载target/classes与target/test-classes.
	 */
	public static void reloadContext(Server server) throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();

		System.out.println("Application reloading");
		context.stop();

		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);
		context.start();
		System.out.println("Application reloaded");
	}

	public static void main(String[] args) throws Exception {
		Server server = JettyWebServer.createServerByConfigure();
		try {
			server.start();
			System.out.println("Server running at ");
			System.out.println("Hit Enter to reload the application");

			// 等待用户输入回车重载应用.
			while (true) {
				char c = (char) System.in.read();
				if (c == '\n') {
					// JettyWebServer.reloadContext(server);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
