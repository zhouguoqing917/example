youku tudou mobile api
===
cd example 
mkdir -p src/main/java/org/example
Creating the HelloWorld Class
vim src/main/java/org/example/HelloWorld.java 

(1)#java
package org.example;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}

(2) Creating the POM Descriptor
 vim pom.xml
(3)  mvn clean compile exec:java 
(4) # curl http://localhost:8080  or ie
      <h1>Hello World</h1>
(5) mvn dependency:tree
  
###Developing a Standard WebApp with Jetty and Maven
cd progject dir
mkdir -p src/main/java/org/example
mkdir -p src/main/webapp/WEB-INF
(1) Creating a Servlet
 
package org.example;
 
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HelloServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
}
##
(2) You need to declare this servlet in the deployment descriptor, so edit the file src/main/webapp/WEB-INF/web.xml and add the following contents:
#web.xml 
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app
   xmlns="http://java.sun.com/xml/ns/javaee"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
   metadata-complete="false"
   version="3.0">
 
  <servlet>
    <servlet-name>Hello</servlet-name>
    <servlet-class>org.example.HelloServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>Hello</servlet-name>
    <url-pattern>/hello/*</url-pattern>
  </servlet-mapping>
</web-app>

(3)Creating the POM Descriptor

#The pom.xml  
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
 
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.example</groupId>
  <artifactId>hello-world</artifactId>
  <version>0.1-SNAPSHOT</version>
  <packaging>war</packaging>
  <name>Jetty HelloWorld WebApp</name>
 
  <properties>
      <jettyVersion>9.0.2.v20130417</jettyVersion>
  </properties>
 
  <dependencies>
    <dependency>
      <groupId>org.eclipse.jetty.orbit</groupId>
      <artifactId>javax.servlet</artifactId>
      <version>3.0.0.v201112011016</version>
      <scope>provided</scope>
    </dependency>
  </dependencies>
 
  <build>
    <plugins>
      <plugin>
        <groupId>org.eclipse.jetty</groupId>
        <artifactId>jetty-maven-plugin</artifactId>
        <version>${jettyVersion}</version>
      </plugin>
    </plugins>
  </build>
 
</project>

#(5) mvn jetty:run
or mvn jboss-as:deploy
You can see the static and dynamic content at http://localhost:8080/hello

There are a great deal of configuration options available for the jetty-maven-plugin to help you build and run your webapp. The full reference is at Configuring the Jetty Maven Plugin.
#(6) Building a WAR file  to Web Application Archive (WAR) file from the project with the command:
mvn package    
#
taglib-json,localCache,ip-seeker
