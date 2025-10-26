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
  <!-- Bootstrap Icons -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

  <style>
    body { font-size: .875rem; }
    .form-label { font-weight: 600; color: #565656; }
    .card { border: 0; border-radius: 1rem; box-shadow: 0 6px 24px rgba(0,0,0,.06); }
    .muted-box { background: #f8f9fa; border: 1px solid #e9ecef; }
    .page-title { letter-spacing: .2px; }

    /* --- FIX: Tabs + Card borda integrada --- */
    .nav-tabs { border-bottom: 0; margin-bottom: 0; }
    .nav-tabs .nav-link { border: 1px solid transparent; border-top-left-radius: 1rem; border-top-right-radius: 1rem; }
    .nav-tabs .nav-link.active {
      background-color: #fff;
      border-color: var(--bs-border-color) var(--bs-border-color) #fff;
    }
    .tabbed-wrapper .card { border: 0; border-radius: 1rem; box-shadow: none; }
    .tabbed-wrapper .tab-content {
      border: 1px solid var(--bs-border-color);
      border-top: 0;
      border-bottom-left-radius: 1rem;
      border-bottom-right-radius: 1rem;
      box-shadow: 0 6px 24px rgba(0,0,0,.06);
      background: #fff;
    }

    /* --- PADRÃO DE FORMULÁRIO COMPACTO (10~12px) --- */
    .form-id { width: auto; text-align: end; max-width: fit-content; }
    .form-date { width: auto; text-align: end; max-width: fit-content; }
    .form-compact { font-size: .7rem; } /* ~11.2px */
    .form-compact .col-form-label { margin-bottom: 0; color: #565656; } /* grudar no campo */
    .form-compact .form-control,
    .form-compact .form-select {
      min-width: 10px !important;  /* para inputs curtos */
      color: #565656;
      font-size: .7rem;
      padding: .25rem .5rem;           /* aperta altura horizontal/vertical */
      height: calc(1.5em + .5rem + 2px);
      line-height: 1.4;
      border-radius: .4rem;
    }
    .form-compact .form-text,
    .form-compact .invalid-feedback { font-size: .7rem; }

    .btnn {
        min-width: 70px;
        font-size: .7rem;
        text-decoration: none;
        padding: .375rem .75rem;
        border-radius: .4rem;
        background-color: #fefefe;
        color: #565656;
        border: 1px solid #565656;
    }

    /* Gutter curto para deixar label e campo bem próximos */
    .form-compact .row.field-row { --bs-gutter-x: .5rem; }

    /* Largura consistente do bloco de entrada em telas médias+ (opcional) */
    @media (min-width: 768px) {
      .form-compact .control-col { max-width: 420px; }
    }

    /* Estilos adicionais para lista de usuários */
    .badge-sm {
      font-size: 0.6rem;
      padding: 0.2rem 0.4rem;
    }
    
    .fs-7 {
      font-size: 0.7rem;
    }
    
    .list-group-item {
      border-left: 3px solid transparent;
      transition: all 0.2s ease;
    }
    
    .list-group-item:hover {
      border-left-color: var(--bs-primary);
      background-color: var(--bs-light);
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

    <!-- Nav Tabs -->
    <ul class="nav nav-tabs" id="configTabs" role="tablist">
      <li class="nav-item" role="presentation">
        <button
          class="nav-link ${activeTab eq 'usuarios' ? '' : 'active'}"
          id="tab-geral-tab"
          data-bs-toggle="tab"
          data-bs-target="#tab-geral"
          type="button" role="tab"
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
          type="button" role="tab"
          aria-controls="tab-usuarios"
          aria-selected="${activeTab eq 'usuarios' ? 'true' : 'false'}">
          Usuários
        </button>
      </li>
    </ul>

    <div class="card">
      <div class="tab-content p-3 p-md-4" id="configTabsContent">
        <!-- TAB: GERAL -->
        <div class="tab-pane fade ${activeTab eq 'usuarios' ? '' : 'show active'}" id="tab-geral" role="tabpanel" aria-labelledby="tab-geral-tab" tabindex="0">

          <!-- Form principal (COMPACTO) -->
          <form action="<c:url value='/app/config/save-company'/>" method="post" class="needs-validation form-compact" novalidate>
            <!-- CSRF -->
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <!-- Ações do formulário -->
            <div class="d-flex align-items-center gap-2 mb-3">
              <a class="btnn" href="/app">Cancelar</a>
              <button name="submit" id="submit" class="btnn" type="submit">Salvar</button>
            </div>
            <hr />

            <!-- Campos (labels à direita e campos verticalmente alinhados) -->
            <!-- ID -->
            <div class="row field-row align-items-center mb-2">
              <label for="companyId" class="col-12 col-sm-3 col-form-label text-sm-end pe-sm-2">ID</label>
              <div class="col-12 col-sm-9 control-col">
                <input id="companyId" class="form-id form-control muted-box text-end" type="text" value="${company.id}" readonly disabled>
              </div>
            </div>

            <!-- Nome da empresa -->
            <div class="row field-row align-items-center mb-2">
              <label for="name" class="col-12 col-sm-3 col-form-label text-sm-end pe-sm-2">Nome da Empresa</label>
              <div class="col-12 col-sm-9 control-col">
                <input id="name" name="name" class="form-control" type="text" placeholder="Razão social" value="${company.name}" required>
                <div class="invalid-feedback">Informe o nome da empresa.</div>
              </div>
            </div>

            <!-- CNPJ -->
            <div class="row field-row align-items-center mb-2">
              <label for="cnpj" class="col-12 col-sm-3 col-form-label text-sm-end pe-sm-2">CNPJ</label>
              <div class="col-12 col-sm-9 control-col">
                <input id="cnpj" name="cnpj" class="form-control" type="text" placeholder="00.000.000/0000-00" value="${company.cnpj}" required>
                <div class="invalid-feedback">Informe um CNPJ válido.</div>
              </div>
            </div>

            <!-- Criada em -->
            <div class="row field-row align-items-center mb-2">
              <label for="createdAt" class="col-12 col-sm-3 col-form-label text-sm-end pe-sm-2">Criada em</label>
              <div class="col-12 col-sm-9 control-col">
                <input id="createdAt" class="form-date form-control muted-box" type="text"
                       value="<fmt:formatDate value='${createdAtDate}' pattern='dd/MM/yyyy HH:mm:ss'/>" readonly disabled>
              </div>
            </div>
          </form>
        </div>

        <!-- TAB: USUÁRIOS -->
        <div class="tab-pane fade ${activeTab eq 'usuarios' ? 'show active' : ''}" id="tab-usuarios" role="tabpanel" aria-labelledby="tab-usuarios-tab" tabindex="0">
          
          <!-- Lista simples de usuários sem paginação por enquanto -->
          <div class="d-flex align-items-center justify-content-start mb-3">
            
            <button class="btnn" onclick="window.location.href='/app/users/new'">
              Novo
            </button>
          </div>

          <c:choose>
            <c:when test="${empty usersAndPagination.users}">
              <div class="text-center py-4 text-muted">
                <p class="mb-0">Nenhum usuário encontrado</p>
              </div>
            </c:when>
            <c:otherwise>
              <div class="list-group form-compact">
                <c:forEach var="user" items="${usersAndPagination.users}" varStatus="status">
                  <div class="list-group-item d-flex justify-content-between align-items-center">
                    <div class="d-flex align-items-center">
                      <!-- Avatar ou ícone -->
                      <div class="me-3">
                        <div class="bg-light rounded-circle d-flex align-items-center justify-content-center" 
                             style="width: 32px; height: 32px;">
                          <i class="bi bi-person-fill text-muted"></i>
                        </div>
                      </div>
                      
                      <!-- Informações do usuário -->
                      <div>
                        <div class="fw-bold fs-6">
                          ${user.username}
                        </div>
                        
                        <div class="d-flex align-items-center gap-2 fs-7">
                          <!-- Status -->
                          <c:choose>
                            <c:when test="${user.enabled}">
                              <span class="badge bg-success badge-sm">Ativo</span>
                            </c:when>
                            <c:otherwise>
                              <span class="badge bg-secondary badge-sm">Inativo</span>
                            </c:otherwise>
                          </c:choose>
                        </div>
                      </div>
                    </div>

                    <!-- Ações -->
                    <div class="btn-group" role="group">
                      <a href="/app/users/edit/${user.id}" class="btn btn-sm btn-outline-primary">
                        <i class="bi bi-pencil"></i>
                      </a>
                      <c:if test="${user.enabled}">
                        <button class="btn btn-sm btn-outline-warning" 
                                onclick="toggleUserStatus(${user.id}, false)">
                          <i class="bi bi-pause-circle"></i>
                        </button>
                      </c:if>
                      <c:if test="${not user.enabled}">
                        <button class="btn btn-sm btn-outline-success" 
                                onclick="toggleUserStatus(${user.id}, true)">
                          <i class="bi bi-play-circle"></i>
                        </button>
                      </c:if>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:otherwise>
          </c:choose>

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
      $("#name").on("input", function() { this.value = this.value.toUpperCase(); });

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
      const hash = url.hash;
      const qpTab = url.searchParams.get('tab');

      if (hash === '#tab-usuarios' || qpTab === 'usuarios') {
        tabTrigger('#tab-usuarios');
      } else {
        tabTrigger('#tab-geral');
      }

      // Ao trocar de aba, atualiza hash e querystring ?tab=...
      tabElList.forEach(tabBtn => {
        tabBtn.addEventListener('shown.bs.tab', (event) => {
          const target = event.target.getAttribute('data-bs-target');
          const name = target === '#tab-usuarios' ? 'usuarios' : 'geral';
          const u = new URL(window.location.href);
          u.hash = target;
          u.searchParams.set('tab', name);
          window.history.replaceState({}, '', u);
        });
      });

      // Função para alterar status do usuário
      window.toggleUserStatus = function(userId, enable) {
        const action = enable ? 'ativar' : 'desativar';
        if (confirm(`Deseja ${action} este usuário?`)) {
          // Implementar chamada AJAX aqui futuramente
          alert(`Funcionalidade de ${action} usuário será implementada em breve.`);
        }
      };
    })();
  </script>
</body>
</html>
