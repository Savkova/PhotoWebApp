<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>result</title>
    </head>
    <body>
        <c:set var="root" value="${pageContext.request.contextPath}" />

    <div align="center">
            <h1>Your photo id is: ${photo_id}</h1>

            <input type="submit" value="Delete Photo" onclick="window.location='${root}/delete/${photo_id}';" />
            <input type="submit" value="Upload New" onclick="window.location='${root}/';" />

            <br/><br/><img src="${root}/photo/${photo_id}" />
        </div>
    </body>
</html>
