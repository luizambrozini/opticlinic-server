import React from "react";

const AgendaPage: React.FC = () => {
  return (
    <div className="p-6">
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900 mb-2">Agenda</h1>
        <p className="text-gray-600">Gerencie consultas e horários</p>
      </div>

      <div className="bg-white rounded-lg shadow border border-gray-200">
        <div className="p-6 border-b border-gray-200">
          <div className="flex justify-between items-center">
            <h2 className="text-lg font-semibold text-gray-900">
              Consultas de Hoje
            </h2>
            <button className="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition-colors">
              Nova Consulta
            </button>
          </div>
        </div>

        <div className="p-6">
          <div className="grid gap-4">
            {[
              {
                horario: "08:00",
                paciente: "Maria Silva Santos",
                tipo: "Consulta de Rotina",
                status: "agendado",
              },
              {
                horario: "09:30",
                paciente: "João Carlos Oliveira",
                tipo: "Exame de Vista",
                status: "em-andamento",
              },
              {
                horario: "11:00",
                paciente: "Ana Paula Costa",
                tipo: "Retorno",
                status: "concluido",
              },
              {
                horario: "14:00",
                paciente: "Carlos Eduardo Lima",
                tipo: "Primeira Consulta",
                status: "agendado",
              },
              {
                horario: "15:30",
                paciente: "Fernanda Rocha Silva",
                tipo: "Consulta de Rotina",
                status: "agendado",
              },
            ].map((consulta, index) => (
              <div
                key={index}
                className={`p-4 rounded-lg border-l-4 ${
                  consulta.status === "concluido"
                    ? "border-green-400 bg-green-50"
                    : consulta.status === "em-andamento"
                    ? "border-blue-400 bg-blue-50"
                    : "border-gray-400 bg-gray-50"
                }`}
              >
                <div className="flex justify-between items-start">
                  <div className="flex-1">
                    <div className="flex items-center space-x-4">
                      <span className="font-bold text-lg text-gray-900">
                        {consulta.horario}
                      </span>
                      <div>
                        <p className="font-medium text-gray-900">
                          {consulta.paciente}
                        </p>
                        <p className="text-sm text-gray-600">{consulta.tipo}</p>
                      </div>
                    </div>
                  </div>
                  <div className="flex items-center space-x-2">
                    <span
                      className={`px-2 py-1 rounded-full text-xs font-medium ${
                        consulta.status === "concluido"
                          ? "bg-green-100 text-green-800"
                          : consulta.status === "em-andamento"
                          ? "bg-blue-100 text-blue-800"
                          : "bg-gray-100 text-gray-800"
                      }`}
                    >
                      {consulta.status === "concluido"
                        ? "Concluído"
                        : consulta.status === "em-andamento"
                        ? "Em Andamento"
                        : "Agendado"}
                    </span>
                    <button className="text-gray-400 hover:text-gray-600">
                      <svg
                        className="w-5 h-5"
                        fill="currentColor"
                        viewBox="0 0 20 20"
                      >
                        <path d="M10 6a2 2 0 110-4 2 2 0 010 4zM10 12a2 2 0 110-4 2 2 0 010 4zM10 18a2 2 0 110-4 2 2 0 010 4z" />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AgendaPage;
