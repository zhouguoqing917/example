<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
 
</head>
<body>
<fieldset><legend>用户注册</legend><form:form
	commandName="account">
	<ul>
		<li><form:label path="username">用户名:</form:label><form:input
			path="username" /></li>
		<li><form:label path="password">密码:</form:label><form:password
			path="password" /></li>
		<li><form:label path="birthday">生日:</form:label><form:input
            path="birthday" /></li>		 
		<li><form:label path="email">Email:</form:label><form:input
			path="email" /></li>
		<li>
		<button type="submit">注册</button>
		<button type="reset">重置</button>
		</li>
	</ul>
</form:form></fieldset>
</body>
</html>