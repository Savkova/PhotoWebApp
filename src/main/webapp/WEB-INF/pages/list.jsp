<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list</title>
</head>
<body>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<div align="center">
    <h2>Photo Album</h2>
    <c:if test="${not empty photos}">
        <form action="${root}/list" method="POST">
            <table border="0">
                <c:forEach var="photo" items="${photos}">
                    <tr>
                        <td>
                            <input type="checkbox" name="list_item" value="<c:out value="${photo.key}"></c:out>">
                        </td>
                        <td>
                            <a href="${root}/photo/<c:out value="${photo.key}"></c:out>">
                                <c:out value="${photo.key}"></c:out>
                            </a>
                        </td>
                        <td>
                            <img src="${root}/photo/<c:out value="${photo.key}"></c:out>" height="50">
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <input type="submit" value="Delete Photos"/>

        </form>
    </c:if>

    <input type="submit" value="Upload Photo" onclick="window.location='${root}/';"/>

</div>

</body>
</html>
