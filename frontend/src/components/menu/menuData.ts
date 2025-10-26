import type { MenuSection } from "./types";

export const menuStructure: MenuSection[] = [
  {
    id: "dashboard",
    title: "Painel Principal",
    items: [
      { id: "overview", label: "Visão Geral", path: "/dashboard" },
      { id: "analytics", label: "Análises", path: "/dashboard/analytics" },
      { id: "reports", label: "Relatórios", path: "/dashboard/reports" },
    ],
  },
  {
    id: "patients",
    title: "Gestão de Pacientes",
    items: [
      { id: "patient-list", label: "Lista de Pacientes", path: "/pacientes" },
      {
        id: "patient-add",
        label: "Cadastrar Paciente",
        path: "/pacientes/novo",
      },
      {
        id: "patient-records",
        label: "Prontuários",
        items: [
          {
            id: "medical-history",
            label: "Histórico Médico",
            path: "/prontuarios/historico",
          },
          {
            id: "prescriptions",
            label: "Receitas",
            path: "/prontuarios/receitas",
          },
          { id: "exams", label: "Exames", path: "/prontuarios/exames" },
        ],
      },
    ],
  },
  {
    id: "appointments",
    title: "Agendamentos",
    items: [
      { id: "schedule", label: "Agenda", path: "/agenda" },
      { id: "new-appointment", label: "Nova Consulta", path: "/agenda/nova" },
      { id: "calendar", label: "Calendário", path: "/agenda/calendario" },
      {
        id: "appointment-types",
        label: "Tipos de Consulta",
        items: [
          {
            id: "routine",
            label: "Consulta de Rotina",
            path: "/consultas/rotina",
          },
          {
            id: "emergency",
            label: "Emergência",
            path: "/consultas/emergencia",
          },
          { id: "followup", label: "Retorno", path: "/consultas/retorno" },
        ],
      },
    ],
  },
  {
    id: "clinical",
    title: "Serviços Clínicos",
    items: [
      {
        id: "professionals",
        label: "Profissionais",
        path: "/profissionais",
      },
      {
        id: "clinic",
        label: "Clínica",
        items: [
          {
            id: "frames",
            label: "Procedimentos",
            path: "/clinica/procedimentos",
          },
          { id: "lenses", label: "Exames", path: "/clinica/lentes" },
        ],
      },
    ],
  },
  {
    id: "inventory",
    title: "Estoque",
    items: [
      { id: "products", label: "Produtos", path: "/estoque/produtos" },
      { id: "suppliers", label: "Fornecedores", path: "/estoque/fornecedores" },
      { id: "orders", label: "Pedidos", path: "/estoque/pedidos" },
      {
        id: "categories",
        label: "Categorias",
        items: [
          { id: "frames-cat", label: "Armações", path: "/categorias/armacoes" },
          { id: "lenses-cat", label: "Lentes", path: "/categorias/lentes" },
          {
            id: "accessories",
            label: "Acessórios",
            path: "/categorias/acessorios",
          },
        ],
      },
    ],
  },
  {
    id: "financial",
    title: "Financeiro",
    items: [
      { id: "billing", label: "Faturamento", path: "/financeiro/faturamento" },
      { id: "payments", label: "Pagamentos", path: "/financeiro/pagamentos" },
      { id: "insurance", label: "Convênios", path: "/financeiro/convenios" },
      {
        id: "reports-financial",
        label: "Relatórios",
        items: [
          { id: "revenue", label: "Receitas", path: "/relatorios/receitas" },
          { id: "expenses", label: "Despesas", path: "/relatorios/despesas" },
          { id: "profit", label: "Lucros", path: "/relatorios/lucros" },
        ],
      },
    ],
  },
  {
    id: "settings",
    title: "Configurações",
    items: [
      { id: "general", label: "Geral", path: "/configuracoes/geral" },
      { id: "users", label: "Usuários", path: "/configuracoes/usuarios" },
      {
        id: "permissions",
        label: "Permissões",
        path: "/configuracoes/permissoes",
      },
      {
        id: "system",
        label: "Sistema",
        items: [
          { id: "backup", label: "Backup", path: "/sistema/backup" },
          { id: "logs", label: "Logs", path: "/sistema/logs" },
          {
            id: "maintenance",
            label: "Manutenção",
            path: "/sistema/manutencao",
          },
        ],
      },
    ],
  },
];
