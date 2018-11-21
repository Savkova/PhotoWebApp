<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>list</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
</head>
<body>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<div align="center">
    <h2>Photo Album</h2>

    <c:if test="${not empty photos}">
        <table border="0">
            <c:forEach var="photo" items="${photos}">
                <tr>
                    <td><input type="checkbox" name="items_id[]" value="${photo.key}" id="checkbox_${photo.key}"/></td>
                    <td><a href="${root}/photo/<c:out value="${photo.key}"></c:out>">
                        <c:out value="${photo.key}"></c:out>
                    </a></td>
                    <td><img src="${root}/photo/<c:out value="${photo.key}"></c:out>" height="50"></td>
                </tr>
            </c:forEach>
        </table>
        <p>
            <input type="submit" value="Home" onclick="window.location='${root}/';"/>
            <button type="button" id="download_button">Download</button>
            <button type="button" id="delete_button">Delete</button>
        </p>
    </c:if>

</div>

<script>
    $('#delete_button').click(function () {
        var items = {'items_id[]': []};
        $(':checked').each(function () {
            items['items_id[]'].push($(this).val());
        });
        $.post("${root}/list", items, function (data, status) {
            window.location.reload();
        });
    });
</script>

</body>
</html>
