<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<div align="center">
    <form action="${root}/view" method="POST">
        Photo id: <input type="text" name="photo_id">
        <input type="submit"  value="Open"/>
    </form>

    <form action="${root}/add_photo" enctype="multipart/form-data" method="POST">
        Photo: <input type="file" name="photo">
        <input type="submit" value="Save"/>
    </form>

    <input type="submit" value="All Photos" onclick="window.location='${root}/list'"/>

</div>
</body>
</html>
