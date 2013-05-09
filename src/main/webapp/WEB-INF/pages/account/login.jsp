<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>登录</title>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
	<fieldset>
		<legend>登录</legend>
		<form:form commandName="account">
			<form:hidden path="id" />
			<ul>
				<li><form:label path="username">用户名:</form:label> <form:input
						path="username" /></li>
				<li><form:label path="password">密码:</form:label> <form:password
						path="password" /></li>
				<li>
					<button type="submit">登录</button>
					<button onclick="window.location.href='register.do';">注册</button>
				</li>
			</ul>
		</form:form>
	</fieldset>
</body>
</html>