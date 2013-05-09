<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户信息</title>
<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<body>
<fieldset><legend>用户信息</legend>
<ul>
	<li><label>用户名:</label><c:out value="${account.username}" /></li>
	<li><label>生日:</label><fmt:formatDate value="${account.birthday}"
		pattern="yyyy年MM月dd日" /></li>
	<li><label>Email:</label><c:out value="${account.email}" /></li>
</ul>
</fieldset>
</body>
</html>