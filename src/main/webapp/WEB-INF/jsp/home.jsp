<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Home</title>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/fragments/navbar.jsp" %>

    <div class="container mt-5">
        <h2>Bem-vindo, <c:out value="${pageContext.request.userPrincipal.name}"/>!</h2>
    </div>
</body>
</html>
