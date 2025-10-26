<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<%--
  Fragment: User DataGrid
  
  Parâmetros esperados:
  - users: List<User> - lista de usuários
  - pagination: objeto com informações de paginação (currentPage, totalPages, totalElements, etc.)
  - baseUrl: String - URL base para navegação e ações (ex: "/app/users")
  - showActions: Boolean - se deve mostrar coluna de ações (default: true)
  - compact: Boolean - se deve usar estilo compacto (default: false)
--%>

<c:set var="showActions" value="${empty showActions ? true : showActions}" />
<c:set var="compact" value="${empty compact ? false : compact}" />
<c:set var="baseUrl" value="${empty baseUrl ? '/app/users' : baseUrl}" />

<div class="user-datagrid ${compact ? 'form-compact' : ''}">
  <!-- Cabeçalho com contadores e ação de novo usuário -->
  <div class="d-flex align-items-center justify-content-between mb-3">
    <div>
      <h5 class="mb-1">Usuários</h5>
      <c:if test="${not empty pagination}">
        <small class="text-muted">
          Mostrando ${pagination.currentPage * pagination.size + 1} - 
          ${pagination.currentPage * pagination.size + fn:length(users)} de ${pagination.totalElements} usuários
        </small>
      </c:if>
    </div>
    <c:if test="${showActions}">
      <button class="btn ${compact ? 'btn-sm' : ''} btn-outline-success" 
              onclick="window.location.href='${baseUrl}/new'">
        <i class="bi bi-plus-circle me-1"></i>Novo Usuário
      </button>
    </c:if>
  </div>

  <!-- Lista de usuários -->
  <c:choose>
    <c:when test="${empty users}">
      <div class="text-center py-4 text-muted">
        <i class="bi bi-person-x fs-1 d-block mb-2"></i>
        <p class="mb-0">Nenhum usuário encontrado</p>
      </div>
    </c:when>
    <c:otherwise>
      <div class="list-group">
        <c:forEach var="user" items="${users}" varStatus="status">
          <div class="list-group-item d-flex justify-content-between align-items-center">
            <div class="d-flex align-items-center">
              <!-- Avatar ou ícone -->
              <div class="me-3">
                <div class="bg-light rounded-circle d-flex align-items-center justify-content-center" 
                     style="width: ${compact ? '32px' : '40px'}; height: ${compact ? '32px' : '40px'};">
                  <i class="bi bi-person-fill text-muted"></i>
                </div>
              </div>
              
              <!-- Informações do usuário -->
              <div>
                <div class="fw-bold ${compact ? 'fs-6' : ''}">
                  ${user.username}
                  <c:if test="${not empty user.fullName}">
                    <small class="text-muted fw-normal">(${user.fullName})</small>
                  </c:if>
                </div>
                
                <div class="d-flex align-items-center gap-2 ${compact ? 'fs-7' : 'small'}">
                  <!-- Status -->
                  <c:choose>
                    <c:when test="${user.enabled}">
                      <span class="badge bg-success ${compact ? 'badge-sm' : ''}">Ativo</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-secondary ${compact ? 'badge-sm' : ''}">Inativo</span>
                    </c:otherwise>
                  </c:choose>
                  
                  <!-- Roles/Authorities -->
                  <c:if test="${not empty user.authorities}">
                    <c:forEach var="authority" items="${user.authorities}" varStatus="authStatus">
                      <span class="badge bg-light text-dark ${compact ? 'badge-sm' : ''}">
                        ${fn:replace(authority.authority, 'ROLE_', '')}
                      </span>
                    </c:forEach>
                  </c:if>
                  
                  <!-- Data de criação -->
                  <c:if test="${not empty user.createdAt}">
                    <span class="text-muted">
                      <i class="bi bi-calendar3 me-1"></i>
                      <fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy" />
                    </span>
                  </c:if>
                </div>
              </div>
            </div>

            <!-- Ações -->
            <c:if test="${showActions}">
              <div class="btn-group" role="group">
                <a href="${baseUrl}/edit/${user.id}" 
                   class="btn ${compact ? 'btn-sm' : ''} btn-outline-primary">
                  <i class="bi bi-pencil"></i>
                  <c:if test="${not compact}"> Editar</c:if>
                </a>
                <c:if test="${user.enabled}">
                  <button class="btn ${compact ? 'btn-sm' : ''} btn-outline-warning" 
                          onclick="toggleUserStatus(${user.id}, false)">
                    <i class="bi bi-pause-circle"></i>
                    <c:if test="${not compact}"> Desativar</c:if>
                  </button>
                </c:if>
                <c:if test="${not user.enabled}">
                  <button class="btn ${compact ? 'btn-sm' : ''} btn-outline-success" 
                          onclick="toggleUserStatus(${user.id}, true)">
                    <i class="bi bi-play-circle"></i>
                    <c:if test="${not compact}"> Ativar</c:if>
                  </button>
                </c:if>
              </div>
            </c:if>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>

  <!-- Paginação -->
  <c:if test="${not empty pagination and pagination.totalPages > 1}">
    <nav aria-label="Navegação de usuários" class="mt-3">
      <ul class="pagination ${compact ? 'pagination-sm' : ''} justify-content-center">
        <!-- Primeira página -->
        <c:if test="${pagination.currentPage > 0}">
          <li class="page-item">
            <a class="page-link" href="${baseUrl}?page=0&size=${pagination.size}">
              <i class="bi bi-chevron-double-left"></i>
            </a>
          </li>
        </c:if>

        <!-- Página anterior -->
        <c:if test="${pagination.currentPage > 0}">
          <li class="page-item">
            <a class="page-link" href="${baseUrl}?page=${pagination.currentPage - 1}&size=${pagination.size}">
              <i class="bi bi-chevron-left"></i>
            </a>
          </li>
        </c:if>

        <!-- Páginas numeradas -->
        <c:set var="startPage" value="${pagination.currentPage - 2 < 0 ? 0 : pagination.currentPage - 2}" />
        <c:set var="endPage" value="${pagination.currentPage + 2 >= pagination.totalPages ? pagination.totalPages - 1 : pagination.currentPage + 2}" />
        
        <c:forEach var="i" begin="${startPage}" end="${endPage}">
          <li class="page-item ${i == pagination.currentPage ? 'active' : ''}">
            <a class="page-link" href="${baseUrl}?page=${i}&size=${pagination.size}">
              ${i + 1}
            </a>
          </li>
        </c:forEach>

        <!-- Próxima página -->
        <c:if test="${pagination.currentPage < pagination.totalPages - 1}">
          <li class="page-item">
            <a class="page-link" href="${baseUrl}?page=${pagination.currentPage + 1}&size=${pagination.size}">
              <i class="bi bi-chevron-right"></i>
            </a>
          </li>
        </c:if>

        <!-- Última página -->
        <c:if test="${pagination.currentPage < pagination.totalPages - 1}">
          <li class="page-item">
            <a class="page-link" href="${baseUrl}?page=${pagination.totalPages - 1}&size=${pagination.size}">
              <i class="bi bi-chevron-double-right"></i>
            </a>
          </li>
        </c:if>
      </ul>
    </nav>

    <!-- Info da paginação -->
    <div class="text-center mt-2">
      <small class="text-muted">
        Página ${pagination.currentPage + 1} de ${pagination.totalPages}
        (${pagination.totalElements} usuários no total)
      </small>
    </div>
  </c:if>
</div>

<style>
  .user-datagrid .badge-sm {
    font-size: 0.6rem;
    padding: 0.2rem 0.4rem;
  }
  
  .user-datagrid .fs-7 {
    font-size: 0.7rem;
  }
  
  .user-datagrid .list-group-item {
    border-left: 3px solid transparent;
    transition: all 0.2s ease;
  }
  
  .user-datagrid .list-group-item:hover {
    border-left-color: var(--bs-primary);
    background-color: var(--bs-light);
  }
</style>

<script>
// Função para alterar status do usuário
function toggleUserStatus(userId, enable) {
  const action = enable ? 'ativar' : 'desativar';
  if (confirm(`Deseja ${action} este usuário?`)) {
    // Aqui você pode implementar a chamada AJAX ou redirecionar para uma URL
    fetch(`${baseUrl}/toggle-status/${userId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
      },
      body: JSON.stringify({ enabled: enable })
    })
    .then(response => {
      if (response.ok) {
        location.reload();
      } else {
        alert('Erro ao alterar status do usuário');
      }
    })
    .catch(error => {
      console.error('Erro:', error);
      alert('Erro ao alterar status do usuário');
    });
  }
}
</script>