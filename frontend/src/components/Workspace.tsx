import React from "react";
import { useWorkspace } from "../contexts/WorkspaceContext";

const Workspace: React.FC = () => {
  const { windows, setActiveWindow, closeWindow } = useWorkspace();

  if (windows.length === 0) {
    return (
      <div className="h-full flex items-center justify-center bg-linear-to-br from-blue-50 to-indigo-100">
        <div className="text-center">
          <div className="w-24 h-24 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <svg
              className="w-12 h-12 text-blue-600"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"
              />
            </svg>
          </div>
          <h2 className="text-2xl font-bold text-gray-900 mb-2">
            Área de Trabalho
          </h2>
          <p className="text-gray-600 max-w-md">
            Selecione uma opção no menu lateral para abrir uma nova janela de
            trabalho.
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="h-full flex flex-col bg-gray-100">
      {/* Barra de abas */}
      <div className="bg-white border-b border-gray-200 flex items-center px-4 py-2 overflow-x-auto shrink-0">
        <div className="flex space-x-1">
          {windows.map((window) => (
            <div
              key={window.id}
              className={`flex items-center px-4 py-2 rounded-t-lg cursor-pointer transition-colors ${
                window.isActive
                  ? "bg-blue-600 text-white"
                  : "bg-gray-100 text-gray-700 hover:bg-gray-200"
              }`}
              onClick={() => setActiveWindow(window.id)}
            >
              <span className="text-sm font-medium truncate max-w-32">
                {window.title}
              </span>
              <button
                onClick={(e) => {
                  e.stopPropagation();
                  closeWindow(window.id);
                }}
                className={`ml-2 p-1 rounded-full hover:bg-opacity-20 hover:bg-gray-900 transition-colors ${
                  window.isActive ? "text-white" : "text-gray-500"
                }`}
              >
                <svg
                  className="w-4 h-4"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M6 18L18 6M6 6l12 12"
                  />
                </svg>
              </button>
            </div>
          ))}
        </div>
      </div>

      {/* Conteúdo da janela ativa */}
      <div className="flex-1 min-h-0 overflow-hidden">
        {windows.map((window) => (
          <div
            key={window.id}
            className={`h-full ${window.isActive ? "block" : "hidden"}`}
          >
            <div className="h-full overflow-y-auto bg-white p-6">
              {window.component}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Workspace;
