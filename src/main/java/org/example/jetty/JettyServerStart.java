package org.example.jetty;
//http://blog.sina.com.cn/s/blog_6151984a0101503j.html
public class JettyServerStart {
	public static String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	public static String WEBDEFAULT= "src/main/resources/etc/webdefault.xml";
	public static String JETTY= "src/main/resources/etc/jetty.xml";
	public static String xmlConfigPath;
	public static String contextPath="/";
	public static String warPath="target/example-1.0.war";
	public static String resourceBase = "src/main/webapp";
	public static String webXmlPath = "src/main/webapp/WEB-INF/web.xml";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 JettyServer server = new JettyServer(JETTY,contextPath);
		    server.startServer();
		    
	}
}
