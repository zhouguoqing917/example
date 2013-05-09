package org.example.jetty;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

/**
 * http://download.eclipse.org/jetty/stable-8/dist/
 * http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
 * http://nbaertuo.iteye.com/blog/366847
 * @author guoqing
 *
 */
public class JettyWebServer {


	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	private static final String WEBDEFAULT_PATH = "etc/webdefault.xml";
	private static final String JETTY_FILE = "etc/jetty.xml";
	private static final String JETTY_SPRING_FILE = "src/main/resources/etc/jetty-spring.xml";
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

			// Resource springconfig = Resource.newResource(JETTY_SPRING_FILE);
			// System.out.println(springconfig.getFile().getAbsolutePath());
			// XmlConfiguration.main(new String[] { springconfig.getFile().getAbsolutePath() });

		} catch (IOException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		WebAppContext wac = new WebAppContext();
		wac.setDescriptor("WEB-INF/web.xml");
		wac.setResourceBase(DEFAULT_WEBAPP_PATH);
		wac.setWar(DEFAULT_WEBAPP_PATH);
		wac.setDefaultsDescriptor(WEBDEFAULT_PATH);
		wac.setContextPath("/");
		wac.setTempDirectory(new File("target/tmp"));
		wac.setParentLoaderPriority(true);
		server.setHandler(wac);
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
