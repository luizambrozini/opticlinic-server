<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"  %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Configurações da Empresa - Opticlinic</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    /* Tipografia mais compacta – alinhado ao padrão que você gostou */
    body { font-size: .875rem; }
    .form-label { font-weight: 600; }
    .card { border: 0; border-radius: 1rem; box-shadow: 0 6px 24px rgba(0,0,0,.06); }
    .muted-box { background: #f8f9fa; border: 1px solid #e9ecef; }
    .page-title { letter-spacing: .2px; }
  </style>
</head>
<body>
  <%@ include file="/WEB-INF/jsp/fragments/navbar.jsp" %>

  <div class="container py-4">
    <!-- Cabeçalho + ações -->
    <div class="d-flex flex-wrap align-items-center justify-content-between gap-3 mb-3">
      <div>
        <h1 class="h4 page-title mb-0">Configurações da Empresa</h1>
        <small class="text-muted">Gerencie as informações institucionais exibidas no sistema.</small>
      </div>
      <div class="d-flex gap-2">
        <!-- Botões estão dentro do <form> mais abaixo; estes aqui são opcionais -->
      </div>
    </div>

    <!-- Feedback (opcional): usa query params ?ok=1 ou ?err=mensagem -->
    <c:if test="${param.ok ne null}">
      <div class="alert alert-success py-2">Configurações salvas com sucesso.</div>
    </c:if>
    <c:if test="${param.err eq 'CD01'}">
        <div class="alert alert-danger py-2"><strong>Erro:</strong> Cnpj inválido.</div>
    </c:if>

    <div class="card">
      <div class="card-body p-4 p-md-5">
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

            <!-- Criada em (somente leitura, formatada) -->
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
                     value="${company.cnpj}"
                     required>
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
            <button class="btn btn-primary" type="submit">Salvar alterações</button>
          </div>
        </form>
        <hr />
      </div>

    </div>
  </div>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Validação client-side Bootstrap -->
  <script>
    (() => {
      'use strict';
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
    })();
  </script>
</body>
</html>
