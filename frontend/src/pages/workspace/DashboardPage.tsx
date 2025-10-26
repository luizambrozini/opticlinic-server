import React from "react";

const DashboardPage: React.FC = () => {
  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900 mb-2">Dashboard</h1>
        <p className="text-gray-600">Visão geral da clínica</p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
        {[
          { title: "Pacientes", value: "1,234", change: "+5%", color: "blue" },
          {
            title: "Consultas Hoje",
            value: "23",
            change: "+12%",
            color: "green",
          },
          {
            title: "Receita Mensal",
            value: "R$ 45.230",
            change: "+8%",
            color: "purple",
          },
          {
            title: "Exames Pendentes",
            value: "7",
            change: "-2%",
            color: "orange",
          },
        ].map((card, index) => (
          <div
            key={index}
            className="bg-white rounded-lg shadow border border-gray-200 p-6"
          >
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-gray-600">
                  {card.title}
                </p>
                <p className="text-2xl font-bold text-gray-900">{card.value}</p>
              </div>
              <div
                className={`text-sm font-medium ${
                  card.change.startsWith("+")
                    ? "text-green-600"
                    : "text-red-600"
                }`}
              >
                {card.change}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white rounded-lg shadow border border-gray-200">
          <div className="p-6 border-b border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900">
              Próximas Consultas
            </h3>
          </div>
          <div className="p-6">
            <div className="space-y-4">
              {[
                {
                  paciente: "Maria Silva",
                  horario: "09:00",
                  tipo: "Consulta de Rotina",
                },
                {
                  paciente: "João Santos",
                  horario: "10:30",
                  tipo: "Exame de Vista",
                },
                { paciente: "Ana Costa", horario: "14:00", tipo: "Retorno" },
              ].map((consulta, index) => (
                <div
                  key={index}
                  className="flex justify-between items-center py-2 border-b border-gray-100 last:border-b-0"
                >
                  <div>
                    <p className="font-medium text-gray-900">
                      {consulta.paciente}
                    </p>
                    <p className="text-sm text-gray-500">{consulta.tipo}</p>
                  </div>
                  <div className="text-sm font-medium text-blue-600">
                    {consulta.horario}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow border border-gray-200">
          <div className="p-6 border-b border-gray-200">
            <h3 className="text-lg font-semibold text-gray-900">
              Atividades Recentes
            </h3>
          </div>
          <div className="p-6">
            <div className="space-y-4">
              {[
                {
                  acao: "Nova consulta agendada",
                  paciente: "Carlos Lima",
                  tempo: "2 min atrás",
                },
                {
                  acao: "Receita emitida",
                  paciente: "Fernanda Rocha",
                  tempo: "15 min atrás",
                },
                {
                  acao: "Paciente cadastrado",
                  paciente: "Roberto Silva",
                  tempo: "1 hora atrás",
                },
              ].map((atividade, index) => (
                <div
                  key={index}
                  className="flex justify-between items-start py-2 border-b border-gray-100 last:border-b-0"
                >
                  <div>
                    <p className="font-medium text-gray-900">
                      {atividade.acao}
                    </p>
                    <p className="text-sm text-gray-500">
                      {atividade.paciente}
                    </p>
                  </div>
                  <div className="text-xs text-gray-400">{atividade.tempo}</div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
