<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Login - Opticlinic</title>

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

  <style>
    body {
        font-size: 0.75rem;
      min-height: 100vh;
      background: radial-gradient(1200px 800px at 10% 10%, #e8f0ff 0%, #f6f8fc 40%, #ffffff 100%);
    }
    .brand-circle {
      width: 44px; height: 44px;
      border-radius: 50%;
      background: #0d6efd10;
      display: inline-flex; align-items: center; justify-content: center;
      color: #0d6efd; font-weight: 700;
      border: 1px solid #0d6efd22;
    }
    h1.h4 {
      font-size: 1.25rem; /* título "Opticlinic" menor */
    }

    p.text-muted {
      font-size: 0.75rem;
    }

    .form-control-lg,
    .input-group-lg > .form-control,
    .input-group-lg > .btn {
      font-size: 0.75rem; /* campos e botão "mostrar" menores */
    }

    .btn-lg {
      font-size: 0.75rem; /* botão "Entrar" mais compacto */
      padding: 0.6rem 1rem;
    }
  </style>
</head>
<body class="d-flex align-items-center justify-content-center">

  <main class="container">
    <div class="row justify-content-center">
        <div class="w-50 shadow-sm border-0 rounded-4">
          <div class="card-body p-4 p-md-5">

            <!-- Brand / título -->
            <div class="d-flex align-items-center gap-2 mb-3">
              <span class="brand-circle">O</span>
              <h1 class="h4 mb-0 fw-semibold">Opticlinic</h1>
            </div>
            <p class="text-muted mb-4">Acesse sua conta para continuar.</p>

            <!-- Feedback de erro / logout -->
            <c:if test="${param.error ne null}">
              <div class="alert alert-danger py-2" role="alert">
                Usuário ou senha inválidos.
              </div>
            </c:if>
            <c:if test="${param.logout ne null}">
              <div class="alert alert-success py-2" role="alert">
                Você saiu da sessão com sucesso.
              </div>
            </c:if>

            <!-- Form -->
            <form action="<c:url value='/app/auth/login'/>" method="post" class="needs-validation" novalidate>
              <!-- CSRF (mantendo CSRF habilitado na cadeia web) -->
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

              <div class="mb-3">
                <label for="username" class="form-label">Usuário</label>
                <input type="text" class="form-control form-control-lg" id="username" name="username"
                       placeholder="seu.usuario" required autofocus>
                <div class="invalid-feedback">Informe o usuário.</div>
              </div>

              <div class="mb-2">
                <label for="password" class="form-label">Senha</label>
                <div class="input-group input-group-lg">
                  <input type="password" class="form-control" id="password" name="password"
                         placeholder="••••••••" required>
                  <button class="btn btn-outline-secondary" type="button" id="togglePwd">Mostrar</button>
                  <div class="invalid-feedback">Informe a senha.</div>
                </div>
              </div>

              <div class="d-flex align-items-center justify-content-between mb-4">
                <div class="form-check">
                  <input class="form-check-input" type="checkbox" value="on" id="rememberMe" name="remember-me">
                  <label class="form-check-label" for="rememberMe">Lembrar-me</label>
                </div>
                <!-- opcional: link de ajuda -->
                <a class="small text-decoration-none" href="#">Esqueci minha senha</a>
              </div>

              <button class="btn btn-primary btn-lg w-100" type="submit">Entrar</button>
            </form>

          </div>
        </div>

        <p class="text-center text-muted small mt-3 mb-0">
          © <script>document.write(new Date().getFullYear())</script> Opticlinic
        </p>
      </div>
  </main>

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Validação simples + mostrar/ocultar senha -->
  <script>
    (function () {
      'use strict';
      const forms = document.querySelectorAll('.needs-validation');
      Array.prototype.slice.call(forms).forEach(function (form) {
        form.addEventListener('submit', function (event) {
          if (!form.checkValidity()) {
            event.preventDefault(); event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false);
      });

      const btn = document.getElementById('togglePwd');
      const pwd = document.getElementById('password');
      if (btn && pwd) {
        btn.addEventListener('click', function () {
          const isText = pwd.type === 'text';
          pwd.type = isText ? 'password' : 'text';
          btn.textContent = isText ? 'Mostrar' : 'Ocultar';
        });
      }
    })();
  </script>
</body>
</html>
