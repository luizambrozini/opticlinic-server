<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"  uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>

<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<c:set var="uri" value="${pageContext.request.requestURI}" />

<style>

    .logo {
        display: flex;
        align-items: center;
        justify: center;
        gap: 8px;

        text-decoration: none;
        margin-right: 16px;

    }
    .logo_name {
        color: #ffffff;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        font-size: 20px;
        letter-spacing: 2px;
        margin: 0;
    }

</style>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary shadow-sm">
  <div class="container-fluid">
    <!-- Logo / Título -->
    <a class="logo" href="/app">
      <p class="logo_name">
        Opticlinic
      </p>
    </a>

    <!-- Botão de colapso (mobile) -->
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent"
            aria-controls="navbarContent" aria-expanded="false" aria-label="Alternar navegação">
      <span class="navbar-toggler-icon"></span>
    </button>

    <!-- Conteúdo -->
    <div class="collapse navbar-collapse" id="navbarContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link ${fn:startsWith(uri, '/WEB-INF/jsp/home.jsp') ? 'active' : ''}" href="/app">Início</a>
        </li>

        <li class="nav-item">
          <a class="nav-link ${fn:startsWith(uri, '/WEB-INF/jsp/config/index.jsp') ? 'active' : ''}" href="/app/config">Configurações</a>
        </li>
        <!-- Adicione outros itens aqui -->
      </ul>

      <!-- Sessão do usuário -->
      <sec:authorize access="isAuthenticated()">
        <c:set var="username" value="${pageContext.request.userPrincipal.name}" />
        <c:set var="initial"  value="${fn:toUpperCase(fn:substring(username, 0, 1))}" />

        <div class="d-flex align-items-center gap-3">
          <!-- Dropdown de usuário -->
          <div class="dropdown">
            <button class="btn p-0 border-0 bg-transparent" type="button" id="userMenu" data-bs-toggle="dropdown"
                    aria-expanded="false" title="${username}">
              <span class="rounded-circle bg-light text-primary fw-bold d-inline-flex align-items-center justify-content-center"
                    style="width: 40px; height: 40px; font-size: 16px; border: 1px solid rgba(255,255,255,.6);">
                ${initial}
              </span>
            </button>
            <ul class="dropdown-menu dropdown-menu-end shadow" aria-labelledby="userMenu">
              <li class="dropdown-header">
                <strong>${username}</strong>
              </li>
              <li><a class="dropdown-item" href="/app/profile">Meu perfil</a></li>
              <li><hr class="dropdown-divider"></li>
              <li>
                <form action="<c:url value='/app/auth/logout'/>" method="post" class="px-3">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  <button type="submit" class="dropdown-item px-0">Sair</button>
                </form>
              </li>
            </ul>
          </div>
        </div>
      </sec:authorize>
    </div>
  </div>
</nav>

<!-- Bootstrap JS (colapsar menu, etc.) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
