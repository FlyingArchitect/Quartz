<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${basePath}/util/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${basePath}/js/quartz.js"></script>
<title>定时任务首页list</title>
</head>
<body>
	<div id="div">
		<table border="1" cellspacing="0" width="600">
			<thead>
				<tr>
					<td>class</td>
					<td>jobName</td>
					<td>jobGroupName</td>
					<td>triggerName</td>
					<td>triggerGroupName</td>
					<td>cron</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="tbodyid">
			</tbody>
			<tfoot>
				<tr>
					<button type="button" id='add'>添加</button>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>