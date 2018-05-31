<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加</title>
</head>
<body>
<h3>添加页面</h3>
<form action="doAdd">
<p>class:<input type="text"  name="clz"/></p>
<p>jobName:<input type="text"  name="jobName"/></p>
<p>jobGroupName:<input type="text"  name="jobGroupName"/></p>
<p>triggerName:<input type="text"  name="triggerName"/></p>
<p>triggerGroupName:<input type="text"  name="triggerGroupName"/></p>
<p>cron:<input type="text"  name="cron"/></p>
<p><input type="submit" value="提交"/></p>
</form>
</body>
</html>