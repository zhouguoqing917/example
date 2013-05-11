package org.example.jetty;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

import org.eclipse.jetty.server.ssl.SslSelectChannelConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection; 
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.GzipHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;
import org.xml.sax.SAXException;

public class JettyServer extends Server {
	private Logger LOG = LoggerFactory.getLogger(JettyServer.class.getName());

	public static String webDefault = "src/main/resources/etc/webdefault.xml";
	private String xmlConfigPath = "src/main/resources/etc/jetty.xml";
	private String contextPath = "/";
	private String warTmp = "target/tmp";
	private String warPath = "target/example-1.0.war";
	private String resourceBase = "src/main/webapp";
	private String webXmlPath = "src/main/webapp/WEB-INF/web.xml";

	public JettyServer(String xmlConfigPath, String contextPath,
			String resourceBase, String webXmlPath) {
		this(xmlConfigPath, contextPath, resourceBase, webXmlPath, null);
	}

	public JettyServer(String xmlConfigPath, String contextPath) {
		this(xmlConfigPath, contextPath, null, null, null);
	}

	public JettyServer(String xmlConfigPath, String contextPath, String warPath) {
		this(xmlConfigPath, contextPath, null, null, warPath);
	}

	public JettyServer(String xmlConfigPath, String contextPath,
			String resourceBase, String webXmlPath, String warPath) {
		super();

		if (StringUtils.isNotBlank(xmlConfigPath)) {
			this.xmlConfigPath = xmlConfigPath;
			readXmlConfig();
		}

		if (StringUtils.isNotBlank(warPath)) {
			this.warPath = warPath;
			if (StringUtils.isNotBlank(contextPath)) {
				this.contextPath = contextPath;
				applyHandle(true);
			}
		} else {
			if (StringUtils.isNotBlank(resourceBase))
				this.resourceBase = resourceBase;
			if (StringUtils.isNotBlank(webXmlPath))
				this.webXmlPath = webXmlPath;
			if (StringUtils.isNotBlank(contextPath)) {
				this.contextPath = contextPath;
				applyHandle(false);
			}
		}

	}

	private void readXmlConfig() {
		try {
			XmlConfiguration configuration = new XmlConfiguration(
					new FileInputStream(this.xmlConfigPath));
			// Resource cfg = Resource.newResource(this.xmlConfigPath);
			LOG.info("" + this.xmlConfigPath);
			configuration.configure(this);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyHandle(Boolean warDeployFlag) {
		if (StringUtils.isBlank(webDefault)) {
			webDefault = "org.eclipse.jetty.webapp.webdefault.xml";
		}

		// contexts handler
		ContextHandlerCollection contexthandler = new ContextHandlerCollection();

		WebAppContext webapp = new WebAppContext();
		webapp.setContextPath(contextPath);
		webapp.setDefaultsDescriptor(webDefault);
		webapp.setTempDirectory(new File(warTmp));

		// webapp.setParentLoaderPriority(true);
		if (!warDeployFlag) {
			webapp.setResourceBase(resourceBase);
			webapp.setDescriptor(webXmlPath);
		} else {
			webapp.setWar(warPath);
		}
	
		ResourceHandler resource_handler = new ResourceHandler();
		resource_handler.setDirectoriesListed(true);
		resource_handler.setWelcomeFiles(new String[] { "index.html" });
		resource_handler.setResourceBase(resourceBase);
		 
	 
		contexthandler.setHandlers(new Handler[] { webapp, resource_handler,
				new DefaultHandler(),new StatisticsHandler(),new GzipHandler() });		
	    
		super.setHandler(contexthandler);
	}

	public void startServer() {
		try {
			super.start();
			LOG.info("jetty " + super.getVersion() + " | current thread:"
					+ super.getThreadPool().getThreads() + "| idle thread:"
					+ super.getThreadPool().getIdleThreads());
			LOG.info(super.getHandlers().toString());
			super.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getXmlConfigPath() {
		return xmlConfigPath;
	}

	public void setXmlConfigPath(String xmlConfigPath) {
		this.xmlConfigPath = xmlConfigPath;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getResourceBase() {
		return resourceBase;
	}

	public void setResourceBase(String resourceBase) {
		this.resourceBase = resourceBase;
	}

	public String getWebXmlPath() {
		return webXmlPath;
	}

	public void setWebXmlPath(String webXmlPath) {
		this.webXmlPath = webXmlPath;
	}

	public String getWarPath() {
		return warPath;
	}

	public void setWarPath(String warPath) {
		this.warPath = warPath;
	}

}