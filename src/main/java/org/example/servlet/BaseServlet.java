package org.example.servlet;

import org.example.cache.Cache;
import org.example.cache.CacheImpl;
import org.example.util.ToolUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 2310694379807490943L;

	private static final Log logger = LogFactory.getLog(BaseServlet.class);

	protected WebApplicationContext wac;
	// protected MobilePlayService mobilePlayService;

	protected Cache localCache;

	public void init() throws ServletException {
		if (inited) {
			return;
		}
		super.init();
		wac = WebApplicationContextUtils.getWebApplicationContext(this
				.getServletContext());
		// mobilePlayService =
		// (MobilePlayService)wac.getBean(MobilePlayService.class);
		localCache = wac.getBean("localCache", CacheImpl.class);
		inited = true;
	}

	boolean inited = false;

	public void printGBKNoCache(HttpServletResponse response, String result) {
		response.setContentType("text/html; charset=GBK");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getOutputStream().write(result.getBytes("GBK"));
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	public void printUTF8NoCache(HttpServletResponse response, String result) {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getOutputStream().write(result.getBytes("UTF-8"));
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	public void printAutoEncodeNoCache(HttpServletRequest request,
			HttpServletResponse response, String result) {
		byte[] outBytes;
		try {
			if ("true".equals(request.getParameter("gbk"))) {
				response.setContentType("text/html; charset=GBK");
				outBytes = result.getBytes("gbk");
			} else {
				response.setContentType("text/html; charset=UTF-8");
				outBytes = result.getBytes("utf-8");
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getOutputStream().write(outBytes);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	public void printNoCache(HttpServletResponse response, boolean result) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().print(result);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	// 支持callback方式
	public void printAutoEncodeNoCache(HttpServletRequest request,
			HttpServletResponse response, String result, String callBack) {
		if (StringUtils.isNotBlank(callBack)) {
			result = new StringBuffer(ToolUtil.filterHtml(callBack))
					.append("(").append(result).append(");").toString();
		}
		byte[] outBytes;
		try {
			String encoding = "gbk";
			if (StringUtils.isNotBlank(request.getParameter("encoding"))) {
				encoding = request.getParameter("encoding");
			}
			if ("gbk".equalsIgnoreCase(encoding.trim())) {
				response.setContentType("text/html; charset=GBK");
				outBytes = result.getBytes("gbk");
			} else {
				response.setContentType("text/html; charset=UTF-8");
				outBytes = result.getBytes("utf-8");
			}
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getOutputStream().write(outBytes);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	public void printNoCache(HttpServletResponse response, String result) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().print(result);
			response.flushBuffer();
		} catch (IOException e) {
			logger.error("print result error", e);
		}
	}

	public void setCache(HttpServletResponse response, long seconds) {

		java.util.Date date = new java.util.Date();
		response.setDateHeader("Last-Modified", date.getTime());
		response.setDateHeader("Expires", date.getTime() + seconds);
		response.setHeader("Cache-Control", "public");
		response.setHeader("Pragma", "Pragma");
	}

	public static String getMobileNumberByCookie(HttpServletRequest request,
			String name) {
		Cookie[] cookies = request.getCookies();
		String nick = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					String value = cookies[i].getValue();
					if (value != null) {
						nick = value;
					}
				}
			}
		}
		return nick;
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	public static String getResponseAsString(InputStream is, String encoding)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		if (encoding != null) {
			in = new BufferedReader(new InputStreamReader(is, encoding));
		} else {
			in = new BufferedReader(new InputStreamReader(is));
		}
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		String response = sb.toString();
		// log.info("response is "+response);
		return response;
	}

	protected static boolean match(String regex, String str) {

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {
			return true;
		}
		return false;

	}

	/**
	 * 判断是否为UC浏览器
	 * 
	 * @param regex
	 * @param str
	 * @return
	 */
	protected static boolean isUC(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		String agent = request.getHeader("User-Agent");

		boolean ret = false;
		if (null != accept) {
			ret = match("UC/[\\d]", accept);
		}
		if (!ret && null != agent) {
			ret = match("UC", agent);
		}

		return ret;
	}

}
