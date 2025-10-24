<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Configurações da Empresa - Opticlinic</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body { font-size: .875rem; }
    .form-label { font-weight: 600; }
    .card { border: 0; border-radius: 1rem; box-shadow: 0 6px 24px rgba(0,0,0,.06); }
    .muted-box { background: #f8f9fa; border: 1px solid #e9ecef; }
    .page-title { letter-spacing: .2px; }

    /* --- FIX: Tabs + Card borda integrada --- */
    /* Remove a borda inferior padrão das tabs para evitar “linha dupla” */
    .nav-tabs { border-bottom: 0; margin-bottom: 0; }

    /* Aba ativa: borda nas laterais e topo; embaixo “abre” para unir com o conteúdo */
    .nav-tabs .nav-link {
      border: 1px solid transparent;
      border-top-left-radius: 1rem;
      border-top-right-radius: 1rem;
    }
    .nav-tabs .nav-link.active {
      background-color: #fff;
      border-color: var(--bs-border-color) var(--bs-border-color) #fff; /* top/left/right com borda; bottom “some” */
    }

    /* O card vira apenas “container de sombra/raio”, sem borda própria */
    .tabbed-wrapper .card {
      border: 0;
      border-radius: 1rem;     /* mantém o raio geral */
      box-shadow: none;        /* a sombra vai para o conteúdo para não “quebrar” no topo */
    }

    /* A borda fica na área de conteúdo das abas */
    .tabbed-wrapper .tab-content {
      border: 1px solid var(--bs-border-color);
      border-top: 0; /* encaixa com a aba ativa */
      border-bottom-left-radius: 1rem;
      border-bottom-right-radius: 1rem;
      box-shadow: 0 6px 24px rgba(0,0,0,.06); /* sombra contínua só no bloco de conteúdo */
      background: #fff;
    }
  </style>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/fragments/navbar.jsp" %>

  <div class="container py-4 tabbed-wrapper">
    <!-- Cabeçalho + ações -->
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
      <div>
        <h1 class="h4 page-title mb-0">Configurações da Empresa</h1>
        <small class="text-muted">Gerencie as informações institucionais exibidas no sistema.</small>
      </div>
    </div>

    <!-- Feedback (opcional) -->
    <c:if test="${param.ok ne null}">
      <div class="alert alert-success py-2">Configurações salvas com sucesso.</div>
    </c:if>
    <c:if test="${param.err eq 'CD01'}">
      <div class="alert alert-danger py-2"><strong>Erro:</strong> Cnpj inválido.</div>
    </c:if>

    <!-- Define aba ativa no primeiro load (server-side) -->
    <c:set var="activeTab" value="${empty param.tab ? 'geral' : param.tab}" />

    <!-- Nav Tabs (mesmo arquivo) -->
    <ul class="nav nav-tabs" id="configTabs" role="tablist">
      <li class="nav-item" role="presentation">
        <button
          class="nav-link ${activeTab eq 'usuarios' ? '' : 'active'}"
          id="tab-geral-tab"
          data-bs-toggle="tab"
          data-bs-target="#tab-geral"
          type="button"
          role="tab"
          aria-controls="tab-geral"
          aria-selected="${activeTab eq 'usuarios' ? 'false' : 'true'}">
          Geral
        </button>
      </li>
      <li class="nav-item" role="presentation">
        <button
          class="nav-link ${activeTab eq 'usuarios' ? 'active' : ''}"
          id="tab-usuarios-tab"
          data-bs-toggle="tab"
          data-bs-target="#tab-usuarios"
          type="button"
          role="tab"
          aria-controls="tab-usuarios"
          aria-selected="${activeTab eq 'usuarios' ? 'true' : 'false'}">
          Usuários
        </button>
      </li>
    </ul>

    <div class="card">
      <!-- Removi as classes de borda do card-body e passei o padding para a tab-content -->
      <div class="tab-content p-4 p-md-5" id="configTabsContent">
        <!-- TAB: GERAL -->
        <div
          class="tab-pane fade ${activeTab eq 'usuarios' ? '' : 'show active'}"
          id="tab-geral"
          role="tabpanel"
          aria-labelledby="tab-geral-tab"
          tabindex="0">

          <!-- Form principal -->
          <form action="<c:url value='/app/config/save-company'/>" method="post" class="needs-validation" novalidate>
            <!-- CSRF -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="row g-3">
              <!-- ID (somente leitura) -->
              <div class="col-12 col-md-3">
                <label class="form-label" for="companyId">ID</label>
                <input id="companyId" class="form-control muted-box" type="text" disabled
                       value="${company.id}" readonly>
              </div>

              <!-- Criada em (somente leitura) -->
              <div class="col-12 col-md-4">
                <label class="form-label" for="createdAt">Criada em</label>
                <input id="createdAt" class="form-control muted-box" type="text" disabled
                       value="<fmt:formatDate value='${createdAtDate}' pattern='dd/MM/yyyy HH:mm'/>" readonly>
              </div>

              <!-- CNPJ -->
              <div class="col-12 col-md-5">
                <label class="form-label" for="cnpj">CNPJ</label>
                <input id="cnpj" name="cnpj" class="form-control" type="text"
                       placeholder="00.000.000/0000-00"
                       value="${company.cnpj}" required>
                <div class="invalid-feedback">Informe um CNPJ válido.</div>
              </div>

              <!-- Nome da empresa -->
              <div class="col-12">
                <label class="form-label" for="name">Nome da Empresa</label>
                <input id="name" name="name" class="form-control" type="text"
                       placeholder="Razão social"
                       value="${company.name}" required>
                <div class="invalid-feedback">Informe o nome da empresa.</div>
              </div>
            </div>

            <!-- Ações -->
            <div class="d-flex align-items-center justify-content-end gap-2 mt-4">
              <a class="btn btn-outline-secondary" href="/app">Cancelar</a>
              <button name="submit" id="submit" class="btn btn-primary" type="submit">Salvar alterações</button>
            </div>
          </form>
        </div>

        <!-- TAB: USUÁRIOS -->
        <div
          class="tab-pane fade ${activeTab eq 'usuarios' ? 'show active' : ''}"
          id="tab-usuarios"
          role="tabpanel"
          aria-labelledby="tab-usuarios-tab"
          tabindex="0">

          <div class="d-flex align-items-center justify-content-between">
            <h5 class="mb-3">Usuários</h5>
            <button class="btn btn-sm btn-outline-success mb-3" onclick="window.location.href='/app/users/new'">
              Adicionar novo usuário
            </button>
          </div>

          <ul class="list-group">
            <c:forEach var="user" items="${usersAndPagination.users}">
              <li class="list-group-item d-flex justify-content-between align-items-center">
                <span>
                  <strong>${user.username}</strong>
                  <c:if test="${user.enabled}">
                    <span class="badge bg-success rounded-pill">Ativo</span>
                  </c:if>
                  <c:if test="${not user.enabled}">
                    <span class="badge bg-secondary rounded-pill">Inativo</span>
                  </c:if>
                </span>
                <span>
                  <a href="/app/users/edit/${user.id}" class="btn btn-sm btn-outline-primary">Editar</a>
                </span>
              </li>
            </c:forEach>
          </ul>

        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap JS + jQuery -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

  <!-- Validação client-side + Uppercase nome + Controle de aba ativa -->
  <script>
    (() => {
      'use strict';

      // Uppercase em tempo real
      $("#name").on("input", function() {
        this.value = this.value.toUpperCase();
      });

      // Bootstrap validation
      const forms = document.querySelectorAll('.needs-validation');
      Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
          if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false);
      });

      // --- Controle da aba ativa (cliente) ---
      const tabElList = [].slice.call(document.querySelectorAll('#configTabs button[data-bs-toggle="tab"]'));
      const tabTrigger = (id) => {
        const triggerEl = document.querySelector(`[data-bs-target="${id}"]`);
        if (triggerEl) new bootstrap.Tab(triggerEl).show();
      };

      const url = new URL(window.location.href);
      const hash = url.hash; // ex: #tab-usuarios
      const qpTab = url.searchParams.get('tab'); // ex: usuarios

      if (hash === '#tab-usuarios' || qpTab === 'usuarios') {
        tabTrigger('#tab-usuarios');
      } else {
        tabTrigger('#tab-geral');
      }

      // Ao trocar de aba, atualiza hash e querystring ?tab=...
      tabElList.forEach(tabBtn => {
        tabBtn.addEventListener('shown.bs.tab', (event) => {
          const target = event.target.getAttribute('data-bs-target'); // ex: #tab-usuarios
          const name = target === '#tab-usuarios' ? 'usuarios' : 'geral';
          const u = new URL(window.location.href);
          u.hash = target;
          u.searchParams.set('tab', name);
          window.history.replaceState({}, '', u);
        });
      });
    })();
  </script>
</body>
</html>
