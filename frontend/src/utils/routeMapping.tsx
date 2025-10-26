import DashboardPage from "../pages/workspace/DashboardPage";
import PacientesPage from "../pages/workspace/PacientesPage";
import AgendaPage from "../pages/workspace/AgendaPage";

// Páginas placeholder para outras rotas
const PlaceholderPage = ({ title }: { title: string }) => (
  <div className="p-6">
    <div className="bg-white rounded-lg shadow border border-gray-200 p-8 text-center">
      <h1 className="text-2xl font-bold text-gray-900 mb-4">{title}</h1>
      <p className="text-gray-600">Esta página está em desenvolvimento.</p>
    </div>
  </div>
);

export const routeComponents: Record<
  string,
  { component: React.ComponentType; title: string }
> = {
  "/dashboard": { component: DashboardPage, title: "Dashboard" },
  "/dashboard/analytics": {
    component: () => <PlaceholderPage title="Análises" />,
    title: "Análises",
  },
  "/dashboard/reports": {
    component: () => <PlaceholderPage title="Relatórios" />,
    title: "Relatórios",
  },

  "/pacientes": { component: PacientesPage, title: "Lista de Pacientes" },
  "/pacientes/novo": {
    component: () => <PlaceholderPage title="Cadastrar Paciente" />,
    title: "Cadastrar Paciente",
  },

  "/prontuarios/historico": {
    component: () => <PlaceholderPage title="Histórico Médico" />,
    title: "Histórico Médico",
  },
  "/prontuarios/receitas": {
    component: () => <PlaceholderPage title="Receitas" />,
    title: "Receitas",
  },
  "/prontuarios/exames": {
    component: () => <PlaceholderPage title="Exames" />,
    title: "Exames",
  },

  "/agenda": { component: AgendaPage, title: "Agenda" },
  "/agenda/nova": {
    component: () => <PlaceholderPage title="Nova Consulta" />,
    title: "Nova Consulta",
  },
  "/agenda/calendario": {
    component: () => <PlaceholderPage title="Calendário" />,
    title: "Calendário",
  },

  "/consultas/rotina": {
    component: () => <PlaceholderPage title="Consulta de Rotina" />,
    title: "Consulta de Rotina",
  },
  "/consultas/emergencia": {
    component: () => <PlaceholderPage title="Emergência" />,
    title: "Emergência",
  },
  "/consultas/retorno": {
    component: () => <PlaceholderPage title="Retorno" />,
    title: "Retorno",
  },

  "/exames": {
    component: () => <PlaceholderPage title="Exames Visuais" />,
    title: "Exames Visuais",
  },

  "/oculos/armacoes": {
    component: () => <PlaceholderPage title="Armações" />,
    title: "Armações",
  },
  "/oculos/lentes": {
    component: () => <PlaceholderPage title="Lentes" />,
    title: "Lentes",
  },
  "/oculos/pedidos": {
    component: () => <PlaceholderPage title="Pedidos de Óculos" />,
    title: "Pedidos de Óculos",
  },

  "/lentes-contato": {
    component: () => <PlaceholderPage title="Lentes de Contato" />,
    title: "Lentes de Contato",
  },

  "/estoque/produtos": {
    component: () => <PlaceholderPage title="Produtos" />,
    title: "Produtos",
  },
  "/estoque/fornecedores": {
    component: () => <PlaceholderPage title="Fornecedores" />,
    title: "Fornecedores",
  },
  "/estoque/pedidos": {
    component: () => <PlaceholderPage title="Pedidos de Estoque" />,
    title: "Pedidos de Estoque",
  },

  "/categorias/armacoes": {
    component: () => <PlaceholderPage title="Categorias - Armações" />,
    title: "Categorias - Armações",
  },
  "/categorias/lentes": {
    component: () => <PlaceholderPage title="Categorias - Lentes" />,
    title: "Categorias - Lentes",
  },
  "/categorias/acessorios": {
    component: () => <PlaceholderPage title="Acessórios" />,
    title: "Acessórios",
  },

  "/financeiro/faturamento": {
    component: () => <PlaceholderPage title="Faturamento" />,
    title: "Faturamento",
  },
  "/financeiro/pagamentos": {
    component: () => <PlaceholderPage title="Pagamentos" />,
    title: "Pagamentos",
  },
  "/financeiro/convenios": {
    component: () => <PlaceholderPage title="Convênios" />,
    title: "Convênios",
  },

  "/relatorios/receitas": {
    component: () => <PlaceholderPage title="Relatório - Receitas" />,
    title: "Relatório - Receitas",
  },
  "/relatorios/despesas": {
    component: () => <PlaceholderPage title="Relatório - Despesas" />,
    title: "Relatório - Despesas",
  },
  "/relatorios/lucros": {
    component: () => <PlaceholderPage title="Relatório - Lucros" />,
    title: "Relatório - Lucros",
  },

  "/configuracoes/geral": {
    component: () => <PlaceholderPage title="Configurações Gerais" />,
    title: "Configurações Gerais",
  },
  "/configuracoes/usuarios": {
    component: () => <PlaceholderPage title="Usuários" />,
    title: "Usuários",
  },
  "/configuracoes/permissoes": {
    component: () => <PlaceholderPage title="Permissões" />,
    title: "Permissões",
  },

  "/sistema/backup": {
    component: () => <PlaceholderPage title="Backup" />,
    title: "Backup",
  },
  "/sistema/logs": {
    component: () => <PlaceholderPage title="Logs do Sistema" />,
    title: "Logs do Sistema",
  },
  "/sistema/manutencao": {
    component: () => <PlaceholderPage title="Manutenção" />,
    title: "Manutenção",
  },
};
