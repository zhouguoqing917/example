package org.example.spring;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

public class Server extends org.eclipse.jetty.server.Server {

	public void setBeans(Collection<Object> beans) {
		for (Object o : beans)
			addBean(o);
	}

	public static void ServerMain(String[] args) throws Exception {
		Resource config = Resource.newResource(args.length == 1 ? args[0] : "src/main/resources/etc/jetty-spring.xml");
		XmlConfiguration configuration = new XmlConfiguration(config.getURL());
		Object obj = configuration.configure();

	}

       
}